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

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

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

	public static class JavassistProxy {

		public static Object createProxy(Class clazz) throws Throwable {
			ClassPool pool = ClassPool.getDefault();
			CtClass cc = pool.getAndRename(clazz.getName(), clazz.getName()+"$Proxy");
			CtMethod cm = cc.getDeclaredMethod("getSayHello", new CtClass[0]);
			
			//方法前的操作.
			cm.insertBefore("{ System.out.println(\"before\"); }");
			//方法后的操作.
			cm.insertAfter("{ System.out.println(\"after\"); }");
			
			return cc.toClass().newInstance();

		}
	}

	@Test
	public void test_javassistProxy() throws Throwable {
		Foo foo = (Foo) JavassistProxy.createProxy(FooImpl.class);
		foo.getSayHello();
	}
}
