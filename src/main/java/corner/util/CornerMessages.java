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

import org.apache.hivemind.impl.MessageFormatter;

/**
 * corner里面展示国际化消息
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.3.7
 */
public class CornerMessages {
	private static final MessageFormatter _formatter = new MessageFormatter(
			CornerMessages.class);
	public static String totalPage(Integer pageNum){
		return _formatter.format("total-page", pageNum);
	}
}
