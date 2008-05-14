//==============================================================================
// file :       $Id$
// project:     corner2.5
//
// last change: date:       $Date$
//              by:         $Author$
//              revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.demo.page.iframe;

import java.util.Iterator;

import org.apache.tapestry.IBinding;
import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.binding.LiteralBinding;
import org.apache.tapestry.components.ILinkComponent;
import org.apache.tapestry.engine.ILink;
import org.apache.tapestry.html.BasePage;
import org.apache.tapestry.link.ILinkRenderer;

/**
 * @author <a href="mailto:xf@bjmaxinfo.com">xiafei</a>
 * @version $Revision$
 * @since 2.5
 */
public abstract class IFramePage extends BasePage{
	
	/**
	 * @return
	 */
	public ILinkRenderer getIframeRenderPage() {
		return new ILinkRenderer() {
			public void renderLink(IMarkupWriter writer, IRequestCycle cycle,
					ILinkComponent linkComponent) {
				writer.begin("iframe");
				Iterator i = linkComponent.getBindingNames().iterator();
				while (i.hasNext()) {
					String name = (String) i.next();
					IBinding b = linkComponent.getBinding(name);
					if (b instanceof LiteralBinding
							&& !"src".equalsIgnoreCase(name)
							&& !"page".equalsIgnoreCase(name)) {
						writer.attribute(name, b.getObject().toString());
					}
				}
				ILink l = linkComponent.getLink(cycle);
				writer.attribute("src", l.getURL());
				writer.end("iframe");
			}
		};
	}
}
