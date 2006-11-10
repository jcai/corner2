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
public class FieldMinTest extends BaseComponentTestCase {

	@Test
	public void testRightValidator() {
		double d = 12.0; // 当前组建值
		FieldMin min = new FieldMin();
		String minF = "minField"; // 设置的组建名称
		double minV = 12; // 设置组建值
		min.setFieldMin(minF); // 传入设置组建名称

		IFormComponent field = newMock(IFormComponent.class); // 模拟出一个类，实现了IFormComponent接口
		IPage page = newMock(IPage.class);

		EasyMock.expect(field.getPage()).andReturn(page); // 模拟运行field.getPage()方法，返回什么结果

		IRequestCycle cycle = newCycle();
		EasyMock.expect(page.getRequestCycle()).andReturn(cycle);

		EasyMock.expect(cycle.getParameter(minF)).andReturn(
				Double.toString(minV));

		ValidationMessages messages = null;

		replay();
		try {
			min.validate(field, messages, d);
		} catch (ValidatorException e) {
			fail("can't reacheable!");
		}
		verify();

	}

	@Test
	public void testWrongValidator() {
		double d = 12.0; // 当前组建值
		FieldMin min = new FieldMin();
		String minF = "minField"; // 设置的组建名称
		double minV = 100.01; // 设置组建值
		min.setFieldMin(minF); // 传入设置组建名称

		IFormComponent field = newMock(IFormComponent.class); // 模拟出一个类，实现了IFormComponent接口
		IPage page = newMock(IPage.class);

		EasyMock.expect(field.getPage()).andReturn(page); // 模拟运行field.getPage()方法，返回什么结果

		IRequestCycle cycle = newCycle();
		EasyMock.expect(page.getRequestCycle()).andReturn(cycle);

		EasyMock.expect(cycle.getParameter(minF)).andReturn(
				Double.toString(minV));

		String fieldName = "fieldName";
		EasyMock.expect(field.getDisplayName()).andReturn(fieldName);

		ValidationMessages messages = new ValidationMessagesImpl(field, Locale
				.getDefault());

		replay();
		try {
			min.validate(field, messages, d);
			fail("can't reacheable!");
		} catch (ValidatorException e) {
			assertEquals(e.getMessage(), String.format("fieldName的数值不能小于%s。",
					minV));
		}
		verify();

	}
}
