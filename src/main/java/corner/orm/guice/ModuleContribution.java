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
import org.apache.hivemind.impl.BaseLocatable;

import com.google.inject.Module;

/**
 * 实现其他的module
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.3.7
 */
public class ModuleContribution extends BaseLocatable{
	private Class<Module> moduleClass;

	/**
	 * @return Returns the moduleClass.
	 */
	public Class<Module> getModuleClass() {
		return moduleClass;
	}

	/**
	 * @param moduleClass The moduleClass to set.
	 */
	@SuppressWarnings("unchecked")
	public void setModuleClassName(String moduleClassName) {
		try {
			this.moduleClass = (Class<Module>) (Class.forName(moduleClassName));
		} catch (ClassNotFoundException e) {
			throw new ApplicationRuntimeException(e);
		}
	}
}
