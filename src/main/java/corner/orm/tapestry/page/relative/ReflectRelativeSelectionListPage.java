/**
 * copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
 */
package corner.orm.tapestry.page.relative;

import java.util.Collection;

import corner.util.BeanUtils;
import corner.util.EntityConverter;

/**
 * 在增加关系的时候，供选择的对象。
 * @author Jun Tsai
 * @version $Revision$
 * @since 2.0.5
 */
public abstract class ReflectRelativeSelectionListPage extends
		AbstractRelativeSelectionListPage<Object,Object> {
	/**
	 * 得到关联实体的名称，作为集合的复数。
	 * @return
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
