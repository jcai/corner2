// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2007-07-31
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

import java.util.ArrayList;
import java.util.List;

import corner.orm.tapestry.component.checkbox.StringCheckbox;
import corner.orm.tapestry.pdf.AbstractPdfPage;

/**
 * 保存Corner中一些的常量
 * 
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 2.3.7
 */
public class Constants {
	/**
	 * PdfImage组件中，图片缩放的方式
	 * percent:百分比缩放 border:边框缩放
	 */
	public static final String PDFIMAGE_COMPONENT_SCALE_TYPE_PERCENT = "percent";
	public static final String PDFIMAGE_COMPONENT_SCALE_TYPE_BORDER = "border";
	
	/**
	 * {@link AbstractPdfPage}显示StringCheckbox组件的时候，使用的常量
	 */
	public static final String PDF_PAGE_STRING_CHECKBOX_TRUE_FLAG = "1";
	public static final String PDF_PAGE_STRING_CHECKBOX_FALSE_FLAG = "0";
	
	/**
	 * {@link AbstractPdfPage}显示Checkbox组件的时候，使用的常量
	 */
	public static final boolean PDF_PAGE_BOOLEAN_CHECKBOX_TRUE_FLAG = true;
	public static final boolean PDF_PAGE_BOOLEAN_CHECKBOX_FALSE_FLAG = false;
	
	/**
	 * {@link org.apache.tapestry.form.Checkbox}和{@link StringCheckbox}使用的回显字符串
	 */
	public static final String PDF_PAGE_CHECKBOX_TRUE_STR = "是";
	public static final String PDF_PAGE_CHECKBOX_FALSE_STR = "否";
	
	/**
	 * 发送的消息
	 */
	private static List<String> msgList = new ArrayList<String>();
//	static {
//		for(int i=0;i<100;i++){
//			msgList.add("show me the money!");
//		}
//	}
	
	public static void addMsg(String s){
		msgList.clear();
		msgList.add(s);
	}
	
	public static String getMsg(){
		if(msgList.size()>0){
			String str = msgList.get(0);
			msgList.clear();
			return str;
		} else{
			return null;
		}
	}
	
}
