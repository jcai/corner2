/**
 * copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
 */
package corner.orm.tapestry.page.relative;

import corner.util.EntityConverter;

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
	
	

//	/**
//	 * 新增加一个关联关系的操作。
//	 *
//	 * @param obj
//	 *            供操作的对象。
//	 * @param names
//	 *            名称
//	 * @return 操作后返回的页面。
//	 * @deprecated 将在2.0.8中删除，请使用#{@link AbstractManyEntityFormPage#doNewRelativeEntityAction(T, String)}
//	 */
//	public IPage doNewRelativeAction(Object obj, String name) {
//
//
//		StringBuffer sb = new StringBuffer();
//		sb.append(getCurrentPagePath());
//		sb.append(EntityConverter.getShortClassName(obj) + name);
//		AbstractRelativeSelectionListPage<Object, Object> page = (AbstractRelativeSelectionListPage<Object, Object>) this
//				.getRequestCycle().getPage(sb.toString());
//
//		page.setRootedObject(obj);
//		return page;
//	}

	/**
	 * @deprecated Use {@link #deleteRelationship(Object,Object,String)} instead
	 */
	@SuppressWarnings("unchecked")
	protected void deleteRelationship(Object t, Object e) {
		deleteRelationship(t, e, null);
	}



	@SuppressWarnings("unchecked")
	protected void deleteRelationship(Object t, Object e, String relativeName) {
		if(relativeName==null){
			// 得到属性的名称，譬如：groups,users 注意后面的复数s。
			String name = EntityConverter.getShortClassName(t);
	
			StringBuffer sb = new StringBuffer();
			sb.append(Character.toLowerCase(name.charAt(0)));
			sb.append(name.substring(1));
			sb.append("s");
			relativeName=sb.toString();
		}
		super.deleteRelationship(t, e, relativeName);
	}
	


}
