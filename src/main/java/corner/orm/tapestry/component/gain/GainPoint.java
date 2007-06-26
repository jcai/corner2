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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.tapestry.BaseComponent;
import org.apache.tapestry.IForm;
import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.IScript;
import org.apache.tapestry.PageRenderSupport;
import org.apache.tapestry.TapestryUtils;
import org.apache.tapestry.annotations.InjectScript;
import org.apache.tapestry.annotations.Parameter;
import org.apache.tapestry.form.IFormComponent;
import org.apache.tapestry.json.JSONArray;
import org.apache.tapestry.json.JSONObject;
import org.apache.tapestry.valid.IValidationDelegate;

/**
 * 增长点组件
 * 设置显示的属性、类名、查询数据源和复制table的id
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision$
 * @since 2.3.7
 */
public abstract class GainPoint extends BaseComponent implements IFormComponent {

	/**
	 * Invoked from {@link #renderComponent(IMarkupWriter, IRequestCycle)} to
	 * rewind the component. If the component is
	 * {@link IFormComponent#isDisabled() disabled} this will not be invoked.
	 * 
	 * @param writer
	 * @param cycle
	 */
	protected void rewindFormComponent(IMarkupWriter writer, IRequestCycle cycle) {
		
		initData(); //初始化

		setup(cycle); 	//初始化页面信息
		
		entityWorkshop();	//整理entity
	}

	/**
	 * 整理页面传来的值
	 */
	private void setup(IRequestCycle cycle) {
		this.setForegroundEntitys(new ArrayList<List>());
		
		for(String s : this.getEntityPropertys()){
			String sl[] = cycle.getParameters(s);
			
			this.getForegroundEntitys().add(Arrays.asList(sl));
			this.setForegroundLength(sl.length);
		}
	}

	/**
	 * 整理entity
	 */
	private <T> void entityWorkshop() {

		Object entity = null;

		int Size = this.getForegroundLength();

		Class entityClass = null;
		try {
			entityClass = Class.forName(getEntityClass());
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		neatenPersistentEntity(); //整理持久化类

		Object temp = null;	//临时变量
		
		Iterator FEList = null;

		for (int i = 0; i < Size; i++) {

			if (i < this.getPersistentSize()) {
				entity = this.getEntitys().get(i);
			} else {
				try {
					entity = entityClass.newInstance();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}

			FEList = this.getForegroundEntitys().iterator();
			
			for (int j =0; j<this.getForegroundEntitys().size() ;j++) {
				
				
				List foregroundEntity = (List) FEList.next();
				
				try {
					temp = foregroundEntity.get(i);

					if (temp != null && !temp.equals("")) {
						PropertyUtils.setProperty(entity, this.getEntityPropertys().get(j),
								temp);
					}

				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
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
		this.setForegroundEntitys(null);
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

		String foregroundList[] = this.getPage().getRequestCycle().getParameters(this.getPersistentId());
		
		String id = null;

		for (Object entity : this.getSource()) { // 获得需要更新的entity
			try {
				id = (String) PropertyUtils.getProperty(entity, this.getPersistentId());
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}

			this.getDeleteEntitys().add(entity); // 删除的先加入

			for (String s : foregroundList) {
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
	private void initData() {
		String entityPropertys[] = this.getShowPropertys().split(",");
		this.setEntityPropertys(Arrays.asList(entityPropertys));
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
			
			this.initData();
			
			/**
			 * 加入js
			 */
			PageRenderSupport pageRenderSupport = TapestryUtils
					.getPageRenderSupport(cycle, this);

			Map<String, Object> scriptParms = new HashMap<String, Object>();
			
			//获得json串
			JSONObject JSONElementValues = getJSONElementValues();
			
			scriptParms.put("tableId", this.getTableId());	//循环的表名，只使用一次
			
			scriptParms.put("persistentId", this.getPersistentId());	//持久化id
			
			scriptParms.put("elementSize", this.getSource().size());	//tr循环的次数，只使用一次
			
			scriptParms.put("gpid", this.getClientId());	//怕重复使用gpid
			
			scriptParms.put("elementValues", JSONElementValues.toString());	//
			
			getScript().execute(this, cycle, pageRenderSupport, scriptParms);
			
		}
	}
	
	
//	
	/**
	 * 获得json串
	 * @param elements
	 */
	private JSONObject getJSONElementValues() {
		
		JSONObject json = new JSONObject();
		
		JSONArray elementValues = null;
		
		for(String propertyName : this.getEntityPropertys()){	//遍历，获得属性名
			
			elementValues = new JSONArray();
			
			for(Object entity : this.getSource()){
				try {
					elementValues.put(PropertyUtils.getProperty(entity, propertyName));
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				}
			}
			
			json.put(propertyName, elementValues);
		}
		
		return json;
	}

	/**
	 * 写入js
	 */
	@InjectScript("GainPoint.script")
	public abstract IScript getScript();

	
	/**
	 * 前台entity
	 */
	private List<List> foregroundEntitys;
	
	private int foregroundLength;
	
	private List<Object> entitys;
	
	/**
	 * @return Returns the entitys.
	 */
	protected List<Object> getEntitys() {
		return entitys;
	}

	/**
	 * @param entitys The entitys to set.
	 */
	protected void setEntitys(List<Object> entitys) {
		this.entitys = entitys;
	}

	/**
	 * 要显示的属性列表
	 */
	public abstract List<String> getEntityPropertys();
	public abstract void setEntityPropertys(List<String> ls);
	
	
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

	/**
	 * 数据源，用于回显或者显示
	 */
	@Parameter(required = true)
	public abstract <T> List<T> getSource();

	/**
	 * 输入的元素
	 */
	public abstract List<GainPoint> getGainPoints();

	public abstract void setGainPoints(List<GainPoint> l);

	/**
	 * 要显示的对象属性,用分号(;)隔开
	 */
	@Parameter(required = true)
	public abstract String getShowPropertys();

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
		return false;
	}

	protected void setName(IForm form) {
		setName(form.getElementId(this));
	}

	/**
	 * @return Returns the foregroundEntitys.
	 */
	protected List<List> getForegroundEntitys() {
		return foregroundEntitys;
	}

	/**
	 * @param foregroundEntitys The foregroundEntitys to set.
	 */
	protected void setForegroundEntitys(List<List> foregroundEntitys) {
		this.foregroundEntitys = foregroundEntitys;
	}

	/**
	 * @return Returns the foregroundLength.
	 */
	protected int getForegroundLength() {
		return foregroundLength;
	}

	/**
	 * @param foregroundLength The foregroundLength to set.
	 */
	protected void setForegroundLength(int foregroundLength) {
		this.foregroundLength = foregroundLength;
	}
}
