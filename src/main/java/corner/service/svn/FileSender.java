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
import java.io.UnsupportedEncodingException;

import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.io.ISVNEditor;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.diff.SVNDeltaGenerator;

/**
 * 发生文件的专项处理操作.
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.5
 */
public class FileSender {

	public static final String FILE_ENCODING = "UTF-8";
	private boolean noExist;
	private String content;
	private String filePath;

	public FileSender(SVNRepository repository, String json, String filePath) throws SVNException {
		noExist=repository.checkPath(filePath,-1) ==SVNNodeKind.NONE;
		this.content=json;
		this.filePath=filePath;
	}

	public void sendFile(ISVNEditor editor) throws SVNException {
		if(noExist){
			editor.addFile(filePath,null,-1);
		}else{
			editor.openFile(filePath,-1);
		}
		editor.applyTextDelta(filePath, null);
		
		SVNDeltaGenerator deltaGenerator = new SVNDeltaGenerator();
		String checksum = null;
		try {
			checksum = deltaGenerator.sendDelta(filePath, new ByteArrayInputStream(content.getBytes(FILE_ENCODING)), editor, true);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		editor.closeFile(filePath, checksum);
	}
}
