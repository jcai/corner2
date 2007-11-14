// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2006-11-18
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