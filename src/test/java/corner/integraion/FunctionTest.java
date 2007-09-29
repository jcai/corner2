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

package corner.integraion;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.3.7
 */
public class FunctionTest extends AbstractIntegrationTestCase{

	/**
	 * 
	 */
	public FunctionTest() {
		super("src/main/webapp");

	}
	@Test
	public void test_one_open(){
		this.open("/app");
		this.click("link=[ONE]");
		this.waitForPageToLoad("30000");
		this.click("link=[LINK.ADD]");
		this.waitForPageToLoad("30000");
		this.type("nameField", "test");
		this.click("Submit");
		this.waitForPageToLoad("30000");
		Assert.assertTrue(this.isTextPresent("test"));
		this.click("link=[LINK.EDIT]");
		this.waitForPageToLoad("30000");
		this.type("nameField", "test2");
		this.click("Submit");
		this.waitForPageToLoad("30000");
		assertTrue(this.isTextPresent("test2"));
		this.click("link=[LINK.DELETE]");
		this.waitForPageToLoad("30000");
	}
	@Test
	public void test_one_to_many(){
		this.open("/app");
		this.click("link=[ONE2MANY]");
		this.waitForPageToLoad("30000");
		this.click("link=[LINK.ADD]");
		this.waitForPageToLoad("30000");
		this.type("nameField", "aaa");
		this.click("Submit");
		this.waitForPageToLoad("30000");
		this.click("link=[LINK.EDIT]");
		this.waitForPageToLoad("30000");
		this.type("nameField", "bbb");
		this.click("Submit");
		this.waitForPageToLoad("30000");
		this.click("link=[LINK.EDIT]");
		this.waitForPageToLoad("30000");
		this.click("//input[@value='[BUTTON.CANCLE]']");
		this.waitForPageToLoad("30000");
		this.click("link=[LINK.DELETE]");
		this.waitForPageToLoad("30000");
		this.click("link=[LINK.ADD]");
		this.waitForPageToLoad("30000");
		this.type("nameField", "aaa");
		this.click("Submit");
		this.waitForPageToLoad("30000");
		this.click("link=[LINK.ADD]");
		this.waitForPageToLoad("30000");
		this.type("nameField", "bbb");
		this.click("Submit");
		this.waitForPageToLoad("30000");
		this.click("link=[LINK.EDIT]");
		this.waitForPageToLoad("30000");
		this.click("link=Add A to B");
		this.waitForPageToLoad("30000");
		this.type("nameField", "111");
		this.click("Submit");
		this.waitForPageToLoad("30000");
		this.click("link=Add A to B");
		this.waitForPageToLoad("30000");
		this.type("nameField", "222");
		this.click("Submit");
		this.waitForPageToLoad("30000");
		this.click("link=[DOEDITRELATIVEACTION]");
		this.waitForPageToLoad("30000");
		this.type("nameField", "333");
		this.click("//input[@value='[BUTTON.CANCEL]']");
		this.waitForPageToLoad("30000");
		this.click("link=[DOEDITRELATIVEACTION]");
		this.waitForPageToLoad("30000");
		this.type("nameField", "333");
		this.click("Submit");
		this.waitForPageToLoad("30000");
		this.click("link=[DOEDITRELATIVEACTION]");
		this.waitForPageToLoad("30000");
		this.click("//input[@value='[BUTTON.CANCEL]']");
		this.waitForPageToLoad("30000");
		this.click("link=[DODELETERELATIVEACTION]");
		this.waitForPageToLoad("30000");
		this.click("link=[DODELETERELATIVEACTION]");
		this.waitForPageToLoad("30000");
		this.click("//input[@value='[BUTTON.CANCLE]']");
		this.waitForPageToLoad("30000");
		this.click("link=[LINK.DELETE]");
		this.waitForPageToLoad("30000");
		this.click("link=[LINK.DELETE]");
		this.waitForPageToLoad("30000");
	}
	@Test
	public void test_many_to_many(){
		this.open("/app");
		this.click("link=[MANY2MANY-USER]");
		this.waitForPageToLoad("30000");
		this.click("link=[LINK.ADD]");
		this.waitForPageToLoad("30000");
		this.type("nameField", "aaa");
		this.click("Submit");
		this.waitForPageToLoad("30000");
		this.click("link=[LINK.EDIT]");
		this.waitForPageToLoad("30000");
		this.type("nameField", "bbb");
		this.click("Submit");
		this.waitForPageToLoad("30000");
		this.click("link=[LINK.EDIT]");
		this.waitForPageToLoad("30000");
		this.click("//input[@value='[BUTTON.CANCLE]']");
		this.waitForPageToLoad("30000");
		this.click("link=[LINK.DELETE]");
		this.waitForPageToLoad("30000");
		this.click("link=[LINK.ADD]");
		this.waitForPageToLoad("30000");
		this.type("nameField", "aaa");
		this.click("Submit");
		this.waitForPageToLoad("30000");
		this.click("link=[LINK.ADD]");
		this.waitForPageToLoad("30000");
		this.type("nameField", "bbb");
		this.click("Submit");
		this.waitForPageToLoad("30000");
		this.click("link=[LINK.ADD]");
		this.waitForPageToLoad("30000");
		this.type("nameField", "ccc");
		this.click("Submit");
		this.waitForPageToLoad("30000");
		this.click("link=Goto BList");
		this.waitForPageToLoad("30000");
		this.click("link=[LINK.ADD]");
		this.waitForPageToLoad("30000");
		this.type("nameField", "111");
		this.click("Submit");
		this.waitForPageToLoad("30000");
		this.click("link=[LINK.EDIT]");
		this.waitForPageToLoad("30000");
		this.type("nameField", "222");
		this.click("Submit");
		this.waitForPageToLoad("30000");
		this.click("link=[LINK.EDIT]");
		this.waitForPageToLoad("30000");
		this.click("//input[@value='[BUTTON.CANCLE]']");
		this.waitForPageToLoad("30000");
		this.click("link=[LINK.DELETE]");
		this.waitForPageToLoad("30000");
		this.click("link=[LINK.ADD]");
		this.waitForPageToLoad("30000");
		this.type("nameField", "111");
		this.click("Submit");
		this.waitForPageToLoad("30000");
		this.click("link=[LINK.ADD]");
		this.waitForPageToLoad("30000");
		this.type("nameField", "222");
		this.click("Submit");
		this.waitForPageToLoad("30000");
		this.click("link=[LINK.ADD]");
		this.waitForPageToLoad("30000");
		this.type("nameField", "333");
		this.click("Submit");
		this.waitForPageToLoad("30000");
		this.click("link=[LINK.EDIT]");
		this.waitForPageToLoad("30000");
		this.click("link=Add B to A");
		this.waitForPageToLoad("30000");
		this.click("Checkbox");
		this.click("Checkbox_0");
		this.click("Checkbox_1");
		this.click("//input[@value='Submit']");
		this.waitForPageToLoad("30000");
		this.click("link=[DODELETERELATIVEACTION]");
		this.waitForPageToLoad("30000");
		this.click("link=[DODELETERELATIVEACTION]");
		this.waitForPageToLoad("30000");
		this.click("link=[DODELETERELATIVEACTION]");
		this.waitForPageToLoad("30000");
		this.click("link=Add B to A");
		this.waitForPageToLoad("30000");
		this.click("Checkbox");
		this.click("Checkbox_0");
		this.click("Checkbox_1");
		this.click("//input[@value='Submit']");
		this.waitForPageToLoad("30000");
		this.click("link=[DODELETERELATIVEACTION]");
		this.waitForPageToLoad("30000");
		this.click("link=[DODELETERELATIVEACTION]");
		this.waitForPageToLoad("30000");
		this.click("link=[DODELETERELATIVEACTION]");
		this.waitForPageToLoad("30000");
		this.click("//input[@value='[BUTTON.CANCLE]']");
		this.waitForPageToLoad("30000");
		this.click("link=Goto AList");
		this.waitForPageToLoad("30000");
		this.click("link=[LINK.EDIT]");
		this.waitForPageToLoad("30000");
		this.click("link=Add A to B");
		this.waitForPageToLoad("30000");
		this.click("Checkbox");
		this.click("Checkbox_0");
		this.click("Checkbox_1");
		this.click("//input[@value='Submit']");
		this.waitForPageToLoad("30000");
		this.click("link=[DODELETERELATIVEACTION]");
		this.waitForPageToLoad("30000");
		this.click("link=[DODELETERELATIVEACTION]");
		this.waitForPageToLoad("30000");
		this.click("link=[DODELETERELATIVEACTION]");
		this.waitForPageToLoad("30000");
		this.click("link=Add A to B");
		this.waitForPageToLoad("30000");
		this.click("Checkbox");
		this.click("//input[@value='Submit']");
		this.waitForPageToLoad("30000");
		this.click("//input[@value='[BUTTON.CANCLE]']");
		this.waitForPageToLoad("30000");
		this.click("link=Goto BList");
		this.waitForPageToLoad("30000");
		this.click("link=[LINK.EDIT]");
		this.waitForPageToLoad("30000");
		this.click("link=Add B to A");
		this.waitForPageToLoad("30000");
		this.click("Checkbox");
		this.click("//input[@value='Submit']");
		this.waitForPageToLoad("30000");
		this.click("//input[@value='[BUTTON.CANCLE]']");
		this.waitForPageToLoad("30000");
		this.click("link=[LINK.DELETE]");
		this.waitForPageToLoad("30000");
		this.click("link=[LINK.DELETE]");
		this.waitForPageToLoad("30000");
		this.click("link=[LINK.DELETE]");
		this.waitForPageToLoad("30000");
		this.click("link=Goto AList");
		this.waitForPageToLoad("30000");
		this.click("link=[LINK.DELETE]");
		this.waitForPageToLoad("30000");
		this.click("link=[LINK.DELETE]");
		this.waitForPageToLoad("30000");
		this.click("link=[LINK.DELETE]");
		this.waitForPageToLoad("30000");
	}
	@Test
	public void test_one_action(){
		this.open("/app");
		this.click("link=[ONE]");
		this.waitForPageToLoad("30000");
		this.click("link=[LINK.ADD]");
		this.waitForPageToLoad("30000");
		this.type("nameField", "aaa");
		this.click("Submit");
		this.waitForPageToLoad("30000");
		this.click("link=[LINK.EDIT]");
		this.waitForPageToLoad("30000");
		this.type("nameField", "bbb");
		this.click("Submit");
		this.waitForPageToLoad("30000");
		this.click("link=[LINK.EDIT]");
		this.waitForPageToLoad("30000");
		this.click("//input[@value='[BUTTON.CANCLE]']");
		this.waitForPageToLoad("30000");
		this.click("link=[LINK.DELETE]");
		this.waitForPageToLoad("30000");
		this.click("link=[LINK.ADD]");
		this.waitForPageToLoad("30000");
		this.type("nameField", "aaa");
		this.click("Submit");
		this.waitForPageToLoad("30000");
		this.click("link=[LINK.ADD]");
		this.waitForPageToLoad("30000");
		this.type("nameField", "bbb");
		this.click("Submit");
		this.waitForPageToLoad("30000");
		this.click("link=[LINK.ADD]");
		this.waitForPageToLoad("30000");
		this.type("nameField", "ccc");
		this.click("Submit");
		this.waitForPageToLoad("30000");
		this.click("link=[LINK.DELETE]");
		this.waitForPageToLoad("30000");
		this.click("link=[LINK.DELETE]");
		this.waitForPageToLoad("30000");
		this.click("link=[LINK.DELETE]");
		this.waitForPageToLoad("30000");
	}

}
