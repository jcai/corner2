package corner.orm.tapestry.page.generic;

import org.apache.tapestry.IComponent;
import org.apache.tapestry.annotations.Bean;
import org.apache.tapestry.annotations.Component;
import org.apache.tapestry.annotations.InjectObject;
import org.wtstudio.trip.service.ITripService;
import org.wtstudio.trip.tapestry.TripValidationDelegate;

import corner.orm.tapestry.worker.MagicField;

/**
 * 对一些常用操作的接口.
 * @author jcai
 * @version $Revision$
 * @since 0.1
 */
public interface IGenericPage<T> {

	@InjectObject("spring:tripService")
	public abstract ITripService getTripService();

	@Component(type = "Form", bindings = { "delegate=beans.delegateBean",
			"clientValidationEnabled=true",
			"success=listener:doSaveEntityAction",
			"cancel=listener:doCancelEntityAction" })
	public abstract IComponent getGenericForm();

	@MagicField(entity = "entity")
	public abstract void getMagicField();
	
	@Bean
	public abstract TripValidationDelegate getDelegateBean();

}