package corner.orm.tapestry.validator;

import org.apache.tapestry.IPage;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.form.FormComponentContributorContext;
import org.apache.tapestry.form.IFormComponent;
import org.apache.tapestry.form.validator.BaseValidatorTestCase;
import org.apache.tapestry.valid.ValidatorException;
import org.easymock.EasyMock;
import org.testng.annotations.Test;

public class FieldPlusEqTest extends BaseValidatorTestCase {
	
	@Test
	public void testRenderContribution_right(){
		FieldPlusEq eq=new FieldPlusEq("fieldPlusEq={f1:f2}");
		IFormComponent field=this.newField();
		IRequestCycle cycle=newCycle();
//		FormComponentContributorContext context = new Context();
//		eq.renderContribution(writer, cycle, context, field);
	}
	
	@Test
	public void test_functionRight(){
		FieldPlusEq eq=new FieldPlusEq("fieldPlusEq={f1:f2}");
		String [] fields = {"f1","f2"};
		String t = eq.toFieldString(fields);
		assertEquals("\"f1\",\"f2\"",t);
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
