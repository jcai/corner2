/*		
 * Copyright 2007-2008 The Beijing Maxinfo Technology Ltd. 
 * site: http://www.bjmaxinfo.com
 * file: $Id: IMoveTreeNodeProcessor.java 9172 2008-03-27 01:57:58Z jcai $
 * created at:2007-04-19
 */

package corner.service.tree;

import corner.model.tree.ITreeAdaptor;
import corner.service.EntityService;

/**
 * 对树进行处理的字处理程序
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author <a href="mailto:xf@bjmaxinfo.com">xiafei</a>
 * @author <a href="mailto:Ghostbb@bjmaxinfo.com">Ghostbb</a>
 * @version $Revision$
 * @since 2.5
 */
public interface IMoveTreeNodeProcessor {

	/**
	 * @param node 需要移动的节点
	 * @param service corner基础服务类
	 * @param n 需要移动的距离,n>0 时候 向上移动，n<0的时候向下移动
	 * @param clazz 给定的查询类，如果给定的为空，则从node中获取对应的类.
	 */
	public void execute(ITreeAdaptor node,EntityService service,int n, Class clazz);

}
