package corner.orm.tapestry.validator;

import java.util.Locale;

import org.apache.tapestry.form.IFormComponent;
import org.apache.tapestry.form.ValidationMessages;
import org.apache.tapestry.form.ValidationMessagesImpl;
import org.apache.tapestry.valid.ValidatorException;
import org.easymock.EasyMock;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PersonIDTest {
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
}
