// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2006-06-22
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package corner.orm.tapestry.record;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

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
		//调整集合里面元素的值，由字符串变为对象。
		Collection<PropertyChangeImpl> tmpCollection=new HashSet<PropertyChangeImpl>();
		for(PropertyChangeImpl propertyChange:c){
			tmpCollection.add(new PropertyChangeImpl(propertyChange.getComponentPath(),
                    propertyChange.getPropertyName(),
                    this.dataSqueezer.unsqueeze((String) propertyChange.getNewValue())));

		}
		c.clear();
		return tmpCollection;
	}

	/**
	 * @return Returns the dataSqueezer.
	 */
	public DataSqueezer getDataSqueezer() {
		return dataSqueezer;
	}

	/**
	 * 进行对hibernate对象进行序列化。
	 * @param dataSqueezer
	 *            The dataSqueezer to set.
	 */
	public void setDataSqueezer(DataSqueezer dataSqueezer) {
		this.dataSqueezer = dataSqueezer;
	}

}
