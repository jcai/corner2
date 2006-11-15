//==============================================================================
//file :        MagicField.java
//
//last change:	date:       $Date$
//           	by:         $Author$
//           	revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Wang Tsai Studio http://www.wtstudio.org
//==============================================================================

package corner.orm.tapestry.worker;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 对magicField的定义
 * @author jcai
 * @version $Revision$
 * @since 2.3
 */
@Target(
		{ ElementType.METHOD })
		@Retention(RetentionPolicy.RUNTIME)
		@Documented
public @interface MagicField {
	/**
	 * 实体对应的属性名称.
	 * @return 实体在页面的属性名称.
	 */
	String entity();
}
