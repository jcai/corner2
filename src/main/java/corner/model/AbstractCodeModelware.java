/*		
 * Copyright 2007-2008 The Beijing Maxinfo Technology Ltd. 
 * site: http://www.bjmaxinfo.com
 * file: $Id: AbstractCodeModelware.java 9980 2008-06-03 02:19:20Z jcai $
 * created at:2007-06-28
 */

package corner.model;

import corner.util.HanziUtils;

/**
 * 继承自{@link AbstractPersistModel}完全实现了{@link ICodeModel}接口的抽象类
 * 
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @author <a href="mailto:xf@bjmaxinfo.com">xiafei</a>
 * @version $Revision$
 * @since 2.5
 */
public abstract class AbstractCodeModelware extends AbstractPersistModel
		implements ICodeModel {

	private String abcCode;

	private String numCode;

	private String engName;

	private String chnName;

	private String indexCode;

	public String getIndexCode() {
		return this.indexCode;
	}

	/**
	 * @see com.bjmaxinfo.piano.model.ICodeModel#setIndexCode(java.lang.String)
	 */
	public void setIndexCode(String indexCode) {
		this.indexCode = indexCode;
	}

	/**
	 * @see com.bjmaxinfo.piano.model.ICodeModel#getAbcCode()
	 */
	public String getAbcCode() {

		return this.abcCode;
	}

	/**
	 * @see com.bjmaxinfo.piano.model.ICodeModel#setAbcCode(java.lang.String)
	 */
	public void setAbcCode(String abcCode) {

		this.abcCode = abcCode;
	}

	/**
	 * @see com.bjmaxinfo.piano.model.ICodeModel#getNumCode()
	 */
	public String getNumCode() {
		return this.numCode;
	}

	/**
	 * @see com.bjmaxinfo.piano.model.ICodeModel#setNumCode(java.lang.String)
	 */
	public void setNumCode(String numCode) {
		this.numCode = numCode;
	}

	/**
	 * @see com.bjmaxinfo.piano.model.ICodeModel#getEngName()
	 */
	public String getEngName() {
		return this.engName;
	}

	/**
	 * @see com.bjmaxinfo.piano.model.ICodeModel#setEngName(java.lang.String)
	 */
	public void setEngName(String engName) {
		this.engName = engName;
	}

	/**
	 * @see com.bjmaxinfo.piano.model.ICodeModel#getChnName()
	 */
	public String getChnName() {

		return this.chnName;
	}

	/**
	 * @see com.bjmaxinfo.piano.model.ICodeModel#setChnName(java.lang.String)
	 */
	public void setChnName(String chnName) {
		this.chnName = chnName;
	}

	/**
	 * @see com.bjmaxinfo.piano.model.ICodeModel#initAbcCode()
	 */
	public void initAbcCode() {
		if (this.chnName != null && this.chnName.length() > 0) {
			this.abcCode = HanziUtils.getPinyin(this.chnName);
		} else {
			this.abcCode = "";
		}

		// 拼音码最大长度为10
		if (this.abcCode.length() > 10) {
			this.abcCode = this.abcCode.substring(0, 10);
		}
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.getChnName();
	}

	

    /* bean properties begin */
    public static final String ABC_CODE_PRO_NAME="abcCode";
    public static final String NUM_CODE_PRO_NAME="numCode";
    public static final String ENG_NAME_PRO_NAME="engName";
    public static final String CHN_NAME_PRO_NAME="chnName";
    public static final String INDEX_CODE_PRO_NAME="indexCode";
    /* bean properties end */
}
