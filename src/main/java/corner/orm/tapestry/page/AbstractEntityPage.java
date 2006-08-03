//==============================================================================
//file :       $Id$
//project:     corner
//
//last change:	date:       $Date$
//           	by:         $Author$
//           	revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Wang&Tsai Studio http://www.wtstudio.org
//==============================================================================

package corner.orm.tapestry.page;

import java.text.Format;
import java.text.SimpleDateFormat;

import org.apache.tapestry.annotations.InjectObject;
import org.apache.tapestry.html.BasePage;
import org.apache.tapestry.services.DataSqueezer;

import corner.orm.hibernate.v3.HibernateObjectRelativeUtils;

/**
 * 抽象的实体类.
 * <p>
 * 实现了抽象实体基本的方法。
 *
 * @author jcai
 * @version $Revision$
 * @since 2006-3-5
 * @param <T>
 *            当前抽象的实体。
 */
public abstract class AbstractEntityPage<T> extends BasePage implements
		EntityPage<T> {
	/** 对日期类型的格式化 * */
	private SimpleDateFormat _dateFormat;




	/**
	 * 保存和更新实体。
	 */
	protected void saveOrUpdateEntity() {
		getEntityService().saveOrUpdateEntity(getEntity());

	}

	/**
	 * 得到日期格式化对象.
	 *
	 * @return 日期格式化对象.
	 */
	public Format getDateFormat() {
		if (_dateFormat == null)
			_dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return _dateFormat;
	}

	/**
	 * 通过给定的日期格式化字符串,来得到一个格式化对象.
	 *
	 * @param formatStr
	 *            格式化模式.
	 * @return 格式化对象.
	 */
	public Format getDateFormat(String formatStr) {
		return new SimpleDateFormat(formatStr);
	}

	/**
	 *
	 * 对hibernate进行flush操作。
	 *
	 * @since 2.0.3
	 */
	protected void flushHibernate() {
		((HibernateObjectRelativeUtils) this.getEntityService()
				.getObjectRelativeUtils()).getHibernateTemplate().flush();
	}

	/**
	 * 得到tapestry的datasqueezer。
	 *
	 * @return datasqueezer
	 * @since 2.0.3
	 */
	@InjectObject("service:tapestry.data.DataSqueezer")
	public abstract DataSqueezer getDataSqueezer();

	public String getCurrentPagePath() {
		String thisPageName = this.getPageName();
		StringBuffer sb = new StringBuffer();
		sb.append(thisPageName.substring(0, thisPageName.lastIndexOf("/")));
		sb.append("/");
		return sb.toString();
	}

}
