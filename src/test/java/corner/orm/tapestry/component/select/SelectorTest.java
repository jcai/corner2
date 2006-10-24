package corner.orm.tapestry.component.select;

import org.apache.hivemind.Registry;
import org.apache.hivemind.lib.SpringBeanFactoryHolder;
import org.apache.tapestry.BaseComponentTestCase;
import org.apache.tapestry.IComponent;
import org.apache.tapestry.IPage;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.dojo.form.Autocompleter;
import org.apache.tapestry.form.Hidden;
import org.apache.tapestry.form.TextField;
import org.apache.tapestry.json.IJSONWriter;
import org.apache.tapestry.services.DataSqueezer;
import org.easymock.EasyMock;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import corner.demo.model.one.A;
import corner.orm.spring.SpringContainer;

import corner.service.EntityService;


public class SelectorTest extends BaseComponentTestCase{
	private A a;
	@BeforeMethod
	public void saveEntity(){
		a=new A();
		a.setCnName("阿菜");
		a.save();
	}
	@AfterMethod
	public void delEntity(){
		a.delete();
	}
	
	
	
	@Test
	public void test_renderComponent() throws Exception{
		
		final Registry reg = buildFrameworkRegistry(new String[]{});
        final DataSqueezer squeezer = ( DataSqueezer )reg.getService( DataSqueezer.class );
        SpringBeanFactoryHolder spring=(SpringBeanFactoryHolder) reg.getService("hivemind.lib.DefaultSpringBeanFactoryHolder",SpringBeanFactoryHolder.class);
        EntityService entityService=(EntityService) spring.getBeanFactory().getBean("entityService");
        
		IJSONWriter json = newBufferJSONWriter();
		IRequestCycle cycle = newMock(IRequestCycle.class);
		
		replay();
		IPoSelectorModel model=new SelectorModel();
		Selector selector=(Selector) newInstance(Selector.class,new Object[]
			                 		                                          { "labelField", "cnName",
	            "filter", "阿", "dataSqueezer", squeezer,"model",model,"queryClassName",A.class.getName(),"entityService",entityService});
		selector.renderComponent(json, cycle);
		verify();
		assertEquals(json.object().get("阿菜"),"S阿菜");
	}
	@Test
	public void test_renderComponentWithObject() throws Exception{
		
		final Registry reg = buildFrameworkRegistry(new String[]{});
        final DataSqueezer squeezer = newMock(DataSqueezer.class );
        SpringBeanFactoryHolder spring=(SpringBeanFactoryHolder) reg.getService("hivemind.lib.DefaultSpringBeanFactoryHolder",SpringBeanFactoryHolder.class);
        EntityService entityService=(EntityService) spring.getBeanFactory().getBean("entityService");
        
        
        String SerialStr="HB:TEST:ID";

        EasyMock.expect(squeezer.squeeze(EasyMock.isA(A.class))).andReturn(SerialStr);

        
		IJSONWriter json = newBufferJSONWriter();
		IRequestCycle cycle = newMock(IRequestCycle.class);
		
		replay();
		IPoSelectorModel model=new SelectorModel();
		Selector selector=(Selector) newInstance(Selector.class,new Object[]
			                 		                                          { "labelField", "cnName",
	            "filter", "阿","returnValueFields","this", "dataSqueezer", squeezer,"model",model,"queryClassName",A.class.getName(),"entityService",entityService});
		selector.renderComponent(json, cycle);
		verify();
		assertEquals(json.object().get("阿菜"),SerialStr);
	}
	@Test
	public void test_Render_Multi_JSON() throws Exception
    {
		final Registry reg = buildFrameworkRegistry(new String[]{});
        final DataSqueezer squeezer = newMock(DataSqueezer.class );
        IPage page=newPage();
        
        SpringBeanFactoryHolder spring=(SpringBeanFactoryHolder) reg.getService("hivemind.lib.DefaultSpringBeanFactoryHolder",SpringBeanFactoryHolder.class);
        EntityService entityService=(EntityService) spring.getBeanFactory().getBean("entityService");
        String SerialStr="HB:TEST:ID";

        EasyMock.expect(squeezer.squeeze(EasyMock.isA(A.class))).andReturn(SerialStr);
		IJSONWriter json = newBufferJSONWriter();
		IRequestCycle cycle = newMock(IRequestCycle.class);

		EasyMock.expect(page.getComponent("cnName")).andReturn(newComponent()).anyTimes();
		
		
		replay();
		IPoSelectorModel model=new SelectorModel();
		Autocompleter selector = newInstance(Selector.class, new Object[]
		                                                                 { "name", "fred", "labelField","cnName","returnValueFields","this,cnName","updateFields","this,cnName","model",  model,
		                                                                     "filter", "阿", "dataSqueezer", squeezer ,"queryClassName","corner.demo.model.one.A","entityService",entityService,"page",page});
		                                                                 
		selector.renderComponent(json, cycle);
		verify();
		assertEquals(json.object().get("阿菜"),"\"HB:TEST:ID\",\"阿菜\"");
		
        
        
		
        
    }
	@Test
	public void test_Render_Multi_Hidden_JSON() throws Exception
    {
		final Registry reg = buildFrameworkRegistry(new String[]{});
        final DataSqueezer squeezer = newMock(DataSqueezer.class );
        IPage page=newPage();
        
        SpringBeanFactoryHolder spring=(SpringBeanFactoryHolder) reg.getService("hivemind.lib.DefaultSpringBeanFactoryHolder",SpringBeanFactoryHolder.class);
        EntityService entityService=(EntityService) spring.getBeanFactory().getBean("entityService");
        String SerialStr="HB:TEST:ID";

        EasyMock.expect(squeezer.squeeze(EasyMock.isA(A.class))).andReturn(SerialStr);
		IJSONWriter json = newBufferJSONWriter();
		IRequestCycle cycle = newMock(IRequestCycle.class);

		EasyMock.expect(page.getComponent("cnName")).andReturn(newComponent()).anyTimes();
		EasyMock.expect(page.getComponent("password")).andReturn((IComponent) newInstance(Hidden.class)).anyTimes();
		
		EasyMock.expect(squeezer.squeeze((Object)null)).andReturn("X");
		
		replay();
		IPoSelectorModel model=new SelectorModel();
		Autocompleter selector = newInstance(Selector.class, new Object[]
		                                                                 { "name", "fred", "labelField","cnName","returnValueFields","this,cnName,password","updateFields","this,cnName,password","model",  model,
		                                                                     "filter", "阿", "dataSqueezer", squeezer ,"queryClassName","corner.demo.model.one.A","entityService",entityService,"page",page});
		                                                                 
		selector.renderComponent(json, cycle);
		verify();
		assertEquals(json.object().get("阿菜"),"\"HB:TEST:ID\",\"阿菜\",\"X\"");
		
        
        
		
        
    }
}
