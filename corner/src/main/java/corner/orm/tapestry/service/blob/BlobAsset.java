//==============================================================================
//file :        BlobImageAsset.java
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

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.tapestry.IEngine;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.asset.AbstractAsset;
import org.apache.tapestry.engine.IEngineService;
import org.apache.tapestry.engine.ILink;

/**
 * 提供了blob的Asset用以显示blob的值.
 * <p>显示一个blob的图片可以使用
 * 需要提供了两个参数:
 * <li>tableType 表的类型,见{@link poison.pageservice.BlobService#LOB_PROVIDER_CLAZZS}
 * <li>tableKey 表的主键值,{@link BlobService#LOB_PROVIDER_CLAZZS};
 *
 * @author	<a href="http://wiki.java.net/bin/view/People/JunTsai">Jun Tsai</a>
 * @version	$Revision$
 * @since	2006-1-20
 * @see org.apache.tapestry.IAsset
 */
public class BlobAsset extends AbstractAsset {

	private IEngineService _blobService;

	private List parameters = new ArrayList();

	/**
	 * 构造一个Asset
	 * @param cycle 客户请求.
	 * @param tableType 表的类型
	 * @param key 表的主键
	 */
	public BlobAsset(IRequestCycle cycle, String tableType, Serializable key) {
		super(null, null);

		IEngine engine = cycle.getEngine();

		_blobService = engine.getService(AbstractBlobService.SERVICE_NAME);

		parameters.add(tableType);
		parameters.add(key);

	}
	/**
	 * 构建URL.
	 * @see org.apache.tapestry.IAsset#buildURL()
	 */
	public String buildURL() {
		ILink l = _blobService.getLink(false, parameters.toArray());

		return l.getURL();
	}

	public InputStream getResourceAsStream() {
		return null;
	}

	public InputStream getResourceAsStream(IRequestCycle cycle, Locale locale) {
		return null;
	}
}
