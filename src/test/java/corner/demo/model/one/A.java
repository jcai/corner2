package corner.demo.model.one;

import javax.persistence.Entity;
import javax.persistence.Lob;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import corner.demo.model.AbstractModel;
import corner.model.IBlobModel;
import corner.orm.hibernate.v3.MatrixRow;

/**
 *
 * @author jcai
 * @version $Revision:1162 $
 * @since 0.5.2
 * @hibernate.class table="oneA"
 * @hibernate.cache usage="read-write"
 * @hibernate.mapping auto-import="false"
 */
@Entity(name="oneA")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class A extends AbstractModel implements IBlobModel {

	/**
	 *
	 */
	private static final long serialVersionUID = -1296703401706863951L;

	/**
	 * @hibernate.property type="corner.orm.hibernate.v3.VectorType"
	 */
	
	private MatrixRow<String> colors;
	private String password;
	private double num;
	/**
	 * @return Returns the num.
	 */
	public double getNum() {
		return num;
	}

	/**
	 * @param num The num to set.
	 */
	public void setNum(double num) {
		this.num = num;
	}

	/**
	 * @return Returns the colors.
	 */
	@Type(type="corner.orm.hibernate.v3.VectorType")
	public MatrixRow<String> getColors() {
		return colors;
	}

	/**
	 * @param colors
	 *            The colors to set.
	 */
	public void setColors(MatrixRow<String> colors) {
		this.colors = colors;
	}

	/**
	 * @return Returns the password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password The password to set.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

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
