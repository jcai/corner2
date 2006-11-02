package corner.orm.tapestry.component.select;

import java.util.HashMap;
import java.util.Map;

import org.apache.tapestry.BaseComponentTestCase;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.json.IJSONWriter;
import org.apache.tapestry.services.DataSqueezer;
import org.easymock.EasyMock;
import org.testng.annotations.Test;

import corner.demo.model.one.A;


public class SelectorTest extends BaseComponentTestCase{
	
	
	
	@Test
	public void test_renderComponent() throws Exception{
		String searchStr="阿";
		IPoSelectorModel model=new SelectorModel();
		
		Map<String,String> resultMap=new HashMap<String,String>();
		resultMap.put("L阿菜","V阿菜");
		
		DataSqueezer squeezer = newMock(DataSqueezer.class);
		IJSONWriter json = newBufferJSONWriter();
		IRequestCycle cycle = newMock(IRequestCycle.class);
		ISelectFilter selectFilter=newMock(ISelectFilter.class);
		EasyMock.expect(selectFilter.query(searchStr, model)).andReturn(resultMap);
		replay();
		
		Selector selector=(Selector) newInstance(Selector.class,new Object[]
			                 		                                          { "labelField", "cnName",
	            "filter", "阿", "selectFilter",selectFilter,"dataSqueezer", squeezer,"model",model,"queryClassName",A.class.getName()});
		selector.renderComponent(json, cycle);
		verify();
		assertEquals(json.object().get("L阿菜"),"V阿菜");
	}
	
	
}
