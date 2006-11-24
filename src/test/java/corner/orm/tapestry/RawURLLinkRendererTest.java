package corner.orm.tapestry;

import java.io.CharArrayWriter;
import java.io.PrintWriter;

import org.apache.tapestry.BaseComponentTestCase;
import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.components.ILinkComponent;
import org.apache.tapestry.engine.ILink;
import org.apache.tapestry.markup.MarkupFilter;
import org.apache.tapestry.markup.MarkupWriterImpl;
import org.apache.tapestry.markup.UTFMarkupFilter;
import org.easymock.EasyMock;
import org.testng.annotations.Test;

public class RawURLLinkRendererTest extends BaseComponentTestCase {

	private CharArrayWriter _writer;

	@Test
	public void test_raw_url_link() {
		PrintWriter writer = newPrintWriter();
		MarkupFilter filter = new UTFMarkupFilter();
		IMarkupWriter mw = new MarkupWriterImpl("text/html", writer, filter);

		RawURLLinkRenderer renderer = new RawURLLinkRenderer();
		ILinkComponent linkComponent = newMock(ILinkComponent.class);
		IRequestCycle cycle = newCycle();
		
		ILink link=newMock(ILink.class);
		EasyMock.expect(linkComponent.getLink(cycle)).andReturn(link);
		EasyMock.expect(link.getAbsoluteURL()).andReturn("http://dev.wtstudio.org");
		replay();

		renderer.renderLink(mw, cycle, linkComponent);
		 assertOutput("http://dev.wtstudio.org");
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
}
