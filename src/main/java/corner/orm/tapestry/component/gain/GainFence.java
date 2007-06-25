//==============================================================================
// file :       $Id$
// project:     corner
//
// last change: date:       $Date$
//              by:         $Author$
//              revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.orm.tapestry.component.gain;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.tapestry.BaseComponent;
import org.apache.tapestry.IForm;
import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.TapestryUtils;
import org.apache.tapestry.annotations.Parameter;
import org.apache.tapestry.event.PageBeginRenderListener;
import org.apache.tapestry.event.PageEvent;
import org.apache.tapestry.form.IFormComponent;
import org.apache.tapestry.valid.IValidationDelegate;

import corner.orm.spring.SpringContainer;
import corner.service.EntityService;

/**
 * 
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision$
 * @since 2.3.7
 */
public abstract class GainFence extends BaseComponent implements
		IFormComponent, PageBeginRenderListener {

	/**
	 * @see org.apache.tapestry.event.PageBeginRenderListener#pageBeginRender(org.apache.tapestry.event.PageEvent)
	 */
	public void pageBeginRender(PageEvent event) {

		initData(event.getRequestCycle());

		for (GainPoint gp : this.getGainPoints()) { // 初始化gp
			gp.setElements(new ArrayList<String>());
			gp.setTableId(this.getTableId());
			gp.setPersistentId(this.getPersistentId());
		}

		// 初始化gp赋值
		for (int i = 0; i < this.getSource().size(); i++) {
			Object entity = this.getSource().get(i);

			for (GainPoint gp : this.getGainPoints()) {
				try {
					gp.getElements().add(
							(String) PropertyUtils.getProperty(entity, gp
									.getElementName()));
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Invoked from {@link #renderComponent(IMarkupWriter, IRequestCycle)} to
	 * rewind the component. If the component is
	 * {@link IFormComponent#isDisabled() disabled} this will not be invoked.
	 * 
	 * @param writer
	 * @param cycle
	 */
	protected void rewindFormComponent(IMarkupWriter writer, IRequestCycle cycle) {
		initData(cycle);

		entityWorkshop();
	}

	/**
	 * 整理entity
	 */
	private <T> void entityWorkshop() {

		Object entity = null;

		GainPoint gp = this.getGainPoints().iterator().next();

		int Size = gp.getElementLength();

		Class entityClass = null;
		try {
			entityClass = Class.forName(getEntityClass());
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		neatenPersistentEntity(); //整理持久化类

		String temp = null;

		for (int i = 0; i < Size; i++) {

			if (i < this.getPersistentSize()) {
				entity = this.getEntitys().get(i);
			} else {
				try {
					entity = entityClass.newInstance();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			for (GainPoint g : this.getGainPoints()) {
				try {
					temp = g.getElements().get(i);

					if (temp != null && !temp.equals("")) {
						PropertyUtils.setProperty(entity, g.getElementName(),
								temp);
					}

				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			this.getSaveOrUpdateEntitys().add(entity);
		}
	}

	/**
	 * @see org.apache.tapestry.AbstractComponent#cleanupAfterRender(org.apache.tapestry.IRequestCycle)
	 */
	@Override
	protected void cleanupAfterRender(IRequestCycle cycle) {
		super.cleanupAfterRender(cycle);
		this.setEntitys(null);
	}

	/**
	 * 获得已经持久化的类
	 */
	private int getPersistentSize() {
		return this.getEntitys().size();
	}

	/**
	 * 整理已经持久化的entity
	 */
	private <T> void neatenPersistentEntity() {
		
		this.setSaveOrUpdateEntitys(new ArrayList<T>());

		this.setDeleteEntitys(new ArrayList<T>());
		
		this.setEntitys(new ArrayList<Object>());

		GainPoint gp = (GainPoint) this.getPage().getComponent(
				"GainPointIdField");

		String id = null;

		for (Object entity : this.getSource()) { // 获得需要更新的entity
			try {
				id = (String) PropertyUtils.getProperty(entity, gp
						.getElementName());
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			this.getDeleteEntitys().add(entity); // 删除的先加入

			for (String s : gp.getElements()) {
				if (id.equals(s)) {
					this.getDeleteEntitys().remove(
							this.getDeleteEntitys().size() - 1); // 如果相同则减少删除的
					this.getEntitys().add(entity);
				}
			}
		}
	}

	/**
	 * 初始化信息
	 */
	private void initData(IRequestCycle cycle) {
		String GainPointFields[] = this.getGainPointFields().split(";");
		if (this.getGainPoints() == null) {
			this.setGainPoints(new ArrayList<GainPoint>());
		}

		for (String s : GainPointFields) {
			this.getGainPoints().add(
					(GainPoint) cycle.getPage().getComponent(s));
		}
	}

	/**
	 * @see org.apache.tapestry.form.AbstractFormComponent#renderFormComponent(org.apache.tapestry.IMarkupWriter,
	 *      org.apache.tapestry.IRequestCycle)
	 */
	@Override
	protected void renderComponent(IMarkupWriter writer, IRequestCycle cycle) {

		/**
		 * 处理form
		 */
		IForm form = TapestryUtils.getForm(cycle, this);

		setForm(form);

		if (form.wasPrerendered(writer, this))
			return;

		IValidationDelegate delegate = form.getDelegate();

		delegate.setFormComponent(this);

		setName(form);

		/**
		 * 当提交时和为显示时
		 */
		if (form.isRewinding()) {
			if (!isDisabled()) {
				rewindFormComponent(writer, cycle);
			}

			// This is for the benefit of the couple of components (LinkSubmit)
			// that allow a body.
			// The body should render when the component rewinds.

			if (getRenderBodyOnRewind())
				renderBody(writer, cycle);
		}
		if (!cycle.isRewinding()) {
			super.renderComponent(writer, cycle);
		}
	}

	private List<Object> entitys;
	
	/**
	 * @return Returns the entitys.
	 */
	private List<Object> getEntitys() {
		return entitys;
	}

	/**
	 * @param entitys The entitys to set.
	 */
	private void setEntitys(List<Object> entitys) {
		this.entitys = entitys;
	}

	/**
	 * 需要增加或更新的Entitys
	 */
	public abstract <T> List<T> getSaveOrUpdateEntitys();

	public abstract <T> void setSaveOrUpdateEntitys(List<T> l);

	/**
	 * 需要删除的的Entitys
	 */
	public abstract <T> List<T> getDeleteEntitys();

	public abstract <T> void setDeleteEntitys(List<T> l);

	@Parameter
	public abstract <T> List<T> getSource();

	/**
	 * 输入的元素
	 */
	public abstract List<GainPoint> getGainPoints();

	public abstract void setGainPoints(List<GainPoint> l);

	/**
	 * 与相连的GainPoint用分号(;)隔开
	 */
	@Parameter(required = true)
	public abstract String getGainPointFields();

	/**
	 * 输入的元素
	 */
	@Parameter(required = true)
	public abstract String getEntityClass();

	/**
	 * 相应的tableId,由gf赋值
	 */
	@Parameter(required = true)
	public abstract String getTableId();
	
	/**
	 * 相应的tableId,由gf赋值
	 */
	@Parameter(defaultValue = "literal:id")
	public abstract String getPersistentId();

	/**
	 * 
	 */
	public abstract IForm getForm();

	public abstract void setForm(IForm form);

	protected boolean getRenderBodyOnRewind() {
		return true;
	}

	protected void setName(IForm form) {
		setName(form.getElementId(this));
	}

	protected EntityService getEntityService() {
		return (EntityService) SpringContainer.getInstance()
				.getApplicationContext().getBean("entityService");
	}
}
