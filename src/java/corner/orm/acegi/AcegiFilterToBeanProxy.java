//==============================================================================
//file :        AcegiFilterToBeanProxy.java
//project:      poisoning
//
//last change:	date:       $Date$
//           	by:         $Author$
//           	revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	China Java Users Group http://cnjug.dev.java.net
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.orm.acegi;

import javax.servlet.FilterConfig;

import net.sf.acegisecurity.util.FilterToBeanProxy;

import org.springframework.context.ApplicationContext;

import corner.orm.spring.SpringContainer;

/**
 * 对Acegi的包装，覆盖getContext方法，从而从SpringContainer中得到上下文。
 * 
 * @author	<a href="http://wiki.java.net/bin/view/People/JunTsai">Jun Tsai</a>
 * @version	$Revision$
 * @since	2005-9-12
 */
public class AcegiFilterToBeanProxy extends FilterToBeanProxy {
	/**
	 * 
	 * @see net.sf.acegisecurity.util.FilterToBeanProxy#getContext(javax.servlet.FilterConfig)
	 */
	protected ApplicationContext getContext(FilterConfig filterConfig) {
        return SpringContainer.getInstance().getApplicationContext();
    }
}
