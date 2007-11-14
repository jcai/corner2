// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2006-11-17
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

package corner.orm.hibernate.event;

import org.hibernate.event.PostLoadEvent;
import org.hibernate.event.PostLoadEventListener;

import corner.orm.hibernate.IOldObjectAccessable;

/**
 * 对老对象进行赋值操作。
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.3
 */
public class InitOldObjectListener implements PostLoadEventListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7823483061289662153L;

	/**
	 * @see org.hibernate.event.PostLoadEventListener#onPostLoad(org.hibernate.event.PostLoadEvent)
	 */
	public void onPostLoad(PostLoadEvent event) {
		if(event.getEntity() instanceof IOldObjectAccessable){
			IOldObjectAccessable obj=(IOldObjectAccessable) event.getEntity();
			obj.initOldObject();
		}
	}
}
