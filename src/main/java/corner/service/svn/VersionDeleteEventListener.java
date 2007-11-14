// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2007-11-08
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

package corner.service.svn;

import org.hibernate.event.PostDeleteEvent;
import org.hibernate.event.PostDeleteEventListener;

import corner.orm.spring.SpringContainer;

/**
 * 删除时候的事件响应.
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.5
 */
public class VersionDeleteEventListener implements PostDeleteEventListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5462105501277206298L;

	public void onPostDelete(PostDeleteEvent event) {
		Object obj=event.getEntity();
		if(obj instanceof IVersionable){
			IVersionService service=(IVersionService) SpringContainer.getInstance().getApplicationContext().getBean(IVersionProvider.VERSION_SPRING_BEAN_NAME);
			service.delete((IVersionable) obj);
		}
	}
}
