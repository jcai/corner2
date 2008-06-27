/*		
 * Copyright 2007-2008 The Beijing Maxinfo Technology Ltd. 
 * site: http://www.bjmaxinfo.com
 * file: $Id: ITreeAdaptor.java 9172 2008-03-27 01:57:58Z jcai $
 * created at:2007-04-19
 */

package corner.service.tree;

import corner.orm.hibernate.IPersistModel;

/**
 * 用来定义树的适配器
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author <a href="mailto:xf@bjmaxinfo.com">xiafei</a>
 * @version $Revision$
 * @since 2.5
 */
public interface ITreeAdaptor extends ITree,IPersistModel{
	
    /**
	 * @return 返回名称.
	 */
	public String getNodeName();
	/**
	 * 保存名称
	 */
	public void setNodeName(String name);
	
	/**
	 * 得到树的缩进字符串
	 * @return 缩进字符串
	 */
	public String getIndentString();
}
