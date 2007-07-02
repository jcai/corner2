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

package corner.demo.page.mulitupload;

import java.util.List;

import org.apache.tapestry.request.IUploadFile;

import corner.model.IBlobModel;

/**
 * 文件上传页面类接口
 * <p>
 * 实现多文件上传页面必须实现该接口
 * 
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 2.3.7
 */
public interface IMulitUpload extends IBlobModel {

	/**
	 * 设置上传文件
	 */
	public abstract void setFiles(List<IUploadFile> files);

	/**
	 * 取得所有上传的文件
	 * 
	 * @return 一个封装了{@link IUploadFile}的{@link List}
	 */
	public abstract List<IUploadFile> getFiles();

	public abstract IBlobModel getBlobModel();

	public abstract void setBlobModel(IBlobModel model);

}
