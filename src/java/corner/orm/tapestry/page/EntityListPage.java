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
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tapestry.IPage;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.annotations.InitialValue;
import org.apache.tapestry.annotations.InjectObject;
import org.apache.tapestry.annotations.Persist;
import org.apache.tapestry.components.IPrimaryKeyConverter;
import org.apache.tapestry.contrib.table.model.IBasicTableModel;
import org.apache.tapestry.contrib.table.model.ITableColumn;
import org.apache.tapestry.event.PageAttachListener;
import org.apache.tapestry.event.PageBeginRenderListener;
import org.apache.tapestry.event.PageDetachListener;
import org.apache.tapestry.event.PageEvent;
import org.apache.tapestry.html.BasePage;

import corner.orm.tapestry.ReflectPrimaryKeyConverter;
import corner.service.EntityService;
import corner.util.PaginationBean;

/**
 * 针对单一实体操作的Page类。
 * 
 * @author	<a href="http://wiki.java.net/bin/view/People/JunTsai">Jun Tsai</a>
 * @version	$Revision$
 * @since	2005-11-3
 */
public abstract class EntityListPage<T> extends BasePage implements
		EntityPage<T>,PageBeginRenderListener,PageDetachListener,PageAttachListener {
	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactory.getLog(EntityListPage.class);

	/** primary key converter* */
	private ReflectPrimaryKeyConverter<T> pkc;
	
	/** 进行操作实体的类**/
	private Class<T> clazz;
	public EntityListPage(Class<T> clazz, String key) {
		this.clazz = clazz;
		this.pkc = new ReflectPrimaryKeyConverter<T>(clazz, key);
		
	}
	
	/**
	 * @see corner.orm.tapestry.page.EntityPage#loadEntity(java.io.Serializable)
	 */
	public void loadEntity(Serializable key) {
		this.setEntity(getBaseService().loadEntity(clazz,key));
	}

	
	/**记载选中的list**/
	@InitialValue("new java.util.ArrayList()")
	public abstract List<T> getSelectedEntities();
	public abstract void setSelectedEntities(List<T> list);

	/**用于分页的bean**/
	@Persist("client")
	@InitialValue("new corner.util.PaginationBean()")
	public abstract PaginationBean getPaginationBean();
	public abstract void setPaginationbean(PaginationBean pb);

	/**得到对主键的Converter**/
	public IPrimaryKeyConverter getConverter() {
		return pkc;
	}

	@InjectObject("spring:entityService")
	public abstract EntityService getBaseService();

	/**得到实体的行数.**/
	protected int getEntityRowCount() {
		return getBaseService().count(clazz);
	}
	/**得到当前的页的数据**/
	protected Iterator<T> getCurrentPageRows(int nFirst, int nPageSize,
			ITableColumn column, boolean sort) {
		PaginationBean pb = getPaginationBean();
		pb.setFirst(nFirst);
		pb.setPageSize(nPageSize);
		return getBaseService().find(clazz, pb).iterator();

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
	 * @return
	 * @see IBasicTableeModel
	 */
	public abstract IBasicTableModel getSource() ;
	public abstract void setSource(IBasicTableModel btm);

	public boolean getCheckboxSelected() {
		return false;
	}

	public void setCheckboxSelected(boolean bSelected) {
		if (logger.isDebugEnabled()) {
			logger.debug("setCheckboxSelected(boolean) bSelected ["+bSelected+"]"); //$NON-NLS-1$
		}

		if (bSelected){
			this.getSelectedEntities().add(getEntity());

			if (logger.isDebugEnabled()) {
				logger.debug("get SelectedEntities size ["+getSelectedEntities().size()+"]"); //$NON-NLS-1$
			}
		}
	}
	
	
	/*-------------------------------------------------------------------------
     * 对实体的页面操作的响应.
     * ------------------------------------------------------------------------
     */
	/**
	 * 删除实体.
	 */
	public void deleteEntities(IRequestCycle cycle) {
		if (logger.isDebugEnabled()) {
			logger.debug("deleteNodes(IRequestCycle),getSelectedEntities size ["+getSelectedEntities().size()+"]"); //$NON-NLS-1$
		}
		
		getBaseService().deleteEntities(this.getSelectedEntities().toArray());
		this.getPaginationBean().setRowCount(this.getEntityRowCount());
	}

	public IPage selectEntity(Serializable key) {
		if (logger.isDebugEnabled()) {
			logger.debug("selectEntity(String) - start"); //$NON-NLS-1$
		}
		EntityPage<T> page=getEntityFormPage();
		page.loadEntity(key);
		
		return page;
	}

	public abstract EntityPage<T> getEntityFormPage();


	/**
	 * @see org.apache.tapestry.event.PageDetachListener#pageDetached(org.apache.tapestry.event.PageEvent)
	 */
	public void pageDetached(PageEvent event) {
		
	}

	/**
	 * @see org.apache.tapestry.event.PageBeginRenderListener#pageBeginRender(org.apache.tapestry.event.PageEvent)
	 */
	public void pageBeginRender(PageEvent event) {
		if(!event.getRequestCycle().isRewinding()){
			getPaginationBean().setRowCount(getEntityRowCount());
		}
	}

	/**
	 * @see org.apache.tapestry.event.PageAttachListener#pageAttached(org.apache.tapestry.event.PageEvent)
	 */
	public void pageAttached(PageEvent event) {
		if(this.getSource()==null){
			setSource(new BasicTableModelProxy());
			
		}
	}

}
