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

package corner.orm.tapestry.component.captcha;

import org.apache.tapestry.AbstractComponent;
import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.annotations.InjectObject;
import org.apache.tapestry.engine.IEngineService;

/**
 * 输出验证图像的组件
 * 
 * @author jcai
 * @version $Revision$
 * @since 2.3
 */
public abstract class CaptchaImage extends AbstractComponent {

	@InjectObject("service:corner.service.CaptchaService")
	public abstract IEngineService getCaptchaService();

	/**
	 * @see org.apache.tapestry.AbstractComponent#renderComponent(org.apache.tapestry.IMarkupWriter,
	 *      org.apache.tapestry.IRequestCycle)
	 */
	@Override
	protected void renderComponent(IMarkupWriter writer, IRequestCycle cycle) {
		writer.begin("img");
		writer.attribute("src", getCaptchaService().getLink(false, null)
				.getURL());
		renderInformalParameters(writer, cycle);
		writer.end();
	}

}
