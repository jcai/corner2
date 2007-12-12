package corner.orm.tapestry.component;

import java.util.HashMap;
import java.util.Map;

import org.apache.hivemind.ApplicationRuntimeException;
import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.IScript;
import org.apache.tapestry.PageRenderSupport;
import org.apache.tapestry.TapestryUtils;
import org.apache.tapestry.components.Any;

/**
 * 大部分使用的any组建
 * @author <a href="mailto:xf@bjmaxinfo.com">xiafei</a>
 * @version $Revision$
 * @since 2.5
 */
public abstract class AbstractAny extends Any {

	public abstract IScript getScript();

	/**
	 * @see org.apache.tapestry.components.Any#renderComponent(org.apache.tapestry.IMarkupWriter,
	 *      org.apache.tapestry.IRequestCycle)
	 */
	@Override
	protected void renderComponent(IMarkupWriter writer, IRequestCycle cycle) {
		String element = getElement();
	    
	    if (element == null)
	        throw new ApplicationRuntimeException(exceptionMessage(), this,
	                null, null);
	
	    boolean rewinding = cycle.isRewinding();
	
	    if (!rewinding){
	        writer.begin(element);
	            renderInformalParameters(writer, cycle);
	            if (getId() != null && !isParameterBound("id"))
	                renderIdAttribute(writer, cycle);
	        writer.end(element);
	    }
	    
		PageRenderSupport prs = TapestryUtils.getPageRenderSupport(cycle, this);
		Map<String, String> parms = new HashMap<String, String>();
		parms.put("id", this.getClientId());
		getScript().execute(this, cycle, prs, parms);
	}
	
	protected abstract String exceptionMessage();
}