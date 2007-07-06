/*		
 * Copyright 2006-2007 The Beijing Maxinfo Technology Ltd. 
 * site: http://www.bjmaxinfo.com
 * file : $Id: AssociateAutocompleter.java 6057 2007-05-29 02:26:25Z jcai $
 * created at:2007-04-30
 */
package corner.orm.tapestry.component.prototype;

import java.text.ParseException;

import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.annotations.Parameter;
import org.apache.tapestry.json.JSONObject;
import org.apache.tapestry.valid.ValidatorException;

import corner.util.BeanUtils;

/**
 * 保存关联的自动完成组件
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision$
 * @since 2.3.7
 */
public abstract class AssociateAutocompleter extends AutoEvaluateModelAutocompleter{
	
	/**
	 * 查询的字段,如果未指定，则为labes字段的第一个
	 */
	@Parameter (required = true)
	public abstract String getShowField();
	
	protected static final String ASSOCIATE_SUFFIX="_ASS";
	/** 
	 * 展示关联对象时候的属性，如果未指定，则调用关联对象的toString方法
	 */
	@Parameter
	public abstract String getAssociateProperty();

	/**
	 * @see corner.orm.tapestry.component.prototype.BaseAutocompleter#appendField(org.apache.tapestry.IMarkupWriter, java.lang.Object)
	 */
	@Override
	protected void appendField(IMarkupWriter writer, Object value) {
		writer.beginEmpty("input");
		writer.attribute("name",this.getName()+ASSOCIATE_SUFFIX);
		writer.attribute("id",this.getClientId()+ASSOCIATE_SUFFIX);
		writer.attribute("type","hidden");
		writer.attribute("value",this.getDataSqueezer().squeeze(value));
		
	}
	/**
	 * Rewinds the component, doing translation, validation and binding.
	 */
	@Override
	protected void rewindFormComponent(IMarkupWriter writer, IRequestCycle cycle) {
		String value = cycle.getParameter(getName());
		try {
			Object object = getTranslatedFieldSupport().parse(this, value);
			getValidatableFieldSupport().validate(this, writer, cycle, object);
			
			if(object==null){ //删除已有的关联
				this.setValue(null);
				return;
			}
			String ass=cycle.getParameter(this.getName()+ASSOCIATE_SUFFIX);
			this.setValue(this.getDataSqueezer().unsqueeze(ass)); //转变关联对象
		} catch (ValidatorException e) {
			getForm().getDelegate().record(e);
		}
	}

	/**
	 * @see corner.orm.tapestry.component.prototype.AutoEvaluateModelAutocompleter#constructSelectModel()
	 */
	@Override
	protected ISelectModel constructSelectModel() {
		IAutoEvaluateSelectModel model=this.getSelectModel();
		String updateFields = this.getUpdateFields();
		if(updateFields==null){
			updateFields="{"+this.getId()+ASSOCIATE_SUFFIX+":this}";
		}else{
			
			//TODO 把解析json的字符串放在此处
			try {
				JSONObject json=new JSONObject(updateFields);
				json.put(this.getId()+ASSOCIATE_SUFFIX,"this");
				updateFields=json.toString();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		model.parseParameter(this.getQueryFieldName(),
				this.getLabelFields(), updateFields);
		return model;
		
	}

	/**
	 * @see corner.orm.tapestry.component.prototype.BaseAutocompleter#formatValue(java.lang.Object)
	 */
	@Override
	protected Object formatValue(Object value) {
		return getShowField() != null ? BeanUtils.getProperty(value,getShowField()) : value;
	}
}
