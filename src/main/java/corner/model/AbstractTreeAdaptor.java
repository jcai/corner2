/*		
 * Copyright 2007-2008 The Beijing Maxinfo Technology Ltd. 
 * site: http://www.bjmaxinfo.com
 * file: $Id: AbstractTreeAdaptor.java 9172 2008-03-27 01:57:58Z jcai $
 * created at:2007-04-20
 */

package corner.model;

import corner.service.tree.ITreeAdaptor;

/**
 * 抽象的树的模型.
 * 
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author <a href="mailto:xf@bjmaxinfo.com">xiafei</a>
 * @version $Revision$
 * @since 2.5
 */
public abstract class AbstractTreeAdaptor extends AbstractCodeModel implements
		ITreeAdaptor {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6410796891166599761L;

	/**
	 * 
	 * @see com.bjmaxinfo.piano.model.ITreeAdaptor#getIndentString()
	 */
	public String getIndentString() {
		StringBuffer sb = new StringBuffer();
		int i = this.getDepth();
		while (i > 1) {
			sb.append("&nbsp;&nbsp;&nbsp;&nbsp;");
			i--;
		}
		return sb.toString();
	}
	
    /* bean properties begin */
    public static final String LEFT_PRO_NAME="left";
    public static final String RIGHT_PRO_NAME="right";
    public static final String DEPTH_PRO_NAME="depth";
    /* bean properties end */
}
