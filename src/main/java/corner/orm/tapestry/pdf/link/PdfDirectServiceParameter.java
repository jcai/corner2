/*		
 * Copyright 2007 The Beijing Maxinfo Technology Ltd. 
 * site: http://www.bjmaxinfo.com
 * file: $Id$
 * created at:2007-04-12
 */

package corner.orm.tapestry.pdf.link;

import corner.orm.tapestry.pdf.IPdfDirect;

/**
 * 一个pdf跳转的服务参数类
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 2.3.7
 */
public class PdfDirectServiceParameter {

	/**
	 * 监听的参数列表
	 */
	private Object[] parameters;
	
	/**
	 * 是否保存为文件 true:保存为文件 false:直接在网页中打开
	 */
	private boolean saveAsFile; 
	
	/**
	 * 取得保存为文件时候的文件名称
	 */
	private String downLoadFileName;
	/**
	 * 对应跳转
	 */
	private IPdfDirect direct;

	public PdfDirectServiceParameter(IPdfDirect direct, Object[] serviceParameters) {
		this.parameters=serviceParameters;
		this.direct=direct;

	}

	/**
	 * @return Returns the direct.
	 */
	public IPdfDirect getDirect() {
		return direct;
	}

	/**
	 * @return Returns the parameters.
	 */
	public Object[] getServiceParameters() {
		return parameters;
	}

	public String getDownLoadFileName() {
		return downLoadFileName;
	}

	public void setDownLoadFileName(String downLoadFileName) {
		this.downLoadFileName = downLoadFileName;
	}

	public boolean isSaveAsFile() {
		return saveAsFile;
	}

	public void setSaveAsFile(boolean saveAsFile) {
		this.saveAsFile = saveAsFile;
	}
	
	

}
