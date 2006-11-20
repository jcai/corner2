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

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.testng.Assert;
import org.testng.annotations.Test;

import corner.study.proxy.model.Foo;
import corner.study.proxy.model.FooImpl;

/**
 * 对代理类的学习
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.3
 */
public class JdkProxyTestCase extends Assert{



	public static class JdkInvocationHandler implements InvocationHandler{

		private Object targetObject;
		private Object proxyObject;
		public JdkInvocationHandler(Object targetObject){
			this.targetObject=targetObject;
			this.proxyObject=(Object) Proxy.newProxyInstance(targetObject.getClass().getClassLoader(),targetObject.getClass().getInterfaces(),this);
		}
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			System.out.println("------------------------now to execute method ["+method.getName()+"]-----------------------------");
			System.out.println("catch before method from jdk proxy");
			Object r=method.invoke(targetObject, args);
			System.out.println("catch after method from jdk proxy");
			return r;
		}
		public Object getProxyObject(){
			return this.proxyObject;
		}
		
	}
	
	public static class JdkProxy {
		public static<T> T createProxy(Class<T> clazz) throws Throwable{
			Object obj=clazz.newInstance();
			JdkInvocationHandler handler=new JdkInvocationHandler(obj);
			return (T) handler.getProxyObject();
		}
	}
	@Test
	public void test_jdk_proxy() throws Throwable{
		Foo foo= JdkProxy.createProxy(FooImpl.class);
		assertEquals(foo.getSayHello(),"hello");
	}
	
	
}
