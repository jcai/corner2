/*		
 * Copyright 2007-2008 The Beijing Maxinfo Technology Ltd. 
 * site: http://www.bjmaxinfo.com
 * file: $Id: MoveDownProcessor.java 9172 2008-03-27 01:57:58Z jcai $
 * created at:2007-04-19
 */

package corner.service.tree;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import corner.model.AbstractTreeAdaptor;
import corner.util.BeanUtils;

/**
 * 向下移动的处理程序
 * 
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author <a href="mailto:xf@bjmaxinfo.com">xiafei</a>
 * @version $Revision$
 * @since 2.5
 */
class MoveDownProcessor extends AbstractMoveTreeNodeProcessor
		implements IMoveTreeNodeProcessor {

	private int moveBlockLeftStart = 0;

	private int moveBlockLeftEnd = Integer.MAX_VALUE;

	private int offset = 0;

	@Override
	protected void appendQueryReplaceNodeCriteria(DetachedCriteria criteria) {
		criteria.add(Restrictions.gt(AbstractTreeAdaptor.RIGHT_PRO_NAME, getCurrentNode().getRight()))
				.add(Restrictions.lt(AbstractTreeAdaptor.RIGHT_PRO_NAME, getParentNode().getRight()));
		criteria.addOrder(Order.asc(AbstractTreeAdaptor.LEFT_PRO_NAME));

	}

	@Override
	protected void fetchMoveBlockInfo(List list) {
		if (list.size() == 0) { // 未找到了移动的位置，说明已经溢出
			offset = (getParentNode().getRight() - 1)
					- getCurrentNode().getRight();
			moveBlockLeftStart = getCurrentNode().getRight() + 1;
			moveBlockLeftEnd = getParentNode().getRight();
		} else {// 找到了移动的位置
			ITreeAdaptor replaceNode = (ITreeAdaptor) list.get(0);
			offset = replaceNode.getRight() - getCurrentNode().getRight();
			moveBlockLeftStart = getCurrentNode().getRight() + 1;
			moveBlockLeftEnd = replaceNode.getRight();
		}

	}

	@Override
	protected int getMoveBlockLeftEnd() {
		return this.moveBlockLeftEnd;
	}

	@Override
	protected int getMoveBlockLeftStart() {
		return this.moveBlockLeftStart;
	}

	@Override
	protected int getOffset() {
		return this.offset;
	}

	@Override
	protected int getUpdateWidth() {
		return getCurrentNode().getLeft() - getCurrentNode().getRight() - 1;
	}

	@Override
	protected ITreeAdaptor constructRootNode() {
		ITreeAdaptor rootNode = (ITreeAdaptor) BeanUtils
				.instantiateClass(getTreeClassName());
		rootNode.setLeft(0);

		long rowCount = (Long) getHibernateTemplate()
				.find("select count(*) from " + getTreeClassName()).get(0);
		rootNode.setRight((int) (rowCount * 2 + 1));

		return rootNode;
	}
}
