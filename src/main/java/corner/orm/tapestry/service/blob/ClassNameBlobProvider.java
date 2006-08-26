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

package corner.orm.tapestry.service.blob;


/**
 * 通过class的名称来创建一个blob的服务提供者.
 * @author Jun Tsai
 * @version $Revision$
 * @since 2.1
 */
public class ClassNameBlobProvider extends AbstractBlobProvider {

	private Class clazz;
	/**
	 * @see corner.orm.tapestry.service.blob.AbstractBlobProvider#getBlobDataClass()
	 */
	@Override
	protected Class getBlobDataClass() {
		return clazz;
	}
	public void setBlobDataClass(String blobDataClass) throws ClassNotFoundException{
		this.clazz=Class.forName(blobDataClass);
	}
}
