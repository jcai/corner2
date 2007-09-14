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

package corner.demo.page.one;

import org.apache.tapestry.annotations.EventListener;
import org.apache.tapestry.annotations.Persist;
import org.apache.tapestry.event.BrowserEvent;
import org.apache.tapestry.form.IPropertySelectionModel;

import corner.demo.model.one.A;
import corner.orm.hibernate.v3.MatrixRow;
import corner.orm.tapestry.component.propertyselection.PropertySelectionModel;
import corner.orm.tapestry.page.AbstractEntityFormPage;
import corner.orm.tapestry.service.blob.IBlobPageDelegate;
import corner.orm.tapestry.service.blob.SqueezeBlobPageDelegate;

/**
 * 对blob的处理
 * 
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.2.1
 */
public abstract class OnePageWithBlob extends AbstractEntityFormPage<A> {

	@Persist("client")
	public abstract String getSheetCateType();
	
	@EventListener(events="onchange", targets={ "sheetCateHidden" }, elements = { "SheetCateField" })
	public void onTriggered( BrowserEvent event )
	{
		System.out.println(getSheetCateType());
		event.getMethodArguments();
		
//	   System.out.println(event.getMethodArguments().getJSONObject(0).getInt("theAnswer"));
	}
	
	/**
	 * @return
	 */
	public IPropertySelectionModel getSheetCatePageModel() {
		return new PropertySelectionModel("1,2,3,4,5".split(","));
	}
	
	/**
	 * @see corner.orm.tapestry.page.AbstractEntityPage#saveOrUpdateEntity()
	 */
	@Override
	protected void saveOrUpdateEntity() {

		super.saveOrUpdateEntity();

		if (isEditBlob()) {
			IBlobPageDelegate<A> delegate = new SqueezeBlobPageDelegate<A>(
					A.class, getUploadFile(), this.getEntity(), this
							.getEntityService());
			delegate.save();
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
