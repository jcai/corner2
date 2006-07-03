/**
 * copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
 */
package corner.model;

import corner.orm.spring.SpringContainer;
import corner.service.EntityService;

/**
 * 抽象的持久化对象，提供了一些常规的操作。
 * <p>提供了增，删，改，查等。
 * @author Jun Tsai
 * @version $Revision$
 * @since 2.0.5
 */
public abstract class AbstractPersistDomain {
	/**
	 * 保存实体。
	 *
	 */
	public void Save(){
		this.getEntityService().saveEntity(this);
	}
	/**
	 * 更新实体
	 *
	 */
	public void update(){
		this.getEntityService().updateEntity(this);
	}
	/**
	 * 保存或者更新
	 *
	 */
	public void saveOrUpdate(){
		this.getEntityService().saveOrUpdateEntity(this);
	}
	/**
	 * 删除一个实体
	 *
	 */
	public void delete(){
		this.getEntityService().deleteEntities(this);
	}
	/**
	 * 得到entity service
	 * @return entity service
	 */
	protected EntityService getEntityService(){
		return (EntityService) SpringContainer.getInstance().getApplicationContext().getBean("entityService");
	}
}
