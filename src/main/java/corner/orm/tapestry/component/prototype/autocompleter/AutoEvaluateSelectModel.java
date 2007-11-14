// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2007-07-06
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package corner.orm.tapestry.component.prototype.autocompleter;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.tapestry.IComponent;
import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.json.JSONObject;
import org.apache.tapestry.services.DataSqueezer;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import corner.orm.tapestry.state.IContext;
import corner.util.BeanUtils;

/**
 * 自动赋值selector
 * 
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision$
 * @since 2.3.7
 */
public class AutoEvaluateSelectModel extends AbstractSelectModel implements IAutoEvaluateSelectModel {
	
	/**
	 * 在带有自查询的查询中－创建createCriteria时为根实体创建的别名
	 */
	public static final String ROOT_ENTITY_ALIAS = "ro_";

	/**
	 * @see corner.orm.tapestry.component.prototype.autocompleter.ISelectModel#renderResultRow(org.apache.tapestry.IMarkupWriter, java.lang.Object, java.lang.String, org.apache.tapestry.services.DataSqueezer)
	 */
	public void renderResultRow(IMarkupWriter writer, Object entity,
			String template, DataSqueezer squeezer) {

		Object showValue = null;
		if (updates.size() > 0) { // 当需要更新的时候不进行更新span的渲染

			writer.begin("span");
			writer.attribute("class", "returnValue");
			writer.attribute("style", "display:none");

			/**
			 * 返回集合
			 */
			JSONObject returnValue = new JSONObject();

			for (SelectMutualModel model : updates) {
				
				
				if(model.isTemplate()){	//如果是使用模板的
					showValue = this.getReturnObject(model,entity);
				}else{
					showValue = this.getReturnObject(model.getReturnVolume(),
							entity); // 获得值
				}

				if (model.isSequence()
						|| "this".equalsIgnoreCase(model.getReturnVolume())) { // 序列化
					showValue = squeezer.squeeze(showValue);
				}
				
				returnValue.put(model.getUpdateField(), showValue == null ? ""
						: showValue.toString());
			}
			writer.begin("script");
			writer.attribute("type", "text/javascript");
			writer.printRaw("<!--\n");

			writer.printRaw(returnValue.toString());
			
			writer.printRaw("\n// -->");
			writer.end("script");

			writer.end("span");
		}
		
		/**
		 * 显示的结果集合
		 */
		writer.printRaw(this.getTemplatedString(template,Arrays.asList(labels),entity,true));
		
	}
	

	/**
	 * 显示的结果集合
	 * @param template 模板
	 * @param volumes 显示的字段列表
	 * @param entity 要从中提取的实体
	 * @return
	 */
	private String getTemplatedString(String template, Iterable<String> volumes,Object entity,boolean isEscape) {
		Object showValue = null;
		ArrayList<String> LValues = new ArrayList<String>();
		for (String label : volumes) {
			showValue = this.getReturnObject(label, entity);
			
			LValues.add(isEscape ? escapeHtml(showValue) : showValue == null ? "  " : showValue.toString());
		}
		
		return String.format(template, LValues.toArray());
	}

	/**
	 * 使用模板返回值时才调用此方法
	 * 
	 * @param model 要处理的selectModel
	 * @param entity 查询出来的值
	 * @return 已经配合模板整理好的字符串
	 */
	protected Object getReturnObject(SelectMutualModel model, Object entity) {
		return this.getTemplatedString(model.getReturnTemplate(),model.getReturnVolumes(),entity,false);
	}

	/**
	 * @see corner.orm.tapestry.component.prototype.autocompleter.ISelectModel#search(org.springframework.orm.hibernate3.HibernateTemplate, java.lang.String, java.lang.String, corner.orm.tapestry.state.IContext, org.apache.tapestry.services.DataSqueezer, java.lang.String[])
	 */
	public List search(HibernateTemplate ht, final String queryClassName,
			final String searchString, final IContext context,final DataSqueezer squeezer,final String [] dependFieldsValue) {
		return ht.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(queryClassName, ROOT_ENTITY_ALIAS);
				
				Class clazz = null;
				try {
					clazz = Class.forName(queryClassName);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				
				appendContext(clazz,criteria,context);
				appendCriteria(criteria, queryName, searchString,squeezer,dependFieldsValue,context);
				criteria.setMaxResults(20);// 最多显示20个
				return criteria.list();
			}
		});
	}

	/**
	 * 增加上下文查询
	 * @param context
	 */
	protected void appendContext(Class clazz,Criteria criteria,IContext context){
//		if (ICompanyModel.class.isAssignableFrom(clazz) && context.getCompany() != null) {
//			criteria.add(Restrictions.eq(AbstractCompanywareModel.COMPANY_PRO_NAME, context.getCompany()));
//		}
	}
	
	/**
	 * @param criteria
	 * @param match
	 */
	protected void appendCriteria(Criteria criteria, String queryName,
			String match,DataSqueezer squeezer,String [] dependFieldsValue,IContext context) {
		if (match == null || match.trim().length() == 0) {
			return;
		} else {
			criteria.add(Restrictions.like(queryName, match.trim() + "%"));
		}
	}

	/**
	 * 得到返回对象，并把他转换为字符串。
	 * 
	 * @param pro
	 *            属性名称。
	 * @param obj
	 *            待操作的对象。
	 * @return 字符串。
	 */
	protected Object getReturnObject(String pro, Object obj) {
		Object value = obj;
		if (!Criteria.ROOT_ALIAS.equals(pro)) {
			value = BeanUtils.getProperty(obj, pro); // 得到属性
		} else {
			value = value == null ? "" : value;
		}

		return value;
	}

	/**
	 * 
	 * @see corner.orm.tapestry.component.prototype.autocompleter.IAutoEvaluateSelectModel#parseParameter(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void parseParameter(String queryFieldName, String labelFields,
			String updateFields,String returnTemplates) {
		this.queryName = queryFieldName;

		if (labelFields != null) {
			// 初始化显示
			String[] temp = labelFields.split(SEP_SPLIT_DOT_VALUE);

			if (queryName == null) {
				queryName = temp[0];// 默认查询第一个字段
			}
			labels = temp;

		}

		// 初始化传值
		updates = new ArrayList<SelectMutualModel>(); // 初始化集合

		if (updateFields != null) {

			SelectMutualModel selectMutualModel = null;

			JSONObject json = null;
			JSONObject returnTemplateJson = null;
			
			try {
				json = new JSONObject(updateFields); // 使用json
			} catch (ParseException e) {
				e.printStackTrace();
			}

			for (Iterator it = json.keys(); it.hasNext();) { // 遍历
				String key = (String) it.next(); // 获得key

				selectMutualModel = new SelectMutualModel();

				selectMutualModel.setUpdateField(key);

				String value = json.getString(key); // 获得key对应的值
				
				selectMutualModel.setTemplate(value.toLowerCase().startsWith("returntemplates_"));

				selectMutualModel.setSequence(value.toLowerCase().startsWith(
						"s_"));

				if(selectMutualModel.isTemplate()){
					
					try {
						returnTemplateJson = new JSONObject(returnTemplates);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					
					int index = 0;
					
					for (Iterator rtit = returnTemplateJson.keys(); rtit.hasNext();) { // 遍历
						key = (String) rtit.next();
						if(("returnTemplates_"+index).equals(value)){
							String revalue = returnTemplateJson.getString(key);
							selectMutualModel.setReturnVolumes(Arrays.asList(key.trim().split("\\$")));
							selectMutualModel.setReturnTemplate(revalue);
						}
						index++;
					}
					
				}else{
					if (selectMutualModel.isSequence()) {
						selectMutualModel.setReturnVolume(value.substring(2));
					} else {
						selectMutualModel.setReturnVolume(value);
					}
				}

				updates.add(selectMutualModel);
			}
		}

	}
	
	private static final String SEP_SPLIT_DOT_VALUE = ",";

	/** 查询字段的名称 * */
	protected String queryName;

	protected String[] labels;

	protected List<SelectMutualModel> updates;
	
	private IComponent component;

	/**
	 * @see corner.orm.tapestry.component.prototype.autocompleter.ISelectModel#getComponent()
	 */
	public IComponent getComponent() {
		return component;
	}

	/**
	 * @see corner.orm.tapestry.component.prototype.autocompleter.ISelectModel#setComponent(org.apache.tapestry.IComponent)
	 */
	public void setComponent(IComponent component) {
		this.component = component;
	}
}
