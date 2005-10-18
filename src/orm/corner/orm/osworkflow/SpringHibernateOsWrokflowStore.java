package corner.orm.osworkflow;

import java.util.HashMap;

import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.module.propertyset.PropertySetManager;
import com.opensymphony.workflow.StoreException;
import com.opensymphony.workflow.spi.hibernate.SpringHibernateWorkflowStore;

public class SpringHibernateOsWrokflowStore extends
		SpringHibernateWorkflowStore {

	public PropertySet getPropertySet(long entryId) throws StoreException {
		HashMap<String, Object> args = new HashMap<String, Object>();
		args.put("entityName", "OSWorkflowEntry");
		args.put("entityId", new Long(entryId));

		return PropertySetManager.getInstance("hibernate", args);
	}

}
