/*		
 * Copyright 2007 The Beijing Maxinfo Technology Ltd. 
 * site:http://www.bjmaxinfo.com
 * file : $Id$
 * created at:2007-9-12
 */

package corner.orm.tapestry.asset;

import java.util.Locale;

import org.apache.hivemind.Location;
import org.apache.hivemind.Resource;
import org.apache.tapestry.IAsset;
import org.apache.tapestry.asset.AssetFactory;
import org.apache.tapestry.engine.IEngineService;
import org.apache.tapestry.spec.IComponentSpecification;

/**
 * 数据库中的Asset
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision$
 * @since 0.8.3
 */
public class DatabaseAssetFactory implements AssetFactory{
	
	private IEngineService _assetService;

	/**
	 * @see org.apache.tapestry.asset.AssetFactory#assetExists(org.apache.tapestry.spec.IComponentSpecification, org.apache.hivemind.Resource, java.lang.String, java.util.Locale)
	 */
	public boolean assetExists(IComponentSpecification spec, Resource baseResource, String path, Locale locale) {
		return false;
	}

	/**
	 * @see org.apache.tapestry.asset.AssetFactory#createAbsoluteAsset(java.lang.String, java.util.Locale, org.apache.hivemind.Location)
	 */
	public IAsset createAbsoluteAsset(String path, Locale locale, Location location) {
		
		System.out.println("呵呵！我进来了！1");
		
		return null;
	}

	/**
	 * @see org.apache.tapestry.asset.AssetFactory#createAsset(org.apache.hivemind.Resource, org.apache.hivemind.Location)
	 */
	public IAsset createAsset(Resource resource, Location location) {
		System.out.println("呵呵！我进来了！2");
		return null;
	}

	/**
	 * @see org.apache.tapestry.asset.AssetFactory#createAsset(org.apache.hivemind.Resource, org.apache.tapestry.spec.IComponentSpecification, java.lang.String, java.util.Locale, org.apache.hivemind.Location)
	 */
	public IAsset createAsset(Resource baseResource, IComponentSpecification spec, String path, Locale locale, Location location) {
		System.out.println("呵呵！我进来了！3");
		return null;
	}

	/**
	 * @param assetService
	 */
	public void setAssetService(IEngineService assetService) {
		_assetService = assetService;
	}
}
