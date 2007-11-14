/*		
 * Copyright 2007 The Beijing Maxinfo Technology Ltd. 
 * site: http://www.bjmaxinfo.com
 * file: $Id$
 * created at:2007-04-05
 */

package corner.orm.tapestry.pdf;

import org.apache.tapestry.IComponent;
import org.apache.tapestry.IRequestCycle;

/**
 * 实现PDF跳转的接口
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 2.3.7
 */
public interface IPdfDirect extends IComponent{
	/**
	 * 响应跳转
	 * @param cycle 请求的RequestCycle
	 */
	public void trigger(IRequestCycle cycle);

}
