/*		
 * Copyright 2006-2007 The Beijing Maxinfo Technology Ltd. 
 * site: http://www.bjmaxinfo.com
 * file : $Id: SelectMutualModel.java 6057 2007-05-29 02:26:25Z jcai $
 * created at:2007-04-26
 */
package corner.orm.tapestry.component.prototype;

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
}
