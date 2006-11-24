package corner.orm.tapestry;

import org.apache.tapestry.BaseComponentTestCase;
import org.apache.tapestry.contrib.table.model.ITableColumn;
import org.easymock.EasyMock;
import org.testng.annotations.Test;

import corner.demo.model.one.A;

public class BeanPropertyTableColumnEvaluatorTest  extends BaseComponentTestCase{
	@Test
	public void test_evaluator(){
		ITableColumn column=newMock(ITableColumn.class);
		EasyMock.expect(column.getColumnName()).andReturn("name");
		replay();
		A a=new A();
		a.setName("Jun");
		BeanPropertyTableColumnEvaluator evaluator=new BeanPropertyTableColumnEvaluator();
		assertEquals("Jun",evaluator.getColumnValue(column,a));
		verify();
	}

}
