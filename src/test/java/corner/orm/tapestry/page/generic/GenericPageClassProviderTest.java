package corner.orm.tapestry.page.generic;

import org.apache.tapestry.BaseComponentTestCase;
import org.apache.tapestry.INamespace;
import org.apache.tapestry.pageload.ComponentClassProviderContext;
import org.apache.tapestry.spec.IComponentSpecification;
import org.testng.annotations.Test;

public class GenericPageClassProviderTest extends BaseComponentTestCase{
	@Test
	public void test_load_FormPage(){
		INamespace namespace = newMock(INamespace.class);
        IComponentSpecification spec = newSpec();
        replay();
		ComponentClassProviderContext context = new ComponentClassProviderContext("bar/BazForm", spec,
                namespace);
		GenericPageClassProvider provider=new GenericPageClassProvider();
		String className=provider.provideComponentClassName(context);
		
		assertEquals(className,"corner.orm.tapestry.page.generic.GenericFormPage");
		
	}
	@Test
	public void test_load_ListPage(){
		INamespace namespace = newMock(INamespace.class);
        IComponentSpecification spec = newSpec();
        replay();
		ComponentClassProviderContext context = new ComponentClassProviderContext("bar/BazList", spec,
                namespace);
		GenericPageClassProvider provider=new GenericPageClassProvider();
		String className=provider.provideComponentClassName(context);
		
		assertEquals(className,"corner.orm.tapestry.page.generic.GenericListPage");
		
	}
	@Test
	public void test_load_Null(){
		INamespace namespace = newMock(INamespace.class);
        IComponentSpecification spec = newSpec();
        replay();
		ComponentClassProviderContext context = new ComponentClassProviderContext("bar/Baz", spec,
                namespace);
		GenericPageClassProvider provider=new GenericPageClassProvider();
		String className=provider.provideComponentClassName(context);
		
		assertEquals(className,null);
		
	}
}
