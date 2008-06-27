/*		
 * Copyright 2008 The Fepss Pty Ltd. 
 * site: http://www.fepss.com
 * file: $Id: TreeService.java 508 2008-05-27 01:44:48Z Ghostbb $
 * created at:2008-05-11
 */

package corner.service.tree;

import java.util.List;

import org.apache.tapestry.IPage;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import corner.model.tree.ITreeAdaptor;
import corner.service.EntityService;
import corner.util.BeanUtils;

/**
 * 提供了对tee组件的操作服务类.
 * 
 * TODO :有一个比较大的问题就是怎么锁定表？？？
 * 
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author <a href="mailto:xf@bjmaxinfo.com">xiafei</a>
 * @version $Revision$
 * @since 2.5
 */
public class TreeService extends EntityService {

	//查询所有节点数量
	private static final String COUNT_ALL_NODE_HSQL="select count(*) from %s";
	//删除节点
	private static final String DELETE_NODE_HSQL="delete %s n where (n.left between ? and ? )";
	//更新左边节点
	private static final String UPDATE_LEFT_HSQL="update %s n set n.left=n.left+%d where n.left>?";
	//更新右边节点
	private static final String UPDATE_RIGHT_HSQL="update %s n set n.right=n.right+%d where n.right>?";
	
	/**
	 * 得到相应深度的数
	 * @param page 
	 * 
	 * @param clazz 查询的class对应的类
	 * @param depend 
	 * @param depth	要查询的深度
	 */
	public List getDepthTree(
			IPage page, Class clazz, String [] depends, int depth, int left,
			int right) {
		HibernateTemplate ht = ((HibernateDaoSupport) this
				.getObjectRelativeUtils()).getHibernateTemplate();
		DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
		
		if(depth == 1){
//			select * from MP_S_ACCOUNT_ITEM_CODE where tree_depth = 1;
			criteria.add(Restrictions.eq(ITreeAdaptor.DEPTH_PRO_NAME, depth));
		}else{
//			select * from MP_S_ACCOUNT_ITEM_CODE where tree_depth = 2 and tree_left_code > 1 and tree_right_code < 8;
			Criterion leftright = Restrictions.and(Restrictions.gt(ITreeAdaptor.LEFT_PRO_NAME, left), Restrictions.lt(ITreeAdaptor.RIGHT_PRO_NAME, right));
			criteria.add(Restrictions.and(Restrictions.eq(ITreeAdaptor.DEPTH_PRO_NAME, depth), leftright));
		}
		
		//扩展
		if(depends.length != 0 && depends[0] != null && depends[0].length() != 0){
			((ITreeQueryPage)page).appendCriteria(criteria,depends);
		}
		
		criteria.addOrder(Order.asc(ITreeAdaptor.LEFT_PRO_NAME));

		return ht.findByCriteria(criteria);
	}
	
	/**
	 *  得到相应深度的数
	 * @param page 
	 * @param queryClassName	查询的className
	 * @param depend 
	 * @param depth	要查询的深度
	 */
	@SuppressWarnings("unchecked")
	public List getDepthTree(IPage page, String queryClassName, String [] depends, int depth, int left,
			int right) {
		Class clazz;
		try {
			clazz = Class.forName(queryClassName);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}

		return getDepthTree(page,clazz,depends,depth,left,right);
	}
	
	
	
	
	/**
	 * 保存树型结构的一个节点.此方法只是针对新建树节点时候，
	 *  <p>需要注意的是：在调用改方法之前，一定不能先保存了改节点
	 * 
	 * @param node
	 *            待保存的节点
	 * @param parentNode 父节点
	 * @param clazz 待保存节点的类,目的是区分有继承关系的实体.
	 */
	public void saveTreeChildNode(ITreeAdaptor node, ITreeAdaptor parentNode, Class clazz) {
		String treeClassName = getTreeClassName(node,clazz);

		// 得到HibernateTemplate object
		HibernateTemplate ht = getHibernateTemplate();

		if (parentNode == null) { // 插入顶级节点
			parentNode = (ITreeAdaptor) BeanUtils.instantiateClass(EntityService.getEntityClass(node).getName());
			parentNode.setLeft(0);
			parentNode.setDepth(0);
			long rowCount = (Long) ht.find(String.format(COUNT_ALL_NODE_HSQL, treeClassName)).get(0);
			parentNode.setRight((int) (rowCount * 2 + 1));
		} else { // reload parent object
			ht.refresh(parentNode);
		}
		// 得到父节点的右边值
		int parentRight = parentNode.getRight();
		// 更新大于节点的值
		/*
		 * update table set left=left+2 where left>parentRight update table set
		 * right=right+2 where right>=parentRight
		 */

		String updateLeftHQL = String.format(UPDATE_LEFT_HSQL,treeClassName,2);
		
		ht.bulkUpdate(updateLeftHQL, new Object[] { parentRight});

		String updateRightHQL = String.format(UPDATE_RIGHT_HSQL,treeClassName,2);
		ht.bulkUpdate(updateRightHQL, new Object[] { parentRight - 1});

		// 更新当前节点的值
		node.setLeft(parentRight);
		node.setRight(parentRight + 1);
		node.setDepth(parentNode.getDepth() + 1);

		// 保存当前的实体
		ht.saveOrUpdate(node);
		if (parentNode.getId() != null) {
			ht.refresh(parentNode);
		}
	}

	

	/**
	 * 得到完整树
	 * 
	 * @param clazz
	 *            对应的类
	 */
	@SuppressWarnings("unchecked")
	public List<? extends ITreeAdaptor> getTree(
			Class<? extends ITreeAdaptor> clazz) {
		HibernateTemplate ht = ((HibernateDaoSupport) this
				.getObjectRelativeUtils()).getHibernateTemplate();
		DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
		criteria.addOrder(Order.asc(ITreeAdaptor.LEFT_PRO_NAME));

		return ht.findByCriteria(criteria);
	}

	/**
	 * 同级移动节点.
	 *  <p>
	 *  当n>0的时候，为向上移动，
	 *  当 n<0的时候，为向下移动.
	 * 
	 * @param node
	 *            节点
	 * @param n        移动的位置
	 * @param clazz  给定的查询类
	 */
	public void moveNode(ITreeAdaptor node, int n, Class clazz) {
		if (n == 0) {
			return;
		}
		
		//得到当前的Hibernate模板
		HibernateTemplate ht = ((HibernateDaoSupport) getObjectRelativeUtils()).getHibernateTemplate();

		IMoveTreeNodeProcessor processor=createSubProcessor(node,ht,n);
		
		processor.execute(node,ht,n,clazz);
		
		
	}

	private IMoveTreeNodeProcessor createSubProcessor(ITreeAdaptor node,HibernateTemplate ht, int n) {
		if(n>0){
			return new MoveUpProcessor();
		}else{
			return new MoveDownProcessor();
		}
		
	}



	private String getTreeClassName(ITreeAdaptor node, Class clazz) {
		if(clazz!=null){
			return clazz.getName();
		}else{
			return EntityService.getEntityClass(node).getName();
		}
		
	}

	/**
	 * 删除一个节点
	 * 
	 * @param node 节点
	 * @param clazz 用来查询的模型类.
	 */
	public void deleteTreeNode(ITreeAdaptor node, Class clazz) {
		
		String treeClassName =getTreeClassName(node,clazz);
		int left = node.getLeft();
		int right = node.getRight();

		int width = left-right - 1;

		// 删除该节点，以及节点下面所属的字节点
		HibernateTemplate ht = ((HibernateDaoSupport) this
				.getObjectRelativeUtils()).getHibernateTemplate();

		ht.bulkUpdate(String.format(DELETE_NODE_HSQL,treeClassName),
				new Object[] { left, right});

		// 更新其他节点的左右值
		ht.bulkUpdate(String.format(UPDATE_LEFT_HSQL,treeClassName,width), new Object[] {right});
		ht.bulkUpdate(String.format(UPDATE_RIGHT_HSQL,treeClassName,width), new Object[] {right});

	}
}
