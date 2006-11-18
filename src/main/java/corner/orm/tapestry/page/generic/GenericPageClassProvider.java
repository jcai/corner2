//==============================================================================
//file :        GenericPageClassProvider.java
//
//last change:	date:       $Date$
//           	by:         $Author$
//           	revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Wang Tsai Studio http://www.wtstudio.org
//==============================================================================

package corner.orm.tapestry.page.generic;

import org.apache.tapestry.pageload.ComponentClassProvider;
import org.apache.tapestry.pageload.ComponentClassProviderContext;


/**
 * 定义对Form和List页面自定义查找对应的页面类
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.3
 */
public class GenericPageClassProvider implements ComponentClassProvider{

	public String provideComponentClassName(ComponentClassProviderContext context) {
		String name=context.getName();
		if(name.endsWith("Form")){
			return GenericFormPage.class.getName();
		}else if(name.endsWith("List")){
			return GenericListPage.class.getName();
		}
		return null;
	}

	
}
