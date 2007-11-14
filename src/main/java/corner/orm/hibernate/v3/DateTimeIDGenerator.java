// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2005-11-23
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

package corner.orm.hibernate.v3;

import java.io.Serializable;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.type.Type;
import org.hibernate.util.PropertiesHelper;

import corner.orm.hibernate.AbstractDateTimeIDGenerator;

/**
 * 一个根据时间来自动生成主键的类。
 * 生成的ID为:prefix+yyyyMMddHHmmssSSS.
 * 
 * @author <a href="http://wiki.java.net/bin/view/People/JunTsai">Jun Tsai</a>
 * @version $Revision:3677 $
 * @since 2005-3-3
 */

public class DateTimeIDGenerator extends AbstractDateTimeIDGenerator implements IdentifierGenerator, Configurable {
	private String prefix;
	private static final String PREFIX="prefix";

	/**
	 * @see net.sf.hibernate.id.IdentifierGenerator#generate(net.sf.hibernate.engine.SessionImplementor,
	 *      java.lang.Object)
	 */
	public Serializable generate(SessionImplementor session, Object obj)
			throws  HibernateException {
		String tempTime = getNowTimeFormatted();
		if(this.prefix!=null){
			tempTime=prefix+tempTime;
		}
		return tempTime;
	}
	

	public void configure(Type type, Properties params, Dialect dialect) {
		this.prefix = PropertiesHelper.getString(PREFIX, params, "");
	}
}
