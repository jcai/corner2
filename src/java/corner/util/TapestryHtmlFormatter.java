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
import java.nio.CharBuffer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * 
 * @author AgileWang
 * @version $Revision$
 * @since 2005-12-7
 */
public class TapestryHtmlFormatter {
	// 如果有其它的tag,如select,直接加到checkbox后面就可

	
	private static final Pattern jwcidPattern = Pattern.compile(".*(jwcid\\s*=\\s*\"?.*\"?).*");
	
	private static final Pattern pattern = Pattern.compile(
			   "<(form|input|textarea|checkbox|select|button|option|)([^<>]*)(name=\"?([^<>\\s\"]+)\"?)([^<>]*)>",
			   Pattern.CASE_INSENSITIVE|Pattern.MULTILINE);
			 


	public static StringBuffer format(InputStream io) throws IOException {
		StringBuffer inputBuffer = new StringBuffer();

		BufferedReader isr = new BufferedReader(new InputStreamReader(io));
		String s = null;
		char[] buffer = new char[1024];
		int len = -1;
		while ((len = isr.read(buffer)) != -1) {
			inputBuffer.append(buffer, 0, len);
		}
		isr.close();

		Matcher matcher = pattern.matcher(inputBuffer.toString());

		StringBuffer buf = new StringBuffer();
		while ((matcher.find())) {
			String value = null;
			String s1 = matcher.group(1).trim();
			String s2 = matcher.group(2).trim().replaceAll("jwcid=\"?[^\\s]*\"?","");
			String s3 = matcher.group(3).trim();
			String name =matcher.group(4).trim();
			String jwcid="jwcid=\"";
				
			if("form".equalsIgnoreCase(s1)){
				jwcid+=name+"Form";
			}else{
				jwcid+=lowerFirstLetter(name)+"Field";
			}
			jwcid+="\"";
			
			String s5 = matcher.group(5).trim().replaceAll("jwcid=\"?[^\\s]*\"?","");;
			value = String.format("<%s %s %s %s %s>", s1, jwcid,s2, s3,s5);
			matcher.appendReplacement(buf, value);
		}

		matcher.appendTail(buf);
		return buf;
	}
	 public static String lowerFirstLetter(String data)
	    {
	        String firstLetter = data.substring(0, 1).toLowerCase();
	        String restLetters = data.substring(1);
	        return firstLetter + restLetters;
	    }

}
