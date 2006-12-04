//==============================================================================
//file :        PianoResponseContributorImpl.java
//
//last change:	date:       $Date$
//           	by:         $Author$
//           	revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//==============================================================================

package corner.demo;

import java.io.IOException;

import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.services.ResponseBuilder;
import org.apache.tapestry.services.impl.DefaultResponseContributorImpl;

/**
 * 实现了piano部分对页面的渲染.
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.5.0
 */
public class PianoResponseContributorImpl extends
		DefaultResponseContributorImpl {
    public ResponseBuilder createBuilder(IRequestCycle cycle)
    throws IOException
    {
        return new PianoResponseBuilder(_localeManager, _markupWriterSource, _webResponse);
    }
}
