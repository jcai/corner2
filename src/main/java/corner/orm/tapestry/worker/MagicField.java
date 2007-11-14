// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2006-11-15
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
