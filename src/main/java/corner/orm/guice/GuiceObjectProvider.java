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

package corner.orm.guice;

import org.apache.hivemind.ApplicationRuntimeException;
import org.apache.hivemind.Location;
import org.apache.hivemind.internal.Module;
import org.apache.hivemind.service.ObjectProvider;

import com.google.inject.Injector;

/**
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.3.7
 */
public class GuiceObjectProvider implements ObjectProvider{

	private Injector injector;
	@SuppressWarnings("unchecked")
	public Object provideObject(Module contributingModule, Class propertyType, String locator,
			Location location){
		Class<?> clazz;
		try {
			clazz = Class.forName(locator);
		} catch (ClassNotFoundException e) {
			throw new ApplicationRuntimeException(e);
		}
		return injector.getInstance(clazz);
	}
	public void setInjector(Injector injector){
		this.injector = injector;
	}
}
