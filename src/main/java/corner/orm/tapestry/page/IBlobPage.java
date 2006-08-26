//==============================================================================
//file :        IBlobPage.java
//project:     corner
//
//last change:	date:       $Date$
//           	by:         $Author$
//           	revision:   $Revision$
//------------------------------------------------------------------------------
//==============================================================================

package corner.orm.tapestry.page;

import org.apache.tapestry.annotations.InjectObject;
import org.apache.tapestry.engine.IEngineService;
import org.apache.tapestry.request.IUploadFile;

/**
 * 抽象的针对blob的页面. 
 * <p>提供了上传文件的句柄,可编辑blob对象的判断,显示图像的Asset. 
 * @author	<a href="http://wiki.java.net/bin/view/People/JunTsai">Jun Tsai</a>
 * @version	$Revision$
 * @since	2006-2-6
 */
public interface IBlobPage {
	
	/**
	 * 上传文件的句柄.
	 * @return 上传文件句柄.
	 */
	public abstract IUploadFile getUploadFile();
	/**
	 * 是否编辑blob.
	 * @return 是否编辑.
	 */
	public abstract boolean isEditBlob();
	/**
	 * 设定是否编辑blob对象.
	 * @param isEditBlob 是否编辑.
	 */
	public abstract void setEditBlob(boolean isEditBlob);
	/**
	 * 得到blob服务。
	 * @return blob服务
	 */
	@InjectObject("service:corner.blob.BlobService")
	public abstract IEngineService getBlobService();
	

}