package corner.orm.tapestry.validator;

import java.util.Locale;

import org.apache.tapestry.form.IFormComponent;
import org.apache.tapestry.form.ValidationMessages;
import org.apache.tapestry.form.ValidationMessagesImpl;
import org.apache.tapestry.form.validator.BaseValidatorTestCase;
import org.apache.tapestry.request.IUploadFile;
import org.apache.tapestry.valid.ValidatorException;
import org.easymock.EasyMock;
import org.testng.annotations.Test;

/**
 * 文件类型验证
 * @author xiafei
 * @version $Revision$
 * @since 2.3.7
 */
public class FileTypeTest extends BaseValidatorTestCase{
	
	/**
	 * 
	 */
	@Test
	public void test_wrong(){
		FileType ft = new FileType("fileType={.exe;.exl;.ppt;.jpg}");
		
		IFormComponent field=this.newField();
		ValidationMessages messages = new ValidationMessagesImpl(field, Locale
				.getDefault());
		
		IUploadFile upfile = newMock(IUploadFile.class);
		
		EasyMock.expect(upfile.getFileName()).andReturn("C:\\test.fff");
		
		replay();
		try {
			ft.validate(field, messages, upfile);
			fail("不能到达!");
		} catch (ValidatorException e) {
			assertEquals(e.getMessage(), "只允上传.exe;.exl;.ppt;.jpg类型的文件.");
		}
		verify();
	}
	
	/**
	 * 
	 */
	@Test
	public void test_right(){
		FileType ft = new FileType("fileType={.exe;.exl;.ppt;.jpg}");
		
		IFormComponent field=this.newField();
		
		IUploadFile upfile = newMock(IUploadFile.class);
		
		EasyMock.expect(upfile.getFileName()).andReturn("C:\\test.exe");
		
		replay();
		try {
			ft.validate(field, null, upfile);
		} catch (ValidatorException e) {
			fail("不该到达这里!");
		}
		verify();
	}
}
