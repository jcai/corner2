package corner.orm.tapestry.translator;

import static org.easymock.EasyMock.aryEq;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;

import java.util.Locale;

import org.apache.tapestry.BaseComponentTestCase;
import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.form.FormComponentContributorContext;
import org.apache.tapestry.form.IFormComponent;
import org.apache.tapestry.form.ValidationMessages;
import org.apache.tapestry.form.translator.NumberTranslator;
import org.apache.tapestry.json.JSONObject;
import org.apache.tapestry.valid.ValidationStrings;
import org.apache.tapestry.valid.ValidatorException;
import org.easymock.EasyMock;
import org.testng.annotations.Test;

import corner.orm.hibernate.v3.MatrixRow;

public class VectorTranslatorTest extends BaseComponentTestCase {
	@Test
	public void test_formatObject() {
		VectorTranslator translator = new VectorTranslator();
		MatrixRow<String> row = new MatrixRow<String>();
		row.add("test1");
		row.add("test2");
		row.add("test3");

		assertEquals(translator.format(null, null, row), "test1,test2,test3");
	}

	@Test
	public void test_validate_pattern() {
		VectorTranslator translator = new VectorTranslator();
		assertEquals(translator.validateText("test1,test2,test3"), true);
		assertEquals(translator.validateText("test1,,test2,test3"), false);
		assertEquals(translator.validateText("test1,a,,atest2,test3"), false);
	}

	@Test
	public void test_parse_right_text() {
		VectorTranslator translator = new VectorTranslator();
		try {
			MatrixRow<String> row = (MatrixRow<String>) translator.parseText(
					null, null, "test1,test2,test3");
			assertEquals(3, row.size());

			assertEquals("test1", row.get(0));
			assertEquals("test2", row.get(1));
			assertEquals("test3", row.get(2));

		} catch (ValidatorException e) {
			fail("不能到达此处!");
		}

	}

	@Test
	public void test_parse_wrong_text() {
		VectorTranslator translator = new VectorTranslator();
		IFormComponent field = newMock(IFormComponent.class);
		EasyMock.expect(field.getDisplayName()).andReturn("My Field");
		ValidationMessages messages = newMock(ValidationMessages.class);
		String wrongMessage = "My Field的格式不正确，正确格式是以逗号分割的字符串.";
		EasyMock.expect(
				messages.formatValidationMessage(
						eq("{0}的格式不正确，正确格式是以逗号分割的字符串."), (String) eq(null),
						aryEq(new String[] { "My Field" }))).andReturn(
				wrongMessage);
		replay();
		try {
			MatrixRow<String> row = (MatrixRow<String>) translator.parseText(
					field, messages, "test1,test2,,test3");
			fail("不能到达此处!");
		} catch (ValidatorException e) {
			assertEquals(e.getMessage(), wrongMessage);
		}
		verify();
	}
	@Test
    public void testRenderContribution()
    {
		VectorTranslator translator = new VectorTranslator();
		IFormComponent field = newMock(IFormComponent.class);
		
		 JSONObject json = new JSONObject();
		 
		
		
		
	
		
        
       
        
        IMarkupWriter writer = newWriter();
        IRequestCycle cycle = newCycle();
        
        FormComponentContributorContext context = newMock(FormComponentContributorContext.class);
	       
        expect(context.getProfile()).andReturn(json);
        expect(field.getClientId()).andReturn("clientId");
        expect(field.getDisplayName()).andReturn("My Field");
		
		
        
        String wrongMessage = "My Field的格式不正确，正确格式是以逗号分割的字符串.";
        expect(context.formatValidationMessage(eq("{0}的格式不正确，正确格式是以逗号分割的字符串."), (String) eq(null),
				aryEq(new String[] { "My Field" }))).andReturn(wrongMessage);
        expect(field.getClientId()).andReturn("clientId").times(3);
        

        
        replay();
        
        translator.renderContribution(writer, cycle, context, field);
        
        verify();
        
        assertEquals( "{\"constraints\":{\"clientId\":[[tapestry.form.validation.isValidPattern,\"^([^,]+,)*[^,]+$\"]]},\"clientId\":{\"constraints\":[\"My Field的格式不正确，正确格式是以逗号分割的字符串.\"]}}",
                json.toString());
    }
             
}
