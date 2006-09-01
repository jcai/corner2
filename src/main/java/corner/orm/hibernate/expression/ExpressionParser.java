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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 对表达式进行分析.
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.1
 */
public class ExpressionParser {
	public final static String DATE_PATTERN="([\\d]*)~([\\d]*)";
	
	public final static String EXP_PATTERN="^([^\\s]*)([\\s]+(AND|OR)[\\s]+([^\\s]+))*$";
	public static  Iterator<ExpPair> parseExpression(String value){
		
		
		Matcher matcher=Pattern.compile(EXP_PATTERN,Pattern.CASE_INSENSITIVE ).matcher(value);
		
		//声明匹配的组.
		List<ExpPair> list=new ArrayList<ExpPair>();
		
		while(matcher.find()){
			System.out.println("groupCount:"+matcher.groupCount());
			ExpressionParser.ExpPair pair= new ExpressionParser.ExpPair();
			pair.exp="and";
			pair.value=matcher.group(1);
			list.add(pair);
			
			if(matcher.group(3)!=null){
				pair=new ExpressionParser.ExpPair();
				pair.exp=matcher.group(3);
				pair.value=matcher.group(4);
				list.add(pair);
			}
			
			
		}
		return list.iterator();
	}
	public static class ExpPair{
		public String exp;
		public Object value;
	}
}
