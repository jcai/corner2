//==============================================================================
//file :        HibernateConverter.java
//
//last change:	date:       $Date$
//           	by:         $Author$
//           	revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//==============================================================================

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
