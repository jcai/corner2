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

import org.testng.Assert;
import org.testng.annotations.Test;
import org.tmatesoft.svn.core.SVNCommitInfo;
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

/**
 * svnkit增删改测试
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision$
 * @since 2.5
 */
public class CommitTest extends Assert{
	
	@Test
	public void SaveOrUpdateFile() throws SVNException{
		setupLibrary(); //初始化
		SVNURL url = SVNURL.parseURIEncoded("http://dev.bjmaxinfo.com/svn/svn-test/");
		
		String userName = "jetty";
        String userPassword = "jetty";
        
        String svnPath = "svnKitTest";
        String fileName = "test" + (new java.util.Date()).getTime() + ".txt";
        byte[] contents = "第一次增加".getBytes();	//文件内容
        byte[] modifiedContents = "修改的内容".getBytes();
        
        System.out.println("fileName :" + fileName);
        
        
        SVNRepository repository = SVNRepositoryFactory.create(url);//连接
		
        ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(userName, userPassword);	//密码
        repository.setAuthenticationManager(authManager);	//连接加入秘密
        
        SVNNodeKind nodeKind = repository.checkPath(svnPath, -1);	//最后版本和位置
        
        long latestRevision = repository.getLatestRevision();	//获得最后一个版本号
        System.out.println("最后一个版本号: " + latestRevision);
        
        ISVNEditor editor = repository.getCommitEditor("测试增加", null);	//增加时的一些话
        
        SVNCommitInfo commitInfo = null;
        
        commitInfo = addDir(editor, svnPath, fileName, contents,nodeKind);
        System.out.println("The directory was added: " + commitInfo);
        
        editor = repository.getCommitEditor("改变"+fileName, null);
        commitInfo = modifyFile(editor, svnPath, svnPath +"/" + fileName, contents, modifiedContents);
        System.out.println("The file was changed: " + commitInfo);
	}
	
	@Test
	public void DelectFile() throws SVNException{
		setupLibrary(); //初始化
		SVNURL url = SVNURL.parseURIEncoded("http://dev.bjmaxinfo.com/svn/svn-test/");
		
		String userName = "jetty";
        String userPassword = "jetty";
        
        String svnPath = "svnKitTest";
        String fileName = "test" + (new java.util.Date()).getTime() + ".txt";
        byte[] contents = "第一次增加".getBytes();	//文件内容
        
        System.out.println("fileName :" + fileName);
        
        SVNRepository repository = SVNRepositoryFactory.create(url);//连接
		
        ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(userName, userPassword);	//密码
        repository.setAuthenticationManager(authManager);	//连接加入秘密
        
        SVNNodeKind nodeKind = repository.checkPath(svnPath, -1);	//最后版本和位置
        
        long latestRevision = repository.getLatestRevision();	//获得最后一个版本号
        System.out.println("最后一个版本号: " + latestRevision);
        
        ISVNEditor editor = repository.getCommitEditor("测试增加", null);	//增加时的一些话
        
        SVNCommitInfo commitInfo = null;
        
        commitInfo = addDir(editor, svnPath, fileName, contents,nodeKind);
        System.out.println("The directory was added: " + commitInfo);
        
        editor = repository.getCommitEditor("删除" + fileName, null);
        commitInfo = deleteFileOrDir(editor, svnPath +"/" + fileName);
        System.out.println("The directory was deleted: " + commitInfo);
	}
	
	
	/*
     * This method performs committing file modifications.
     */
    private static SVNCommitInfo modifyFile(ISVNEditor editor, String dirPath,
            String filePath, byte[] oldData, byte[] newData) throws SVNException {
        /*
         * Always called first. Opens the current root directory. It  means  all
         * modifications will be applied to this directory until  a  next  entry
         * (located inside the root) is opened/added.
         * 
         * -1 - revision is HEAD
         */
        editor.openRoot(-1);
        /*
         * Opens a next subdirectory (in this example program it's the directory
         * added  in  the  last  commit).  Since this moment all changes will be
         * applied to this directory.
         * 
         * dirPath is relative to the root directory.
         * -1 - revision is HEAD
         */
        editor.openDir(dirPath, -1);
        /*
         * Opens the file added in the previous commit.
         * 
         * filePath is also defined as a relative path to the root directory.
         */
        editor.openFile(filePath, -1);
        
        /*
         * The next steps are directed to applying and writing the file delta.
         */
        editor.applyTextDelta(filePath, null);
        
        /*
         * Use delta generator utility class to generate and send delta
         * 
         * Note that you may use only 'target' data to generate delta when there is no 
         * access to the 'base' (previous) version of the file. However, here we've got 'base' 
         * data, what in case of larger files results in smaller network overhead.
         * 
         * SVNDeltaGenerator will call editor.textDeltaChunk(...) method for each generated 
         * "diff window" and then editor.textDeltaEnd(...) in the end of delta transmission.  
         * Number of diff windows depends on the file size. 
         *  
         */
        SVNDeltaGenerator deltaGenerator = new SVNDeltaGenerator();
        String checksum = deltaGenerator.sendDelta(filePath, new ByteArrayInputStream(oldData), 0, new ByteArrayInputStream(newData), editor, true);

        /*
         * Closes the file.
         */
        editor.closeFile(filePath, checksum);

        /*
         * Closes the directory.
         */
        editor.closeDir();

        /*
         * Closes the root directory.
         */
        editor.closeDir();

        /*
         * This is the final point in all editor handling. Only now all that new
         * information previously described with the editor's methods is sent to
         * the server for committing. As a result the server sends the new
         * commit information.
         */
        return editor.closeEdit();
    }
	
	
	/**
	 * 删除文件或文件夹
	 */
	private SVNCommitInfo deleteFileOrDir(ISVNEditor editor, String dirPath) throws SVNException {
		editor.openRoot(-1);
		
		editor.deleteEntry(dirPath, -1);
        editor.closeDir();
		
		return editor.closeEdit();
	}

	/*
     * This method performs commiting an addition of a  directory  containing  a
     * file.
     */
    private static SVNCommitInfo addDir(ISVNEditor editor, String dirPath,
            String filePath, byte[] data,SVNNodeKind nodeKind) throws SVNException {
        /*
         * Always called first. Opens the current root directory. It  means  all
         * modifications will be applied to this directory until  a  next  entry
         * (located inside the root) is opened/added.
         * 
         * -1 - revision is HEAD (actually, for a comit  editor  this number  is 
         * irrelevant)
         */
        editor.openRoot(-1);
        /*
         * Adds a new directory (in this  case - to the  root  directory  for 
         * which the SVNRepository was  created). 
         * Since this moment all changes will be applied to this new  directory.
         * 
         * dirPath is relative to the root directory.
         * 
         * copyFromPath (the 2nd parameter) is set to null and  copyFromRevision
         * (the 3rd) parameter is set to  -1  since  the  directory is not added 
         * with history (is not copied, in other words).
         */
        
        if (nodeKind == SVNNodeKind.NONE) {
        	editor.addDir(dirPath,null ,-1);
        } else if (nodeKind == SVNNodeKind.DIR) {
        	editor.openDir(dirPath, -1);
        }
        
        /*
         * Adds a new file to the just added  directory. The  file  path is also 
         * defined as relative to the root directory.
         *
         * copyFromPath (the 2nd parameter) is set to null and  copyFromRevision
         * (the 3rd parameter) is set to -1 since  the file is  not  added  with 
         * history.
         */
        editor.addFile(filePath, null, -1);
        /*
         * The next steps are directed to applying delta to the  file  (that  is 
         * the full contents of the file in this case).
         */
        editor.applyTextDelta(filePath, null);
        
        /*
         * Use delta generator utility class to generate and send delta
         * 
         * Note that you may use only 'target' data to generate delta when there is no 
         * access to the 'base' (previous) version of the file. However, using 'base' 
         * data will result in smaller network overhead.
         * 
         * SVNDeltaGenerator will call editor.textDeltaChunk(...) method for each generated 
         * "diff window" and then editor.textDeltaEnd(...) in the end of delta transmission.  
         * Number of diff windows depends on the file size. 
         *  
         */
        SVNDeltaGenerator deltaGenerator = new SVNDeltaGenerator();
        String checksum = deltaGenerator.sendDelta(filePath, new ByteArrayInputStream(data), editor, true);

        /*
         * Closes the new added file.
         */
        editor.closeFile(filePath, checksum);
        /*
         * Closes the new added directory.
         */
        editor.closeDir();
        /*
         * Closes the root directory.
         */
        editor.closeDir();
        /*
         * This is the final point in all editor handling. Only now all that new
         * information previously described with the editor's methods is sent to
         * the server for committing. As a result the server sends the new
         * commit information.
         */
        return editor.closeEdit();
    }
	
	
	/*
     * Initializes the library to work with a repository via 
     * different protocols.
     */
    private static void setupLibrary() {
        /*
         * For using over http:// and https://
         */
        DAVRepositoryFactory.setup();
        /*
         * For using over svn:// and svn+xxx://
         */
        SVNRepositoryFactoryImpl.setup();
        
        /*
         * For using over file:///
         */
        FSRepositoryFactory.setup();
    }
}
