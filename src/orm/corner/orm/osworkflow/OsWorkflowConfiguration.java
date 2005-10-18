package corner.orm.osworkflow;

import org.springmodules.workflow.osworkflow.configuration.ConfigurationBean;

import com.opensymphony.workflow.StoreException;
import com.opensymphony.workflow.spi.WorkflowStore;

/**
 * 对osworkflow的配置支持。
 * 
 * @author	<a href="http://wiki.java.net/bin/view/People/JunTsai">Jun Tsai</a>
 * @version	$Revision$
 * @since	2005-9-12
 */
public class OsWorkflowConfiguration extends ConfigurationBean {
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -652413080942986995L;
	private WorkflowStore store;

	public void setWorkflowStore(WorkflowStore store) {
		this.store = store;
	}

	public WorkflowStore getWorkflowStore() throws StoreException {
		return store;
	}
	
}
