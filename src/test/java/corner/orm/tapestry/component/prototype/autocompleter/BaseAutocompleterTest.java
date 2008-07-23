/*		
 * Copyright 2006-2007 The Beijing Maxinfo Technology Ltd. 
 * site:http://www.bjmaxinfo.com
 *	file : $Id$
 *	created at:2007-4-28
 */

package corner.orm.tapestry.component.prototype.autocompleter;

import java.io.CharArrayWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
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
import org.apache.tapestry.markup.MarkupFilter;
import org.apache.tapestry.markup.MarkupWriterImpl;
import org.apache.tapestry.markup.UTFMarkupFilter;
import org.apache.tapestry.services.DataSqueezer;
import org.apache.tapestry.services.ResponseBuilder;
import org.apache.tapestry.valid.IValidationDelegate;
import org.apache.tapestry.valid.ValidationDelegate;
import org.apache.tapestry.valid.ValidatorException;
import org.easymock.EasyMock;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.testng.annotations.Test;

import corner.orm.hibernate.v3.HibernateObjectRelativeUtils;
import corner.orm.tapestry.component.BaseFormComponentTestCase;
import corner.orm.tapestry.state.IContext;
import corner.orm.tapestry.state.MockContext;
import corner.service.EntityService;

/**
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision$
 * @since 2.3.7
 */
public class BaseAutocompleterTest extends BaseFormComponentTestCase {

	private CharArrayWriter _writer;

	@Test
	public void teset_render_searializable() {
		PrintWriter writer = newPrintWriter();
		MarkupFilter filter = new UTFMarkupFilter();
		IMarkupWriter mw = new MarkupWriterImpl("text/html", writer, filter);

		EntityService e = null;
		
//		EntityService e = new EntityService();
//		HibernateObjectRelativeUtils oru = new HibernateObjectRelativeUtils();
//		HibernateTemplate template = new HibernateTemplate();
//		oru.setHibernateTemplate(template);
//		e.setObjectRelativeUtils(oru);
		

		IContext context = new MockContext();
		

		ISelectModel model = newMock(ISelectModel.class);
		
		List<String[]> list = new ArrayList<String[]>();
		String[] v = { "inde code", "chn name", "eng name" };
		list.add(v);

		
		BaseAutocompleter completer = (BaseAutocompleter) newInstance(
				CodeModelAutocompleter.class, "entityService", e, "context",
				context, "selectModel", model);

		model.setComponent(completer);
		
		EasyMock.expect(model.search(null, null, null, context,null,null)).andReturn(list);

		model.renderResultRow(mw, v, null, null);


		IRequestCycle cycle = newCycle();

		replay();
		completer.renderPrototypeComponent(mw, cycle);
		verify();
		assertOutput("<ul><li></li></ul>");
		System.out.println(_writer.toString());
	}

	@Test
	public void testRewind() {
		TranslatedFieldSupport tfs = newMock(TranslatedFieldSupport.class);
		ValidatableFieldSupport vfs = newMock(ValidatableFieldSupport.class);

		CodeModelAutocompleter component = newInstance(
				CodeModelAutocompleter.class, new Object[] {
						"translatedFieldSupport", tfs,
						"validatableFieldSupport", vfs });

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

		expect(cycle.renderStackPop()).andReturn(component);

		replay();

		component.render(writer, cycle);

		verify();

		assertEquals(value, component.getValue());
	}

	
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
       
       CodeModelAutocompleter component = newInstance(CodeModelAutocompleter.class, 
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
        
        this.assertBuffer("<input type=\"text\" autocomplete=\"off\" value=\"test value\" name=\"fred\" id=\"fred\" />");
    }
	private void assertOutput(String expected) {
		assertEquals(expected, _writer.toString());

		_writer.reset();
	}

	private PrintWriter newPrintWriter() {
		_writer = new CharArrayWriter();

		return new PrintWriter(_writer);
	}

}
