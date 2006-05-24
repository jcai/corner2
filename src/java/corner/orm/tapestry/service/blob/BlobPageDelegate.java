//==============================================================================
//file :        BlobPageDelegate.java
//project:      poison-system
//
//last change:	date:       $Date$
//           	by:         $Author$
//           	revision:   $Revision: 825 $
//------------------------------------------------------------------------------
//copyright:	China Java Users Group http://cnjug.dev.java.net
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.orm.tapestry.service.blob;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tapestry.request.IUploadFile;
import org.springframework.util.FileCopyUtils;

import corner.model.AbstractBlobModel;
import corner.service.EntityService;
import corner.util.BeanUtils;

/**
 * 针对blob字段的处理的委派类.
 * 
 * @author	<a href="http://wiki.java.net/bin/view/People/JunTsai">Jun Tsai</a>
 * @version	$Revision: 825 $
 * @since	2006-1-20
 */
public class BlobPageDelegate <T extends AbstractBlobModel>{
	private static final Log log=LogFactory.getLog(BlobPageDelegate.class);
	private IUploadFile uploadFile;
	private String keyValue;
	private EntityService service;
	private Class<T> clazz;

	/**
	 * 构造一个委派类对象.
	 * @param clazz 类.
	 * @param uploadFile 上传文件对象.
	 * @param keyValue 主键的值.
	 * @param service 实体服务.
	 * @see EntityService
	 */
	public BlobPageDelegate(Class<T> clazz,IUploadFile uploadFile, String keyValue,EntityService service) {
		this.clazz=clazz;
		this.keyValue=keyValue;
		this.uploadFile=uploadFile;
		this.service=service;
	}
	/**
	 * 保存blob对象.
	 * @param callback 回调函数.
	 * @see org.springframework.orm.hibernate3.support.BlobByteArrayType
	 * 
	 */
	public void save(IBlobBeforSaveCallBack<T> callback) {
		//如果上传为空.
		if(uploadFile==null){
			if(keyValue!=null) //主键有值,则进行删除.
				service.deleteEntityById(clazz,keyValue);
			return;
		}
		//申明blob对象.
		T blob = null;
		//如果主键的值不为空,则先从表中提取数据.
		if(keyValue!=null){
			blob=service.getEntity(clazz,keyValue);
		}
		//如果表中无数据,则是新增的一个对象.
		if(blob == null){
			blob=BeanUtils.instantiateClass(clazz);
		}
		
		//设定blob的字节数组值.
		try {
			blob.setBlobData(FileCopyUtils
					.copyToByteArray(uploadFile.getStream()));
		} catch (IOException e) {
			log.warn("fail to set blob data ["+e.getMessage()+"]");
			return;
		}
		
		//设定content类型.
		blob.setContentType(this.uploadFile.getContentType());
		
		//执行回调函数,进行额外数据的操作.
		if(callback!=null){
			callback.doBeforeSaveBlob(blob);
		}
		
		//保存操作.
		this.service.saveOrUpdateEntity(blob);
	}
	/**
	 * 保存blob.
	 * @see #save(IBlobBeforSaveCallBack)
	 */
	public void save() {
		this.save(null);
	}

}
