//==============================================================================
// file :       $Id$
// project:     corner
//
// last change: date:       $Date$
//              by:         $Author$
//              revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

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
