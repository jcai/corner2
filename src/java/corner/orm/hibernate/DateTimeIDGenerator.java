//==============================================================================
//file :       $Id$
//project:     pcrm
//
//last change:	date:       $Date$
//           	by:         $Author$
//           	revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	China Java Users Group(http://cnjug.dev.java.net).
//license:	Apache License(http://www.apache.org/licenses/LICENSE-2.0)
//==============================================================================

package corner.orm.hibernate;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Properties;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.dialect.Dialect;
import net.sf.hibernate.engine.SessionImplementor;
import net.sf.hibernate.id.Configurable;
import net.sf.hibernate.id.IdentifierGenerator;
import net.sf.hibernate.type.Type;
import net.sf.hibernate.util.PropertiesHelper;

/**
 * 一个根据时间来自动生成主键的类。
 * 生成的ID为:prefix+yyyyMMddHHmmssSSS.
 * 
 * @author <a href="http://wiki.java.net/bin/view/People/JunTsai">Jun Tsai</a>
 * @version $Revision$
 * @since 2005-3-3
 */

public class DateTimeIDGenerator extends AbstractDateTimeIDGenerator implements IdentifierGenerator, Configurable {
	

	private String prefix;
	private static final String PREFIX="prefix";
	
	public DateTimeIDGenerator(){
		System.err.println("new DateTimeIDGenerator");
	}

	/**
	 * @see net.sf.hibernate.id.IdentifierGenerator#generate(net.sf.hibernate.engine.SessionImplementor,
	 *      java.lang.Object)
	 */
	public Serializable generate(SessionImplementor session, Object obj)
			throws SQLException, HibernateException {
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
