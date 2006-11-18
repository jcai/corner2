package corner.orm.tapestry.page;

import java.io.CharArrayWriter;
import java.io.PrintWriter;

import org.apache.tapestry.BaseComponentTestCase;
import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.form.IFormComponent;
import org.apache.tapestry.markup.MarkupFilter;
import org.apache.tapestry.markup.MarkupWriterImpl;
import org.apache.tapestry.markup.UTFMarkupFilter;
import org.easymock.EasyMock;
import org.testng.annotations.Test;


public class CornerValidationDelegateTest extends BaseComponentTestCase{
	private static CharArrayWriter _writer;

	@Test
	public void test_write_prefix() {
		MarkupFilter filter = new UTFMarkupFilter();
		PrintWriter writer = newPrintWriter();
		 IMarkupWriter mw = new MarkupWriterImpl("text/html", writer, filter);
		 
		IFormComponent component=newMock(IFormComponent.class);
		IRequestCycle cycle=newCycle();
		
		EasyMock.expect(component.getName()).andReturn("Field");
		replay();
		CornerValidationDelegate delegate=new CornerValidationDelegate();
		delegate.writePrefix(mw, cycle, component, null);
		assertOutput("");
		verify();
	}

	@Test
	public void test_write_message_prefix() {
		MarkupFilter filter = new UTFMarkupFilter();
		IFormComponent field = newMock(IFormComponent.class);

		EasyMock.expect(field.getName()).andReturn("myField").times(4);
        
        IRequestCycle cycle=newCycle();
        PrintWriter writer = newPrintWriter();
        replay();
        IMarkupWriter mw = new MarkupWriterImpl("text/html", writer, filter);
        CornerValidationDelegate delegate = new CornerValidationDelegate();

        delegate.record(field, "My Error Message");
        delegate.writePrefix(mw, cycle, field, null);
        assertOutput("<div class=\"error-div\">My Error Message</div>");
        verify();
	}

	private void assertOutput(String expected) {
		assertEquals(expected, _writer.toString());

		_writer.reset();
	}

	private PrintWriter newPrintWriter() {
		_writer = new CharArrayWriter();

		return new PrintWriter(_writer);
	}
	   protected void trainGetName(IFormComponent field, String name)
	    {
	        EasyMock.expect(field.getName()).andReturn(name);
	    }
}
