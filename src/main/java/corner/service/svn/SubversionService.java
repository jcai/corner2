// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2007-10-31
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package corner.service.svn;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.tmatesoft.svn.core.ISVNLogEntryHandler;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNRevisionProperty;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.io.ISVNEditor;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import corner.service.EntityService;
import corner.util.BeanUtils;


/**
 * Subversion服务包，提供了和subversion交互操作.
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.5
 */
public class SubversionService  implements IVersionService,InitializingBean{
	
	/**
	 * 供repository回掉使用.
	 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
	 * @version $Revision$
	 * @since 2.5
	 */
	public interface ISvnCallback {
		/**
		 * 在资源库中的进行一些其他操作.
		 * @param repository 资源库.
		 * @return 返回对象.
		 * @throws SVNException 加入发生资源库操作.
		 */
		public Object doInRepository(SVNRepository repository) throws SVNException;
		
	}
	
	private static final Log logger = LogFactory.getLog(SubversionService.class);
	
	private static final String ENTITY_FILIE_SUFFIX=".txt";
	
	/**
	 * svn信息
	 */
	private String url;
	private String username;
	private String password;
	
	

	/**
	 * @throws SVNException 
	 * @see corner.service.svn.IVersionService#checkin(corner.service.svn.IVersionable)
	 */
	public  long checkin(final IVersionable versionableObject) {
		int flag=0;
		if(!versionableObject.isSvnCommit()){ //不提交
			flag= -1;
		}
		//得到JSON字符串
		final String json =  XStreamDelegate.toJSON(versionableObject);
		
		if(isSameVersionableObject(versionableObject,json)){ //相同的实体
			flag= -2;
		}
		if(flag<0){
			return flag;
		}
		
		//得到文件路径
		final StringBuffer groupPath = getGroupPath(versionableObject);
		 
		final String filePath = groupPath.toString()+"/"+getFilePath(versionableObject)+ENTITY_FILIE_SUFFIX;
		logger.debug(filePath);
		
		
		return (Long) this.execute(new ISvnCallback(){

			public Object doInRepository(SVNRepository repository) throws SVNException {
//				目录遍历器
				DirectoryFacade facade = null;
				//subversion 编辑器.
				ISVNEditor editor = null;
				facade = new DirectoryFacade(repository,groupPath.toString());
				
				FileSender sender=new FileSender(repository,json,filePath);
				
				/*
				 * 此处增加默认的空注释，
				 * 当且仅当ssh连接的时候,comment为空的时候抛出 svn: 210002: Network connection closed unexpectedly
				 * 其他方式的连接无此问题，找了一天，才知道这个原因. ~_~ // Jun Tsai
				 */
				String comment = versionableObject.getSvnLog();
				if(comment == null){
					comment = "";
				}
				
				editor = repository.getCommitEditor(comment, null); 
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
			        
			        long SVNRevision=editor.closeEdit().getNewRevision();
			        /**
			         * 设定提交人信息,此项操作需要两个条件:
			         *    1: 连接版本库必须是 "file" or "svn+ssh" 两种协议 
			         *    2: 版本控制库实现了pre-revprop-change hook script.同时注意修改此程序中的svn:log,为svn:author
			         *  see http://www.subversion.org.cn/svnbook/1.2/svn.reposadmin.create.html#svn.reposadmin.create.hooks
			         *  http://www.nabble.com/Doing-a-commit-with-svn%3Aauthor-different-from-authenticated-user--t4335217.html#a12366459
			         */
			        
			        if(versionableObject.getSvnAuthor()!=null){
			        	repository.setRevisionPropertyValue(SVNRevision,SVNRevisionProperty.AUTHOR, versionableObject.getSvnAuthor());
			        }
			        return SVNRevision;
				
				}
			}
		});
	}
	
	private Object execute(ISvnCallback callback){
		SVNRepository repository = null;
		try {
			repository=createSvnRepository();
			return callback.doInRepository(repository);
		}catch (SVNException e) {
			throw new RuntimeException(e);
		}finally{
			try {
				if(repository!=null){
					repository.closeSession();
				}
			} catch (SVNException e) {
				logger.warn(e.getMessage());
			}
		}
	}
	
	/**
	 * 判断是否相同
	 * @param versionableObject 需要比对的对象
	 * @param newVer 新对象的json字符串.
	 * @return 如果有差异返回true，如果没有差异返回false;
	 */
	public boolean isSameVersionableObject(IVersionable versionableObject,String newVer){
		
		
		String oldVer = null;
		
		try{
			oldVer = fetchObjectAsJson(versionableObject, -1);
		}catch(RuntimeException e){
			oldVer = "";
		}
		
		return newVer.equals(oldVer);
	}
	
	/**
	 * 
	 * @see corner.service.svn.IVersionService#fetchObjectAsJson(corner.service.svn.IVersionable, long)
	 */
	public   String fetchObjectAsJson(IVersionable versionableObject, final long revision) {
		
		final StringBuffer groupPath = getGroupPath(versionableObject);
		 
		final String filePath = groupPath.toString()+"/"+getFilePath(versionableObject)+ENTITY_FILIE_SUFFIX;
		
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        this.execute(new ISvnCallback(){

			public Object doInRepository(SVNRepository repository) throws SVNException {
				SVNNodeKind nodeKind = repository.checkPath(filePath , revision);
		        
		        if (nodeKind == SVNNodeKind.NONE || nodeKind == SVNNodeKind.DIR) {
		        	throw new RuntimeException("未发现文件");
		        }
		        
		        repository.getFile(filePath, revision, new HashMap(), baos);
		        
		        return null;
			}});
        
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
	@SuppressWarnings("unchecked")
	public List<VersionResult> fetchVersionInfo(final IVersionable versionableObject) {
		final StringBuffer groupPath = getGroupPath(versionableObject);
		
		final String filePath = groupPath.toString()+"/"+getFilePath(versionableObject)+ENTITY_FILIE_SUFFIX;
		
		return (List<VersionResult>) this.execute(new ISvnCallback(){

			public Object doInRepository(SVNRepository repository) throws SVNException {
				final ArrayList<VersionResult> list = new ArrayList<VersionResult>();
				String svnPath = filePath;
				
				//要展示当前实体的svn地址所以注释掉了
//				if(isGroupClass(versionableObject)){
//					svnPath = groupPath.toString();
//				}
				
				repository.log(new String[] {svnPath}, 
				        0, -1, true, true,new ISVNLogEntryHandler(){
							int seq = 1;
							
							public void handleLogEntry(SVNLogEntry logEntry) throws SVNException {
								VersionResult result = new VersionResult();
								result.setAuthor(logEntry.getAuthor());
								result.setComment(logEntry.getMessage());
								result.setCreateDate(logEntry.getDate());
								result.setVersionNum(logEntry.getRevision());
								result.setVersionSeq(seq++);
								list.add(result);			
							}});
				Collections.sort(list);
				return list;

			}});
	}
	/**
	 * 
	 * @see corner.service.svn.IVersionService#delete(corner.service.svn.IVersionable)
	 */
	public void delete(final IVersionable versionableObject) {
		
		final StringBuffer groupPath = getGroupPath(versionableObject);
		 
		final String filePath = groupPath.toString()+"/"+getFilePath(versionableObject)+ENTITY_FILIE_SUFFIX;
		logger.debug(filePath);
		 this.execute(new ISvnCallback(){

			public Object doInRepository(SVNRepository repository) throws SVNException {
				if(repository.checkPath(filePath,-1) == SVNNodeKind.NONE){
					return null;
				}
				String comment = versionableObject.getSvnLog();
				if(comment == null){
					comment = "";
				}
				ISVNEditor editor = repository.getCommitEditor(comment, null); //增加时的一些话
				//open root dir
				editor.openRoot(-1);
				
				if(isGroupClass(versionableObject)){
					editor.deleteEntry(groupPath.toString(), -1);
				}else{
					editor.deleteEntry(filePath, -1);
				}
				editor.closeDir();
				
				long SVNRevision = editor.closeEdit().getNewRevision();
				
				//修改作者信息
				 if(versionableObject.getSvnAuthor()!=null){
			        	repository.setRevisionPropertyValue(SVNRevision,SVNRevisionProperty.AUTHOR, versionableObject.getSvnAuthor());
			       }
				return null;
			}});
	}
	
	/**
	 * 通过给定的类名来产生一个文件的路径名称.
	 * @param clazz 类名.
	 * @return 路径名称.
	 */
	private  StringBuffer getGroupPath(IVersionable versionableObject){
		Class<?> entityClass = EntityService.getEntityClass(versionableObject);
		VersionGroup versionGroup=entityClass.getAnnotation(VersionGroup.class);
		StringBuffer entityPath=new StringBuffer();
		
		if(versionGroup!=null){
			entityPath.append(versionGroup.groupClass().getName().replaceAll("\\.",DirectoryFacade.SVN_PATH_SEPERATOR));
			String groupId=(String) BeanUtils.getProperty(versionableObject,versionGroup.groupIdExp());
			entityPath.append("/");
			entityPath.append(groupId);
		}else{
			entityPath.append(entityClass.getName().replaceAll("\\.",DirectoryFacade.SVN_PATH_SEPERATOR));
		}
		
		return entityPath;
	}
	private boolean isGroupClass(IVersionable versionableObject){
		Class<?> entityClass = EntityService.getEntityClass(versionableObject);
		VersionGroup versionGroup=entityClass.getAnnotation(VersionGroup.class);
		return versionGroup!=null&&entityClass.equals(versionGroup.groupClass());
	}
	private String getFilePath(IVersionable versionableObject){
		Class<?> entityClass = EntityService.getEntityClass(versionableObject);
		return entityClass.getSimpleName()+"_"+versionableObject.getId();
	}

	
	
	/**
	 * 初始化svn
	 */
	SVNRepository createSvnRepository() {
        SVNRepository repository;
		try {
        	/*
        	 * 获得svnurl
        	 */
            repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(url));
            /*
             * 设置用户和密码
             */
            ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(username, password);
            repository.setAuthenticationManager(authManager);
            repository.testConnection(); 
        } catch (SVNException e) {
        	throw new RuntimeException(e);
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
	 * @param password IVersionablehe password to set.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @param url IVersionablehe url to set.
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @param username IVersionablehe username to set.
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	public void afterPropertiesSet() throws Exception {
		setupLibrary();
		
	}
	
}