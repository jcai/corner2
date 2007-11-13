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

package corner.orm.tapestry.component.prototype.window;

import java.util.HashMap;
import java.util.Map;

import org.apache.tapestry.BaseComponent;
import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.IScript;
import org.apache.tapestry.TapestryUtils;
import org.apache.tapestry.annotations.InjectScript;
import org.apache.tapestry.annotations.Parameter;
import org.apache.tapestry.form.IFormComponent;

/**
 * svn提交时使用的组建<br/>
 * 用checkbox判断是否提交，弹出框写入
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision$
 * @since 2.5
 */
public abstract class VersionCommentBox extends BaseComponent implements IFormComponent{
	
	@InjectScript("VersionCommentBox.script")
	public abstract IScript getScript();
	
	/**
	 * @see org.apache.tapestry.BaseComponent#renderComponent(org.apache.tapestry.IMarkupWriter, org.apache.tapestry.IRequestCycle)
	 */
	@Override
	protected void renderComponent(IMarkupWriter writer, IRequestCycle cycle) {
		super.renderComponent(writer, cycle);
		
		if (!cycle.isRewinding()) {
            
            Map<String,Object> parms = new HashMap<String,Object>();
            parms.put("component", this);
            
            getScript().execute(this, cycle, TapestryUtils.getPageRenderSupport(cycle, this), parms);
        }
	}
	
	public String getShowFunc(){
		return "if(check()){\n"+
				"pwin_WindowQueryDialogField.showCenter(); \n"+
				"}else{ \n" +
				"pwin_WindowQueryDialogField.close();\n" +
				"}";
	}
	
	@Parameter
	public abstract String getQueryPageName();
	
	/**
	 * 是否提交svn.
	 */
	@Parameter(required=true)
	public abstract boolean isSvnCommit();
	
	@Parameter(required=true)
	public abstract String getSvnAuthor();
	
	@Parameter
	public abstract String getSvnAuthorDefaultValue();
	
	@Parameter
	public abstract String getSvnLog();
	
	/**
	 * 按钮上的文字
	 */
	@Parameter
	public abstract String getWindowShowButtonText();
	
	/**
	 * 获得需要定义控制事件的Id
	 */
	@Parameter(defaultValue = "literal:svnCommit")
	public abstract String getEventHeaderId();
}
