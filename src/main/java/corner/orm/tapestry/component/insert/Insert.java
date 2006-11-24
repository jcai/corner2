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

package corner.orm.tapestry.component.insert;

import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;

/**
 * 复写Tapestry的Insert,让Insert可以指定显示字符的长度,大于该长度的部分用...代替
 * @author Ghost
 * @version $Revision$
 * @since 2.2.1
 */
public abstract class Insert extends org.apache.tapestry.components.Insert {

	
	/**
	 * @see org.apache.tapestry.components.Insert#renderComponent(org.apache.tapestry.IMarkupWriter, org.apache.tapestry.IRequestCycle)
	 */
	@Override
	protected void renderComponent(IMarkupWriter writer, IRequestCycle cycle) {

		if(this.getValue() != null && this.getValue().toString().trim().length()>0 &&
				this.getLength()>0 &&
				this.getValue().toString().length()>this.getLength()){//指定的长度小于value的长度
			StringBuffer buffer = new StringBuffer(this.getValue().toString().trim().substring(0, this.getLength()));
			buffer.append("...");
			this.setValue(buffer.toString());
		}
		super.renderComponent(writer, cycle);
	}

	/**
	 * 设置取得的value值
	 * @param value
	 */
	public abstract void setValue(Object value);
	
	/**
	 * 取得指定该字段显示的长度
	 * @return
	 */
	public abstract int getLength();
}
