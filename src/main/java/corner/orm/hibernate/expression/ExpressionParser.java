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
	/** 对日期类型的数据进行分析 **/
	public final static String DATE_PATTERN="^([\\d]*)~([\\d]*)$";
	/** 对表达式的模式进行分析 **/
	public final static String EXP_PATTERN="^([^\\s]*)(([\\s]+(AND|OR)[\\s]+([^\\s]+))*)\\s*$";
	/** 对表达式的字模式进行分析 **/
	public final static String EXP_SUB_PATTER="[\\s]+(AND|OR)[\\s]+([^\\s]+)";
	
	static Pattern date_pattern=Pattern.compile(DATE_PATTERN,Pattern.CASE_INSENSITIVE );
	private static Pattern str_pattern=Pattern.compile(EXP_PATTERN,Pattern.CASE_INSENSITIVE );
	private static Pattern str_sub_pattern=Pattern.compile(EXP_SUB_PATTER,Pattern.CASE_INSENSITIVE);
	/**
	 * 对日期类型的数据进行分析
	 * <p>返回为拆分候的数组，此数组有可能为一个有可能为两个.
	 * @param input 输入的数据.
	 * @return 分析完的表达式组.
	 */
	public static String[] parseDateExpression(String input){
		Matcher matcher=date_pattern.matcher(input);
		if(matcher.find()){
			return new String[]{matcher.group(1),matcher.group(2)};
		}else{
			return new String[] {input};
		}
	}
	/**
	 * 对字符串进行解析,分析出来表达式.
	 * <p>返回为一个ExpPair的集合。
	 * @param input 待分析的值。
	 * @return 分析完毕之后的表达式组.
	 */
	public static  List<ExpPair> parseStringExpression(String input){
		
		Matcher matcher=str_pattern.matcher(input);
		
		//声明匹配的结果组.
		List<ExpPair> list=new ArrayList<ExpPair>();
		if(matcher.find()){
			ExpressionParser.ExpPair pair= new ExpressionParser.ExpPair();
			//第一个字符串为表达式为空
			pair.exp=" ";
			pair.value=matcher.group(1);
			list.add(pair);
			
			//如果有子串则对其进行分析
			if(matcher.group(2)!=null){
				doSubParse(list,matcher.group(2));
			}
			
			
		}else{
			ExpressionParser.ExpPair pair= new ExpressionParser.ExpPair();
			//第一个字符串为表达式为空
			pair.exp=" ";
			pair.value=input;
			list.add(pair);
			
		}
		return list;
	}
	
	/**
	 * 对子串进行分析.
	 * @param list 匹配结果保存的列表.
	 * @param str 待分析的子串.
	 */
	private static void doSubParse(List<ExpPair> list,String str) {
		Matcher matcher=str_sub_pattern.matcher(str);
		while(matcher.find()){//循环得到所有的子串.
			ExpressionParser.ExpPair pair= new ExpressionParser.ExpPair();
			pair.exp=matcher.group(1);
			pair.value=matcher.group(2);
			list.add(pair);
		}
		
	}
	public static class ExpPair{
		public String exp;
		public Object value;
	}
}
