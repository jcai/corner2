// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2006-06-21
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

package corner.orm.tapestry;

import org.apache.tapestry.components.IPrimaryKeyConverter;
import org.apache.tapestry.services.DataSqueezer;

/**
 * 针对list列表进行转换。
 * <p>利用了hibernate的datasqueezer。
 * @author jcai
 * @version $Revision$
 * @since 2.0.3
 */
public class HibernateConverter implements IPrimaryKeyConverter {
	private DataSqueezer dataSqueezer;

	public HibernateConverter(DataSqueezer dataSqueezer){
		this.dataSqueezer=dataSqueezer;
	}
	/**
	 * @see org.apache.tapestry.components.IPrimaryKeyConverter#getPrimaryKey(java.lang.Object)
	 */
	public Object getPrimaryKey(Object object) {
		return this.dataSqueezer.squeeze(object);
	}

	/**
	 * @see org.apache.tapestry.components.IPrimaryKeyConverter#getValue(java.lang.Object)
	 */
	public Object getValue(Object obj) {
		return this.dataSqueezer.unsqueeze((String) obj);
	}

}
