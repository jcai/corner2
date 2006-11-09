package corner.orm.tapestry.validator;

import java.util.Locale;

import org.apache.tapestry.IPage;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.form.IFormComponent;
import org.apache.tapestry.form.ValidationMessages;
import org.apache.tapestry.form.ValidationMessagesImpl;
import org.apache.tapestry.valid.ValidatorException;
import org.easymock.EasyMock;
import org.testng.annotations.Test;

/**
 * @author xiafei
 * @version $Revision$
 * @since 2.3
 */
public class FieldMaxTest {
	
	
	public void testWrongValidate(){
		FieldMax fieldMax = new FieldMax();
		IFormComponent field=EasyMock.createMock(IFormComponent.class);
		IPage page=EasyMock.createMock(IPage.class);
//		field.getPage().getRequestCycle().getParameter(this.get_fieldMax())
		IRequestCycle cyc = EasyMock.createMock(IRequestCycle.class);
		cyc.setAttribute("aaaField", "20");
		
		
		field.setPage(page);
		
		
		ValidationMessages messages=new ValidationMessagesImpl(field, Locale.getDefault());
		try {
			fieldMax.validate(field,messages,"10");
			fieldMax.set_fieldMax("aaaField");
		}catch(ValidatorException e){
			
		}
	}
}
