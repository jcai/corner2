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

package corner.orm.hibernate.expression;

import java.util.Iterator;

import junit.framework.TestCase;

import org.testng.annotations.Test;

import corner.orm.hibernate.expression.ExpressionParser.ExpPair;
@Test
public class ExpressionParserTest extends TestCase {

	/**
	 * Test method for
	 * {@link corner.orm.hibernate.expression.ExpressionParser#parseStringExpression(java.lang.String)}.
	 */
	public void testParseExpression() {
		String client_str = "asdf";
		Iterator<ExpPair> r = ExpressionParser
				.parseStringExpression(client_str).iterator();
		ExpPair p = r.next();
		assertEquals(client_str, p.value);
		assertEquals(" ", p.exp);

		client_str = "蔡君";
		r = ExpressionParser.parseStringExpression(client_str).iterator();
		p = r.next();
		assertEquals(client_str, p.value);
		assertEquals(" ", p.exp);
	}

	/**
	 * Test method for
	 * {@link corner.orm.hibernate.expression.ExpressionParser#parseStringExpression(java.lang.String)}.
	 */
	public void testParseAndExpression() {
		String client_str = "我们 AND fdsa";
		Iterator<ExpPair> r = ExpressionParser
				.parseStringExpression(client_str).iterator();
		ExpPair p = r.next();
		assertEquals("我们", p.value);

		assertEquals(" ", p.exp.toLowerCase());

		p = r.next();
		assertEquals("fdsa", p.value);
		assertEquals("and", p.exp.toLowerCase());

	}

	/**
	 * Test method for
	 * {@link corner.orm.hibernate.expression.ExpressionParser#parseStringExpression(java.lang.String)}.
	 */
	public void testParseOrExpression() {
		String client_str = "你好 or fdsa";
		Iterator<ExpPair> r = ExpressionParser
				.parseStringExpression(client_str).iterator();
		ExpPair p = r.next();
		assertEquals("你好", p.value);
		assertEquals(" ", p.exp.toLowerCase());

		p = r.next();
		assertEquals("fdsa", p.value);
		assertEquals("or", p.exp.toLowerCase());
	}

	/**
	 * Test method for
	 * {@link corner.orm.hibernate.expression.ExpressionParser#parseStringExpression(java.lang.String)}.
	 */
	public void testParseMultiExpression() {
		String client_str = "你好 or fdsa and 蔡君";
		Iterator<ExpPair> r = ExpressionParser
				.parseStringExpression(client_str).iterator();
		ExpPair p = r.next();
		assertEquals("你好", p.value);
		assertEquals(" ", p.exp.toLowerCase());

		p = r.next();
		assertEquals("fdsa", p.value);
		assertEquals("or", p.exp.toLowerCase());
		p = r.next();
		assertEquals("蔡君", p.value);
		assertEquals("and", p.exp.toLowerCase());
	}

	/**
	 * Test method for
	 * {@link corner.orm.hibernate.expression.ExpressionParser#parseStringExpression(java.lang.String)}.
	 */
	public void testParseManyMultiExpression() {
		String client_str = "你好 or fdsa and 蔡君 and 你 or 它";
		Iterator<ExpPair> r = ExpressionParser
				.parseStringExpression(client_str).iterator();
		ExpPair p = r.next();
		assertEquals("你好", p.value);
		assertEquals(" ", p.exp.toLowerCase());

		p = r.next();
		assertEquals("fdsa", p.value);
		assertEquals("or", p.exp.toLowerCase());
		p = r.next();
		assertEquals("蔡君", p.value);
		assertEquals("and", p.exp.toLowerCase());

		p = r.next();
		assertEquals("你", p.value);
		assertEquals("and", p.exp.toLowerCase());

		p = r.next();
		assertEquals("它", p.value);
		assertEquals("or", p.exp.toLowerCase());
	}
	/**
	 * Test method for
	 * {@link corner.orm.hibernate.expression.ExpressionParser#parseStringExpression(java.lang.String)}.
	 */
	public void testParseBlankExpression() {
		String client_str = "你好 蔡君";
		Iterator<ExpPair> r = ExpressionParser
				.parseStringExpression(client_str).iterator();
		ExpPair p = r.next();
		assertEquals("你好 蔡君", p.value);
		assertEquals(" ", p.exp.toLowerCase());


	}
	
	public void testParseDateExpression(){
		String client_str="1231231asdf";
		String [] r=ExpressionParser.parseDateExpression(client_str);
		assertTrue(r.length==1);
		assertEquals(client_str,r[0]);
		
		client_str="20050607~20060809";
		r=ExpressionParser.parseDateExpression(client_str);
		assertTrue(r.length==2);
		assertEquals("20050607",r[0]);
		assertEquals("20060809",r[1]);
		
		
		
	}

}
