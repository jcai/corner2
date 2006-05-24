//==============================================================================
//file :       $Id$
//project:     corner
//
//last change:	date:       $Date$
//           	by:         $Author$
//           	revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	China Java Users Group http://cnjug.dev.java.net
//License:		the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.orm.tapestry.page;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tapestry.IPage;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.annotations.InitialValue;
import org.apache.tapestry.annotations.Persist;
import org.apache.tapestry.components.IPrimaryKeyConverter;
import org.apache.tapestry.contrib.table.model.IBasicTableModel;
import org.apache.tapestry.contrib.table.model.ITableColumn;
import org.apache.tapestry.event.PageAttachListener;
import org.apache.tapestry.event.PageBeginRenderListener;
import org.apache.tapestry.event.PageDetachListener;
import org.apache.tapestry.event.PageEvent;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.HibernateCallback;

import corner.orm.hibernate.expression.ExpressionExample;
import corner.orm.hibernate.v3.HibernateObjectRelativeUtils;
import corner.orm.tapestry.ReflectPrimaryKeyConverter;
import corner.util.PaginationBean;

/**
 * 针对单一实体操作的Page类。
 * 
 * @author <a href="http://wiki.java.net/bin/view/People/JunTsai">Jun Tsai</a>
 * @version $Revision$
 * @since 2005-11-3
 * @deprecated 将在2.1中删除，请使用 {@link corner.orm.tapestry.page.PoListPage}代替。
 */
public abstract class EntityListPage<T> extends AbstractEntityListPage<T> implements
		PageBeginRenderListener, PageDetachListener, PageAttachListener {
	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactory.getLog(EntityListPage.class);



	protected void appendCriteria(Criteria criteria) {
		if (this.getQueryEntity() != null)
			criteria.add(ExpressionExample.create(getQueryEntity()).enableLike()
					.ignoreCase());
	}

	public abstract String getKeyName();

	public abstract void setKeyName(String keyName);

	

	/** 用于分页的bean* */
	@Persist("client")
	@InitialValue("new corner.util.PaginationBean()")
	public abstract PaginationBean getPaginationBean();

	public abstract void setPaginationbean(PaginationBean pb);

	/** 得到对主键的Converter* */
	public IPrimaryKeyConverter getConverter() {
		return new ReflectPrimaryKeyConverter<T>(getEntity().getClass(),
				getKeyName());
	}

	/** 得到实体的行数.* */
	protected int getEntityRowCount() {
		return ((Integer) ((HibernateObjectRelativeUtils) getEntityService()
				.getObjectRelativeUtils()).getHibernateTemplate().executeFind(
				new HibernateCallback() {

					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Criteria criteria = session.createCriteria(
								getEntity().getClass()).setProjection(
								Projections.rowCount());
						appendCriteria(criteria);
						return criteria.list();

					}
				}).iterator().next()).intValue();

	}

	/** 得到当前的页的数据* */
	@SuppressWarnings("unchecked")
	protected Iterator<? extends Object> getCurrentPageRows(final int nFirst,
			final int nPageSize, final ITableColumn column, final boolean sort) {
		return ((HibernateObjectRelativeUtils) getEntityService()
				.getObjectRelativeUtils()).getHibernateTemplate().executeFind(
				new HibernateCallback() {

					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {

						Criteria criteria = session.createCriteria(getEntity()
								.getClass());
						appendCriteria(criteria);

						criteria.setFirstResult(nFirst);
						criteria.setMaxResults(nPageSize);
						if (column != null) {

							criteria.addOrder(sort ? Order.asc(column
									.getColumnName()) : Order.desc(column
									.getColumnName()));
						}

						return criteria.list();

					}
				}).iterator();

	}

	class BasicTableModelProxy implements IBasicTableModel {
		/**
		 * Logger for this class
		 */
		private final Log logger = LogFactory
				.getLog(BasicTableModelProxy.class);

		public int getRowCount() {
			return getPaginationBean().getRowCount();
		}

		public Iterator getCurrentPageRows(int nFirst, int nPageSize,
				ITableColumn column, boolean flag) {
			if (getRequestCycle().isRewinding()) {
				return null;
			}

			if (logger.isDebugEnabled()) {
				logger
						.debug("getCurrentPageRows(int, int, ITableColumn, boolean) -  : nFirst=" + nFirst + ", nPageSize=" + nPageSize + ", column=" + column + ", sortable=" + flag); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			}

			return EntityListPage.this.getCurrentPageRows(nFirst, nPageSize,
					column, flag);
		}
	}

	/**
	 * 得到Table的source。
	 * 
	 * @return
	 * @see IBasicTableeModel
	 */
	public abstract IBasicTableModel getSource();

	public abstract void setSource(IBasicTableModel btm);

	

	/*-------------------------------------------------------------------------
	 * 对实体的页面操作的响应.
	 * ------------------------------------------------------------------------
	 */
	/**
	 * 批量删除实体.
	 * @deprecated 将在2.1中删除，请使用 {@link AbstractEntityListPage#doDeleteEntitiesAction()}
	 */
	public void deleteEntities(IRequestCycle cycle) {
		if (logger.isDebugEnabled()) {
			logger
					.debug("deleteNodes(IRequestCycle),getSelectedEntities size [" + getSelectedEntities().size() + "]"); //$NON-NLS-1$
		}

		getEntityService().deleteEntities(this.getSelectedEntities().toArray());
		this.getPaginationBean().setRowCount(this.getEntityRowCount());
	}

	/**
	 * 选择某一个实体.
	 * 
	 * @param key
	 *            主健值.
	 * @return 页面.
	 * @deprecated 将在2.1中删除 ,请使用 {@link AbstractEntityListPage#doDeleteEntityAction(T)}
	 */
	public IPage selectEntity(Serializable key) {
		if (logger.isDebugEnabled()) {
			logger.debug("selectEntity(String) - start"); //$NON-NLS-1$
		}
		EntityPage<T> page = getEntityFormPage();
		page.loadEntity(key);

		return page;
	}

	/**
	 * 转到增加实体的页面.
	 * 
	 * @return 增加实体页面.
	 * @deprecated 将在2.1 中删除，请使用 {@link #doNewEntityAction()}
	 */
	public IPage go2AddEntityForm() {
		return this.getEntityFormPage();
	}

	

	/**
	 * 编辑实体.
	 * 
	 * @param key
	 * @return
	 * @deprecated 用 {@link #doEditEntityAction(T)}代替.
	 */
	public IPage go2EditEntityForm(Serializable key) {
		EntityPage<T> page = this.getEntityFormPage();
		page.loadEntity(key);
		return page;
	}

	/**
	 * 删除实体操作.
	 * 
	 * @param key
	 * @deprecated 将在 2.1中删除,用 {@link #doDeleteEntityAction(T)}代替.
	 */
	public void deleteEntityAction(Serializable key) {
		getEntityService().deleteEntityById(getEntity().getClass(), key);
	}

	// 查询实体.
	/**
	 * @deprecated 将在 2.1中删除，请使用 {@link AbstractEntityListPage#doQueryEntityAction()}
	 */
	public void queryEntity() {

	}

	/**
	 * @see org.apache.tapestry.event.PageDetachListener#pageDetached(org.apache.tapestry.event.PageEvent)
	 */
	public void pageDetached(PageEvent event) {

	}

	/**
	 * @see org.apache.tapestry.event.PageBeginRenderListener#pageBeginRender(org.apache.tapestry.event.PageEvent)
	 */
	public void pageBeginRender(PageEvent event) {
		if (!event.getRequestCycle().isRewinding()) {
			getPaginationBean().setRowCount(getEntityRowCount());
		}
	}

	/**
	 * @see org.apache.tapestry.event.PageAttachListener#pageAttached(org.apache.tapestry.event.PageEvent)
	 */
	public void pageAttached(PageEvent event) {
		if (this.getSource() == null) {
			setSource(new BasicTableModelProxy());

		}
	}

	
}
