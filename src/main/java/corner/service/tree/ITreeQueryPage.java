/*		
 * Copyright 2007-2008 The Beijing Maxinfo Technology Ltd. 
 * site: http://www.bjmaxinfo.com
 * file: $Id: ITreeQueryPage.java 9172 2008-03-27 01:57:58Z jcai $
 * created at:2007-11-30
 */

package corner.service.tree;

import org.hibernate.criterion.DetachedCriteria;

/**
 * 使用leftTree组建可以通过本接口实现查询功能的扩展
 * @author <a href="mailto:xf@bjmaxinfo.com">xiafei</a>
 * @version $Revision$
 * @since 2.5
 */
public interface ITreeQueryPage {
	/**
	 * 
	 * @param depend 
	 * @param 
	 * @return
	 */
	public abstract void appendCriteria(DetachedCriteria criteria, String [] depends);
}