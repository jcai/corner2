// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2007-07-31
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

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
