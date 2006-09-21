//==============================================================================
//file :        AbstractBlobModel.java
//project:     corner
//
//last change:	date:       $Date$
//           	by:         $Author$
//           	revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.model;

import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Type;

/**
 * 抽象的blob模型,所有的blob模型必须继承此类.
 * <p>此类提供了blob两个必须的东西,一个数据,一个为类型.
 *  TODO 调整blob数据从byte数组到IO流.
 * 
 * @author	<a href="http://wiki.java.net/bin/view/People/JunTsai">Jun Tsai</a>
 * @version	$Revision$
 * @since	2006-1-20
 */
@MappedSuperclass
public class AbstractBlobModel implements IBlobModel {
	
	/**
	 * blob数据.
	 * @hibernate.property column="BlobData" length="2147483647"
	 *                     type="org.springframework.orm.hibernate3.support.BlobByteArrayType"
	 * 
	 */
	private byte[] blobData;
	/**
	 * blob数据的类型,此类型用来web页面的显示,可能的结果为:image/jpeg,image/gif,application/pdf 等.
	 * @hibernate.property column="ContentType" length="30"
	 */
	private String contentType;
	
	/**
	 * @see corner.model.IBlobModel#getBlobData()
	 */
	@Lob
	@Type(type="org.springframework.orm.hibernate3.support.BlobByteArrayType")
	public byte[] getBlobData() {
		return blobData;
	}

	/**
	 * @see corner.model.IBlobModel#setBlobData(byte[])
	 */
	public void setBlobData(byte[] blobData) {
		this.blobData = blobData;
	}

	/**
	 * @see corner.model.IBlobModel#getContentType()
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * @see corner.model.IBlobModel#setContentType(java.lang.String)
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	

}
