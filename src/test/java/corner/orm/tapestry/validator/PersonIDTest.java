package corner.orm.tapestry.validator;

import java.text.DecimalFormatSymbols;
import java.util.Locale;

import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.form.FormComponentContributorContext;
import org.apache.tapestry.form.IFormComponent;
import org.apache.tapestry.form.ValidationMessages;
import org.apache.tapestry.form.ValidationMessagesImpl;
import org.apache.tapestry.form.validator.BaseValidatorTestCase;
import org.apache.tapestry.form.validator.Max;
import org.apache.tapestry.json.JSONObject;
import org.apache.tapestry.valid.ValidationStrings;
import org.apache.tapestry.valid.ValidatorException;
import org.easymock.EasyMock;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PersonIDTest extends BaseValidatorTestCase{
	@Test
	public void testWrongValidate(){
		PersonID personID=new PersonID();

		IFormComponent field=EasyMock.createMock(IFormComponent.class);
		EasyMock.expect(field.getDisplayName()).andReturn("身份证号码");
		EasyMock.replay(field);
		ValidationMessages messages=new ValidationMessagesImpl(field, Locale.getDefault());
		try {
			personID.validate(field,messages, "413028198009121513");
			Assert.fail("不应该执行到这里!");
		} catch (ValidatorException e) {
			Assert.assertEquals(e.getMessage(),"身份证号码不正确.格式为15／18位身份证号码.");
		}
		EasyMock.verify(field);
	}
	@Test
	public void testRightValidate(){
		PersonID personID=new PersonID();

		IFormComponent field=EasyMock.createMock(IFormComponent.class);
		
		EasyMock.replay(field);
		ValidationMessages messages=new ValidationMessagesImpl(field, Locale.getDefault());
		try {
			personID.validate(field,messages, "413028198009121514");
		} catch (ValidatorException e) {
			Assert.fail("不应该执行到这里!");
		}
		EasyMock.verify(field);
	}
	@Test
    public void testRenderContribution()
    {
        IMarkupWriter writer = newWriter();
        IRequestCycle cycle = newCycle();
        JSONObject json = new JSONObject();
        
        IFormComponent field = newField("My Field", "myfield");
        
        FormComponentContributorContext context = newMock(FormComponentContributorContext.class);
        context.addInitializationScript(field,"dojo.require(\"corner.validate.web\");");
        
        Locale locale = Locale.FRANCE;
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(locale);
        
//        EasyMock.expect(context.getLocale()).andReturn(locale);
        
        EasyMock.expect(context.getProfile()).andReturn(json);
        
//        EasyMock.expect(context.formatValidationMessage("{0}不正确.格式为15／18位身份证号码.", null, new Object[]{"My Field"})).andReturn("");
        trainFormatMessage(context,"{0}不正确.格式为15／18位身份证号码.",null,new Object[]{"My Field"},"My Field不正确.格式为15／18位身份证号码.");
        replay();
        
        new PersonID().renderContribution(writer, cycle, context, field);

        verify();
        
        assertEquals("{\"constraints\":{\"myfield\":[[corner.validate.isPersonID,false,true]]},"
                + "\"myfield\":{\"constraints\":[\"My Field不正确.格式为15／18位身份证号码.\"]}}",
                json.toString());
    }
}
