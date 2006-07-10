package corner.function;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.mortbay.http.SocketListener;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.WebApplicationContext;

public class FunctionTest extends TestCase {
	private org.mortbay.jetty.Server server = new Server();

//	protected void setUp() throws Exception {
//		super.setUp();
//
//
//
//
//	}

	public void testFunction() throws Exception {
		SocketListener socketListener = new SocketListener();
        socketListener.setPort(8888);
        server.addListener(socketListener);

        WebApplicationContext context=new WebApplicationContext();
        context.setContextPath("/");
        context.setWAR("src/main/webapp");

        server.addContext(context);

        server.start();

		List<String> list=new ArrayList<String>();
		list.add("-htmlSuite");
		list.add("*firefox");
		list.add("http://localhost:8888");
		list.add("src/main/webapp/selenium/TestSuite.html");
		list.add("target/result.html");

		try {
			org.openqa.selenium.server.SeleniumServer.main(list.toArray(new String[0]));
		} catch (Exception e) {
			e.printStackTrace();
		}


		server.stop();
	}

//	protected void tearDown() throws Exception {
//
//	}

}
