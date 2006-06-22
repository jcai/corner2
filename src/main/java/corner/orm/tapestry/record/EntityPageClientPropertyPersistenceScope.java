/**
 * copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
 */
package corner.orm.tapestry.record;

import org.apache.tapestry.IPage;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.engine.ServiceEncoding;
import org.apache.tapestry.record.AbstractPrefixedClientPropertyPersistenceScope;
import org.apache.tapestry.record.PersistentPropertyData;

/**
 * 定义一个entity保存到客户端的操作周期。
 * @author Jun Tsai
 * @version $Revision$
 * @since 2.0.4
 */
public class EntityPageClientPropertyPersistenceScope extends
		AbstractPrefixedClientPropertyPersistenceScope {

	 private IRequestCycle _requestCycle;

	    public EntityPageClientPropertyPersistenceScope()
	    {
	        super("entity:");
	    }

	    /**
	     * Returns true if the active page name matches the page for this property. This means that
	     * <em>after a new page has been activated</em>, the state is discarded.
	     */

	    public boolean shouldEncodeState(ServiceEncoding encoding, String pageName,
	            PersistentPropertyData data)
	    {
	        IPage page = _requestCycle.getPage();

	        // TAPESTRY-701: if you try to generate a link using, say, page or external service,
	        // from inside PageValidateListener.pageValidate(), then there may not be an active
	        // page yet. Seems like the right thing to do is hold onto any properties until
	        // we know what the active page is.  I know this one is going to cause a fight
	        // since its not clear whether keeping or discarding is the right way to go.

	        if (page == null)
	            return true;

	        return pageName.equals(page.getPageName());
	    }

	    public void setRequestCycle(IRequestCycle requestCycle)
	    {
	        _requestCycle = requestCycle;
	    }

}
