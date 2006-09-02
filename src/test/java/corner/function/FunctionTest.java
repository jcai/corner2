package corner.function;

import junit.framework.TestCase;

import org.mortbay.http.SocketListener;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.WebApplicationContext;
import org.openqa.selenium.server.SeleniumCommandTimedOutException;
import org.openqa.selenium.server.browserlaunchers.AsyncExecute;
import org.openqa.selenium.server.browserlaunchers.FirefoxCustomProfileLauncher;
import org.openqa.selenium.server.htmlrunner.HTMLResultsListener;
import org.openqa.selenium.server.htmlrunner.HTMLTestResults;
import org.openqa.selenium.server.htmlrunner.SeleniumHTMLRunnerResultsHandler;

public class FunctionTest extends TestCase implements HTMLResultsListener{
	private org.mortbay.jetty.Server server = null;
	private HTMLTestResults results;

	protected void setUp() throws Exception {
		super.setUp();
		server=new Server();

		SocketListener socketListener = new SocketListener();
        socketListener.setPort(8888);
        server.addListener(socketListener);

        WebApplicationContext context=new WebApplicationContext();
        context.setContextPath("/");
        context.setWAR("src/main/webapp");
        SeleniumHTMLRunnerResultsHandler postResultsHandler = new SeleniumHTMLRunnerResultsHandler();
        context.addHandler(postResultsHandler);
        postResultsHandler.addListener(this);
        
        server.addContext(context);

        server.start();

	}

	public void testFunction() throws Exception {
		
		String url="http://localhost:8888/selenium/TestRunner.html?test=TestSuite.html&auto=true&resultsUrl=/postResults";
	    String sessionId = Long.toString(System.currentTimeMillis() % 1000000);
		FirefoxCustomProfileLauncher  launcher=new FirefoxCustomProfileLauncher(8888,sessionId);
		launcher.launch(url);
		long now = System.currentTimeMillis();
        long end = Integer.MAX_VALUE;
        
        
        while (results == null ) {
            AsyncExecute.sleepTight(500);
        }
        launcher.close();
        if (results == null) {
            throw new SeleniumCommandTimedOutException();
        }
//        if (outputFile != null) {
//            FileWriter fw = new FileWriter(outputFile);
//            results.write(fw);
//            fw.close();
//        }
        
		/*
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
		*/

		
	}
	/** Accepts HTMLTestResults for later asynchronous handling */
    public void processResults(HTMLTestResults resultsParm) {
        this.results = resultsParm;
    }

	protected void tearDown() throws Exception {
		server.stop();
	}

}
