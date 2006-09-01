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

import corner.orm.hibernate.expression.ExpressionParser.ExpPair;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.1
 */
public class ExpressionParserTest extends TestCase {

	/**
	 * Test method for {@link corner.orm.hibernate.expression.ExpressionParser#parseExpression(java.lang.String)}.
	 */
	public void testParseExpression() {
		String client_str="asdf";
		Iterator<ExpPair> r=ExpressionParser.parseExpression(client_str);
		ExpPair p=r.next();
		assertEquals(client_str,p.value);
		assertEquals("and",p.exp);
		
		client_str="蔡君";
		r=ExpressionParser.parseExpression(client_str);
		p=r.next();
		assertEquals(client_str,p.value);
		assertEquals("and",p.exp);
	}

	/**
	 * Test method for {@link corner.orm.hibernate.expression.ExpressionParser#parseExpression(java.lang.String)}.
	 */
	public void testParseAndExpression() {
		String client_str="我们 AND fdsa";
		Iterator<ExpPair> r=ExpressionParser.parseExpression(client_str);
		ExpPair p=r.next();
		assertEquals("我们",p.value);
		
		assertEquals("and",p.exp.toLowerCase());

		p=r.next();
		assertEquals("fdsa",p.value);
		assertEquals("and",p.exp.toLowerCase());
		
		
		
	}

	/**
	 * Test method for {@link corner.orm.hibernate.expression.ExpressionParser#parseExpression(java.lang.String)}.
	 */
	public void testParseOrExpression() {
		String client_str="你好 or fdsa";
		Iterator<ExpPair> r=ExpressionParser.parseExpression(client_str);
		ExpPair p=r.next();
		assertEquals("你好",p.value);
		assertEquals("and",p.exp.toLowerCase());
		
		
		p=r.next();
		assertEquals("fdsa",p.value);
		assertEquals("or",p.exp.toLowerCase());
	}

	

}
