//==============================================================================
// file :       $Id$
// project:     corner
//
// last change: date:       $Date$
//              by:         $Author$
//              revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.orm.tapestry.component.matrix;

import org.apache.tapestry.BaseComponent;
import org.apache.tapestry.IForm;
import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.TapestryUtils;
import org.apache.tapestry.annotations.Component;
import org.apache.tapestry.annotations.Parameter;
import org.apache.tapestry.form.IFormComponent;
import org.apache.tapestry.form.TextField;
import org.apache.tapestry.form.ValidatableField;
import org.apache.tapestry.form.ValidatableFieldSupport;
import org.apache.tapestry.form.ValidationMessages;
import org.apache.tapestry.form.ValidationMessagesImpl;
import org.apache.tapestry.form.translator.Translator;
import org.apache.tapestry.valid.IValidationDelegate;
import org.apache.tapestry.valid.ValidatorException;

import corner.orm.hibernate.v3.MatrixRow;

/**
 * 行组件，来产生组件.
 * 
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.2.2
 */
public abstract class MatrixRowField extends BaseComponent implements
		IFormComponent, ValidatableField {

	@Parameter(required = true)
	public abstract MatrixRow getValue();

	public abstract void setValue(MatrixRow value);

	private int star = 0;

	@Parameter(required = true)
	public abstract MatrixRow getRefVector();

	@Parameter
	public abstract Object getDefaultValue();
	
	@Parameter(defaultValue="false")
	public abstract Boolean getReadOnly();
	
	@Parameter(defaultValue = "translator:string")
	public abstract Translator getTranslator();

	@Component(type = "TextField", bindings = { "displayName=displayName",
			"class=inputClass", "value=elementValue", "translator=translator","defaultValue=defaultValue","readOnly=readOnly" })
	public abstract TextField getElementTextField();

	public abstract IForm getForm();

	public abstract void setForm(IForm form);

	public abstract String getName();

	public abstract void setName(String name);

//	@InjectScript("MatrixRowField.script")
//	public abstract IScript getScript();
	/**
	 * 得到循环元素的值.
	 * 
	 * @return
	 * @throws ValidatorException
	 *             当错误验证的时候.
	 */
	public Object getElementValue() throws ValidatorException {
		if (getValue().size() > star) {
			ValidationMessages messages = new ValidationMessagesImpl(this, this
					.getPage().getLocale());
			return this.getTranslator().parse(this, messages,
					 getValue().get(star++).toString());
			// return getValue().get(star++);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public void setElementValue(Object value) {
		if (this.getPage().getRequestCycle().isRewinding()) {
			if (star == 0) {
				setValue(new MatrixRow());
			}
//			if (value != null) {
//			getValue().add(star++, value==null?"":value);
			getValue().add(star++, value);
//			}
		}
	}

	/**
	 * 是否为第一次增加。
	 * 
	 * @return
	 */
	public boolean isFirstNew() {
		return this.getRefVector() == null || this.getRefVector().size() == 0;
	}

	/**
	 * 
	 * @see org.apache.tapestry.AbstractComponent#prepareForRender(org.apache.tapestry.IRequestCycle)
	 */
	@Override
	protected void prepareForRender(IRequestCycle arg0) {
		star = 0;
		if (this.getValue() == null) {
			setValue(new MatrixRow());
		}
	}

	/**
	 * @see org.apache.tapestry.BaseComponent#renderComponent(org.apache.tapestry.IMarkupWriter,
	 *      org.apache.tapestry.IRequestCycle)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void renderComponent(IMarkupWriter writer, IRequestCycle cycle) {
		IForm form = TapestryUtils.getForm(cycle, this);
		this.setForm(form);
		
		if (form.wasPrerendered(writer, this))
			return;

		IValidationDelegate delegate = form.getDelegate();

		delegate.setFormComponent(this);
		
		form.getElementId(this);
		
		form.getDelegate().writePrefix(writer, cycle, this, null);
		super.renderComponent(writer, cycle);
		
		if (form.isRewinding()) {

			try {

				getValidatableFieldSupport().validate(this, writer, cycle,
						((MatrixRow)this.getValue()).getRowSum());

			} catch (ValidatorException e) {
				getForm().getDelegate().record(e);
			}
		} else {
			
//			getValidatableFieldSupport().renderContributions(this, writer,
//					cycle);
//			
//			PageRenderSupport prs = TapestryUtils.getPageRenderSupport(cycle, this);
//			HashMap parms = new HashMap();
//			parms.put("len",this.getRefVector().size());
//			parms.put("fieldName",this.getClientId());
//			getScript().execute(this, cycle, prs, parms);

		}
		
	}

	/**
	 * Injected.
	 */
	public abstract ValidatableFieldSupport getValidatableFieldSupport();

}
