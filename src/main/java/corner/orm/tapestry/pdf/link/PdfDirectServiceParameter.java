// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2007-07-31
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
