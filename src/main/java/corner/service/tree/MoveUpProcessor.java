/*		
 * Copyright 2007-2008 The Beijing Maxinfo Technology Ltd. 
 * site: http://www.bjmaxinfo.com
 * file: $Id: MoveUpProcessor.java 9172 2008-03-27 01:57:58Z jcai $
 * created at:2007-04-19
 */

package corner.service.tree;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import corner.model.tree.AbstractTreeAdaptor;
import corner.model.tree.ITreeAdaptor;
import corner.util.BeanUtils;

/**
 * 向上移动
 * 
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author <a href="mailto:xf@bjmaxinfo.com">xiafei</a>
 * @version $Revision$
 * @since 2.5
 */
class MoveUpProcessor extends AbstractMoveTreeNodeProcessor implements
		IMoveTreeNodeProcessor {
	private int moveBlockLeftStart = 0;

	private int moveBlockLeftEnd = Integer.MAX_VALUE;

	private int offset = 0;

	@Override
	protected void appendQueryReplaceNodeCriteria(DetachedCriteria criteria) {
		criteria.add(Restrictions.lt(AbstractTreeAdaptor.LEFT_PRO_NAME, getCurrentNode().getLeft())).add(
				Restrictions.gt(AbstractTreeAdaptor.LEFT_PRO_NAME, getParentNode().getLeft()));
		criteria.addOrder(Order.desc(AbstractTreeAdaptor.LEFT_PRO_NAME));

	}

	@Override
	protected void fetchMoveBlockInfo(List list) {
		if (list.size() == 0) { // 未找到了移动的位置，说明已经溢出
			offset = getCurrentNode().getLeft()
					- (getParentNode().getLeft() + 1);
			moveBlockLeftStart = getParentNode().getLeft() + 1;
			moveBlockLeftEnd = getCurrentNode().getLeft() - 1;
		} else {// 找到了移动的位置
			ITreeAdaptor replaceNode = (ITreeAdaptor) list.get(0);
			offset = getCurrentNode().getLeft() - replaceNode.getLeft();
			moveBlockLeftStart = replaceNode.getLeft();
			moveBlockLeftEnd = getCurrentNode().getLeft() - 1;
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
		return 0 - this.offset;
	}

	@Override
	protected int getUpdateWidth() {
		return getCurrentNode().getRight() - getCurrentNode().getLeft() + 1;
	}

	@Override
	public ITreeAdaptor constructRootNode() {
		ITreeAdaptor rootNode = (ITreeAdaptor) BeanUtils
				.instantiateClass(getTreeClassName());
		rootNode.setLeft(0);
		rootNode.setRight(Integer.MAX_VALUE);

		return rootNode;
	}

}
