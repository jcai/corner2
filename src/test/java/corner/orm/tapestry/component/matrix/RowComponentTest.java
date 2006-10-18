package corner.orm.tapestry.component.matrix;

import org.apache.tapestry.BaseComponentTestCase;
import org.testng.annotations.Test;

import corner.orm.hibernate.v3.MatrixRow;

public class RowComponentTest extends BaseComponentTestCase{

	@Test
	public void test_Component(){
		
		MatrixRow row=new MatrixRow();
		row.add("L");
		row.add("LX");
		RowComponent c=(RowComponent) this.newInstance(RowComponent.class,new Object[]{"refVector",row});
		c.prepareForRender(newCycle());
		replay();
		assertFalse(c.isFirstNew());
		verify();
		
	}
}
