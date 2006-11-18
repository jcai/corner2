//==============================================================================
//file :        GenericListPage.java
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
			"success=listener:doQueryEntityAction"
			 })
	public abstract IComponent getQueryForm();
	
	@MagicField(entity = "queryEntity")
	public abstract void getMagicField();
}
