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

import java.util.List;

import org.apache.hivemind.ApplicationRuntimeException;
import org.apache.tapestry.IComponent;
import org.apache.tapestry.annotations.Bean;
import org.apache.tapestry.annotations.Component;
import org.apache.tapestry.annotations.InjectObject;
import org.wtstudio.trip.model.config.SiteConfig;
import org.wtstudio.trip.model.glossary.ICodeModel;
import org.wtstudio.trip.service.ITripService;
import org.wtstudio.trip.tapestry.TripValidationDelegate;

import corner.orm.tapestry.page.AbstractEntityFormPage;
import corner.orm.tapestry.worker.MagicField;
import corner.util.HanziUtils;

/**
 * 泛型的表单页面类.
 * 
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.1
 */
public abstract class GenericFormPage<T> extends AbstractEntityFormPage<T> implements IGenericPage<T> {

	/**
	 * @see corner.orm.tapestry.page.generic.IGenericPage#getTripService()
	 */
	@InjectObject("spring:tripService")
	public abstract ITripService getTripService();
	

	/**
	 * @see corner.orm.tapestry.page.generic.IGenericPage#getGenericForm()
	 */
	@Component(type = "Form", bindings = { "delegate=beans.delegateBean",
			"clientValidationEnabled=true",
			"success=listener:doSaveEntityAction",
			"cancel=listener:doCancelEntityAction" })
	public abstract IComponent getGenericForm();

	/**
	 * @see corner.orm.tapestry.page.generic.IGenericPage#getMagicField()
	 */
	@MagicField(entity = "entity")
	public abstract void getMagicField();

	/**
	 * @see corner.orm.tapestry.page.AbstractEntityPage#saveOrUpdateEntity()
	 */
	@Override
	protected void saveOrUpdateEntity() {
		T t = this.getEntity();
		if (t instanceof ICodeModel) {
			ICodeModel model = (ICodeModel) t;
			String name = model.getName();
			if (name != null)
				model.setPinyin(HanziUtils.getPinyin(name)); //转换为拼音检索码
		}
		super.saveOrUpdateEntity();
	}
	

}
