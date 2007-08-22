package corner.model;

/**
 * blob模型的接口
 * @author Jun Tsai
 * @version $Revision$
 * @since 2.1
 */
public interface IBlobModel {

	public abstract byte[] getBlobData();

	public abstract void setBlobData(byte[] blobData);

	public abstract String getContentType();

	public abstract void setContentType(String contentType);
	
	/**
	 * 设置上传的文件名称(包括文件名称和扩展名),用于下载的时候让浏览器可以自动关联程序打开改文件
	 * @param fileName
	 */
	public abstract void setBlobName(String blobName);
	
	public abstract String getBlobName();

}