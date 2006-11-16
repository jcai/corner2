package corner.orm.tapestry.validator;

import java.text.DecimalFormatSymbols;
import java.util.Locale;

import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IPage;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.form.FormComponentContributorContext;
import org.apache.tapestry.form.IFormComponent;
import org.apache.tapestry.form.ValidationMessages;
import org.apache.tapestry.form.ValidationMessagesImpl;
import org.apache.tapestry.form.validator.BaseValidatorTestCase;
import org.apache.tapestry.json.JSONObject;
import org.apache.tapestry.valid.ValidatorException;
import org.easymock.EasyMock;
import org.testng.annotations.Test;

public class FieldPlusEqTest extends BaseValidatorTestCase {
	
	@Test
	public void testRenderContribution(){
		
		IMarkupWriter writer = newWriter();
        JSONObject json = new JSONObject();
        
		IFormComponent f=newField("Field","f");
		IFormComponent f1=newField("Field 1","f1");
		IFormComponent f2=newField("Field 2","f2");
		IPage page=newPage();
		IRequestCycle cycle=newCycle();
		
		EasyMock.expect(f.getPage()).andReturn(page).times(2);
		
		EasyMock.expect(page.getComponent("f1")).andReturn(f1);
		EasyMock.expect(page.getComponent("f2")).andReturn(f2);
        
        FormComponentContributorContext context = newMock(FormComponentContributorContext.class);
        
        context.addInitializationScript(f,"dojo.require(\"corner.validate.web\");");
        
        Locale locale = Locale.getDefault();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(locale);
        
        EasyMock.expect(context.getLocale()).andReturn(locale);
        
        EasyMock.expect(context.getProfile()).andReturn(json);
		
		replay();
        
        new FieldPlusEq("fieldPlusEq={f1:f2}").renderContribution(writer, cycle, context, f);

        verify();
        
        assertEquals("{\"constraints\":{\"f\":[[corner.validate.isFieldPlusEq,{\"fields\":[" +
        		"\"f1\",\"f2\"],decimal:\""
                + symbols.getDecimalSeparator() + "\"}]]},"
                + "\"f\":{\"constraints\":[\"Field 1,Field 2之和必须等于Field.\"]}}",
                json.toString());
	}
	
	@Test
	public void test_wrong_plus(){
		double d=32;
		IFormComponent f=newField("Field","f");
		IFormComponent f1=newField("Field 1","f1");
		IFormComponent f2=newField("Field 2","f2");
		IPage page=newPage();
		IRequestCycle cycle=newCycle();
		
		EasyMock.expect(f.getPage()).andReturn(page).times(3);
		EasyMock.expect(page.getRequestCycle()).andReturn(cycle);
		
		EasyMock.expect(cycle.getParameter("f1")).andReturn("12");
		EasyMock.expect(cycle.getParameter("f2")).andReturn("123");
		
		EasyMock.expect(page.getComponent("f1")).andReturn(f1);
		EasyMock.expect(page.getComponent("f2")).andReturn(f2);
		
		ValidationMessages messages = new ValidationMessagesImpl(f, Locale
				.getDefault());
		
		replay();
		
		FieldPlusEq eq=new FieldPlusEq("fieldPlusEq={f1:f2}");
		try {
			eq.validate(f, messages, d);
			fail("不能到达!");
		} catch (ValidatorException e) {
			assertEquals(e.getMessage(), "Field 1,Field 2之和必须等于Field.");
		}
		verify();
	}
	
	@Test
	public void test_functionRight(){
		String [] fields = {"f1","f2"};
		IFormComponent f=newMock(IFormComponent.class);
		IFormComponent f1=newField("Field 1","f1");
		IFormComponent f2=newField("Field 2","f2");
		IPage page=newPage();
		
		
		EasyMock.expect(f.getPage()).andReturn(page).times(2);
		
		EasyMock.expect(page.getComponent("f1")).andReturn(f1);
		EasyMock.expect(page.getComponent("f2")).andReturn(f2);
		
		FieldPlusEq eq=new FieldPlusEq("fieldPlusEq={f1:f2}");
		
		replay();
		
		
		String t = eq.toFieldString(fields);
		
		String t2 = eq.getDisplayNames(fields, f);
		
		assertEquals("\"f1\",\"f2\"",t);
		
		assertEquals("Field 1,Field 2",t2);
		
		verify();
		
		
	}
	
	@Test
	public void test_right(){
		FieldPlusEq eq=new FieldPlusEq("fieldPlusEq={f1:f2}");
		IFormComponent field=this.newField();
		IPage page=newPage();
		IRequestCycle cycle=newCycle();
		EasyMock.expect(field.getPage()).andReturn(page);
		EasyMock.expect(page.getRequestCycle()).andReturn(cycle);
		EasyMock.expect(cycle.getParameter("f1")).andReturn("20");
		EasyMock.expect(cycle.getParameter("f2")).andReturn("12");
		
		replay();
		try {
			eq.validate(field, null, 32);
		} catch (ValidatorException e) {
			fail("不该到达这里!");
		}
		verify();
		
	}
	
	

}
