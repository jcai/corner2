// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2007-02-02
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

package corner.orm.tapestry.state;

import org.apache.tapestry.annotations.InjectState;

/**
 * 对context的访问操作.
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.3.7
 */
public interface IContextAccessible<T extends IContext> {
	
	/**
	 * 注入StateManager中的key的字符串
	 */
	public static final String CORNER_CONTEXT_KEY_STR = "context";
	
	@InjectState(CORNER_CONTEXT_KEY_STR)
	public  T getContext();
}
