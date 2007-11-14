/*		
 * Copyright 2006-2007 The Beijing Maxinfo Technology Ltd. 
 * site:http://www.bjmaxinfo.com
 *	file : $Id$
 *	created at:2007-4-30
 */



package corner.orm.tapestry.component.prototype.autocompleter;

import java.util.Map;

import org.apache.tapestry.IAsset;
import org.apache.tapestry.IForm;
import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.IScript;
import org.apache.tapestry.PageRenderSupport;
import org.apache.tapestry.engine.DirectServiceParameter;
import org.apache.tapestry.engine.IEngineService;
import org.apache.tapestry.engine.ILink;
import org.apache.tapestry.form.TranslatedFieldSupport;
import org.apache.tapestry.form.ValidatableFieldSupport;
import org.apache.tapestry.services.DataSqueezer;
import org.apache.tapestry.services.ResponseBuilder;
import org.apache.tapestry.valid.IValidationDelegate;
import org.apache.tapestry.valid.ValidationDelegate;
import org.apache.tapestry.valid.ValidatorException;
import org.easymock.EasyMock;
import org.testng.annotations.Test;

import corner.orm.tapestry.component.BaseFormComponentTestCase;

/**
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision$
 * @since 2.3.7
 */
public class AssociateAutocompleterTest extends BaseFormComponentTestCase{
	@Test
    public void test_Render()
    {           
        
        ValidatableFieldSupport vfs = newMock(ValidatableFieldSupport.class);
        TranslatedFieldSupport tfs = newMock(TranslatedFieldSupport.class);
        
        DataSqueezer ds = newMock(DataSqueezer.class);
        ResponseBuilder resp = newMock(ResponseBuilder.class);
        
        IRequestCycle cycle = newMock(IRequestCycle.class);
        IForm form = newMock(IForm.class);
        EasyMock.checkOrder(form, false);
        
        ValidationDelegate delegate = new ValidationDelegate();
        
        IEngineService engine = newEngineService();
        ILink link = newLink();
        
        IScript script = newMock(IScript.class);
        
       String match = "test value";
       IAsset asset=newMock(IAsset.class);
       
       AssociateAutocompleter component = newInstance(AssociateAutocompleter.class, 
                new Object[] { 
    	   "translatedFieldSupport", tfs,
            "name", "fred", 
            "directService", engine,
            "script", script,
            "validatableFieldSupport", vfs, 
            "value", match,
            "dataSqueezer", ds,
            "indicatorAsset",asset,
            "response",resp
            
        });
       IMarkupWriter writer = newBufferWriter();
       
       trainGetForm(cycle, form);
       trainWasPrerendered(form, writer, component, false);
       expect(form.isRewinding()).andReturn(false);
       expect(cycle.isRewinding()).andReturn(false);
       
       
       expect(tfs.format(component, match)).andReturn(match);
       
        expect(cycle.renderStackPush(component)).andReturn(component);
        
        expect(form.getName()).andReturn("testform").anyTimes();
        
        form.setFormFieldUpdating(true);
        
       
        
        DirectServiceParameter dsp = 
            new DirectServiceParameter(component);
        
        trainGetForm(cycle, form);
        trainWasPrerendered(form, writer, component, false);
        
        trainGetDelegate(form, delegate);
        
        delegate.setFormComponent(component);
        
        trainGetElementId(form, component, "fred");
        trainIsRewinding(form, false);
        expect(cycle.isRewinding()).andReturn(false).anyTimes();
        
        delegate.setFormComponent(component);
        
        expect(cycle.getResponseBuilder()).andReturn(resp).anyTimes();
        expect(resp.isDynamic()).andReturn(false).anyTimes();
        tfs.renderContributions(component, writer, cycle);
        vfs.renderContributions(component, writer, cycle);
        
        expect(ds.squeeze(match)).andReturn("seqValue");
        
        trainGetLinkCheckIgnoreParameter(engine, cycle, true, dsp, link); 
        trainGetURL(link, "urlstring");
        
       expect(asset.buildURL()).andReturn("indicator.gif");

        
        PageRenderSupport prs = newPageRenderSupport();
        trainGetPageRenderSupport(cycle, prs);
        
        script.execute(EasyMock.eq(component), EasyMock.eq(cycle), EasyMock.eq(prs), EasyMock.isA(Map.class));
        
        expect(cycle.renderStackPop()).andReturn(component);
        
        
        replay();
        
        component.render(writer, cycle);
        
        writer.closeTag();
        verify();
        
        this.assertBuffer("<input type=\"text\" autocomplete=\"off\" value=\"test value\" name=\"fred\" id=\"fred\" /><input name=\"fred_ASS\" id=\"fred_ASS\" type=\"hidden\" value=\"seqValue\" />");
    }
	@Test
	public void testRewind() {
		TranslatedFieldSupport tfs = newMock(TranslatedFieldSupport.class);
		ValidatableFieldSupport vfs = newMock(ValidatableFieldSupport.class);
		 DataSqueezer ds = newMock(DataSqueezer.class);
		 AssociateAutocompleter component = newInstance(
				 AssociateAutocompleter.class, new Object[] {
						"translatedFieldSupport", tfs,
						"validatableFieldSupport", vfs,
						"dataSqueezer", ds});

		IRequestCycle cycle = newCycle();

		IForm form = newMock(IForm.class);

		IMarkupWriter writer = newWriter();

		IValidationDelegate delegate = newDelegate();

		expect(cycle.renderStackPush(component)).andReturn(component);
		
		
		trainGetForm(cycle, form);
		trainWasPrerendered(form, writer, component, false);
		trainGetDelegate(form, delegate);

		expect(form.isRewinding()).andReturn(true);
		

		trainGetForm(cycle, form);
		trainWasPrerendered(form, writer, component, false);
//		trainGetDelegate(form, delegate);

		delegate.setFormComponent(component);
		
		

		trainGetElementId(form, component, "barney");
		trainIsRewinding(form, true);

		trainGetParameter(cycle, "barney", "10");
		

		Integer value = new Integer(10);

		try {
			expect(tfs.parse(component, "10")).andReturn(value);

			vfs.validate(component, writer, cycle, value);
		} catch (ValidatorException e) {
			unreachable();
		}

		trainGetParameter(cycle, "barney"+"_ASS", "sequeeString");
		Object assObj=new Object();
		expect(ds.unsqueeze("sequeeString")).andReturn(assObj);
		
		expect(cycle.renderStackPop()).andReturn(component);
		replay();

		component.render(writer, cycle);

		verify();

		assertEquals(assObj, component.getValue());
	}
	@Test
	public void testRewind_null() {
		TranslatedFieldSupport tfs = newMock(TranslatedFieldSupport.class);
		ValidatableFieldSupport vfs = newMock(ValidatableFieldSupport.class);
		 DataSqueezer ds = newMock(DataSqueezer.class);
		 AssociateAutocompleter component = newInstance(
				 AssociateAutocompleter.class, new Object[] {
						"translatedFieldSupport", tfs,
						"validatableFieldSupport", vfs,
						"dataSqueezer", ds});

		IRequestCycle cycle = newCycle();

		IForm form = newMock(IForm.class);

		IMarkupWriter writer = newWriter();

		IValidationDelegate delegate = newDelegate();

		expect(cycle.renderStackPush(component)).andReturn(component);

		trainGetForm(cycle, form);
		trainWasPrerendered(form, writer, component, false);
		trainGetDelegate(form, delegate);

		expect(form.isRewinding()).andReturn(true);
		
		
		trainGetForm(cycle, form);
		trainWasPrerendered(form, writer, component, false);
//		trainGetDelegate(form, delegate);

		delegate.setFormComponent(component);

		trainGetElementId(form, component, "barney");
		trainIsRewinding(form, true);

		trainGetParameter(cycle, "barney", null);
		

		
		try {
			expect(tfs.parse(component, null)).andReturn(null);

			vfs.validate(component, writer, cycle, null);
		} catch (ValidatorException e) {
			unreachable();
		}

		
		expect(cycle.renderStackPop()).andReturn(component);
		replay();

		component.render(writer, cycle);

		verify();

		assertEquals(null, component.getValue());
	}
}
