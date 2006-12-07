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
import org.apache.tapestry.form.ValidatableFieldSupport;
import org.apache.tapestry.request.IUploadFile;
import org.apache.tapestry.valid.IValidationDelegate;
import org.apache.tapestry.valid.ValidatorException;

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
	
	public abstract IForm getForm();

	public abstract void setForm(IForm form);
	
	/**
	 * @see org.apache.tapestry.BaseComponent#renderComponent(org.apache.tapestry.IMarkupWriter, org.apache.tapestry.IRequestCycle)
	 */
	@Override
	protected void renderComponent(IMarkupWriter writer, IRequestCycle cycle) {
		IForm form = TapestryUtils.getForm(cycle, this);
		this.setForm(form);
		
		IValidationDelegate delegate = form.getDelegate();

		delegate.setFormComponent(this);
		
		form.getElementId(this);
		
		form.getDelegate().writePrefix(writer, cycle, this, null);
		
		super.renderComponent(writer, cycle);
		
		if (form.isRewinding()) {
			
			ValidatableField file = (ValidatableField) getComponent("blobDataField");
			
			try {
				getValidatableFieldSupport().validate(file, writer, cycle,"validators:required");
			} catch (ValidatorException e) {
				getForm().getDelegate().record(e);
				e.printStackTrace();
			}
			
		}else{
			PageRenderSupport prs = TapestryUtils.getPageRenderSupport(cycle, this);
			Map<String, String> parms = new HashMap<String, String>();
			parms.put("id", this.getClientId());
			getScript().execute(this, cycle, prs, parms);
		}
		
	}
	
	
	/**
	 * 设置上传文件的Validators
	 */
//	@Parameter(required=true)
//	public abstract String getFileValidators();
	
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
	
	/**
	 * Injected.
	 */
	public abstract ValidatableFieldSupport getValidatableFieldSupport();

}
