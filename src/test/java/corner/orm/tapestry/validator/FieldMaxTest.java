package corner.orm.tapestry.validator;

import java.util.Locale;

import org.apache.tapestry.BaseComponentTestCase;
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
public class FieldMaxTest extends BaseComponentTestCase{
	
	
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
	@Test
	public void testRightValidator(){
		double d=12.0;
		FieldMax max=new FieldMax();
		String maxF="maxField";
		double maxV=12;
		max.setFieldMax(maxF);
		
		
		
		IFormComponent field=newMock(IFormComponent.class);
		IPage page=newMock(IPage.class);
		
		EasyMock.expect(field.getPage()).andReturn(page);
		
		IRequestCycle cycle=newCycle();
		EasyMock.expect(page.getRequestCycle()).andReturn(cycle);
		
		EasyMock.expect(cycle.getParameter(maxF)).andReturn(Double.toString(maxV));
		
		
		ValidationMessages messages=null;
		
		replay();
		try {
			max.validate(field, messages, d);
		} catch (ValidatorException e) {
			fail("can't reacheable!");
		}
		verify();
		
	}
	@Test
	public void testWrongValidator(){
		double d=12.0; 
		FieldMax max=new FieldMax();
		String maxF="maxField";
		double maxV=100;
		max.setFieldMax(maxF);
		
		
		
		IFormComponent field=newMock(IFormComponent.class);
		IPage page=newMock(IPage.class);
		
		EasyMock.expect(field.getPage()).andReturn(page);
		
		IRequestCycle cycle=newCycle();
		EasyMock.expect(page.getRequestCycle()).andReturn(cycle);
		
		EasyMock.expect(cycle.getParameter(maxF)).andReturn(Double.toString(maxV));
		
		String fieldName="fieldName";
		EasyMock.expect(field.getDisplayName()).andReturn(fieldName);
		
		ValidationMessages messages=new ValidationMessagesImpl(field, Locale.getDefault());
		
		replay();
		try {
			max.validate(field, messages, d);
			fail("can't reacheable!");
		} catch (ValidatorException e) {
			assertEquals(e.getMessage(),"必须小于或等于fieldName.");
		}
		verify();
		
	}
}
