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



	public String getRelativePropertyName(){
		if(isInverse()){
			return EntityConverter.getClassNameAsCollectionProperty(this.getRootedObject());
		}else{
			return EntityConverter.getClassNameAsCollectionProperty(this.getEntity());
		}
	}

	/**
	 * 当前的本实体是否为反向控制端。
	 * 默认为true。
	 * @return 判断当前的实体是否为反向控制端。
	 */

	public abstract boolean isInverse();
	public abstract void setInverse(boolean inverse);

	public boolean isCheckboxSelected(){

		return this.isContain();
	}
	public void setCheckboxSelected(boolean select){
		if(select){
			if(!this.isContain()){
				if(isInverse()){
					this.getRelationshipCollection(this.getEntity()).add(this.getRootedObject());
					this.getEntityService().saveEntity(this.getEntity());
				}else{
					this.getRelationshipCollection(this.getRootedObject()).add(this.getEntity());
					this.getEntityService().saveEntity(this.getRootedObject());
				}
			}

		}else{
			if(this.isContain()){

				if(isInverse()){
					this.getRelationshipCollection(this.getEntity()).remove(this.getRootedObject());
					this.getEntityService().saveEntity(this.getEntity());
				}else{
					this.getRelationshipCollection(this.getRootedObject()).remove(this.getEntity());
					this.getEntityService().saveEntity(this.getRootedObject());
				}
			}

		}

	}

	private boolean isContain(){
		if(isInverse())
			return this.getRelationshipCollection(this.getEntity()).contains(this.getRootedObject());
		else{
			return this.getRelationshipCollection(this.getRootedObject()).contains(this.getEntity());
		}
	}
	private Collection getRelationshipCollection(Object obj){
		return (Collection) BeanUtils.getProperty(obj,this.getRelativePropertyName());
	}

	public AbstractManyEntityFormPage<Object,Object> getManyEntityFormPage(){
		StringBuffer sb=new StringBuffer();
		sb.append(getCurrentPagePath());
		sb.append(EntityConverter.getShortClassName(this.getRootedObject()));
		sb.append("Form");
		return (AbstractManyEntityFormPage<Object, Object>) this.getRequestCycle().getPage(sb.toString());
	}
}
