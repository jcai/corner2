package corner.demo.model.one;

import java.util.Vector;

import corner.demo.model.AbstractModel;

/**
 *
 * @author jcai
 * @version $Revision:1162 $
 * @since 0.5.2
 * @hibernate.class table="oneA"
 * @hibernate.cache usage="read-write"
 * @hibernate.mapping auto-import="false"
 */
public class A extends AbstractModel {

	/**
	 *
	 */
	private static final long serialVersionUID = -1296703401706863951L;

	/**
	 * @hibernate.property type="corner.orm.hibernate.v3.VectorType"
	 */
	private Vector<String> colors;
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
	public Vector<String> getColors() {
		return colors;
	}

	/**
	 * @param colors
	 *            The colors to set.
	 */
	public void setColors(Vector<String> colors) {
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
}
