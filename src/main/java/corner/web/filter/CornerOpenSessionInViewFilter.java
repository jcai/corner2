// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2006-03-06
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

package corner.web.filter;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.OpenSessionInViewFilter;

import corner.orm.spring.SpringContainer;

public class CornerOpenSessionInViewFilter extends OpenSessionInViewFilter {
	@Override
	protected SessionFactory lookupSessionFactory() {

		return (SessionFactory) SpringContainer.getInstance().getApplicationContext().getBean("sessionFactory");
	}
}
