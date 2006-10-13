//==============================================================================
//file :        GoAnchor.java
//project:      corner
//
//last change:	date:       $Date: 2006-06-15 03:53:52Z $
//           	by:         $Author: jcai $
//           	revision:   $Revision: 1140 $
//------------------------------------------------------------------------------
//copyright:	China Java Users Group http://cnjug.dev.java.net
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.orm.tapestry.component.anchor;

import java.util.HashMap;

import org.apache.hivemind.ApplicationRuntimeException;
import org.apache.tapestry.AbstractComponent;
import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.IScript;
import org.apache.tapestry.PageRenderSupport;
import org.apache.tapestry.TapestryUtils;

public abstract class GoAnchor extends AbstractComponent {

	/**
     * Injected
     * 
     * 
     */
    public abstract IScript getScript();
	@Override
	protected void renderComponent(IMarkupWriter writer, IRequestCycle cycle) {
		PageRenderSupport pageRenderSupport = TapestryUtils.getPageRenderSupport(cycle, this);
		getScript().execute(cycle,pageRenderSupport,new HashMap());
		
		String element = getElement();

        if (element == null)
            throw new ApplicationRuntimeException("not defined!", this,
                    null, null);

        boolean rewinding = cycle.isRewinding();

        if (!rewinding)
        {
            writer.begin(element);
            
            
            
            StringBuffer sb=new StringBuffer();
            
            sb.append("return checkScrollNecessary(this);");
            writer.attribute("onClick",sb.toString());
            
//            writer.attribute("style","cursor=hand");
            
            renderInformalParameters(writer, cycle);
        }

        renderBody(writer, cycle);

        if (!rewinding)
        {
            writer.end(element);
        }

        
		
	}
	/**
	 * 生成的元素
	 * @return 元素的名字
	 */
	public abstract String getElement();
	/**
	 * 需要转向的锚点.
	 * @return 锚点名字.
	 */
	public abstract String getAnchorName();
	
}
