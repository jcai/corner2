// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2006-08-10
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

import corner.orm.tapestry.page.AbstractEntityListPage;

/**
 * 提供从实体列表页面直接跳转到向对应的关联页面.
 * 抽象实体列表页面
 * <p>扩展了AbstractEntityListPage类,主要提供了从实体列表页面直接跳转的ReflectRelativeEntityFormPage和ReflectRelativeEntityListPage页面的方法
 * @author Ghost
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.1
 * @deprecated 方法都已经重构到AbstractEntityListPage里面，请直接使用AbstractEntityListPage
 */
public abstract class AbstractManyEntityListPage<T> extends AbstractEntityListPage<T> implements IRelativeObjectOperatorSupport{

	
}
