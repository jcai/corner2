package corner.service.svn;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import corner.demo.model.one2many.A;
import corner.orm.tapestry.component.insert.VersionManage;

public class SubversionServiceTest extends Assert{
	
	@Test
	public void testRemovePrototype(){
		String temp ="{\"entity\":{\"saleContractMain\":\"com.bjmaxinfo.piano.model.sale.impl.SaleContractMain@6efc82\",\"blobName\":\"06级法律本科第3学期.rar\",\"contentType\":\"application/x-msdownload\",\"blobData\":\"MSwyLDMsNA==\"}}";
		String expect ="{\"entity\":{\"saleContractMain\":\"com.bjmaxinfo.piano.model.sale.impl.SaleContractMain@6efc82\",\"blobName\":\"06级法律本科第3学期.rar\",\"contentType\":\"application/x-msdownload\"}}";
		assertEquals(VersionManage.removeBlobData(temp),expect);
	}
	
	@Test
	public void test_checkin2()throws Exception{
		IVersionService service = constructService();
		A a=new A();
		a.setId("check2");
		
		long revision=service.checkin(a);
		List<VersionResult> version = service.fetchVersionInfo(a);
		String jsonStr = service.fetchObjectAsJson(a, revision);
	}
	
	@Test
	public void test_checkin() throws Exception{
		IVersionService service = constructService();
		A a=new A();
		a.setId("id2");
		
		long revision=service.checkin(a);
		service.checkin(a);
		List<VersionResult> list = service.fetchVersionInfo(a);
		for(VersionResult r:list){
			System.out.println(r.getVersionNum());
		}
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
		service.setUsername("jetty");
		service.setPassword("205jetty@bjmaxinfo.com");
		service.setUrl("http://dev.bjmaxinfo.com/svn/svn-test");
		
		service.afterPropertiesSet();
		return service;
	}
}
