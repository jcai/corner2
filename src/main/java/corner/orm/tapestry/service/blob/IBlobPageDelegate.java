package corner.orm.tapestry.service.blob;

import corner.model.IBlobModel;

/**
 * 提供了对bolb处理的委派类.
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.2.1
 */
public interface IBlobPageDelegate<T extends IBlobModel> {

	/**
	 * 保存blob对象.
	 * @param callback 回调函数.
	 * @see org.springframework.orm.hibernate3.support.BlobByteArrayType
	 * 
	 */
	public abstract void save(IBlobBeforSaveCallBack<T> callback);

	/**
	 * 保存blob.
	 * @see #save(IBlobBeforSaveCallBack)
	 */
	public abstract void save();

}