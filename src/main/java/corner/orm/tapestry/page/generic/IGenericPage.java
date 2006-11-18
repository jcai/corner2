package corner.orm.tapestry.page.generic;

import org.apache.tapestry.IComponent;
import org.apache.tapestry.annotations.Bean;
import org.apache.tapestry.annotations.Component;

import corner.orm.tapestry.page.CornerValidationDelegate;
import corner.orm.tapestry.worker.MagicField;

/**
 * 对一些常用操作的接口.
 * @author jcai
 * @version $Revision$
 * @since 2.3
 */
public interface IGenericPage<T> {

	
	@Component(type = "Form", bindings = { "delegate=beans.delegateBean",
			"clientValidationEnabled=true",
			"success=listener:doSaveEntityAction",
			"cancel=listener:doCancelEntityAction" })
	public abstract IComponent getGenericForm();

	@MagicField(entity = "entity")
	public abstract void getMagicField();
	
	@Bean
	public abstract CornerValidationDelegate getDelegateBean();

}