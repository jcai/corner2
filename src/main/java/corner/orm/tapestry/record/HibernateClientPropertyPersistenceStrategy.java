/**
 * copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
 */
package corner.orm.tapestry.record;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;

import org.apache.tapestry.record.ClientPropertyPersistenceStrategy;
import org.apache.tapestry.record.PropertyChangeImpl;
import org.apache.tapestry.services.DataSqueezer;

/**
 * 针对hibernate的属性值进行客户端的序列化。
 * <p>
 * 提供了对hibernate对象的值进行保存，避免了书写hidden表单的痛苦。
 * @author Jun Tsai
 * @version $Revision$
 * @since 2.0.4
 */
public class HibernateClientPropertyPersistenceStrategy extends
		ClientPropertyPersistenceStrategy {

	private DataSqueezer dataSqueezer;

	public void store(String pageName, String idPath, String propertyName,
			Object newValue) {
		super.store(pageName, idPath, propertyName, this.dataSqueezer
				.squeeze(newValue));

	}

	@SuppressWarnings("unchecked")
	public Collection getStoredChanges(String pageName) {
		Collection<PropertyChangeImpl> c= super.getStoredChanges(pageName);
		if(c==null){
			return Collections.EMPTY_LIST;
		}
		//处理删除集合的元素的问题。
		//TODO 考虑更好的方法。可以采用删除所有的方式。
		Collection<PropertyChangeImpl> tmpCollection=new HashSet<PropertyChangeImpl>();

		for(Iterator<PropertyChangeImpl> it=c.iterator();it.hasNext();){
			PropertyChangeImpl propertyChange=it.next();
			tmpCollection.add(new PropertyChangeImpl(propertyChange.getComponentPath(),
                    propertyChange.getPropertyName(),
                    this.dataSqueezer.unsqueeze((String) propertyChange.getNewValue())));
			it.remove();

		}
		c.addAll(tmpCollection);
		tmpCollection.clear();
		return c;
	}

	/**
	 * @return Returns the dataSqueezer.
	 */
	public DataSqueezer getDataSqueezer() {
		return dataSqueezer;
	}

	/**
	 * @param dataSqueezer
	 *            The dataSqueezer to set.
	 */
	public void setDataSqueezer(DataSqueezer dataSqueezer) {
		this.dataSqueezer = dataSqueezer;
	}

}
