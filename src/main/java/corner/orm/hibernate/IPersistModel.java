/*		
 * Copyright 2006-2007 The Beijing Maxinfo Technology Ltd. 
 * site: http://www.bjmaxinfo.com
 * file: $Id$
 * created at:2006-12-21
 */

package corner.orm.hibernate;

/**
 * 所有实现持久化的model接口.
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.5
 */
public interface IPersistModel {
	/**
	 * 返回实体的主键
	 * @return Returns the id.
	 */
	public abstract String getId();
	/** 用于关联查询时候，对集成类的查询 **/
	public static final String CLASS_PRO_NAME = "class";
}