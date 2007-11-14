// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2006-08-08
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

package corner.orm.tapestry.page.relative;

import org.apache.tapestry.annotations.InjectObject;

import corner.orm.tapestry.page.relative.support.IRelativeObjectOperator;

/**
 * 提供了对关联对象操作.
 * @author jcai
 * @version $Revision$
 * @since 2.1
 */
public interface IRelativeObjectOperatorSupport {
	/**
	 * 得到关联对象操作者.
	 * @return 关联对象操作者。
	 */
	@InjectObject("service:corner.orm.relativeOperator")
	public abstract IRelativeObjectOperator getRelativeObjectOperator();
}
