//==============================================================================
//file :        BlobPageDelegate.java
//project:     corner
//
//last change:	date:       $Date$
//           	by:         $Author$
//           	revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.orm.tapestry.service.blob;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tapestry.request.IUploadFile;
import org.springframework.util.FileCopyUtils;

import corner.model.IBlobModel;
import corner.service.EntityService;
import corner.util.BeanUtils;

/**
 * 针对blob字段的处理的委派类.
 * <p>采用了DataSequeezer对待处理的blob模型进行处理.
 * @author	<a href="http://wiki.java.net/bin/view/People/JunTsai">Jun Tsai</a>
 * @version	$Revision$
 * @since	2.2.1
 */
public class SqueezeBlobPageDelegate <T extends IBlobModel> implements IBlobPageDelegate<T>{
	private static final Log log=LogFactory.getLog(SqueezeBlobPageDelegate.class);
	private IUploadFile uploadFile;
	
	private EntityService service;
	private Class<T> clazz;
	private T oldObj;

	/**
	 * 构造一个委派类对象.
	 * @param clazz 类.
	 * @param uploadFile 上传文件对象.
	 * @param oldObj 待处理的blob对象,该类必须实现{@link IBlobModel IBlobModel}接口.
	 * @param service 实体服务.
	 * @see EntityService
	 */
	public SqueezeBlobPageDelegate(Class<T> clazz,IUploadFile uploadFile, T oldObj,EntityService service) {
		this.clazz=clazz;
		this.oldObj=oldObj;
		this.uploadFile=uploadFile;
		this.service=service;
	}
	/**
	 * @see corner.orm.tapestry.service.blob.IBlobPageDelegate#save(corner.orm.tapestry.service.blob.IBlobBeforSaveCallBack)
	 */
	@SuppressWarnings("unchecked")
	public void save(IBlobBeforSaveCallBack<T> callback) {
		//如果上传为空.
		if(uploadFile==null){
			if(oldObj!=null){
				service.deleteEntities(oldObj);
				
			}
			return;
		}
		//申明blob对象.
		T blob = null;
		//如果主键的值不为空,则先从表中提取数据.
		if(oldObj!=null){
			blob=oldObj;
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
	 * @see corner.orm.tapestry.service.blob.IBlobPageDelegate#save()
	 */
	public void save() {
		this.save(null);
	}

}