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

package corner.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.springframework.util.ResourceUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:Ghostbb@bjmaxinfo.com">Ghostbb</a>
 * @version $Revision$
 * @since 2.5
 */
public class ResourceUtilTest extends Assert{

	@Test
	public void testResourceUtils(){
		try {
			File f = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX+"database/h2-dev.properties");
			Properties props = new Properties();
			try {
				props.load(new FileInputStream(f));
				System.out.println(props.get("hibernate.connection.url"));
				assertEquals(props.get("hibernate.connection.url"), "jdbc:h2:database/testdb");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
