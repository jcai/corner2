//==============================================================================
//file :        PianoResponseBuilder.java
//
//last change:	date:       $Date$
//           	by:         $Author$
//           	revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//==============================================================================

package corner.demo;

import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.markup.MarkupWriterSource;
import org.apache.tapestry.services.RequestLocaleManager;
import org.apache.tapestry.services.impl.DefaultResponseBuilder;
import org.apache.tapestry.web.WebResponse;

/**
 * 对piano的响应的builder
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.5.0
 */
public class PianoResponseBuilder extends DefaultResponseBuilder {

	public PianoResponseBuilder(IMarkupWriter arg0) {
		super(arg0);
	}

	public PianoResponseBuilder(RequestLocaleManager arg0, MarkupWriterSource arg1, WebResponse arg2) {
		super(arg0, arg1, arg2);
	}

	/**
	 * @see org.apache.tapestry.services.impl.DefaultResponseBuilder#writeInitializationScript(org.apache.tapestry.IMarkupWriter, java.lang.String)
	 */
	@Override
	public void writeInitializationScript(IMarkupWriter writer, String script) {
		writer.begin("script");
        writer.attribute("type", "text/javascript");
        writer.printRaw("<!--\n");
        
        writer.printRaw("dojo.event.connect(\"before\",window,\"onload\",function(e) {\n");
        
        writer.printRaw(script);
        
        writer.printRaw("});");
        
        writer.printRaw("\n// -->");
        writer.end();
	}
	
}
