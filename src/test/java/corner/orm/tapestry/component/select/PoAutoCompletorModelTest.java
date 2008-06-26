//==============================================================================
// file :       $Id:PoAutoCompletorModelTest.java 2010 2006-10-16 03:34:32Z jcai $
// project:     corner
//
// last change: date:       $Date:2006-10-16 03:34:32Z $
//              by:         $Author:jcai $
//              revision:   $Revision:2010 $
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.orm.tapestry.component.select;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.hivemind.Registry;
import org.apache.hivemind.lib.SpringBeanFactoryHolder;
import org.apache.tapestry.BaseComponentTestCase;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.dojo.form.Autocompleter;
import org.apache.tapestry.json.IJSONWriter;
import org.apache.tapestry.services.DataSqueezer;
import org.easymock.EasyMock;
import org.hibernate.Criteria;
import org.testng.annotations.Test;

import corner.demo.model.one.A;
import corner.orm.spring.SpringContainer;
import corner.service.EntityService;

/**
 * 
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision:2010 $
 * @since 2.2.1
 */
public class PoAutoCompletorModelTest extends BaseComponentTestCase{

	@Test
	public void test_singleValue(){
		A a=new A();
		a.setCnName("阿菜");
		a.save();
		
		DataSqueezer squeezer=EasyMock.createMock(DataSqueezer.class);
		EasyMock.expect(squeezer.squeeze("阿菜")).andReturn("阿菜").anyTimes();
		
		IPoSelectorModel model=new SelectorModel();
		model.setPoClass(A.class);
		model.setEntityService((EntityService) SpringContainer.getInstance().getApplicationContext().getBean("entityService"));
		model.setLabelField("cnName");
		model.setDataSqueezer(squeezer);
		
		EasyMock.replay(squeezer);
		
		
		//test render
		String value="阿菜";
		assertEquals(model.getPrimaryKey(value),value);
		assertEquals(model.getLabelFor(value),value);
		assertEquals(model.getValue(value),value);
		
		
		String match="阿";
		List filteredValues=model.getValues(match);
		assertNotNull(filteredValues);
		assertTrue(filteredValues.size()>0);
		
		Iterator it = filteredValues.iterator();
		while(it.hasNext()){
			Object value1=(Object) it.next();
			assertEquals(model.getLabelFor(value1),value);
		}
		EasyMock.verify(squeezer);
	}
	@Test
	public void test_saveObject(){
		A a=new A();
		a.setCnName("阿菜");
		a.save();
		
		String serialStr="HB:testClass";
		DataSqueezer squeezer=EasyMock.createMock(DataSqueezer.class);
		EasyMock.expect(squeezer.squeeze(EasyMock.anyObject())).andReturn(serialStr).anyTimes();
		EasyMock.expect(squeezer.unsqueeze(serialStr)).andReturn(a).anyTimes();
		EasyMock.replay(squeezer);
		
		
		IPoSelectorModel model=new SelectorModel();
		model.setPoClass(A.class);
		model.setEntityService((EntityService) SpringContainer.getInstance().getApplicationContext().getBean("entityService"));
		model.setLabelField("cnName");
		model.setReturnValueFields(Criteria.ROOT_ALIAS);
		
		model.setDataSqueezer(squeezer);
		
		
		//test render
		A value=a;
		assertEquals(model.getPrimaryKey(value),serialStr);
		assertEquals(model.getLabelFor(value),"阿菜");
		
		String match="阿";
		List filteredValues=model.getValues(match);
		assertNotNull(filteredValues);
		assertTrue(filteredValues.size()>0);
		
		Iterator it = filteredValues.iterator();
		while(it.hasNext()){
			Object value1= it.next();
			String key=(String) model.getPrimaryKey(value1);
			String Label=model.getLabelFor(value);
			assertTrue(Label.startsWith(match));
			assertEquals(squeezer.unsqueeze((String) key),a);
		}
		EasyMock.verify(squeezer);
	}
	@Test
	public void test_Render_JSON()
    {
		A a=new A();
		a.setCnName("阿菜");
		a.save();
		EntityService entityService=(EntityService) SpringContainer.getInstance().getApplicationContext().getBean("entityService");
		
        IRequestCycle cycle = newMock(IRequestCycle.class);
        DataSqueezer ds = newMock(DataSqueezer.class);
        
        IJSONWriter json = newBufferJSONWriter();
        
        IPoSelectorModel model=new SelectorModel();
		model.setPoClass(A.class);
		model.setEntityService(entityService);
		
		model.setReturnValueFields(Criteria.ROOT_ALIAS);
		model.setDataSqueezer(ds);
        
		
        Autocompleter component = newInstance(Selector.class, new Object[]
        { "name", "fred", "model",  model,
            "filter", "阿", "dataSqueezer", ds,"queryClassName",A.class.getName() ,"entityService",entityService,"labelField","cnName"});
        
        String serialStr="HB:corner.orm.A:testid";
        EasyMock.expect(ds.squeeze(EasyMock.isA(A.class))).andReturn(serialStr).anyTimes();
        EasyMock.expect(ds.squeeze("HB:corner.orm.A:testid")).andReturn(serialStr).anyTimes();
        
        EasyMock.expect(ds.squeeze("阿菜")).andReturn("阿菜").anyTimes();
        
        replay();
        
        component.renderComponent(json, cycle);
        
        verify();
        assertEquals(json.object().get("阿菜"),serialStr);
    }
	@Test
	public void test_selectFilter(){
		IPoSelectorModel model=new SelectorModel();
		model.setSelectFilter(new ISelectFilter(){

			public List query(String match, IPoSelectorModel model) {
				// TODO Auto-generated method stub
				return new ArrayList();
			}

			public String getLabelField() {
				// TODO Auto-generated method stub
				return null;
			}

			public String[] getReturnValueFields() {
				// TODO Auto-generated method stub
				return null;
			}});
		model.getValues("asdf");
		assertTrue(model.getReturnValueFields()==null);
	}
	@Test
	public void test_GetLabelFor() throws Exception{
		final Registry reg = buildFrameworkRegistry(new String[]{});
        final DataSqueezer squeezer = ( DataSqueezer )reg.getService( DataSqueezer.class );
        SpringBeanFactoryHolder spring=(SpringBeanFactoryHolder) reg.getService("hivemind.lib.DefaultSpringBeanFactoryHolder",SpringBeanFactoryHolder.class);
        EntityService entityService=(EntityService) spring.getBeanFactory().getBean("entityService");
        
		IPoSelectorModel model=new SelectorModel();
		model.setEntityService(entityService);
		
		replay();
		Object value = "阿菜";
        assertEquals(model.getLabelFor(value),value);
        verify();
        
        A a=new A();
		a.setCnName("阿菜");
		a.save();
		
		model.setLabelField("cnName");
		value=a;
		assertEquals(model.getLabelFor(value),"阿菜");
	}
	
	

}
