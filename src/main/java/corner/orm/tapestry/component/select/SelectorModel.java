//==============================================================================
// file :       $Id:SelectorModel.java 2023 2006-10-17 05:18:45Z jcai $
// project:     corner
//
// last change: date:       $Date:2006-10-17 05:18:45Z $
//              by:         $Author:jcai $
//              revision:   $Revision:2023 $
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.orm.tapestry.component.select;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.tapestry.IComponent;
import org.apache.tapestry.IPage;
import org.apache.tapestry.dojo.form.Autocompleter;
import org.apache.tapestry.form.Hidden;
import org.apache.tapestry.json.JSONArray;
import org.apache.tapestry.services.DataSqueezer;
import org.hibernate.Criteria;

import corner.service.EntityService;
import corner.util.BeanUtils;

/**
 * 实现一个po的自动完成模型.
 * 
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author ghostbb
 * @version $Revision:2023 $
 * @since 2.2.1
 */
public class SelectorModel implements IPoSelectorModel {

	Class poClass;

	String field;

	EntityService entityService;

	private boolean saveObj;

	DataSqueezer squeezer;

	ISelectFilter filter;

	String[] returnValueFileds;

	String[] updateFields;

	private IComponent nestComp;

	/**
	 * @param field
	 *            The field to set.
	 */
	public void setLabelField(String field) {
		this.field = field;
	}

	public void setEntityService(EntityService entityService) {
		this.entityService = entityService;
	}

	public void setDataSqueezer(DataSqueezer squeezer) {
		this.squeezer = squeezer;
	}

	public void setReturnValueFields(String... strings) {
		this.returnValueFileds = strings;
	}

	public String[] getReturnValueFields() {
		return this.returnValueFileds;
	}

	/**
	 * @param poClass
	 *            The poClass to set.
	 */
	public void setPoClass(Class poClass) {
		this.poClass = poClass;
	}

	public void setSelectFilter(ISelectFilter filter) {
		this.filter = filter;
	}

	/**
	 * 
	 * @see org.apache.tapestry.dojo.form.IAutocompleteModel#getLabelFor(java.lang.Object)
	 */
	public String getLabelFor(Object value) {
		if (this.entityService.isPersistent(value)) {
			boolean fieldIsNull = (field == null);
			boolean filterFieldIsNull = false;
			if (filter != null)
				filterFieldIsNull = (filter.getLabelField() == null);

			if (fieldIsNull) {
				if (!filterFieldIsNull) {
					return BeanUtils.getProperty(value, filter.getLabelField()) != null ? BeanUtils
							.getProperty(value, filter.getLabelField())
							.toString()
							: null;
				}

				return null;

			}

			return BeanUtils.getProperty(value, field) != null ? BeanUtils
					.getProperty(value, field).toString() : null;
		}
		return value.toString();
	}

	/**
	 * 
	 * @see org.apache.tapestry.components.IPrimaryKeyConverter#getPrimaryKey(java.lang.Object)
	 */
	public Object getPrimaryKey(Object obj) {// 利用filter来的到对应选择后的多项值.
		if (this.entityService.isPersistent(obj)) {
			String [] returnValueFields=this.getReturnValueFields();
			if(returnValueFields==null){
				returnValueFields=filter.getReturnValueFields();
			}
			String [] updateFields=this.getUpdateFields();
			int len=returnValueFields.length;
			
			if (len > 1) {// 为连带多个字段内容.
				if (returnValueFields.length != updateFields.length) {
					throw new RuntimeException(
							"查询的字段和更新的字段的长度不相等! returnValueFields:["
									+ Arrays.asList(returnValueFields)
									+ "] updateFields:["
									+ Arrays.asList(updateFields) + "]");
				}
				JSONArray arr = new JSONArray();
				IComponent nestComp = this.getComponent();
				IPage page = nestComp.getPage();
				Map cs = page.getComponents();
				for (int i = 0; i < len; i++) {
					if ("this".equals(updateFields[i])) {
						arr
								.put(getReturnObject(returnValueFields[i], obj,
										true));
						continue;
					}

					// 根据页面对应的组件的来自动进行更新是否需要序列化.

					IComponent c = (IComponent) cs.get(updateFields[i]);

					if (c != null
							&& (c instanceof Hidden || c instanceof Autocompleter)) {
						arr
								.put(getReturnObject(returnValueFields[i], obj,
										true));
					} else {
						arr.put(getReturnObject(returnValueFields[i], obj,
								false));
					}
				}
				return arr.join(",");
			} else if (len == 1) {// 仅仅一个字段
				return getReturnObject(returnValueFields[0], obj, true);
			}
		}
		return obj;
	}
	/**
	 * 得到返回对象，并把他转换为字符串。
	 * @param pro 属性名称。
	 * @param obj 待操作的对象。
	 * @param isSqueeze  是否需要序列化
	 * @return 字符串。
	 */
	protected Object getReturnObject(String pro, Object obj, boolean isSqueeze) {
		Object value=obj;
		if(!Criteria.ROOT_ALIAS.equals(pro)){ 
			value=BeanUtils.getProperty(obj,pro); //得到属性
		}
		if(isSqueeze){
			return getSqueezer().squeeze(value);
		}
		else
			return value==null?"":value;
	
	}
	/**
	 * 
	 * @see org.apache.tapestry.components.IPrimaryKeyConverter#getValue(java.lang.Object)
	 */
	public Object getValue(Object key) {
		return key;
	}

	/**
	 * @see corner.orm.tapestry.component.select.IPoSelectorModel#isSaveObj()
	 */
	public boolean isSaveObj() {
		return saveObj;
	}

	/**
	 * @param saveObj
	 *            The saveObj to set.
	 */
	public void setSaveObj(boolean saveObj) {
		this.saveObj = saveObj;
	}

	/**
	 * @see corner.orm.tapestry.component.select.IPoSelectorModel#getSqueezer()
	 */
	public DataSqueezer getSqueezer() {
		return squeezer;
	}

	/**
	 * @param squeezer
	 *            The squeezer to set.
	 */
	public void setSqueezer(DataSqueezer squeezer) {
		this.squeezer = squeezer;
	}

	/**
	 * @see corner.orm.tapestry.component.select.IPoSelectorModel#getEntityService()
	 */
	public EntityService getEntityService() {
		return entityService;
	}

	/**
	 * @see corner.orm.tapestry.component.select.IPoSelectorModel#getLabelField()
	 */
	public String getLabelField() {
		return field;
	}

	/**
	 * @see corner.orm.tapestry.component.select.IPoSelectorModel#getPoClass()
	 */
	public Class getPoClass() {
		return poClass;
	}

	public IComponent getComponent() {
		return this.nestComp;
	}

	public void setComponent(IComponent c) {
		this.nestComp = c;
	}

	public String[] getUpdateFields() {
		return updateFields;
	}

	public void setUpdateFields(String[] updateFields) {
		this.updateFields = updateFields;
	}

	public List getValues(String match) {
		if (this.filter == null) {
			filter = new DefaultSelectFilter();
		}
		if (this.getReturnValueFields() == null && this.getLabelField() != null) {
			this.setReturnValueFields(this.getLabelField());
		}
		return filter.query(match, this);
	}

}
