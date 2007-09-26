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

package corner.orm.tapestry.jasper.link;

import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.annotations.InjectObject;
import org.apache.tapestry.annotations.Parameter;
import org.apache.tapestry.engine.IEngineService;
import org.apache.tapestry.engine.ILink;
import org.apache.tapestry.link.AbstractLinkComponent;

/**
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision$
 * @since 2.3.7
 */
public abstract class JasperPrinterLink extends AbstractLinkComponent{
	/**
	 * @see org.apache.tapestry.link.AbstractLinkComponent#getLink(org.apache.tapestry.IRequestCycle)
	 */
	@Override
	public ILink getLink(IRequestCycle cycle) {
		Object[] parameters = new Object[6];
		parameters[2] = getTemplatePath();
		parameters[3] = getTemplateEntity();
		parameters[4] = getDetailEntity();
		parameters[5] = getDetailCollection();
		
		return this.getJasperService().getLink(true, parameters);
	}
	
	/**
	 * 细节循环用的page，geter方法名
	 */
	@Parameter
	public abstract String getDetailEntity();
	
	/**
	 * 细节循环的集合
	 */
	@Parameter
	public abstract String getDetailCollection();
	
	/**
	 * 模版放置的位置classpath/context
	 */
	@Parameter
	public abstract String getTemplatePath();
	
	/**
	 * 模板使用的entity
	 */
	@Parameter
	public abstract Object getTemplateEntity();
	
	/**
	 * @return
	 */
	@InjectObject("engine-service:jasperPrinter")
	public abstract IEngineService getJasperService();
}
