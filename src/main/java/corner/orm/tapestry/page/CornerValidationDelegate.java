//==============================================================================
//file :        ValidationDelegate.java
//
//last change:	date:       $Date: 2006-11-16 10:08:48Z $
//           	by:         $Author: jcai $
//           	revision:   $Revision: 2242 $
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//==============================================================================

package corner.orm.tapestry.page;

import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.form.IFormComponent;
import org.apache.tapestry.valid.FieldTracking;
import org.apache.tapestry.valid.IValidator;
import org.apache.tapestry.valid.ValidationDelegate;

/**
 * 
 * 对验证的窗体回填信息的代理类.
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision: 2242 $
 * @since 2.3
 */
public class CornerValidationDelegate extends ValidationDelegate{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4047923735009997385L;

	
	/**
	 * @see org.apache.tapestry.valid.ValidationDelegate#writePrefix(org.apache.tapestry.IMarkupWriter, org.apache.tapestry.IRequestCycle, org.apache.tapestry.form.IFormComponent, org.apache.tapestry.valid.IValidator)
	 */
	@Override
	public void writePrefix(IMarkupWriter writer, IRequestCycle cycle, IFormComponent component, IValidator validator) {
		if(this.isInError(component)){
			FieldTracking ft=this.findCurrentTracking();
			writer.begin("div");
			writer.attribute("class","error-div");
			ft.getErrorRenderer().render(writer,cycle);
			writer.end("div");
            
		}
		
	}
	/**
	 * @see org.apache.tapestry.valid.ValidationDelegate#writeSuffix(org.apache.tapestry.IMarkupWriter, org.apache.tapestry.IRequestCycle, org.apache.tapestry.form.IFormComponent, org.apache.tapestry.valid.IValidator)
	 */
	@Override
	public void writeSuffix(IMarkupWriter arg0, IRequestCycle arg1, IFormComponent arg2, IValidator arg3) {
		//do nothing
	}
}
