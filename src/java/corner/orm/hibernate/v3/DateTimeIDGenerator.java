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

package corner.orm.hibernate.v3;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.type.Type;
import org.hibernate.util.PropertiesHelper;

/**
 * 一个根据时间来自动生成主键的类。
 * 生成的ID为:prefix+yyyyMMddHHmmssSSS.
 * 
 * @author <a href="http://wiki.java.net/bin/view/People/JunTsai">Jun Tsai</a>
 * @version $Revision$
 * @since 2005-3-3
 */

public class DateTimeIDGenerator implements IdentifierGenerator, Configurable {
	String upTime = null;

	private String prefix;
	private static final String PREFIX="prefix";

	private static final Log log = LogFactory.getLog(DateTimeIDGenerator.class);

	/**
	 * @see net.sf.hibernate.id.IdentifierGenerator#generate(net.sf.hibernate.engine.SessionImplementor,
	 *      java.lang.Object)
	 */
	public Serializable generate(SessionImplementor session, Object obj)
			throws  HibernateException {
		upTime = getNowTimeFormatted();
		if(this.prefix!=null){
			upTime=prefix+upTime;
		}
		return upTime;
	}
	/* get now time formatted*/
	private String getNowTimeFormatted() {
		Calendar rightNow = Calendar.getInstance();
		DateFormat formator = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String currentTime = formator.format(rightNow.getTime());
		if (currentTime.equals(upTime)) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				log.error(e.getMessage(), e);
			}
			currentTime = getNowTimeFormatted();
		}
		return currentTime;
	}

	public void configure(Type type, Properties params, Dialect dialect) {
		this.prefix = PropertiesHelper.getString(PREFIX, params, "");
	}
}
