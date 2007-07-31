/*		
 * Copyright 2007 The Beijing Maxinfo Technology Ltd. 
 * site: http://www.bjmaxinfo.com
 * file: $Id: PdfSpecificationResolverDelegate.java 7039 2007-07-09 07:44:26Z jcai $
 * created at:2007-04-09
 */

package corner.orm.tapestry.pdf;

import org.apache.hivemind.ClassResolver;
import org.apache.hivemind.util.ClasspathResource;
import org.apache.tapestry.IAsset;
import org.apache.tapestry.INamespace;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.asset.AssetSource;
import org.apache.tapestry.resolver.ISpecificationResolverDelegate;
import org.apache.tapestry.spec.IComponentSpecification;


/**
 * 对pdf模板的查找代理类
 * 
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 2.3.7
 */
public class PdfSpecificationResolverDelegate implements
		ISpecificationResolverDelegate {

	private AssetSource _assetSource;

	private ClassResolver _classResolver;

	/**
	 * @see org.apache.tapestry.resolver.ISpecificationResolverDelegate#findComponentSpecification(org.apache.tapestry.IRequestCycle,
	 *      org.apache.tapestry.INamespace, java.lang.String)
	 */
	public IComponentSpecification findComponentSpecification(
			IRequestCycle cycle, INamespace namespace, String type) {
		//do nothing
		return null;
	}

	/**
	 * @see org.apache.tapestry.resolver.ISpecificationResolverDelegate#findPageSpecification(org.apache.tapestry.IRequestCycle,
	 *      org.apache.tapestry.INamespace, java.lang.String)
	 */
	public synchronized IComponentSpecification findPageSpecification(IRequestCycle cycle,
			INamespace namespace, String simplePageName) {

		IPdfPageSpecification specification = new PdfPageSpecification();
		specification.setPageSpecification(true);
		
		IAsset asset = _assetSource.findAsset(new ClasspathResource(this._classResolver,"/"), simplePageName, null, namespace
				.getLocation());

		specification.setComponentClassName(PdfEntityPage.class.getName());

		specification.setSpecificationLocation(asset.getResourceLocation());
		specification.setLocation(asset.getLocation());
		

		//解析改pdf模板
		PdfTemplateParser parser=new PdfTemplateParser(asset.getResourceAsStream());
		specification.setNumberOfPage(parser.getNumberOfPages());
		specification.setPageFieldsMap(parser.getPageFields());
		if(parser.getPageClazzName()!=null){//从页面中得到定义的PageClassName
			specification.setComponentClassName(parser.getPageClazzName());
		}
		
		return specification;
	}

	public void setAssetSource(AssetSource assetSource) {
		_assetSource = assetSource;
	}

	public void setClassResolver(ClassResolver classResolver) {
		_classResolver = classResolver;
	}

}
