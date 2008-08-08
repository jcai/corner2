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

package corner.demo.page.palette;

import org.apache.tapestry.IPage;
import org.apache.tapestry.annotations.InjectPage;
import org.apache.tapestry.link.DirectLink;
import org.apache.tapestry.link.ILinkRenderer;

import corner.demo.model.one.A;
import corner.orm.tapestry.RawURLLinkRenderer;
import corner.orm.tapestry.page.AbstractEntityListPage;
import corner.orm.tapestry.page.EntityPage;
import corner.orm.tapestry.page.PoFormPage;

/**
 * 测试DirectLink直接Render的页面
 * 
 * @author <a href="mailto:Ghostbb@bjmaxinfo.com">Ghostbb</a>
 * @version $Revision$
 * @since 2.5
 */
public abstract class AListPage extends AbstractEntityListPage<A> {
	
    /**
     * @return
     */
    public ILinkRenderer getRenderer(){
        return new RawURLLinkRenderer();
    }
    
    public IPage doRedirectTargetAction(){
    	EntityPage<A> page = this.getEntityFormPage();
    	page.setEntity(this.getEntity());
    	return page;
    }
    
    public String getDirectLinkURL(A a){
    	DirectLink link = (DirectLink)this.getComponent("urlLinkField");
    	return link.getLink(this.getRequestCycle()).getAbsoluteURL();
    }
}
