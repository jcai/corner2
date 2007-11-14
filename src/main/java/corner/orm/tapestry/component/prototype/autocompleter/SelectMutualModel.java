/*		
 * Copyright 2006-2007 The Beijing Maxinfo Technology Ltd. 
 * site: http://www.bjmaxinfo.com
 * file : $Id$
 * created at:2007-04-26
 */
package corner.orm.tapestry.component.prototype.autocompleter;

import java.util.List;

/**
 * 选择组建的bean类
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision$
 * @since 2.3.7
 */
class SelectMutualModel {
	/**
	 * 返回的组建
	 */
	private String updateField;
	
	/**
	 * 返回的值
	 */
	private String returnVolume;
	
	/**
	 * 是否序列化，如果需要序列化返回true
	 */
	private boolean isSequence;
	
	/**
	 * 是否使用返回模板
	 */
	private boolean isTemplate;
	
	/**
	 * 要处理的字段
	 */
	private List<String> returnVolumes;
	
	/**
	 * 返回时用到的模板
	 */
	private String returnTemplate;
	

	/**
	 * @return Returns the isSequence.
	 */
	public boolean isSequence() {
		return isSequence;
	}

	/**
	 * @param isSequence The isSequence to set.
	 */
	public void setSequence(boolean isSequence) {
		this.isSequence = isSequence;
	}

	/**
	 * @return Returns the returnValue.
	 */
	public String getReturnVolume() {
		return returnVolume;
	}

	/**
	 * @param returnValue The returnValue to set.
	 */
	public void setReturnVolume(String returnValue) {
		this.returnVolume = returnValue;
	}

	/**
	 * @return Returns the updateField.
	 */
	public String getUpdateField() {
		return updateField;
	}

	/**
	 * @param updateField The updateField to set.
	 */
	public void setUpdateField(String updateField) {
		this.updateField = updateField;
	}

	/**
	 * @return Returns the isTemplate.
	 */
	public boolean isTemplate() {
		return isTemplate;
	}

	/**
	 * @param isTemplate The isTemplate to set.
	 */
	public void setTemplate(boolean isTemplate) {
		this.isTemplate = isTemplate;
	}

	/**
	 * @return Returns the returnTemplate.
	 */
	public String getReturnTemplate() {
		return returnTemplate;
	}

	/**
	 * @param returnTemplate The returnTemplate to set.
	 */
	public void setReturnTemplate(String returnTemplate) {
		this.returnTemplate = returnTemplate;
	}

	/**
	 * @return Returns the returnVolumes.
	 */
	public List<String> getReturnVolumes() {
		return returnVolumes;
	}

	/**
	 * @param returnVolumes The returnVolumes to set.
	 */
	public void setReturnVolumes(List<String> returnVolumes) {
		this.returnVolumes = returnVolumes;
	}
}
