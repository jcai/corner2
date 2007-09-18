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

import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.annotations.EventListener;
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
	
	
	private int SheetCateField;
	
	/**
	 * 是否显示，如果可以显示返回true
	 */
	public boolean isShow(){
		
		if(SheetCateField==5){
			return false;
		}
		
		if(SheetCateField==1){
			return false;
		}
		
		return true;
	}

	@EventListener(events="trigger", elements={ "SheetCateField" })
	public void onTriggered( BrowserEvent event )
	{
		System.out.println("call this method !!!!!!!!");
		
		SheetCateField = event.getMethodArguments().getJSONObject(0).getInt("selected");
		
		System.out.println(SheetCateField);
		
		this.getRequestCycle().getResponseBuilder().updateComponent(
		"selectLinkDiv");
	}
	
	/**
     * @see org.apache.tapestry.AbstractPage#cleanupAfterRender(org.apache.tapestry.IRequestCycle)
     */
    @Override
    protected void cleanupAfterRender(IRequestCycle cycle) {
        super.cleanupAfterRender(cycle);
        SheetCateField = 0;
    }
	
	/**
	 * @return Returns the sheetCateField.
	 */
	public int getSheetCateField() {
		return SheetCateField;
	}

	/**
	 * @param sheetCateField The sheetCateField to set.
	 */
	public void setSheetCateField(int sheetCateField) {
		SheetCateField = sheetCateField;
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
