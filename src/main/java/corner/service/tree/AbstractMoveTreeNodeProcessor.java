/*		
 * Copyright 2007-2008 The Beijing Maxinfo Technology Ltd. 
 * site: http://www.bjmaxinfo.com
 * file: $Id: AbstractMoveTreeNodeProcessor.java 9172 2008-03-27 01:57:58Z jcai $
 * created at:2007-04-19
 */

package corner.service.tree;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateTemplate;

import corner.model.tree.AbstractTreeAdaptor;
import corner.model.tree.ITreeAdaptor;
import corner.service.EntityService;

/**
 * 抽象对树的移动进行的处理
 * 
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision: 9172 $
 * @since 0.7.5
 */
abstract class AbstractMoveTreeNodeProcessor {
	//隔离元素的HQL
	private final static String ISOLATE_NODE_HQL = "update %s  t set t.left=0 - t.left,t.right=0 - t.right  where (t.left between ? and ?)";
	//更新受影响的节点
	private final static String UPDATE_NODES_AFFECTED = "update %1$s  t set t.left=t.left+%2$d,t.right=t.right+%3$d  where (t.left between ? and ?)";
	//更新当前的NODE
	private final static String UPDATE_CURRENT_NODE_HQL = "update %1$s  t set t.left=%2$d-t.left ,t.right=%2$d-t.right  where t.left <?";

	// 父节点
	private ITreeAdaptor parentNode;

	// 当前操作的节点
	private ITreeAdaptor node;

	// Hiberante模板类
	private HibernateTemplate ht;

	// 对应的节点类的名字
	private String treeClassName;

	/**
	 * 构造跟节点对象
	 * 
	 * @return 根节点对象
	 */
	protected abstract ITreeAdaptor constructRootNode();

	/**
	 * 得到当前的节点
	 * 
	 * @return 当前的节点
	 */
	public ITreeAdaptor getCurrentNode() {
		return this.node;
	}

	/**
	 * 得到操作树的类名
	 * 
	 * @return 树的类名
	 */
	protected String getTreeClassName() {
		return this.treeClassName;
	}

	/**
	 * 得到Hibernate模板对象类
	 * 
	 * @return Hibernate模板对象类
	 */
	protected HibernateTemplate getHibernateTemplate() {
		return this.ht;
	}

	// 得到移动块的左边的起始位置,注意的是：查询的时候是 >=
	protected abstract int getMoveBlockLeftStart();

	// 得到移动块的左边的结束位置,注意的是：查询的时候是 <=
	protected abstract int getMoveBlockLeftEnd();

	// 得到当前节点移动的偏移量
	protected abstract int getOffset();

	// 得到受影响块更新的宽度
	protected abstract int getUpdateWidth();

	// 查询移动到的节点条件
	protected abstract void appendQueryReplaceNodeCriteria(
			DetachedCriteria criteria);

	// 获取移动模板的位置信息
	protected abstract void fetchMoveBlockInfo(List list);

	public void execute(ITreeAdaptor node, HibernateTemplate ht, int n,
			Class clazz) {

		this.node = node;
		this.ht = ht;

		if (clazz != null) {
			treeClassName = clazz.getName();
		} else {
			treeClassName = EntityService.getEntityClass(node).getName();
		}
		// 对当前的node刷新
		ht.refresh(node);
		// 查到移动到位置的节点
		List list = fetchReplaceNode(Math.abs(n) - 1);

		// 获取移动模块的位置信息
		fetchMoveBlockInfo(list);

		// 先孤立待移动的节点以及子节点
		insulateCurrentNodeAndChildren();

		// 更新影响到的块
		updateNodesAffected();
		// 更新当前的节点
		updateCurrentNode();

		// 刷新影响的节点
		ht.refresh(node);

		if (list.size() > 0) {
			ht.evict(list.get(0));
		}

	}

	/**
	 * 抓取将要替代的节点
	 * 
	 * @param n
	 *            移动的绝对距离
	 * @return 抓取的对象列表
	 */
	private List fetchReplaceNode(int n) {
		DetachedCriteria criteria = DetachedCriteria.forClass(EntityService
				.getEntityClass(node));
		appendQueryReplaceNodeCriteria(criteria);
		criteria.add(Restrictions.eq(AbstractTreeAdaptor.DEPTH_PRO_NAME, node.getDepth()));
		return ht.findByCriteria(criteria, n, 1);

	}

	// 孤立当前节点以及子节点
	private void insulateCurrentNodeAndChildren() {
		ht.bulkUpdate(String.format(ISOLATE_NODE_HQL, treeClassName),
				new Object[] { node.getLeft(), node.getRight()});
	}

	// 更新受影响的节点
	private void updateNodesAffected() {

		String updateNodesAffectedHQL = String.format(UPDATE_NODES_AFFECTED,
				treeClassName, getUpdateWidth(), getUpdateWidth());
		ht.bulkUpdate(updateNodesAffectedHQL, new Object[] {
				getMoveBlockLeftStart(), getMoveBlockLeftEnd()});

	}

	// 更新当前的块
	private void updateCurrentNode() {

		String updateCurrentNodeHQL = String.format(UPDATE_CURRENT_NODE_HQL,
				treeClassName, getOffset());
		ht.bulkUpdate(updateCurrentNodeHQL,
				new Object[] {0});
	}

	/**
	 * 得到父节点
	 * 
	 * @return 父节点对象
	 */
	protected ITreeAdaptor getParentNode() {
		if (this.parentNode != null) {
			return this.parentNode;
		}
		if (node.getDepth() > 1) {// 不为第一级的节点,则从库中获取
			DetachedCriteria criteria = DetachedCriteria
					.forEntityName(treeClassName);
			/**
			 * 条件为：本公司 上一级 右边大于当前的右边 左边小于当前的左边 并且深度为当前深度 -1
			 */
			criteria.add(Restrictions.gt(AbstractTreeAdaptor.RIGHT_PRO_NAME, node.getRight())).add(
					Restrictions.lt(AbstractTreeAdaptor.LEFT_PRO_NAME, node.getLeft()));
			criteria.addOrder(Order.desc(AbstractTreeAdaptor.LEFT_PRO_NAME));
			List list = ht.findByCriteria(criteria,0,1);

			if (list.size() != 1) { //
				throw new RuntimeException("非法请求，通过节点(" + node.getLeft()
						+ "," + node.getRight() + ")得到父节点为空");
			}
			parentNode = (ITreeAdaptor) list.get(0);
		} else { // 为第一级节点,构造顶极根节点对象

			parentNode = constructRootNode();

		}
		return parentNode;
	}

}