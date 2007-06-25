//==============================================================================
// file :       $Id: MulitUploadFormPage.java 2408 2006-12-04 09:12:52Z xf $
// project:     corner
//
// last change: date:       $Date: 2006-12-04 17:12:52 +0800 (星期一, 04 十二月 2006) $
//              by:         $Author: xf $
//              revision:   $Revision: 2408 $
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.demo.page.mulitupload;

import java.util.List;

import org.apache.tapestry.request.IUploadFile;

import corner.demo.model.one.A;
import corner.orm.hibernate.v3.MatrixRow;
import corner.orm.tapestry.page.AbstractEntityFormPage;
import corner.orm.tapestry.service.blob.IBlobPageDelegate;
import corner.orm.tapestry.service.blob.SqueezeBlobPageDelegate;

/**
 * 对blob的处理
 * 
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision: 2408 $
 * @since 2.2.1
 */
public abstract class MulitUploadFormPage extends AbstractEntityFormPage<A> {

	
	/**
	 * 设置上传文件
	 */
	public abstract void setFiles(List<IUploadFile> files);
	
	/**
	 * 取得所有上传的文件
	 * 
	 * @return 一个封装了{@link IUploadFile}的{@link List}
	 */
	public abstract List<IUploadFile> getFiles();
	
	public abstract void setFile(IUploadFile file);
	public abstract IUploadFile getFile();
	
	/**
	 * @see corner.orm.tapestry.page.AbstractEntityPage#saveOrUpdateEntity()
	 */
	@Override
	protected void saveOrUpdateEntity() {

//		super.saveOrUpdateEntity();

		if (isEditBlob()) {
			IBlobPageDelegate<A> delegate = new SqueezeBlobPageDelegate<A>(
					A.class, getUploadFile(), this.getEntity(), this
							.getEntityService());
			delegate.save();
		} else{
			if(getFiles() != null && getFiles().size()>0){
				Class clazz = this.getEntity().getClass();
				List<IUploadFile> fs = getFiles();
				for(int i=0;i<fs.size();i++){
					try {
						A a = (A)clazz.newInstance();
						IBlobPageDelegate<A> delegate = new SqueezeBlobPageDelegate<A>(
								A.class, fs.get(i), a, this
										.getEntityService());
						delegate.save();
					} catch (InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public MatrixRow getRefVector(){
//		only for test
		MatrixRow<String> matrix=new MatrixRow<String>();
		matrix.add("L");
		matrix.add("XL");
		return matrix;
	}

}
