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
