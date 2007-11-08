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

import org.hibernate.HibernateException;
import org.hibernate.event.SaveOrUpdateEvent;
import org.hibernate.event.def.DefaultSaveOrUpdateEventListener;

import corner.orm.spring.SpringContainer;

/**
 * 保存或者更新时候的event监听器.
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.5
 */
public class VersionSaveUpdateEventListener extends DefaultSaveOrUpdateEventListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1075078528571943074L;

	/**
	 * @see org.hibernate.event.SaveOrUpdateEventListener#onSaveOrUpdate(org.hibernate.event.SaveOrUpdateEvent)
	 */
	public void onSaveOrUpdate(SaveOrUpdateEvent event)
			throws HibernateException {
		super.onSaveOrUpdate(event);
		Object obj=event.getEntity();
		event.getResultId();
		if(obj instanceof IVersionable){
			IVersionService service=(IVersionService) SpringContainer.getInstance().getApplicationContext().getBean(IVersionProvider.VERSION_SPRING_BEAN_NAME);
			service.checkin((IVersionable) obj);
		}
		
	}
}
