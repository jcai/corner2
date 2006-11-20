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
 * 
 * 关于proxy，参见jdk的文档
 *  http://dev.bjmaxinfo.com/docs/jdk/1.5.0_06/api-zh_CN/java/lang/reflect/Proxy.html
 *  
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.3
 */
public class JdkProxyTestCase extends Assert{
	public static class JdkInvocationHandler implements InvocationHandler{
		private Object targetObject; //原来的对象
		private Object proxyObject; //代理的对象
		public JdkInvocationHandler(Object targetObject){
			this.targetObject=targetObject;
			//生成代理类
			this.proxyObject=(Object) Proxy.newProxyInstance(targetObject.getClass().getClassLoader(),targetObject.getClass().getInterfaces(),this);
		}
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			System.out.println("------------------------now to execute method ["+method.getName()+"]-----------------------------");
			//方法执行前的操作.
			System.out.println("catch before method from jdk proxy");
			
			Object r=method.invoke(targetObject, args);//调用原来的方法.
			
			//方法执行后的操作.
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
