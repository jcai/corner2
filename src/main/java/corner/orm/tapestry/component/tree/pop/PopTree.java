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

package corner.orm.tapestry.component.tree.pop;

import java.text.ParseException;

import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.IScript;
import org.apache.tapestry.annotations.InjectScript;
import org.apache.tapestry.annotations.Parameter;
import org.apache.tapestry.json.JSONObject;

import corner.orm.tapestry.component.tree.BaseLeftTree;
import corner.orm.tapestry.component.tree.ITreeSelectModel;



/**
 * 弹出窗
 * @author "<a href=\"mailto:xf@bjmaxinfo.com\">xiafei</a>"
 * @version $Revision$
 * @since 2.5
 */
public abstract class PopTree extends BaseLeftTree{
	
	/** 选择的过滤器 **/
	@Parameter(defaultValue="ognl:new corner.orm.tapestry.component.tree.pop.PopTreeSelectModel()")
	public abstract ITreeSelectModel getSelectModel();
	
	/**
	 * @see corner.orm.tapestry.component.tree.BaseLeftTree#getScript()
	 */
	@InjectScript("PopTree.script")
	public abstract IScript getScript();
	
	/**
	 * @see corner.orm.tapestry.component.tree.BaseLeftTree#constructSelectModel()
	 */
	@Override
	protected ITreeSelectModel constructSelectModel() {
		return this.getSelectModel();
	}

	/**
	 * @see corner.orm.tapestry.component.tree.BaseLeftTree#initTreeSelectModel(org.apache.tapestry.IRequestCycle)
	 */
	@Override
	protected ITreeSelectModel initTreeSelectModel(IRequestCycle cycle) {
		ITreeSelectModel model = super.initTreeSelectModel(cycle);
		model.setParentPage(cycle.getPage(cycle.getParameter("parentPage").trim()));
		try {
			model.setReturnValues(new JSONObject(getReturnValues()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return model;
	}
}
