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
 * <p>
 * 其中{@link #entity()}通常为tapestry里面定义的属性名称，譬如在page中定义
 * &lt;property name="entity" initial-value="new xxx.cxx.Xx()"/&gt;
 * 
 * 而{@link #entityClass()}}则为手工指定需要的类，这个优先级高.
 * 
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
	String entity() default "";
	/**
	 * 默认实体的类.
	 * @return 实体类
	 */
	Class entityClass() default Object.class;
}
