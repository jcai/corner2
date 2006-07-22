/**
 * copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
 */
package corner.orm.tapestry.record;

import org.apache.tapestry.engine.ServiceEncoding;
import org.apache.tapestry.record.AbstractPrefixedClientPropertyPersistenceScope;
import org.apache.tapestry.record.PersistentPropertyData;

/**
 * 保存数据至客户端的持久化周期。
 * <p> 此周期持续整个应用。
 * @author Jun Tsai
 * @version $Revision$
 * @since 2.0.6
 */
public class EntityAppClientPropertyPersistenceScope extends
		AbstractPrefixedClientPropertyPersistenceScope {



	    public EntityAppClientPropertyPersistenceScope()
	    {
	        super("entityapp:");
	    }

	    /**
	     * Returns true if the active page name matches the page for this property. This means that
	     * <em>after a new page has been activated</em>, the state is discarded.
	     */

	    public boolean shouldEncodeState(ServiceEncoding encoding, String pageName,
	            PersistentPropertyData data)
	    {
	       return true;
	    }


}
