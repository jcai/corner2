//==============================================================================
//file :        IBlobImageProvider.java
//project:      poison-system
//
//last change:	date:       $Date$
//           	by:         $Author$
//           	revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	China Java Users Group http://cnjug.dev.java.net
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.orm.tapestry.service.blob;

import corner.service.EntityService;

/**
 * 提供一个blob图像来源.
 * 
 * @author	<a href="http://wiki.java.net/bin/view/People/JunTsai">Jun Tsai</a>
 * @version	$Revision$
 * @since	2006-1-19
 */
public interface IBlobProvider {
	/**
	 * 返回blob对象为字节数组.
	 * @return 字节数组.
	 */
	public byte[] getBlobAsBytes();
	/**
	 * blob的类型,通常用来在web上的显示.
	 * @return blob的类型.
	 */
	public String getContentType();
	/**
	 * blob的主键值.
	 * @param tableKey 主键值.
	 */
	public void setKeyValue(String tableKey);
	/**
	 * 设置实体服务.
	 * 
	 * @param entityService 实体服务.
	 */
	public void setEntityService(EntityService entityService);
}
