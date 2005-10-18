package corner.tapestry;

import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.form.IFormComponent;
import org.apache.tapestry.valid.FieldTracking;
import org.apache.tapestry.valid.IValidator;
import org.apache.tapestry.valid.ValidationDelegate;

public class ShowMessageValidationDelegate extends ValidationDelegate {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8758959678347504102L;

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
