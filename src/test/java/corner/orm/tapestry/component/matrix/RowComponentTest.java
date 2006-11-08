package corner.orm.tapestry.component.matrix;

import org.apache.tapestry.BaseComponentTestCase;
import org.apache.tapestry.IPage;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.html.BasePage;
import org.easymock.EasyMock;
import org.easymock.IAnswer;
import org.testng.annotations.Test;

import corner.orm.hibernate.v3.MatrixRow;

public class RowComponentTest extends BaseComponentTestCase{

	@Test
	public void test_Component(){
		
		MatrixRow<String> row=new MatrixRow<String>();
		row.add("L");
		row.add("LX");
		MatrixRowField c=(MatrixRowField) this.newInstance(MatrixRowField.class,new Object[]{"refVector",row});
		c.prepareForRender(newCycle());
		replay();
		assertFalse(c.isFirstNew());
		verify();
		
	}
	@Test
	public void test_null_same_dim(){
		
		IRequestCycle cycle=newMock(IRequestCycle.class);
		
		IPage page = newMock(IPage.class);
		
		EasyMock.expect(page.getRequestCycle()).andReturn(cycle);
		EasyMock.expect(cycle.isRewinding()).andReturn(true).anyTimes();
		
		
		
		replay();
		
		MatrixRowField c=(MatrixRowField) this.newInstance(MatrixRowField.class,"page",page);
		
		

		c.setElementValue(null);
		
		assertEquals(c.getValue().size(),1);
		assertEquals(c.getValue().getRowSum(),0.0);
		
		verify();
	}
}
