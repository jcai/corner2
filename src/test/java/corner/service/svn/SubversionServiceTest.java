package corner.service.svn;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.io.ISVNEditor;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import corner.demo.model.one2many.A;
import corner.demo.model.one2many.B;

public class SubversionServiceTest extends Assert{
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
		service.setPassword("jetty");
		service.setUrl("svn+ssh://dev.bjmaxinfo.com/home/jetty/svn-test");
		
		service.afterPropertiesSet();
		return service;
	}
}
