/**
 * copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
 */
package corner.orm.tapestry.page.relative;

import java.util.Collection;

import org.apache.tapestry.IPage;

import corner.util.BeanUtils;

/**
 * 提供了通过反射来作操作多对象。
 *
 * @author Jun Tsai
 * @version $Revision$
 * @since 2.0.5
 */
public abstract class ReflectMultiManyEntityFormPage extends
		AbstractMultiManyEntityFormPage<Object> {
	public AbstractRelativeSelectionListPage<Object, Object> getRelativeListPage() {
		throw new UnsupportedOperationException(
				"在relectMutiManyEntityFormPage中，不能调用getRelativeListPage");

	}

	/**
	 * 新增加一个关联关系的操作。
	 *
	 * @param obj
	 *            供操作的对象。
	 * @param names
	 *            名称
	 * @return 操作后返回的页面。
	 */
	public IPage doNewRelativeAction(Object obj, String name) {

		String thisPageName = this.getPageName();
		StringBuffer sb = new StringBuffer();
		sb.append(thisPageName.substring(0, thisPageName.lastIndexOf("/")));
		sb.append("/");
		sb.append(this.getShortClassName(obj) + name);
		AbstractRelativeSelectionListPage<Object, Object> page = (AbstractRelativeSelectionListPage<Object, Object>) this
				.getRequestCycle().getPage(sb.toString());

		page.setRootedObject(obj);
		return page;
	}

	@SuppressWarnings("unchecked")
	protected void deleteRelationship(Object t, Object e) {
		// 得到属性的名称，譬如：groups,users 注意后面的复数s。
		String name = this.getShortClassName(t);

		StringBuffer sb = new StringBuffer();
		sb.append(Character.toLowerCase(name.charAt(0)));
		sb.append(name.substring(1));
		sb.append("s");

		Collection<Object> c = (Collection<Object>) BeanUtils.getProperty(e, sb
				.toString());
		c.remove(t);
		this.getEntityService().saveOrUpdateEntity(e);
	}

	private String getShortClassName(Object obj) {
		String name = this.getEntityService().getEntityClass(obj).getName();

		name = name.substring(name.lastIndexOf(".") + 1);
		return name;
	}
}
