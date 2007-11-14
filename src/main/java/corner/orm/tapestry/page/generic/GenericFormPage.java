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

import corner.orm.tapestry.page.relative.AbstractMultiManyEntityFormPage;
import corner.orm.tapestry.worker.MagicField;

/**
 * 泛型的表单页面类.
 * 
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.1
 */
public abstract class GenericFormPage<T> extends AbstractMultiManyEntityFormPage<T> implements IGenericPage{

	
	/**
	 * @see corner.orm.tapestry.page.generic.IGenericPage#getGenericForm()
	 */
	@Component(type = "Form", bindings = { "delegate=beans.delegateBean",
			"clientValidationEnabled=true",
			"success=listener:doSaveEntityAction",
			"cancel=listener:doCancelEntityAction",
			"focus=false"})
	public abstract IComponent getGenericForm();

	
	@MagicField(entity = "entity")
	public abstract void getMagicField();

}
