package corner.service.svn;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import corner.demo.model.one2many.A;

public class SubversionServiceTest extends Assert{

	@Test
	public void test_checkin() throws Exception{
		IVersionService service = constructService();
		A a=new A();
		a.setId("id");
		service.checkin(a);
		service.delete(a);
	}
	@Test
	public void test_fetchObjectAsJson() throws Exception{
		IVersionService service = constructService();
		A a=new A();
		a.setId("id");
		service.checkin(a);
		String str=service.fetchObjectAsJson(a,-1);
		assertEquals("{\"entity\":{\"id\":\"id\"}}",str);
		
	}
	@Test
	public void test_fetchVersionInfo() throws Exception{
		IVersionService service = constructService();
		A a=new A();
		a.setId("id");
		service.checkin(a);
		List<VersionResult> list = service.fetchVersionInfo(a);
		System.out.println(list);
	}
	
	private IVersionService constructService() throws Exception{
		SubversionService service=new SubversionService();
		service.setUsername("xf");
		service.setUrl("http://dev.bjmaxinfo.com/svn/svn-test");
		service.setPassword("123456");
		service.afterPropertiesSet();
		return service;
	}
}
