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
 * Svn服务包
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision$
 * @since 2.5
 */
public class SvnKitService<T extends ISvnModel>{
	
	private static final Log logger = LogFactory.getLog(SvnKitService.class);
	private static final String FILE_NAME = "id";
	
	/**
	 * svn信息
	 */
	private String url;
	private String username;
	private String password;
	
	/**
	 * 增加和修改svn
	 * @throws SVNException 
	 */
	public void saveOrUpdateSvn(T entity,String massage, List<String> svnPropertyList){
		
		/*
		 * 使用XStream获得json串
		 */
		XStream xstream = new XStream(new JettisonMappedXmlDriver());
		
		Class entityClass = EntityService.getEntityClass(entity);
        xstream.alias(entityClass.getSimpleName(), entityClass);
        
        String jsonSvn = xstream.toXML(entity);
		debugInfo("json new ---- " + jsonSvn);
		
		SVNRepository repository = setupSvnRepository();
		
		//文件内容
		byte[] contents = jsonSvn.getBytes();
		
		//获得文件名
		String fileName = BeanUtils.getProperty(entity, FILE_NAME) + ".txt";
		String entityPath = entityClass.getName();
		entityPath = entityPath.replaceAll("\\.", DirectoryFacade.SVN_PATH_SEPERATOR);
		
		String filePath = entityPath +"/" + fileName;	//文件路径;
		
		DirectoryFacade facade = null;
		
		ISVNEditor editor = null;
		SVNCommitInfo commitInfo = null;
		
		try {
			byte[] oldContents = getOldContent(repository,filePath, -1);
			facade = new DirectoryFacade(repository,entityPath);
			editor = repository.getCommitEditor(massage, null); //增加时的一些话
			commitInfo = addAndModifyFile(editor, entity,facade,filePath, oldContents,contents);
		} catch (SVNException e) {
			e.printStackTrace();
		}
		
        debugInfo("svn增加成功: " + commitInfo);
	}
	
	/**
	 * 获得最后一个版本信息
	 * @throws SVNException 
	 */
	private byte[] getOldContent(SVNRepository repository, String filePath,long revision) throws SVNException {
		debugInfo("filePath " + filePath);
		
		Map fileProperties = new HashMap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		SVNNodeKind nodeKind = repository.checkPath(filePath , revision);
        
        if (nodeKind == SVNNodeKind.NONE) {
            debugInfo("没有找到Url: '" + url + "'");
            return null;
        } else if (nodeKind == SVNNodeKind.DIR) {
        	debugInfo("Url地址是一个文件夹 '" + url
                    + "'");
        	return null;
        }
        
        repository.getFile(filePath, revision, fileProperties, baos);
        
        debugInfo("json old ---- " + baos);
        
		return baos.toByteArray();
	}
	
	/**
	 * 获得相应的版本
	 */
	public String getEntityRevision(T entiy,long revision){
		//获得文件名
		String fileName = BeanUtils.getProperty(entiy, FILE_NAME) + ".txt";
		
		Class entityClass = EntityService.getEntityClass(entiy);
		
		String entityPath = entityClass.getName();
		entityPath = entityPath.replaceAll("\\.", DirectoryFacade.SVN_PATH_SEPERATOR);
		
		String filePath = entityPath +"/" + fileName;	//文件路径
		
		SVNRepository repository = setupSvnRepository();
		
		ByteArrayInputStream bis = null;
		try {
			bis = new ByteArrayInputStream(getOldContent(repository, filePath, revision));
		} catch (SVNException e) {
			e.printStackTrace();
		}
		
		String json = bis.toString();
		
		debugInfo(json);
		
//		XStream xstream = new XStream(new JettisonMappedXmlDriver());
//        xstream.alias(entiy.getClass().getSimpleName(), entiy.getClass());
//		T entity = (T)xstream.fromXML(json);
		
		return json;
	}

	/**
	 * 增加和修改文件
	 */
	private SVNCommitInfo addAndModifyFile(ISVNEditor editor, T entiy, DirectoryFacade facade, String filePath, byte[] oldContents, byte[] contents) throws SVNException {
		
		editor.openRoot(-1);
		
		facade.openPath(editor);
		
		if(oldContents == null || oldContents.length ==0){
			editor.addFile(filePath, null, -1);
		}else{
			editor.openFile(filePath, -1);
		}
		editor.applyTextDelta(filePath, null);
		
		SVNDeltaGenerator deltaGenerator = new SVNDeltaGenerator();
		
		String checksum = null;
		
		if(oldContents == null || oldContents.length ==0){
			checksum = deltaGenerator.sendDelta(filePath, new ByteArrayInputStream(contents), editor, true);
		}else{
			checksum = deltaGenerator.sendDelta(filePath, new ByteArrayInputStream(oldContents), 0, new ByteArrayInputStream(contents), editor, true);
		}
        
        editor.closeFile(filePath, checksum);
        
        facade.closePath(editor);
		
        return editor.closeEdit();
	}
	
//	private String showDifferences(){
//		
//		String returnVal = null;
//		
//		SVNDiffClient theDiff = ourClientManager.getDiffClient();
//		ByteArrayOutputStream diffStream =
//			new ByteArrayOutputStream();	
//		theDiff.doDiff(svnUrl, SVNRevision.create(-1), url, 
//			SVNRevision.create(-2), false, false, diffStream);
//		returnVal = new String(diffStream.toByteArray());	
//	}

	
	/**
	 * 活动路径数组
	 */
	private List<String> getEntityPaths(T entiy) {
		String entityPath = entiy.getClass().getName();
		return Arrays.asList(entityPath.split("\\."));
	}

	/**
	 * @param string
	 */
	private void debugInfo(Object info) {
		if (logger.isDebugEnabled()) {
			logger.debug(info);
		}
	}

	/**
	 * 打印输出svntree
	 * @param repository
	 * @param path
	 * @throws SVNException
	 */
	public void listEntries(SVNRepository repository, String path)
			throws SVNException {
		
		Collection entries = repository.getDir(path, -1, null,
				(Collection) null);
		Iterator iterator = entries.iterator();
		while (iterator.hasNext()) {
			SVNDirEntry entry = (SVNDirEntry) iterator.next();
			System.out.println("/" + (path.equals("") ? "" : path + "/")
					+ entry.getName() + " (author: '" + entry.getAuthor()
					+ "'; revision: " + entry.getRevision() + "; date: "
					+ entry.getDate() + ")");
			/*
			 * 如果entry是一个目录
			 */
			if (entry.getKind() == SVNNodeKind.DIR) {
				listEntries(repository, (path.equals("")) ? entry.getName()
						: path + "/" + entry.getName());
			}
		}
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
        } catch (SVNException svne) {
        	svne.printStackTrace();
        }
        
        /*
         * 设置用户和密码
         */
        ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(username, password);
        repository.setAuthenticationManager(authManager);
        
        try {
            /*
             * 检查版本是最新时的目录
             */
            SVNNodeKind nodeKind = repository.checkPath("", -1);
            if (nodeKind == SVNNodeKind.NONE) {
                System.err.println("设置的url路径错误 '" + url + "'。");
                return null;
            } else if (nodeKind == SVNNodeKind.FILE) {
                System.err.println("设置的url路径 '" + url + "' 是一个文件，请设置一个目录。");
                return null;
            }
            
            debugInfo("URL根路径: " + repository.getRepositoryRoot(true));
            debugInfo("UUID: " + repository.getRepositoryUUID(true));
            
        } catch (SVNException svne) {
            System.err.println("svn地址错误: "
                    + svne.getMessage());
        }
        
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
	 * 获得完整svn路径
	 */
	private String getFullSvnPath(String path) {
		
		return null;
	}

	public void deleteSvn(T o){
		
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