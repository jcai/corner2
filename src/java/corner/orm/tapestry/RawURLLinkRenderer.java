package corner.orm.tapestry;

import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.components.ILinkComponent;
import org.apache.tapestry.link.ILinkRenderer;

/**
 * 扩展tapestry的renderer,供JavaScript中产生URL.
 * 
 * @author Jun Tsai
 * @version $Revision$
 * @since 2006-4-18
 */
public class RawURLLinkRenderer implements ILinkRenderer {
	
	public void renderLink(IMarkupWriter writer, IRequestCycle cycle,
			ILinkComponent linkComponent) {
		writer.print(linkComponent.getLink(cycle).getAbsoluteURL(), true);
	}
}
