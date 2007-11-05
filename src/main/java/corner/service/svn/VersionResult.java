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

package corner.service.svn;

import java.io.Serializable;
import java.util.Date;

/**
 * 记录版本信息的类.
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.5
 */
public class VersionResult implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 6494057726673233107L;
	
	/** 版本号 **/
	private long versionNum;
	/** 作者 **/
	private String author;
	/** 注释 **/
	private String comment;
	/** 创建日期 **/
	private Date createDate;
	/**
	 * @return Returns the author.
	 */
	public String getAuthor() {
		return author;
	}
	/**
	 * @param author The author to set.
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	/**
	 * @return Returns the comment.
	 */
	public String getComment() {
		return comment;
	}
	/**
	 * @param comment The comment to set.
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	/**
	 * @return Returns the createDate.
	 */
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * @param createDate The createDate to set.
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * @return Returns the versionNum.
	 */
	public long getVersionNum() {
		return versionNum;
	}
	/**
	 * @param versionNum The versionNum to set.
	 */
	public void setVersionNum(long versionNum) {
		this.versionNum = versionNum;
	}
}
