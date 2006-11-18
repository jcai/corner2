//==============================================================================
//file :        GenericFormPage.java
//
//last change:	date:       $Date$
//           	by:         $Author$
//           	revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Wang Tsai Studio http://www.wtstudio.org
//==============================================================================

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
			"cancel=listener:doCancelEntityAction" })
	public abstract IComponent getGenericForm();

	
	@MagicField(entity = "entity")
	public abstract void getMagicField();

}
