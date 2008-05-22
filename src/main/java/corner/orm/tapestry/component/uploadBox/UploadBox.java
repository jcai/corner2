// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2006-12-04
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

package corner.orm.tapestry.component.uploadBox;

import java.util.HashMap;
import java.util.Map;

import org.apache.tapestry.BaseComponent;
import org.apache.tapestry.IAsset;
import org.apache.tapestry.IForm;
import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.IScript;
import org.apache.tapestry.PageRenderSupport;
import org.apache.tapestry.TapestryUtils;
import org.apache.tapestry.annotations.InjectObject;
import org.apache.tapestry.annotations.InjectScript;
import org.apache.tapestry.annotations.Parameter;
import org.apache.tapestry.engine.IEngineService;
import org.apache.tapestry.form.IFormComponent;
import org.apache.tapestry.form.ValidatableField;
import org.apache.tapestry.request.IUploadFile;

import corner.model.IBlobModel;
import corner.orm.tapestry.service.blob.BlobAsset;

/**
 * 上传显示组建：
 * 1.简单的上传和显示操作
 * 2.前台对上传的文件进行类型判断，上传的文件如果是图片还可以进行象素大小的判断
 * @author xiafei
 * @version $Revision$
 * @since 2.3
 */
public abstract class UploadBox extends BaseComponent implements IFormComponent, ValidatableField{
	
	@InjectScript("UploadBox.script")
	public abstract IScript getScript();
	
	/**
	 * @see org.apache.tapestry.BaseComponent#renderComponent(org.apache.tapestry.IMarkupWriter, org.apache.tapestry.IRequestCycle)
	 */
	@Override
	protected void renderComponent(IMarkupWriter writer, IRequestCycle cycle) {
		IForm form = TapestryUtils.getForm(cycle, this);
		
		super.renderComponent(writer, cycle);
		
		if (!form.isRewinding()) {
			PageRenderSupport prs = TapestryUtils.getPageRenderSupport(cycle, this);
			Map<String,Object> parms = new HashMap<String,Object>();
			parms.put("id", this.getClientId());
			parms.put("component", this);
			
			getScript().execute(this, cycle, prs, parms);
		}
	}
	
	/**
	 * 上传的文件句柄
	 * @see corner.orm.tapestry.page.IBlobPage#getUploadFile()
	 */
	@Parameter(required = true)
	public abstract IUploadFile getUploadFile();
	
	/**
	 * 用于显示blob对象的实体，该实体必须实现{@link IBlobModel}接口
	 */
	@Parameter(required=true)
	public abstract IBlobModel getBlobEntity();
	
	/**
	 * 是否编辑blob.
	 * @return 是否编辑.
	 */
	@Parameter(required=true)
	public abstract boolean isEditBlob();
	
	/**
	 * 是否是图片文件
	 * @return 如果是图片文件返回true
	 */
	public abstract boolean isImageFile();
	
	public abstract String getMsg();

	/**
	 * 得到blob服务。
	 * @return blob服务
	 */
	@InjectObject("service:corner.blob.BlobService")
	public abstract IEngineService getBlobService();
	/**
	 * 显示的Blob的Asset对象.
	 * @return 供显示Blob对象.
	 */
	public IAsset getBlobAsset() {
		return new BlobAsset(this.getBlobService(),this.getPage().getRequestCycle(),getBlobEntity());
	}
}
