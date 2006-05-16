//==============================================================================
//file :        IBlobBeforSaveCallBack.java
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

import corner.model.AbstractBlobModel;

/**
 * 针对blob对象在保存前的操作回调接口.
 * <p>提供在保存blob对象之前的调用.
 * @author	<a href="http://wiki.java.net/bin/view/People/JunTsai">Jun Tsai</a>
 * @version	$Revision$
 * @since	2006-2-6
 */
public interface IBlobBeforSaveCallBack<T  extends AbstractBlobModel> {
	/**
	 * 在blob保存之前的操作.
	 * @param blob blob对象.
	 */
	public void doBeforeSaveBlob(T blob);

}
