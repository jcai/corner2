// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2006-07-22
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

package corner.orm.tapestry.record;

import org.apache.tapestry.engine.ServiceEncoding;
import org.apache.tapestry.record.AbstractPrefixedClientPropertyPersistenceScope;
import org.apache.tapestry.record.PersistentPropertyData;

/**
 * 保存数据至客户端的持久化周期。
 * <p> 此周期持续整个应用。
 * @author Jun Tsai
 * @version $Revision$
 * @since 2.0.6
 */
public class EntityAppClientPropertyPersistenceScope extends
		AbstractPrefixedClientPropertyPersistenceScope {



	    public EntityAppClientPropertyPersistenceScope()
	    {
	        super("entityapp:");
	    }

	    /**
	     * Returns true if the active page name matches the page for this property. This means that
	     * <em>after a new page has been activated</em>, the state is discarded.
	     */

	    public boolean shouldEncodeState(ServiceEncoding encoding, String pageName,
	            PersistentPropertyData data)
	    {
	       return true;
	    }


}
