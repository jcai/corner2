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
	public void setDataSqueezer(DataSqueezer sequeezer ){
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
