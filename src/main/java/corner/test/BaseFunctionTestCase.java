//==============================================================================
// file :       $Id$
// project:     corner
//
// last change: date:       $Date$
//              by:         $Author$
//              revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import junit.framework.TestCase;

import org.mortbay.http.HttpContext;
import org.mortbay.http.HttpException;
import org.mortbay.http.HttpHandler;
import org.mortbay.http.HttpRequest;
import org.mortbay.http.HttpResponse;
import org.mortbay.http.SocketListener;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.WebApplicationContext;
import org.mortbay.util.StringUtil;
import org.openqa.selenium.server.SeleniumCommandTimedOutException;
import org.openqa.selenium.server.browserlaunchers.AsyncExecute;
import org.openqa.selenium.server.browserlaunchers.FirefoxCustomProfileLauncher;
import org.openqa.selenium.server.htmlrunner.HTMLResultsListener;
import org.openqa.selenium.server.htmlrunner.HTMLTestResults;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 
 * 基本的功能性质的测试.
 * 
 * @author Jun Tsai
 * @version $Revision$
 * @since 2.1
 */
public abstract class BaseFunctionTestCase extends TestCase implements		HTMLResultsListener 
{
	private org.mortbay.jetty.Server server = null;

	private HTMLTestResults results;

	@BeforeMethod
	protected void setUp() throws Exception {
		super.setUp();
		System.setProperty("org.mortbay.http.HttpRequest.maxFormContentSize",
				"1000000");
		server = new Server();

		SocketListener socketListener = new SocketListener();
		socketListener.setPort(this.getListenerPort());
		server.addListener(socketListener);

		WebApplicationContext context = new WebApplicationContext();
		context.setContextPath("/");
		context.setWAR(this.getWebappDirectory());
		SeleniumHTMLRunnerResultsHandler postResultsHandler = new SeleniumHTMLRunnerResultsHandler();
		context.addHandler(postResultsHandler);
		postResultsHandler.addListener(this);

		server.addContext(context);

		server.start();

	}

	protected String getWebappDirectory() {
		return "src/main/webapp";
	}

	protected int getListenerPort() {
		return 8888;
	}

	protected String getSeleinumTestSuitel() {
		return "html_tests/TestSuite.html";

	}

	protected String getOutputFilename(){
		return "target/results.html";
	}
	@Test
	public void testFunction() throws Exception {

		 String sessionId = Long.toString(System.currentTimeMillis() %
		 1000000);
		 FirefoxCustomProfileLauncher launcher=new
		 FirefoxCustomProfileLauncher(8888,sessionId);
		 launcher.launchHTMLSuite("../../"+this.getSeleinumTestSuitel(),
		 "http://localhost:"+getListenerPort(), false);
		 //.launch(this.getSeleinumTestUrl());
		 // long now = System.currentTimeMillis();
		 // long end = Integer.MAX_VALUE;
		        
		        
		 
		        
/*
		List<String> list = new ArrayList<String>();
		list.add("-htmlSuite");
		list.add("*firefox");
		list.add("http://localhost:8888");
		list.add("src/main/webapp/html_tests/TestSuite.html");
		list.add("target/result.html");

		try {
			org.openqa.selenium.server.SeleniumServer.main(list
					.toArray(new String[0]));
		} catch (Exception e) {
			e.printStackTrace();
		}
*/
		while (results == null) {
			AsyncExecute.sleepTight(500);
		}

		if (results == null) {
			throw new SeleniumCommandTimedOutException();
		}
		launcher.close();
		
		File outputFile = new File(getOutputFilename());
		 if (outputFile != null) {
		 FileWriter fw = new FileWriter(outputFile);
		 results.write(fw);
		 fw.close();
	 }
		 
		String failNum = results.getNumTestFailures();

		if (failNum != null && !failNum.equals("0")) {
			fail("功能测试失败,共测试[" + results.getNumTotalTests() + "] 成功：["
					+ results.getNumTestPasses() + "] 失败:["
					+ results.getNumTestFailures() + "],请查看结果文件:"+this.getOutputFilename());

		} else {
			System.out.println("功能测试失成功,共测试[" + results.getNumTotalTests()
					+ "] 成功：[" + results.getNumTestPasses() + "] 失败:["
					+ results.getNumTestFailures() + "],总耗时:["
					+ results.getTotalTime() + "]");
		}
		
	}

	/** Accepts HTMLTestResults for later asynchronous handling */
	public void processResults(HTMLTestResults resultsParm) {
		this.results = resultsParm;
	}

	@AfterMethod
	protected void tearDown() throws Exception {
		System.out.println("close jetty server!");
		server.stop();
	}
}

class SeleniumHTMLRunnerResultsHandler implements HttpHandler {

	HttpContext context;

	List<HTMLResultsListener> listeners;

	boolean started = false;

	public SeleniumHTMLRunnerResultsHandler() {
		listeners = new Vector<HTMLResultsListener>();
	}

	public void addListener(HTMLResultsListener listener) {
		listeners.add(listener);
	}

	public void handle(String pathInContext, String pathParams,
			HttpRequest request, HttpResponse res) throws HttpException,
			IOException {
		if (!"/selenium-server/postResults".equals(pathInContext))
			return;
		request.setHandled(true);
		String result = request.getParameter("result");
		if (result == null) {
			res.getOutputStream().write("No result was specified!".getBytes());
		}
		String seleniumVersion = request.getParameter("selenium.version");
		String seleniumRevision = request.getParameter("selenium.revision");
		String totalTime = request.getParameter("totalTime");
		String numTestPasses = request.getParameter("numTestPasses");
		String numTestFailures = request.getParameter("numTestFailures");
		String numCommandPasses = request.getParameter("numCommandPasses");
		String numCommandFailures = request.getParameter("numCommandFailures");
		String numCommandErrors = request.getParameter("numCommandErrors");
		String suite = request.getParameter("suite");

		int numTotalTests = Integer.parseInt(numTestPasses)
				+ Integer.parseInt(numTestFailures);

		List<String> testTables = createTestTables(request, numTotalTests);

		HTMLTestResults results = new HTMLTestResults(seleniumVersion,
				seleniumRevision, result, totalTime, numTestPasses,
				numTestFailures, numCommandPasses, numCommandFailures,
				numCommandErrors, suite, testTables);

		for (Iterator i = listeners.iterator(); i.hasNext();) {
			HTMLResultsListener listener = (HTMLResultsListener) i.next();
			listener.processResults(results);
			i.remove();
		}
		processResults(results, res);
	}

	/** Print the test results out to the HTML response */
	private void processResults(HTMLTestResults results, HttpResponse res)
			throws IOException {
		res.setContentType("text/html");
		OutputStream out = res.getOutputStream();
		Writer writer = new OutputStreamWriter(out, StringUtil.__ISO_8859_1);
		results.write(writer);
		writer.flush();
	}

	private List<String> createTestTables(HttpRequest request, int numTotalTests) {
		List<String> testTables = new LinkedList<String>();
		for (int i = 1; i <= numTotalTests; i++) {
			String testTable = request.getParameter("testTable." + i);
			// System.out.println("table " + i);
			// System.out.println(testTable);
			testTables.add(testTable);
		}
		return testTables;
	}

	public String getName() {
		return SeleniumHTMLRunnerResultsHandler.class.getName();
	}

	public HttpContext getHttpContext() {
		return context;
	}

	public void initialize(HttpContext c) {
		this.context = c;

	}

	public void start() throws Exception {
		started = true;
	}

	public void stop() throws InterruptedException {
		started = false;
	}

	public boolean isStarted() {
		return started;
	}
}
