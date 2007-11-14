// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2006-12-20
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

package corner.orm.tapestry.component.textfield;

import org.apache.tapestry.IActionListener;
import org.apache.tapestry.IDirect;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.Tapestry;
import org.apache.tapestry.annotations.InjectObject;
import org.apache.tapestry.annotations.Parameter;
import org.apache.tapestry.engine.DirectServiceParameter;
import org.apache.tapestry.engine.IEngineService;
import org.apache.tapestry.link.DirectLink;
import org.apache.tapestry.listener.ListenerInvoker;

/**
 * 抽象的提供了通过DirectService的方法来进行查询的dialog
 * 
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.3
 */
public abstract class DirectQueryDialog extends AbstractQueryDialog implements
		IDirect {
	@Parameter(required = true)
	public abstract IActionListener getListener();
	
	/**
	 * @see org.apache.tapestry.AbstractComponent#isStateful()
	 */
	@Override
	@Parameter(defaultValue="true")
	public abstract boolean isStateful();

	@Parameter
	public abstract Object getParameters();

	@InjectObject("service:tapestry.services.Page")
	public abstract IEngineService getPageService();

	@InjectObject("infrastructure:listenerInvoker")
	public abstract ListenerInvoker getListenerInvoker();

	@InjectObject("service:tapestry.services.Direct")
	public abstract IEngineService getDirectService();
	
	

	/**
	 * @see org.apache.tapestry.IDirect#trigger(org.apache.tapestry.IRequestCycle)
	 */
	public void trigger(IRequestCycle cycle) {
		IActionListener listener = getListener();

		if (listener == null)
			throw Tapestry.createRequiredParameterException(this, "listener");

		getListenerInvoker().invokeListener(listener, this, cycle);
	}

	/**
	 * @see corner.orm.tapestry.component.textfield.AbstractQueryDialog#getUrl()
	 */
	@Override
	protected String getUrl() {
		Object[] serviceParameters = DirectLink
				.constructServiceParameters(getParameters());

		DirectServiceParameter dsp = new DirectServiceParameter(this,
				serviceParameters);

		String url = getDirectService().getLink(false, dsp).getAbsoluteURL();
		return url;
	}
}
