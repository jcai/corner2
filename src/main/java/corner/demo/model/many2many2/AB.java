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

package corner.demo.model.many2many2;

import java.io.Serializable;

/**
 * @author Ghost
 * @version $Revision$
 * @since 2.1
 * @hibernate.class table="many2many2AB"
 * @hibernate.cache usage="read-write"
 * @hibernate.mapping auto-import="false" 
 */
public class AB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1555102955247011594L;

	/**
	 * @hibernate.many-to-one class="corner.demo.model.many2many2.A" column="A" fetch="select"
	 */
	private A a;

	/**
	 * @hibernate.many-to-one class="corner.demo.model.many2many2.B" column="B" fetch="select"
	 */
	private B b;
	
	/**
	 * 中间表中的信息
	 * <p>用于纪录中间表中的信息</p>
	 */
	private String message;
	
	/**
	 * @return Returns the message.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message The message to set.
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return Returns the a.
	 */
	public A getA() {
		return a;
	}

	/**
	 * @param a The a to set.
	 */
	public void setA(A a) {
		this.a = a;
	}

	/**
	 * @return Returns the b.
	 */
	public B getB() {
		return b;
	}

	/**
	 * @param b The b to set.
	 */
	public void setB(B b) {
		this.b = b;
	}

}
