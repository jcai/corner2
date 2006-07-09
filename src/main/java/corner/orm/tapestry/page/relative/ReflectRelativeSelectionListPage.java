/**
 * copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
 */
package corner.orm.tapestry.page.relative;

import java.util.Collection;

import corner.util.BeanUtils;
import corner.util.EntityConverter;

/**
 * 在增加关系的时候，供选择的对象。
 * <P>此类通常用于many-to-many的时候，提供一个供选择的页面。
 * 此类有两个重要属性，可以自定义，一个是
 *  {@link #getRelativePropertyName()} 得到关联属性的名字 
 *  另外一个是
 *  {@link #isInverse()} 
 *  判断当前的 {@link IPageRooted#getRootedObject()}是否为反相控制端。
 *  
 *  通常这两个是配合使用。
 *  譬如：有A和B两个实体关系是many-to-many.
 *  如果从A的页面来操作两者的关系。
 *  	假如 {@link IPageRooted#getRootedObject()} 为反向控制端，
 *  		则 {@link #getRelativePropertyName()} 返回 as
 *      否则 返回 bs
 *      	  
 * @author Jun Tsai
 * @version $Revision$
 * @since 2.0.5
 */
public abstract class ReflectRelativeSelectionListPage extends
		AbstractRelativeSelectionListPage<Object,Object> {
	/**
	 * 得到关联实体的名称，作为集合的复数。
	 * <p>譬如：users,groups等。
	 * @return 关联的额属性名称，一般为复数形式。
	 */
	public String getRelativePropertyName()
	{
		if(isInverse()){
			return EntityConverter.getClassNameAsCollectionProperty(this.getRootedObject());
		}else{
			return EntityConverter.getClassNameAsCollectionProperty(this.getEntity());
		}
	}

	
	/**
	 * 当前的本实体是否为反向控制端。
	 * 默认为false。
	 * @return 判断当前的根实体是否为反向控制端。
	 */
	public abstract boolean isInverse();
	public abstract void setInverse(boolean inverse);

	/**
	 * 判断是否选中。
	 * @return 是否选中
	 */
	public boolean isCheckboxSelected(){
		if(isInverse())
			return this.getRelationshipCollection(this.getEntity()).contains(this.getRootedObject());
		
		else{
			return this.getRelationshipCollection(this.getRootedObject()).contains(this.getEntity());	
		}
	}
	/**
	 * 选中时候的处理。
	 * @see corner.orm.tapestry.page.AbstractEntityListPage#setCheckboxSelected(boolean)
	 */
	public void setCheckboxSelected(boolean select){
		if(isInverse()){
			doSelectCheckbox(this.getEntity(),this.getRootedObject(),select);
		}else{
			doSelectCheckbox(this.getRootedObject(),this.getEntity(),select);
		}
	}
	@SuppressWarnings("unchecked")
	private void doSelectCheckbox(Object obj,Object objInversed,boolean select){
		Collection c=this.getRelationshipCollection(obj);
		if(select){
			if(!c.contains(objInversed)){
				c.add(objInversed);
				this.getEntityService().saveEntity(obj);
			}
		}else{
			if(c.contains(objInversed)){
				c.remove(objInversed);
				this.getEntityService().saveEntity(obj);
			}
		}
	}

	
	private Collection getRelationshipCollection(Object obj){
		return (Collection) BeanUtils.getProperty(obj,this.getRelativePropertyName());
	}

}
