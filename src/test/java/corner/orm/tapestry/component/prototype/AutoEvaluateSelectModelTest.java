/*		
 * Copyright 2006-2007 The Beijing Maxinfo Technology Ltd. 
 * site:http://www.bjmaxinfo.com
 *	file : $Id: AutoEvaluateSelectModelTest.java 7044 2007-07-12 03:46:48Z xf $
 *	created at:2007-4-27
 */

package corner.orm.tapestry.component.prototype;

import java.io.CharArrayWriter;
import java.io.PrintWriter;

import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.markup.MarkupFilter;
import org.apache.tapestry.markup.MarkupWriterImpl;
import org.apache.tapestry.markup.UTFMarkupFilter;
import org.apache.tapestry.services.DataSqueezer;
import org.easymock.EasyMock;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision$
 * @since 2.3.7
 */

public class AutoEvaluateSelectModelTest extends Assert{
	public static class Currency{
		private String chnName;
		private String engName;
		/**
		 * @return Returns the chnName.
		 */
		public String getChnName() {
			return chnName;
		}
		/**
		 * @param chnName The chnName to set.
		 */
		public void setChnName(String chnName) {
			this.chnName = chnName;
		}
		/**
		 * @return Returns the engName.
		 */
		public String getEngName() {
			return engName;
		}
		/**
		 * @param engName The engName to set.
		 */
		public void setEngName(String engName) {
			this.engName = engName;
		}
	}

	private CharArrayWriter _writer;
	@Test
	public void teset_render_searializable(){
		PrintWriter writer = newPrintWriter();
		MarkupFilter filter = new UTFMarkupFilter();
		IMarkupWriter mw = new MarkupWriterImpl("text/html", writer, filter);
	
		AutoEvaluateSelectModel model=new AutoEvaluateSelectModel();
		Currency entity=new Currency();
		entity.setChnName("chnName");
		entity.setEngName("english name");
		
		String labelFields="chnName";
		String updateFields="{updateField:s_engName,updateField2:this}";
		
		model.parseParameter(null, labelFields, updateFields,null);
		
		String template="<span class=\"selecteme\">%1$s</span>";
		DataSqueezer squeezer=EasyMock.createMock(DataSqueezer.class);
		EasyMock.expect(squeezer.squeeze("english name")).andReturn("squeezer string");
		EasyMock.expect(squeezer.squeeze(entity)).andReturn("squeezer object");
		
		EasyMock.replay(squeezer);
		
		
		model.renderResultRow(mw, entity,  template, squeezer);
		StringBuffer sb=new StringBuffer();
		sb.append("<span class=\"returnValue\" style=\"display:none\"><script type=\"text/javascript\"><!--\n");
		sb.append("{\"updateField\":\"squeezer string\",\"updateField2\":\"squeezer object\"}");
		
		sb.append("\n// -->");
		sb.append("</script></span><span class=\"selecteme\">chnName</span>");
		
		assertOutput(sb.toString());
		
		EasyMock.verify(squeezer);
	}
	@Test
	public void test_render_row(){
		PrintWriter writer = newPrintWriter();
		MarkupFilter filter = new UTFMarkupFilter();
		IMarkupWriter mw = new MarkupWriterImpl("text/html", writer, filter);
	
		AutoEvaluateSelectModel model=new AutoEvaluateSelectModel();
		Currency entity=new Currency();
		entity.setChnName("chnName");
		String labelFields="chnName";
		String updateFields=null;
		
		model.parseParameter(null, labelFields, updateFields,null);
		
		String template="<span class=\"selecteme\">%1$s</span>";
		DataSqueezer squeezer=null;
		
		model.renderResultRow(mw, entity,  template, squeezer);
		
		assertOutput("<span class=\"selecteme\">chnName</span>");
	}
	@Test
	public void test_render_html_row2(){
		PrintWriter writer = newPrintWriter();
		MarkupFilter filter = new UTFMarkupFilter();
		IMarkupWriter mw = new MarkupWriterImpl("text/html", writer, filter);
	
		AutoEvaluateSelectModel model=new AutoEvaluateSelectModel();
		Currency entity=new Currency();
		entity.setChnName("chnName");
		entity.setEngName("!@#$%^&*()_+\\]}/.,\"");
		
		String labelFields="chnName";
		String updateFields="{updateField:engName}";
		
		model.parseParameter(null, labelFields, updateFields,null);
		
		String template="<span class=\"selecteme\">%1$s</span>";
		DataSqueezer squeezer=null;
		
		model.renderResultRow(mw, entity,  template, squeezer);
		
		StringBuffer sb=new StringBuffer();
		sb.append("<span class=\"returnValue\" style=\"display:none\"><script type=\"text/javascript\"><!--\n");
		sb.append("{\"updateField\":\"!@#$%^&*()_+\\\\]}/.,\\\"\"}");
		
		sb.append("\n// -->");
		sb.append("</script></span><span class=\"selecteme\">chnName</span>");
		
		
		assertOutput(sb.toString());
	}
	@Test
	public void test_render_html_row(){
		PrintWriter writer = newPrintWriter();
		MarkupFilter filter = new UTFMarkupFilter();
		IMarkupWriter mw = new MarkupWriterImpl("text/html", writer, filter);
	
		AutoEvaluateSelectModel model=new AutoEvaluateSelectModel();
		Currency entity=new Currency();
		entity.setChnName("chnName");
		entity.setEngName("english name");
		
		String labelFields="chnName";
		String updateFields="{updateField:engName}";
		
		model.parseParameter(null, labelFields, updateFields,null);
		
		String template="<span class=\"selecteme\">%1$s</span>";
		DataSqueezer squeezer=null;
		
		model.renderResultRow(mw, entity,  template, squeezer);
		StringBuffer sb=new StringBuffer();
		sb.append("<span class=\"returnValue\" style=\"display:none\"><script type=\"text/javascript\"><!--\n");
		sb.append("{\"updateField\":\"english name\"}");
		
		sb.append("\n// -->");
		sb.append("</script></span><span class=\"selecteme\">chnName</span>");
		
		assertOutput(sb.toString());
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
