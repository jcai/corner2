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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tapestry.IPage;
import org.apache.tapestry.IRequestCycle;

/**
 * 基本的Entity表单页.
 * <P>
 * 提供了针对单一实体的操作,譬如C/U/D操作.
 *
 * @author <a href="http://wiki.java.net/bin/view/People/JunTsai">Jun Tsai</a>
 * @version $Revision$
 * @since 2005-11-4
 */
public abstract class EntityFormPage<T> extends AbstractEntityFormPage<T> {
	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactory.getLog(EntityFormPage.class);

	/**
	 * 得到需要显示list列表的页.
	 *
	 * @return
	 * @deprecated 将在2.1中删除，请使用 {@link #getEntityListPage()}
	 */
	public IPage getListEntityPage() {
		if (this.getRelativePage() != null) {
			EntityFormPage page = (EntityFormPage) getRequestCycle().getPage(
					this.getRelativePage());
			page.loadEntity(this.getRelativeId());
			return page;
		} else {
			return this.getRequestCycle().getPage(
					this.getPageName().substring(0,
							this.getPageName().lastIndexOf("Form"))
							+ "List");
		}
	}


	// 对关联页面的处理
	/**
	 * 增加相关联实体的页面.
	 */
	public IPage go2AddRelativeEntityPage(String entityFormPage) {
		// build form page
		entityFormPage = buildRelativePageName(entityFormPage);

		EntityFormPage page = (EntityFormPage) this.getRequestCycle().getPage(
				entityFormPage);
		constructParentPageInfo(page);

		return page;
	}

	/**
	 * 删除关联的实体.
	 *
	 * @param clazzName
	 *            关联实体类名.
	 * @param key
	 *            主键值.
	 */
	public void deleteRelativeEntityAction(String clazzName, Serializable key) {
		this.getEntityService().deleteEntityById(clazzName, key);
		this.loadEntity(this.getKey());
	}

	/**
	 * 转向编辑关联实体的页面.
	 *
	 * @param entityFormPage
	 *            关联的实体页面.
	 * @param key
	 *            主健值.
	 * @return 编辑页面.
	 */
	public IPage go2EditRelativeEntityPage(String entityFormPage,
			Serializable key) {
		entityFormPage = buildRelativePageName(entityFormPage);

		EntityFormPage page = (EntityFormPage) this.getRequestCycle().getPage(
				entityFormPage);
		constructParentPageInfo(page);

		page.loadEntity(key);
		return page;
	}

	public IPage go2ListEntityPage() {
		return this.getRequestCycle().getPage(
				this.getPageName().substring(0,
						this.getPageName().lastIndexOf("Form"))
						+ "List");
	}

	/**
	 * 构建父页面的一些信息.
	 *
	 * @param page
	 *            关联页面.
	 */
	private void constructParentPageInfo(EntityFormPage page) {
		page.setRelativePage(this.getPageName());
		page.setRelativeId(this.getKey());
		page.setRelativeClassName(this.getEntity().getClass().getName());
	}

	/**
	 * 得到关联的页面名称.
	 *
	 * @param entityFormPage
	 *            关联页.
	 * @return
	 */
	private String buildRelativePageName(String entityFormPage) {
		if (entityFormPage.indexOf("/") == -1) {
			entityFormPage = getPageName().substring(0,
					getPageName().lastIndexOf("/") + 1)
					+ entityFormPage;
		}
		return entityFormPage;
	}

	/**
	 * 退出当前页面的编辑.
	 *
	 * @return 转向的页面.
	 * @deprecated 将在2.1中删除.请使用 {@link #doCancleEntityAction()}
	 */
	public IPage cancleEntity() {
		if (logger.isDebugEnabled()) {
			logger.debug("cancleEntity()"); //$NON-NLS-1$
		}

		return getListEntityPage();
	}

	/**
	 * 确定当前页.
	 *
	 * @param cycle
	 *            页面请求.
	 * @return 转向的页面.
	 * @deprecated 将在2.1中删除.
	 */
	public IPage okayEntity(IRequestCycle cycle) {
		if (logger.isDebugEnabled()) {
			logger.debug("okayEntity(IRequestCycle)"); //$NON-NLS-1$
		}

		saveOrUpdateEntity();

		return getListEntityPage();
	}

	/**
	 * 应用当前页.
	 *
	 * @param cycle
	 *            页面请求.
	 * @deprecated 将在2.1中删除.
	 */
	public void applyEntity(IRequestCycle cycle) {
		if (logger.isDebugEnabled()) {
			logger.debug("applyEntity(IRequestCycle)"); //$NON-NLS-1$
		}
		saveOrUpdateEntity();
	}

	/**
	 * 删除实体.
	 *
	 * @param cycle
	 * @return 转向的list页面.
	 * @deprecated 将在2.1中删除.
	 */
	public IPage deleteEntity(IRequestCycle cycle) {
		getEntityService().deleteEntities(getEntity());
		return getListEntityPage();
	}



}
