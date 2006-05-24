package corner.orm.tapestry;

import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.form.IFormComponent;
import org.apache.tapestry.valid.FieldTracking;
import org.apache.tapestry.valid.IValidator;
import org.apache.tapestry.valid.ValidationDelegate;
/**
 * 能够显示错误消息的ValidationDelegate.
 * 
 * @author	<a href="http://wiki.java.net/bin/view/People/JunTsai">Jun Tsai</a>
 * @version	$Revision$
 * @since	2005-10-21
 */
public class ShowMessageValidationDelegate extends ValidationDelegate {

	
	private static final long serialVersionUID = 8758959678347504102L;

	/**
	 * 
	 * @see org.apache.tapestry.valid.IValidationDelegate#writePrefix(org.apache.tapestry.IMarkupWriter, org.apache.tapestry.IRequestCycle, org.apache.tapestry.form.IFormComponent, org.apache.tapestry.valid.IValidator)
	 */
	@Override
	public void writePrefix(IMarkupWriter writer, IRequestCycle cycle, IFormComponent component,
            IValidator validator) {
		if(this.isInError(component)){
			FieldTracking ft=this.findCurrentTracking();
			writer.printRaw("&nbsp;");
            writer.begin("font");
            writer.attribute("color", "red");
            
            writer.print("**");
            writer.end();
            ft.getErrorRenderer().render(writer,cycle);
		}
	}
	
	

}
