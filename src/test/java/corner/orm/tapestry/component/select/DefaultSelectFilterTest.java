package corner.orm.tapestry.component.select;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tapestry.BaseComponentTestCase;
import org.apache.tapestry.IComponent;
import org.apache.tapestry.IPage;
import org.apache.tapestry.form.Hidden;
import org.apache.tapestry.form.IFormComponent;
import org.apache.tapestry.services.DataSqueezer;
import org.easymock.EasyMock;
import org.testng.annotations.Test;

import corner.demo.model.one.A;

public class DefaultSelectFilterTest extends BaseComponentTestCase {
	@Test
	public void test_convert_list_map() {
		final String name = "阿菜";
		DefaultSelectFilter filter = new DefaultSelectFilter() {

			/**
			 * @see corner.orm.tapestry.component.select.AbstractSelectFilter#query(java.lang.String,
			 *      corner.orm.tapestry.component.select.IPoSelectorModel)
			 */
			@Override
			public Map query(String match, IPoSelectorModel model) {
				this.model = model;
				List<A> list = new ArrayList<A>();
				A a = new A();
				a.setName(name);
				list.add(a);
				return this.convertListAsMap(list);
			}
		};

		IPoSelectorModel model = newMock(IPoSelectorModel.class);
		EasyMock.expect(model.getReturnValueFields()).andReturn(
				new String[] { "name" });
		EasyMock.expect(model.getUpdateFields()).andReturn(
				new String[] { "name" });
		EasyMock.expect(model.getLabelField()).andReturn("name").times(2);

		DataSqueezer squeezer = newMock(DataSqueezer.class);

		EasyMock.expect(model.getSqueezer()).andReturn(squeezer);

		EasyMock.expect(squeezer.squeeze(name)).andReturn("S" + name);

		replay();

		Map map = filter.query(name, model);

		assertEquals(map.size(), 1);

		assertEquals(map.get(name), "S" + name);

		verify();

	}

	@Test
	public void test_convert_many_list_map() {
		final String name = "阿菜";
		final A a = new A();
		a.setName(name);
		a.setPassword("password");
		a.setCnName("hello");

		DefaultSelectFilter filter = new DefaultSelectFilter() {

			/**
			 * @see corner.orm.tapestry.component.select.AbstractSelectFilter#query(java.lang.String,
			 *      corner.orm.tapestry.component.select.IPoSelectorModel)
			 */
			@Override
			public Map query(String match, IPoSelectorModel model) {
				this.model = model;
				List<A> list = new ArrayList<A>();

				list.add(a);
				return this.convertListAsMap(list);
			}
		};

		IPoSelectorModel model = newMock(IPoSelectorModel.class);

		EasyMock.expect(model.getReturnValueFields()).andReturn(
				new String[] { "this", "name","cnName" });
		EasyMock.expect(model.getUpdateFields()).andReturn(
				new String[] { "this", "name","cnName" });

		EasyMock.expect(model.getLabelField()).andReturn("name").times(2);

		IComponent c = newMock(IComponent.class);

		EasyMock.expect(model.getComponent()).andReturn(c);

		IPage page = newMock(IPage.class);
		EasyMock.expect(c.getPage()).andReturn(page);
		

		Map comps = new HashMap();
		comps.put("cnName",newMock(IFormComponent.class));
		comps.put("password",newInstance(Hidden.class));
		
		EasyMock.expect(page.getComponents()).andReturn(comps);
		

		DataSqueezer squeezer = newMock(DataSqueezer.class);

		EasyMock.expect(model.getSqueezer()).andReturn(squeezer);

		EasyMock.expect(squeezer.squeeze(a)).andReturn("HB:classa:id");
		
		
		
//		EasyMock.expect(squeezer.squeeze(name)).andReturn("S" + name);

		replay();

		Map map = filter.query(name, model);

		assertEquals(map.size(), 1);

		assertEquals(map.get(name),"\"HB:classa:id\",\""+name+"\",\"hello\"");

		verify();
		

	}
	
	@Test
	public void test_convert_many_hidden_list_map() {
		final String name = "阿菜";
		final A a = new A();
		a.setName(name);
		a.setPassword("password");
		Map comps = new HashMap();
		comps.put("password",newInstance(Hidden.class));

		DefaultSelectFilter filter = new DefaultSelectFilter() {

			/**
			 * @see corner.orm.tapestry.component.select.AbstractSelectFilter#query(java.lang.String,
			 *      corner.orm.tapestry.component.select.IPoSelectorModel)
			 */
			@Override
			public Map query(String match, IPoSelectorModel model) {
				this.model = model;
				List<A> list = new ArrayList<A>();

				list.add(a);
				return this.convertListAsMap(list);
			}
		};

		IPoSelectorModel model = newMock(IPoSelectorModel.class);

		EasyMock.expect(model.getReturnValueFields()).andReturn(
				new String[] { "this", "name","password" });
		EasyMock.expect(model.getUpdateFields()).andReturn(
				new String[] { "this", "name","password" });

		EasyMock.expect(model.getLabelField()).andReturn("name").times(2);

		IComponent c = newMock(IComponent.class);

		EasyMock.expect(model.getComponent()).andReturn(c);

		IPage page = newMock(IPage.class);
		EasyMock.expect(c.getPage()).andReturn(page);
		

		EasyMock.expect(page.getComponents()).andReturn(comps);
		

		DataSqueezer squeezer = newMock(DataSqueezer.class);

		EasyMock.expect(model.getSqueezer()).andReturn(squeezer);

		EasyMock.expect(squeezer.squeeze(a)).andReturn("HB:classa:id");
		
		
		
		

		EasyMock.expect(model.getSqueezer()).andReturn(squeezer);
		
		EasyMock.expect(squeezer.squeeze("password")).andReturn("Spassword");
		
		

		replay();

		Map map = filter.query(name, model);

		assertEquals(map.size(), 1);

		assertEquals(map.get(name),"\"HB:classa:id\",\""+name+"\",\"Spassword\"");

		verify();
		

	}
}
