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
