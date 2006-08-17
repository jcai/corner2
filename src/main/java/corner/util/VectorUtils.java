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

package corner.util;

import java.io.StringReader;
import java.util.List;
import java.util.Vector;

import antlr.RecognitionException;
import antlr.TokenStreamException;
import antlr.collections.AST;
import corner.expression.calc.ExprLexer;
import corner.expression.calc.ExprParser;
import corner.expression.calc.ExprTreeParser;

/**
 * 对向量类型的操作。
 * @author jcai
 * @version $Revision$
 * @since 2.1
 */
public final class VectorUtils {
	/**
	 * 对向量进行求和.
	 * @param v 向量
	 * @return 求和后的结果.
	 */
	public static double sum(Vector<String> v){
		StringBuffer sb=new StringBuffer();
		for(String str:v){
			sb.append(str);
			sb.append("+");
		}
		sb.append("0");
		return expr(sb.toString());
	}
	/**
	 * 对向量的对象进行求和.
	 * @param list 向量列表.
	 * @return 求和后的向量.
	 * @TODO 判断向量的维数要相同.
	 */
	public static Vector<Double> sumList(List<Vector<String>> list){
		Vector<Double> r=new Vector<Double>();
		if(list.size() == 0){
			return r;
		}
		StringBuffer sb=new StringBuffer();
		
		int width=list.get(0).size();
		for(int i=0;i<width;i++){
			for(Vector<String>v:list){
				sb.append(v.get(i));
				sb.append("+");
			}
			sb.append("0");
			
			r.add(i, expr(sb.toString()));
			
			sb.setLength(0);
		}
		
		return r;
	}
	/**
	 *
	 * @param str 欲求值的字符串。
	 * @param expected 期望值。
	 */
	private static double expr(String str){
		ExprLexer lexer = new ExprLexer(new StringReader(str));
       ExprParser parser = new ExprParser(lexer);
       try {
			parser.expr();
		} catch (RecognitionException e) {
			throw new RuntimeException(e);
			
		} catch (TokenStreamException e) {
			throw new RuntimeException(e);
		}
       AST t = parser.getAST();
       
       ExprTreeParser treeParser = new ExprTreeParser();
       double x;
		try {
			return treeParser.expr(t);
			
		} catch (RecognitionException e) {
			throw new RuntimeException(e);
		}

	}
}
