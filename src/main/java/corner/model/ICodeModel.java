/*		
 * Copyright 2006-2008 The Beijing Maxinfo Technology Ltd. 
 * site: http://www.bjmaxinfo.com
 * file: $Id: ICodeModel.java 9172 2008-03-27 01:57:58Z jcai $
 * created at:2006-10-17
 */

package corner.model;

/**
 * 代码接口，提供了数字编码 中文，英文，拼音等
 * 
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author <a href="mailto:xf@bjmaxinfo.com">xiafei</a>
 * @version $Revision$
 * @since 2.5
 */
public interface ICodeModel {

	/**
	 * 
	 * @see com.bjmaxinfo.piano.model.ICodeModel#getIndexCode()
	 * 
	 * 编码,IndexCode,Char(20).
	 * 
	 * @hibernate.property
	 * @hibernate.column name="Index_Code" comment="编码" length="20"
	 * @hibernate.meta attribute="field-description" value="编号、代号"
	 */
	public abstract String getIndexCode();

	/**
	 * @param indexCode
	 *            The indexCode to set.
	 */
	public abstract void setIndexCode(String indexCode);

	/**
	 * 拼音检索代码,abcCode,Char(100).
	 * 
	 * @hibernate.property
	 * @hibernate.column name="abc_Code" comment="拼音检索代码." length="10"
	 * @hibernate.meta attribute="field-description"
	 *                 value="拼音检索代码,abcCode,Char(100)."
	 */
	public abstract String getAbcCode();

	/**
	 * 设定 拼音检索代码.
	 */
	public abstract void setAbcCode(String abcCode);

	/**
	 * 数字检索代码,numCode,Char(10).
	 * 
	 * @hibernate.property
	 * @hibernate.column name="num_Code" comment="数字检索代码." length="10"
	 * @hibernate.meta attribute="field-description"
	 *                 value="数字检索代码,numCode,Char(10)."
	 */
	public abstract String getNumCode();

	/**
	 * 设定 数字检索代码.
	 */
	public abstract void setNumCode(String numCode);

	/**
	 * 英文名称,engName,Varchar(100).
	 * 
	 * @hibernate.property
	 * @hibernate.column name="eng_Name" comment="英文名称" length="100"
	 * @hibernate.meta attribute="field-description"
	 *                 value="英文名称,engName,Varchar(100)."
	 */
	public abstract String getEngName();

	/**
	 * 设定 英文名称).
	 */
	public abstract void setEngName(String engName);

	/**
	 * 中文名称,chnName,Varchar(100).
	 * 
	 * @hibernate.property
	 * @hibernate.column name="chn_Name" comment="中文名称" length="100"
	 * @hibernate.meta attribute="field-description"
	 *                 value="中文名称,chnName,Varchar(100)."
	 */
	public abstract String getChnName();

	/**
	 * 设定 中文名称.
	 */
	public abstract void setChnName(String chnName);

    /**
     * 初始化拼音码。
     * TODO 更改为 initPinyinCode
     */
	public abstract void initAbcCode();

}
