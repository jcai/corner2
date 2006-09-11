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

/**
 * 
 * 基本的功能性质的测试.
 * @author Jun Tsai
 * @version $Revision$
 * @since 2.1
 */
public abstract class  BaseFunctionTestCase extends TestCase implements HTMLResultsListener{
	private org.mortbay.jetty.Server server = null;
	private HTMLTestResults results;

	protected void setUp() throws Exception {
		super.setUp();
		server=new Server();

		SocketListener socketListener = new SocketListener();
        socketListener.setPort(this.getListenerPort());
        server.addListener(socketListener);

        WebApplicationContext context=new WebApplicationContext();
        context.setContextPath("/");
        context.setWAR(this.getWebappDirectory());
        SeleniumHTMLRunnerResultsHandler postResultsHandler = new SeleniumHTMLRunnerResultsHandler();
        context.addHandler(postResultsHandler);
        postResultsHandler.addListener(this);
        
        server.addContext(context);

        server.start();

	}
	
	protected String getWebappDirectory(){
		return "src/main/webapp";
	}
	protected int getListenerPort(){
		return 8888;
	}
	protected String getSeleinumTestUrl(){
		return "http://localhost:"+this.getListenerPort()+"/selenium/TestRunner.html?test=TestSuite.html&auto=true&resultsUrl=/postResults";
	    
	}

	public void testFunction() throws Exception {
		
		String sessionId = Long.toString(System.currentTimeMillis() % 1000000);
		FirefoxCustomProfileLauncher  launcher=new FirefoxCustomProfileLauncher(8888,sessionId);
		launcher.launch(this.getSeleinumTestUrl());
//		long now = System.currentTimeMillis();
//        long end = Integer.MAX_VALUE;
        
        
        while (results == null ) {
            AsyncExecute.sleepTight(500);
        }
        launcher.close();
        if (results == null) {
            throw new SeleniumCommandTimedOutException();
        }
        String failNum=results.getNumTestFailures();
        
        if(failNum!=null&&!failNum.equals("0")){
        	fail("功能测试失败,共测试["+results.getNumTotalTests()+"] 成功：["+results.getNumTestPasses()+"] 失败:["+results.getNumTestFailures()+"]");
        	
        }else{
        	System.out.println("功能测试失成功,共测试["+results.getNumTotalTests()+"] 成功：["+results.getNumTestPasses()+"] 失败:["+results.getNumTestFailures()+"],总耗时:["+results.getTotalTime()+"]");
        }
//        if (outputFile != null) {
//            FileWriter fw = new FileWriter(outputFile);
//            results.write(fw);
//            fw.close();
//        }
//        
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
