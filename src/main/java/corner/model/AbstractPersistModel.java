/*		
 * Copyright 2006-2008 The Beijing Maxinfo Technology Ltd. 
 * site: http://www.bjmaxinfo.com
 * file: $Id: AbstractPersistModel.java 9172 2008-03-27 01:57:58Z jcai $
 * created at:2006-06-27
 */

package corner.model;

import java.io.Serializable;

import corner.model.AbstractPersistDomain;
import corner.orm.hibernate.IPersistModel;


/**
 * Abstract persist model.
 * 基本的抽象类,提供了所有的主键值的声明.
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author <a href="mailto:xf@bjmaxinfo.com">xiafei</a>
 * @version $Revision$
 * @since 2.5
 */
public abstract class AbstractPersistModel extends AbstractPersistDomain implements Serializable, IPersistModel{
    /**
     * @hibernate.id generator-class="uuid"  type="string"
     * @hibernate.column name="Recno_Pk" comment="主键值" length="32" sql-type="Char(32)" 
     *
     */
    private String id;

	/**
	 * @see com.bjmaxinfo.piano.model.IPersistModel#getId()
	 */
    public String getId() {
        return this.id;
    }

    /**
     * @param id The id to set.
     */
    public void setId(String id) {
        this.id = id;
    }

	
	
	
    /* bean properties begin */
    public static final String ID_PRO_NAME="id";
    /* bean properties end */
}
