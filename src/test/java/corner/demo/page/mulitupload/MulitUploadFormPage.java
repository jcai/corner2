//==============================================================================
// file :       $Id$
// project:     corner
//
// last change: date:       $Date$
//              by:         $Author$
//              revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.demo.page.mulitupload;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry.request.IUploadFile;

import corner.demo.model.mulitupload.TestMany;
import corner.demo.model.mulitupload.TestOne;
import corner.orm.tapestry.page.relative.ReflectMultiManyEntityFormPage;
import corner.orm.tapestry.service.blob.IBlobPageDelegate;
import corner.orm.tapestry.service.blob.SqueezeBlobPageDelegate;

/**
 * 对blob的处理
 * 
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.2.1
 */
public abstract class MulitUploadFormPage extends
		ReflectMultiManyEntityFormPage implements IMulitUpload {

	public abstract List<TestMany> getManys();

	public abstract void setMany(List<TestMany> manys);

	public boolean isSelected() {
		return false;
	}

	public void setSelected(boolean selected) {
		if (selected) {
			this.getEntityService().deleteEntities(this.getBlobModel());
		}
	}

	/**
	 * @see corner.orm.tapestry.page.AbstractEntityPage#saveOrUpdateEntity()
	 */
	@Override
	protected void saveOrUpdateEntity() {

		super.saveOrUpdateEntity();// 保存one端实体
		TestOne one = (TestOne) this.getEntity();

		if (this.getFiles() != null && this.getFiles().size() > 0) {
			List<TestMany> list = new ArrayList<TestMany>();
			for (IUploadFile file : this.getFiles()) {
				TestMany many = new TestMany();
				this.getEntityService().saveEntity(many);
				many.setTestOne(one);
				IBlobPageDelegate<TestMany> delegate = new SqueezeBlobPageDelegate<TestMany>(
						TestMany.class, file, many, this.getEntityService());

				delegate.save();
				list.add(many);
			}
		}
	}

}
