// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2005-12-07
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package corner.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * 对tapestry的文件进行格式化，同时自动加上一些标记。
 * @author AgileWang
 * @author jun tsai
 * @version $Revision:3677 $
 * 
 * @since 2005-12-7
 * 
 */
public class TapestryHtmlFormatter {
	// 如果有其它的tag,如select,直接加到checkbox后面就可

	
	private static final Pattern pattern = Pattern.compile(
			   "<(form|input|textarea|checkbox|select|button|option)([^<>]*)(name=\"?([^<>\\s\"]+)\"?)([^<>]*)>",
			   Pattern.CASE_INSENSITIVE|Pattern.MULTILINE);
			 


	public static StringBuffer format(InputStream io) throws IOException {
		StringBuffer inputBuffer = new StringBuffer();

		BufferedReader isr = new BufferedReader(new InputStreamReader(io));
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
			
			String s5 = matcher.group(5).trim().replaceAll("jwcid=\"?[^\\s]*\"?","");
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
