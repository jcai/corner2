// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2006-07-03
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

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
	public void save(){
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
