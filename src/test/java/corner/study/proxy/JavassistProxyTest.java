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

package corner.study.proxy;

import java.lang.reflect.Method;

import javassist.util.proxy.MethodFilter;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;

import org.testng.annotations.Test;

import corner.study.proxy.model.Foo;
import corner.study.proxy.model.FooImpl;



/**
 * 基于Javassist的代理
 * 网站：http://www.csg.is.titech.ac.jp/~chiba/javassist/
 * JBoos ：http://www.jboss.org/products/javassist
 * 
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.3
 */
public class JavassistProxyTest {

	

	@Test
	public void test_javassistProxy() throws Throwable {
		ProxyFactory f = new ProxyFactory();
		 f.setSuperclass(FooImpl.class);
		 MethodHandler mi = new MethodHandler() {
		     public Object invoke(Object self, Method m, Method proceed,
		                          Object[] args) throws Throwable {
		         System.out.println("Name: " + m.getName());
		         return proceed.invoke(self, args);  // execute the original method.
		     }
		 };
		 f.setFilter(new MethodFilter() {
		     public boolean isHandled(Method m) {
		         // ignore finalize()
		         return !m.getName().equals("finalize");
		     }
		 });
		 Class c = f.createClass();
		 Foo foo = (Foo)c.newInstance();
		 ((ProxyObject)foo).setHandler(mi);
		 foo.getSayHello();
	}


}
