// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2006-11-16
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

package corner.orm.tapestry.page.generic;

import org.apache.tapestry.IComponent;
import org.apache.tapestry.annotations.Component;

import corner.orm.tapestry.page.AbstractEntityListPage;
import corner.orm.tapestry.worker.MagicField;

/**
 * 对泛型的列表页面类.
 * 
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.3
 */
public abstract class GenericListPage<T> extends AbstractEntityListPage<T>  implements IGenericPage{

	@Component(type = "contrib:TableView", bindings = { "source=source",
			"value=entity", "columns=literal:id", "element=literal:table",
			"persist=literal:client" })
	public abstract IComponent getTableView();

	
	/**
	 * 
	 * <component type="Form" id="BrandLineForm"> <binding name="delegate"
	 * value="ognl:beans.delegate"/> <binding name="clientValidationEnabled"
	 * value="ognl:true"/> <binding name="success"
	 * value="listener:doSaveEntityAction"/> <binding name="cancel"
	 * value="listener:doCancelEntityAction"/> <binding name="focus"
	 * value="ognl:false"/> </component>
	 */
	@Component(type = "Form", bindings = { "delegate=beans.delegateBean",
			"clientValidationEnabled=true",
			"success=listener:doQueryEntityAction",
			"focus=false"
			 })
	public abstract IComponent getQueryForm();
	
	@MagicField(entity = "queryEntity")
	public abstract void getMagicField();
}
