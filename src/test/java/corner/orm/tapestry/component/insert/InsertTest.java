package corner.orm.tapestry.component.insert;

import org.apache.tapestry.BaseComponentTestCase;
import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.testng.annotations.Test;

import static org.easymock.EasyMock.*;

public class InsertTest extends BaseComponentTestCase {
	@Test
	public void testNoFormat() {
		IMarkupWriter writer = newWriter();
		IRequestCycle cycle = newCycle(false);

		Insert insert = newInstance(Insert.class, new Object[] { "value",
				"123456789", "length",5});

		expect(cycle.renderStackPush(insert)).andReturn(insert);

		writer.print("12345...", false);

		expect(cycle.renderStackPop()).andReturn(insert);

		replay();

		insert.render(writer, cycle);

		verify();
	}
	@Test
	public void test_with_ChieseFormat() {
		IMarkupWriter writer = newWriter();
		IRequestCycle cycle = newCycle(false);

		Insert insert = newInstance(Insert.class, new Object[] { "value",
				"我123456", "length",5});

		expect(cycle.renderStackPush(insert)).andReturn(insert);

		writer.print("我1234...", false);

		expect(cycle.renderStackPop()).andReturn(insert);

		replay();

		insert.render(writer, cycle);

		verify();
	}
	@Test
	public void test_with_empty_string() {
		IMarkupWriter writer = newWriter();
		IRequestCycle cycle = newCycle(false);

		Insert insert = newInstance(Insert.class, new Object[] { "value",
				"1         ", "length",5});

		expect(cycle.renderStackPush(insert)).andReturn(insert);

		writer.print("1         ", false);

		expect(cycle.renderStackPop()).andReturn(insert);

		replay();

		insert.render(writer, cycle);

		verify();
	}
}
