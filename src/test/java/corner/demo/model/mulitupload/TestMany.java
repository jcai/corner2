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

package corner.demo.model.mulitupload;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import corner.demo.model.AbstractModel;
import corner.model.IBlobModel;

/**
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 2.3.7
 */

@Entity(name = "TestMany")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TestMany extends AbstractModel implements IBlobModel {

	private TestOne testOne;

	/**
	 * @return Returns the testOne.
	 */
	@ManyToOne
	@JoinColumn(name = "testOne")
	public TestOne getTestOne() {
		return testOne;
	}

	/**
	 * @param testOne
	 *            The testOne to set.
	 */
	public void setTestOne(TestOne testOne) {
		this.testOne = testOne;
	}

	/**
	 * blob数据.
	 */
	private byte[] blobData;

	/**
	 * blob数据的类型,此类型用来web页面的显示,可能的结果为:image/jpeg,image/gif,application/pdf 等.
	 */
	private String contentType;

	/**
	 * @see corner.model.IBlobModel#getBlobData()
	 */
	@Lob
	@Type(type = "org.springframework.orm.hibernate3.support.BlobByteArrayType")
	public byte[] getBlobData() {
		return this.blobData;
	}

	/**
	 * @see corner.model.IBlobModel#getContentType()
	 */
	public String getContentType() {
		return this.contentType;
	}

	/**
	 * @see corner.model.IBlobModel#setBlobData(byte[])
	 */
	public void setBlobData(byte[] blobData) {
		this.blobData = blobData;
	}

	/**
	 * @see corner.model.IBlobModel#setContentType(java.lang.String)
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

}
