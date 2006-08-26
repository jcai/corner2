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

}