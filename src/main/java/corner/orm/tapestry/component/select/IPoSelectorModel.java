// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2006-10-16
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

package corner.orm.tapestry.component.select;

import org.apache.tapestry.IComponent;
import org.apache.tapestry.dojo.form.IAutocompleteModel;
import org.apache.tapestry.services.DataSqueezer;

import corner.service.EntityService;

public interface IPoSelectorModel extends IAutocompleteModel{
	/**
	 * @return Returns the squeezer.
	 */
	public abstract DataSqueezer getSqueezer();

	/**
	 * @return Returns the entityService.
	 */
	public abstract EntityService getEntityService();

	/**
	 * @return Returns the field.
	 */
	public abstract String getLabelField();

	/**
	 * @return Returns the poClass.
	 */
	public abstract Class getPoClass();
	/**
	 * 得到返回值的字段列表.
	 * @return 返回字段值的列表。
	 */
	public String[] getReturnValueFields();

	/**
	 * @param field The field to set.
	 */
	public void setLabelField(String field);

	public void setEntityService(EntityService entityService);

	public void setDataSqueezer(DataSqueezer squeezer);

	public void setReturnValueFields(String ...strings);

	/**
	 * @param poClass The poClass to set.
	 */
	public void setPoClass(Class poClass);

	public void setSelectFilter(ISelectFilter filter);

	/**
	 * 得到此model所对应的组件.
	 * @return 组件对象
	 */
	public abstract IComponent getComponent();

	public abstract void setComponent(IComponent selector);

	public abstract void setUpdateFields(String[] updateFields);
	public abstract String[] getUpdateFields();

}