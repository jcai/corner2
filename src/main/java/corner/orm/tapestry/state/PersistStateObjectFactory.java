// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2007-05-21
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

import org.apache.hivemind.ApplicationRuntimeException;
import org.apache.hivemind.util.Defense;
import org.apache.tapestry.engine.state.StateObjectFactory;
import org.apache.tapestry.services.DataSqueezer;

/**
 * 实现序列化的StateObject创建工厂.
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.3.7
 */
public class PersistStateObjectFactory implements StateObjectFactory {

	private Class<?> clazz;
	private DataSqueezer sequeezer;
	/**
	 * @see org.apache.tapestry.engine.state.StateObjectFactory#createStateObject()
	 */
	public Object createStateObject() {
		try {
			IContext context=IContext.class.cast(clazz.newInstance());
			context.setDataSqueezer(sequeezer);
			return context;
		} catch (InstantiationException e) {
			throw new ApplicationRuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new ApplicationRuntimeException(e);
			}
	}
	public void setdataSqueezer(DataSqueezer sequeezer ){
		this.sequeezer=sequeezer;
	}
	public void setStateObjectClassName(String className){
		Defense.notNull(className,"state object class name");
		try {
			this.clazz=Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new ApplicationRuntimeException(e);
		}
	}

}
