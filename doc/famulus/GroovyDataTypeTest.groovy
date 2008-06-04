/**
 * 
 */
 package com.corner.test


import org.testng.annotations.*
import org.testng.TestNG
import org.testng.TestListenerAdapter
import static org.testng.AssertJUnit.*;

/**
* Groovy基本数据类型和语法测试
* <p>
* 本测试中的大部分例子根据《Groovy Repices》,想获得更多详细的信息，请参考该书
*/
public class GroovyDataTypeTest {

	/**
	* Main entry point to run <code>GroovyDataTypeTest</code> as
	* simple Groovy class
	*/
	public static void main(String[] args){
		def testng = new TestNG()
		testng.setTestClasses(GroovyDataTypeTest)
		testng.addListener(new TestListenerAdapter())
		testng.run()
	}
	
	
	/**
	* 判断非空的简单方法
	*/
	@Test
	final void testSafeDereferencing(){
		def s = null
		try{
			s.size();
		} catch(Exception e){
			assertEquals(e.class, java.lang.NullPointerException.class);
		}
		
		assertNull(s?.size());
	}

	/**
	 * 自动封装
	 * 注:groovy中的浮点数计算，是准确的
	 */
	 @Test
	 final void testAutoboxing(){
		def i = 2
		def ii = 2.1
		float iii = 2.2F
		println '2.class:'
		assertEquals(i.class, Integer)
		assertEquals(ii.class, java.math.BigDecimal);
		assertEquals(ii.toFloat().class, Float);
		assertEquals(iii.class, Float);
	}
	
	/**
	 * 布尔值
	 * 0, NULL, and "" (empty strings) all evaluate to false
	 */
	 @Test
	 final void testBoolean(){
		
		println '//=======================true===================+'
		if(1){
			println "1 is true"
		} 
		
		if("a"){
			println "a is true"
		} 
		
		if(['a','b']){
			println '["a","b"] is true'
		} 
		
		if(['a':1]){
			println '["a":1] is true'
		}
		
		if(!null){
			println '!null is true'
		}
		
		println '//================= false=================+'
		
		if(null){
			
		} else {
			println 'null is false'
		}
		
		if(0){
			
		} else {
			println '0 is false'
		}
		
		if([]){
			
		} else {
			println '[] is false'
		}
	
	}
	
	/**
	 * about heredoc:
	 * http://en.wikipedia.org/wiki/Heredoc
	 */
	@Test
	final void testHeredocs(){
		def s = '''lskadfjkljasdjf xcvmnaiosdf#o29095802eur-0s;dfm1asdf a
sdfasdfasdfjcvksdf
asdfjvb90a-03598-2=85=23945'''

	def s1 = '''lskadfjkljasdjf xcvmnaiosdf#o29095802eur-0s;dfm1asdf a
	sdfasdfasdfjcvksdf
	asdfjvb90a-03598-2=85=23945'''
		println s;
		println s1;
	}
	
	/**
	 * List 使用
	 */
	 @Test
	 final void testList(){
		def list = []
		assertEquals(list.size, 0)
		list << 'Ghost'
		assertEquals(list.size, 1)
		assertEquals(list[0], 'Ghost');
		assertEquals(list.get(0), 'Ghost');
		assertEquals(list.getAt(0), 'Ghost');
		
		list << ['xf','lsq',['qsl','ye']]
		list.each{
			println 'name:'+it;
		}
		assertEquals(list.size, 2);
		assertEquals(list.flatten().size,5);//返回一个新的list,原始list并没有被改变
		assertEquals(list.size, 2);
		
		def newList = ['zsl','wlh']
		
		assertEquals((list+newList).size, 4);//list元素可以直接+,很方便
	}
	
	/**
	 * map使用
	 */
	 @Test
	 final void testMap(){
		def map = [:]
		assertNull(map.class);//这里一定要注意
		
		assertEquals(map.size(), 0);
		assertEquals(map.keySet().size(), 0);
		assertEquals(map.values().size(), 0);
		map.zx = 'ghostbb' 
		map.renais = 'qsl'
		map.lsq = 'lsq'
		
		assertEquals(map.keySet().size(), 3);
		assertEquals(map.values().size(), 3);
		
		def newMap = ['早餐':'庆丰包子','中餐':'庆丰包子','晚餐':'庆丰包子','zx':'ghost']
		def xMap = map + newMap
		assertEquals((map+newMap).size(), 6);//map也可以直接+操作
		assertEquals(xMap.zx, 'ghost');
		assertEquals(xMap.早餐, '庆丰包子');
		
		def yMap = newMap + map
		assertEquals(yMap.zx, 'ghostbb');//由此可以看出，在key相同的时候，保持第一map中的key对应的value
	}
	
	/**
	 * 类似String.format,和ruby中的#{}可以在字符串中直接放入变量，而无需拼接字符串
	 */
	 @Test
	 final void testGStrings(){
		 def s = "ghostbb"
		 println "Groovy 数据类型和语法测试，由${s}提供!"
	 }
	 
	 /**
	  * Ranges是Groovy提供原生支持的一种数据类型
	  */
	  @Test
	  final void testRanges(){
		 (1..10).each{//(1..10) is Range Type
			 println 'current num is:'+it
		 }
		 
		 def currentTime = System.currentTimeMillis()
		 def keepTime = (currentTime..currentTime+1)*100//Range Type
		 keepTime.each{
			 println "showTime:"+it
		 }
	 }
	 
	 /**
	  * 闭包和代码块(groovy一直鼓吹的东东,也是动态语言很不错的一个特性)
	  */
	  @Test
	  final void testColsuresAndBlock(){
		 def sayHello = { name, value ->
		 	println "hello ${name} and ${value}!"
		 }
		 
		 (1..10).each{ name ->
			 def tmp = sayHello(name,name+1);
		 }
	 }
}