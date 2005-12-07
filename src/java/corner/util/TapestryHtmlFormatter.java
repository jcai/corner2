//==============================================================================
//file :       $Id$
//project:     corner
//
//last change:	date:       $Date$
//           	by:         $Author$
//           	revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	China Java Users Group http://cnjug.dev.java.net
//License:		the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * 
 * @author	AgileWang
 * @version	$Revision$
 * @since	2005-12-7
 */
public class TapestryHtmlFormatter {
//	如果有其它的tag,如select,直接加到checkbox后面就可
	 private static final Pattern pattern = Pattern.compile(
	   "<(form|input|textarea|checkbox|select|button|option|)([^<>]*)(name=\"?([^<>\\s\"]+)\"?)([jwcid=\"?([^<>\\s\"]+)\"?]?+)([^<>]*)>",
	   Pattern.CASE_INSENSITIVE|Pattern.MULTILINE);
	 
	 public static  StringBuffer format(InputStream io) throws IOException{
		 StringBuffer inputBuffer = new StringBuffer();
		  
		  BufferedReader  isr = new BufferedReader(new InputStreamReader(io));
		  String s = null;
		  while(( s = isr.readLine())!=null){
		   inputBuffer.append(s); 
		   if(s.length()>0){
		    inputBuffer.append("\n");
		   }
		  }  
		  isr.close();
		  
		  Matcher matcher = pattern.matcher(inputBuffer.toString());   
		    
		     StringBuffer buf = new StringBuffer();
		     while ((matcher.find())) {    
		         String s1 = matcher.group(1);
		         String s2 = matcher.group(2);
		         //name
		         String s3 = matcher.group(3);
		         String name = matcher.group(4);
		         //jwcid
		         String s7 = matcher.group(5);
		         String value="";
		         value= String.format("<%s%s%s%s%s>",s1,s2,s3," jwcid=\""+name+"Field\"",s7); 
		          
		         matcher.appendReplacement(buf, value);
		     }
		     
		     matcher.appendTail(buf);
		     
		     return buf;
		     
	 }
	
}
