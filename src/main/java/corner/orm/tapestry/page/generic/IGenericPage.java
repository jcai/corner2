package corner.orm.tapestry.page.generic;

import org.apache.tapestry.annotations.Bean;

import corner.orm.tapestry.page.CornerValidationDelegate;

/**
 * 对一些常用操作的接口.
 * @author jcai
 * @version $Revision$
 * @since 2.3
 */
public interface IGenericPage {
	@Bean
	public abstract CornerValidationDelegate getDelegateBean();

}