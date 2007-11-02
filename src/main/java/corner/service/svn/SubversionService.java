//==============================================================================
// file :       $Id$
// project:     corner
//
// last change: date:       $Date$
//              by:         $Author$
//              revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.service.svn;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.tmatesoft.svn.core.SVNCommitInfo;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.io.ISVNEditor;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.io.diff.SVNDeltaGenerator;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

import corner.service.EntityService;
import corner.util.BeanUtils;


/**
 * Subversion服务包，提供了和subversion交互操作.
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.5
 */
public class SubversionService <T extends IVersionable> implements IVersionService<T>{
	
	private static final Log logger = LogFactory.getLog(SubversionService.class);
	
	private static final String ENTITY_FILIE_SUFFIX=".txt";
	
	/**
	 * svn信息
	 */
	private String url;
	private String username;
	private String password;
	private SVNRepository repository;
	
	public SubversionService(){
		repository = setupSvnRepository();
	}
	/**
	 * @throws SVNException 
	 * @see corner.service.svn.IVersionService#checkin(corner.service.svn.IVersionable)
	 */
	public  long checkin(T versionableObject) {
		//得到文件路径
		
		String entityPath = getEntityPath(versionableObject);
		String filePath = entityPath +"/" + versionableObject.getId()+ENTITY_FILIE_SUFFIX;
		//得到JSON字符串
		String json =  XStreamDelegate.toJSON(versionableObject);
		
		//目录遍历器
		DirectoryFacade facade = null;
		//subversion 编辑器.
		ISVNEditor editor = null;
		
		try {
			facade = new DirectoryFacade(repository,entityPath);
		
			FileSender sender=new FileSender(repository,json,filePath);
			editor = repository.getCommitEditor(versionableObject.getComment(), null); //增加时的一些话
			{
				//打开根目录.
				editor.openRoot(-1);
				//遍历目录
				facade.openPath(editor);
				
				//发送文件.
				sender.sendFile(editor);
				
				//关闭遍历的目录.
				facade.closePath(editor);
				
				//关闭根目录.
				editor.closeDir();
				//返回新的版本号
		        return editor.closeEdit().getNewRevision();
			}
		} catch (SVNException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 
	 * @see corner.service.svn.IVersionService#fetchObjectAsJson(corner.service.svn.IVersionable, long)
	 */
	public   String fetchObjectAsJson(T versionableObject, long revision) {
		String entityPath = getEntityPath(versionableObject);
		String filePath = entityPath +"/" + versionableObject.getId()+ENTITY_FILIE_SUFFIX;
		Map fileProperties = new HashMap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
    		
			SVNNodeKind nodeKind = repository.checkPath(filePath , revision);
	        
	        if (nodeKind == SVNNodeKind.NONE || nodeKind == SVNNodeKind.DIR) {
	        	throw new RuntimeException("未发现文件");
	        }
	        
	        repository.getFile(filePath, revision, fileProperties, baos);
		} catch (SVNException e) {
			throw new RuntimeException(e);
		}
        
        try {
			return new String(baos.toByteArray(),FileSender.FILE_ENCODING);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 
	 * @see corner.service.svn.IVersionService#fetchVersionInfo(corner.service.svn.IVersionable)
	 */
	public List<VersionResult> fetchVersionInfo(T versionableObject) {
		String entityPath = getEntityPath(versionableObject);
		String filePath = entityPath +"/" + versionableObject.getId()+ENTITY_FILIE_SUFFIX;
		
		try {
			Collection logEntries = repository.log(new String[] {filePath}, null,
			        0, -1, true, true);
			List<VersionResult> list = new ArrayList<VersionResult>();
			for(Object obj:logEntries){
				SVNLogEntry logEntry = (SVNLogEntry) obj;
				VersionResult result = new VersionResult();
				result.setAuthor(logEntry.getAuthor());
				result.setComment(logEntry.getMessage());
				result.setCreateDate(logEntry.getDate());
				result.setVersionNum(logEntry.getRevision());
				list.add(result);
			}
			return list;
		} catch (SVNException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 通过给定的类名来产生一个文件的路径名称.
	 * @param clazz 类名.
	 * @return 路径名称.
	 */
	private  String getEntityPath(T versionableObject){
		Class entityClass = EntityService.getEntityClass(versionableObject);
		String entityPath = entityClass.getName();
		entityPath = entityPath.replaceAll("\\.", DirectoryFacade.SVN_PATH_SEPERATOR);
		return entityPath;
	}

	
	
	/**
	 * 初始化svn
	 */
	private SVNRepository setupSvnRepository() {
		setupLibrary();
		
		SVNRepository repository = null;
		
        try {
        	/*
        	 * 获得svnurl
        	 */
            repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(url));
        } catch (SVNException e) {
        	throw new RuntimeException(e);
        }
        
        /*
         * 设置用户和密码
         */
        ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(username, password);
        repository.setAuthenticationManager(authManager);
       
        return repository;
		
	}

	/**
     * 初始化Library
     */
    private static void setupLibrary() {
        /*
         * 使用 http:// 和 https://
         */
        DAVRepositoryFactory.setup();
        /*
         * 使用 svn:// 和 svn+xxx://
         */
        SVNRepositoryFactoryImpl.setup();
        
        /*
         * 使用 file:///
         */
        FSRepositoryFactory.setup();
    }



	/**
	 * @param password The password to set.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @param url The url to set.
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @param username The username to set.
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	
}