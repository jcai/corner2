/*		
 * Copyright 2008 The Fepss Pty Ltd. 
 * site: http://www.fepss.com
 * file: $Id: PropertyAssACSelectionModel.java 343 2008-05-20 04:28:13Z jcai $
 * created at:2008-05-16
 */

package corner.orm.tapestry.component.propertyselection;

import java.util.List;

import org.apache.tapestry.form.IPropertySelectionModel;
import org.springframework.util.Assert;

import corner.orm.hibernate.IPersistModel;

/**
 * 实现一个显示文字，返回pk的SelectionModel
 * @author <a href="mailto:xf@bjmaxinfo.com">xiafei</a>
 * @version $Revision$
 * @since 2.5.1
 */
public class PropertyAssACSelectionModel<T extends IPersistModel> implements IPropertySelectionModel {

	/**
	 * 保存select使用的全部数据
	 */
	private List<T> list;
		
	public PropertyAssACSelectionModel(List<T> list){
		Assert.notNull(list);
		this.list = list;
	}
	
	/**
	 * @return Returns the list.
	 */
	public List<T> getList() {
		return list;
	}

	/**
	 * @param list The list to set.
	 */
	public void setList(List<T> list) {
		Assert.notNull(list);
		this.list = list;
	}

	/**
	 * @see org.apache.tapestry.form.IPropertySelectionModel#getLabel(int)
	 */
	public String getLabel(int index) {
		return this.getList().get(index).toString();
	}

	/**
	 * @see org.apache.tapestry.form.IPropertySelectionModel#getOption(int)
	 */
	public Object getOption(int index) {
		return this.getList().get(index).getId();
	}

	/**
	 * @see org.apache.tapestry.form.IPropertySelectionModel#getOptionCount()
	 */
	public int getOptionCount() {
		return list.size();
	}

	/**
	 * @see org.apache.tapestry.form.IPropertySelectionModel#getValue(int)
	 */
	public String getValue(int index) {
		return String.valueOf(index);
	}

	/**
	 * @see org.apache.tapestry.form.IPropertySelectionModel#isDisabled(int)
	 */
	public boolean isDisabled(int index) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * @see org.apache.tapestry.form.IPropertySelectionModel#translateValue(java.lang.String)
	 */
	public Object translateValue(String value) {
		return this.getList().get(Integer.parseInt(value));
	}

}
