/**
 * copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
 */
package corner.orm.tapestry.state;

import org.apache.tapestry.engine.state.SessionScopeManager;
import org.apache.tapestry.engine.state.StateObjectFactory;
import org.apache.tapestry.services.DataSqueezer;

/**
 * 对应用程序状态的scope管理者。
 * <p>提供了把hibernate的对象存在session的时候，仅仅保存字符串。
 * @author jcai
 * @version $Revision$
 * @since 2.0.6
 */
public class EntitySessionScopeManager extends SessionScopeManager {

	/**
	 * @see org.apache.tapestry.engine.state.SessionScopeManager#get(java.lang.String, org.apache.tapestry.engine.state.StateObjectFactory)
	 */
	@Override
	public Object get(String objectName, StateObjectFactory factory){
		return super.get(objectName, factory);
	}

	/**
	 * @see org.apache.tapestry.engine.state.SessionScopeManager#store(java.lang.String, java.lang.Object)
	 */
	@Override
	public void store(String objectName, Object stateObject){
		super.store(objectName, stateObject);
	}
	private DataSqueezer dataSqueezer;
	/**
	 * @return Returns the dataSqueezer.
	 */
	public DataSqueezer getDataSqueezer() {
		return dataSqueezer;
	}
	
}
