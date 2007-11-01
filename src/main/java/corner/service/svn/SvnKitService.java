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

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

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
import org.tmatesoft.svn.core.wc.SVNWCUtil;


/**
 * Svn服务包
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision$
 * @since 2.5
 */
public class SvnKitService<T extends ISvnModel>{
	
	private static final Log logger = LogFactory.getLog(SvnKitService.class);
	
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
	public void saveOrUpdateSvn(T entiy, List svnList){
		
		SVNRepository repository = setupSvnRepository();
		
		int addNum = getAddSvnPathPlace(repository,entiy);
		
		debugInfo(addNum);
		
		ISVNEditor editor = null;
		
		try {
			editor = repository.getCommitEditor("测试修改时增加", null); //增加时的一些话
		} catch (SVNException e) {
			e.printStackTrace();
		}
		
		byte[] contents = "第一次增加".getBytes();	//文件内容
		String fileName = "test" + (new java.util.Date()).getTime() + ".txt";
		
        
        SVNCommitInfo commitInfo = null;
        
        commitInfo = addDir(editor, entiy,addNum, fileName, contents);
        debugInfo("svn增加成功: " + commitInfo);
		
//		try {
//			listEntries(repository, "");
//		} catch (SVNException e) {
//			e.printStackTrace();
//		}
	}
	
	private SVNCommitInfo addDir(ISVNEditor editor, T entiy, int addNum, String fileName, byte[] contents) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 获得需要增加的路径位置
	 * @param repository 
	 */
	private int getAddSvnPathPlace(SVNRepository repository, T entiy) {
		String noncePath = "";
		
		List<String> paths = getEntityPaths(entiy);
		
		Iterator it = paths.iterator();
		
		String path = null;
		
		int i=0;
		
		try {
			for(;i<paths.size();i++){
				path = (String) it.next();
				noncePath = noncePath + path + "/";
				debugInfo("URL操作: " + url + noncePath);
				
				if (repository.checkPath(noncePath, -1) == SVNNodeKind.NONE) {
					return i;
		        }
			}
		} catch (SVNException e) {
			e.printStackTrace();
		}
		
		return i;
	}
	
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