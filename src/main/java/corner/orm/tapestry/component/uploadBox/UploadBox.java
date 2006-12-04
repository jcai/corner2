package corner.orm.tapestry.component.uploadBox;

import org.apache.tapestry.BaseComponent;
import org.apache.tapestry.IAsset;
import org.apache.tapestry.annotations.InjectObject;
import org.apache.tapestry.annotations.Parameter;
import org.apache.tapestry.engine.IEngineService;
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
public abstract class UploadBox extends BaseComponent{
	
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
