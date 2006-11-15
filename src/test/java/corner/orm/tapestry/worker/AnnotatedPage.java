package corner.orm.tapestry.worker;

import org.apache.tapestry.annotations.InitialValue;
import org.apache.tapestry.annotations.Persist;
import org.apache.tapestry.html.BasePage;

public abstract class AnnotatedPage extends BasePage {

	@MagicField(entity="entity")
	public abstract void getMagicField();
	
	@Persist("client")
	@InitialValue("new corner.orm.tapestry.worker.AnnotationModel")
    public abstract Object getEntity();
}
