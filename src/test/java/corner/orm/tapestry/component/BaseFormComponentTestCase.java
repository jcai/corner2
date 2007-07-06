/*		
 * Copyright 2006-2007 The Beijing Maxinfo Technology Ltd. 
 * site:http://www.bjmaxinfo.com
 *	file : $Id: BaseFormComponentTestCase.java 5370 2007-04-28 02:36:29Z jcai $
 *	created at:2007-4-28
 */



package corner.orm.tapestry.component;

import org.apache.tapestry.BaseComponentTestCase;
import org.apache.tapestry.IActionListener;
import org.apache.tapestry.IBinding;
import org.apache.tapestry.IComponent;
import org.apache.tapestry.IForm;
import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.TapestryUtils;
import org.apache.tapestry.form.IFormComponent;
import org.apache.tapestry.valid.IValidationDelegate;
import org.easymock.EasyMock;
import org.easymock.IExpectationSetters;

/**
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision$
 * @since 2.3.7
 */
public class BaseFormComponentTestCase extends BaseComponentTestCase{
	protected IValidationDelegate newDelegate()
    {
        return newMock(IValidationDelegate.class);
    }

    protected void trainIsInError(IValidationDelegate delegate, boolean isInError)
    {
        expect(delegate.isInError()).andReturn(isInError);
    }

    protected IForm newForm()
    {
        IForm mock = newMock(IForm.class);
        
       EasyMock.checkOrder(mock, false);
        
        return mock;
    }

    protected void trainGetForm(IRequestCycle cycle, IForm form)
    {
        expect(cycle.getAttribute(TapestryUtils.FORM_ATTRIBUTE)).andReturn(form);
    }

    protected void trainGetDelegate(IForm form, IValidationDelegate delegate)
    {
        expect(form.getDelegate()).andReturn(delegate).anyTimes();
    }

    protected void trainGetParameter(IRequestCycle cycle, String parameterName,
            String parameterValue)
    {
        expect(cycle.getParameter(parameterName)).andReturn(parameterValue);
    }

    protected void trainWasPrerendered(IForm form, IMarkupWriter writer, IComponent component,
            boolean wasPrerendered)
    {
        expect(form.wasPrerendered(writer, component)).andReturn(wasPrerendered);
    }

    protected void trainIsRewinding(IForm form, boolean isRewinding)
    {
        expect(form.isRewinding()).andReturn(isRewinding);
    }
    
    protected void trainGetElementId(IForm form, IFormComponent component, String name)
    {
        form.getElementId(component);
        component.setName(name);
        component.setClientId(name);
        EasyMock.expectLastCall().andReturn(name);
    }

    protected IBinding newBinding()
    {
        return newMock(IBinding.class);
    }

    protected IActionListener newListener()
    {
        return newMock(IActionListener.class);
    }

    protected IFormComponent newField()
    {
        return newMock(IFormComponent.class);
    }
    protected <T> IExpectationSetters<T> expect(T t){
    	return EasyMock.expect(t);
    }
    
}
