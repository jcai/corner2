package corner.orm.tapestry.service.blob;

import corner.model.IBlobModel;

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