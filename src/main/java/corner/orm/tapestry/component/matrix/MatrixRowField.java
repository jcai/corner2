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

package corner.orm.tapestry.component.matrix;

import org.apache.tapestry.BaseComponent;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.annotations.Parameter;

import corner.orm.hibernate.v3.MatrixRow;

/**
 * 行组件，来产生组件.
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.2.2
 */
public abstract class MatrixRowField extends BaseComponent {
	
	@Parameter(required=true)
	public abstract MatrixRow getValue();
	public abstract void setValue(MatrixRow value);
	
	private int star=0;
	@Parameter(required=true)
	public abstract MatrixRow getRefVector();
	
	/**
	 * 得到循环元素的值.
	 * @return
	 */
	public String getElementValue(){
		if(getValue().size()>star)
			return (String) getValue().get(star++);
		else
			return null;
	}
	@SuppressWarnings("unchecked")
	public void setElementValue(String value){
		if(this.getPage().getRequestCycle().isRewinding()){
			if(star==0){
				setValue(new MatrixRow());
			}
			if(value!=null)
				getValue().add(star++,value);
		}
	}
	/**
	 * 是否为第一次增加。
	 * @return
	 */
	public boolean isFirstNew(){
		return this.getRefVector()==null||this.getRefVector().size()==0;
	}
	/**
	 * 
	 * @see org.apache.tapestry.AbstractComponent#prepareForRender(org.apache.tapestry.IRequestCycle)
	 */
	@Override
	protected void prepareForRender(IRequestCycle arg0) {
		star=0;
		if(this.getValue()==null){
			setValue(new MatrixRow());
		}
	}
}
