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

package corner.orm.tapestry.validator;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.2.2
 */
public class UniqueEntityTest {

	@Test
	public void testRegex(){
		String className="corner.demo.A";
		String proName="name";
		UniqueEntity entity=new UniqueEntity();
		entity.setUniqueEntity("{"+className+":"+proName+"}");
		Assert.assertEquals(entity.getEntityClassName(),className);
		Assert.assertEquals(entity.getPropertyName(),proName);
		
	}
}
