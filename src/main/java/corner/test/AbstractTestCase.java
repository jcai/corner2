package corner.test;

import junit.framework.TestCase;
import corner.orm.spring.SpringContainer;
import corner.service.EntityService;

/**
 *
 * @author jcai
 * @version $Revision$
 * @since 0.5.2
 */
public class AbstractTestCase extends TestCase{
	private SpringContainer container = SpringContainer.getInstance();
	protected EntityService getEntityService() {
		return (EntityService) container.getApplicationContext().getBean(
				"entityService");
	}
}
