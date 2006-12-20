package corner.orm.tapestry.component.textfield;

import java.util.HashMap;
import java.util.Map;

import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.IScript;
import org.apache.tapestry.TapestryUtils;
import org.apache.tapestry.annotations.InjectScript;
import org.apache.tapestry.annotations.Parameter;
import org.apache.tapestry.dojo.AbstractWidget;
import org.apache.tapestry.json.JSONObject;

/**
 * 抽象的查询窗体
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.3
 */
public abstract class AbstractQueryDialog extends AbstractWidget {

	public AbstractQueryDialog() {
		super();
	}

	/**
	 * 当选中某一条记录的时候，响应的js函数
	 * @return
	 */
	@Parameter
	public abstract String getOnSelectFunName();
	public abstract String getBackgroundColor();

	public abstract float getOpacity();

	/**
	 * {@inheritDoc}
	 */
	public void renderWidget(IMarkupWriter writer, IRequestCycle cycle) {
	        if (!cycle.isRewinding()) {
	            writer.begin(getTemplateTagName()); // use element specified
	            renderIdAttribute(writer, cycle); // render id="" client id
	            renderInformalParameters(writer, cycle);
	        }
	        
	        renderBody(writer, cycle);
	        
	        if (!cycle.isRewinding()) {
	            writer.end();
	        }
	        
	        if (!cycle.isRewinding()) {
	            JSONObject json = new JSONObject();
	            json.put("bgColor", getBackgroundColor());
	            json.put("bgOpacity", getOpacity());
	            json.put("selectFun",getOnSelectFunName());
	            
	            
	//            json.put("url",this.getPageService().getLink(false, this.getQueryPageName()).getAbsoluteURL());
	            json.put("url",getUrl());
	            
	
	            Map<String,Object> parms = new HashMap<String,Object>();
	            parms.put("component", this);
	            parms.put("props", json.toString());
	            getScript().execute(this, cycle, TapestryUtils.getPageRenderSupport(cycle, this), parms);
	        }
	    }

	/** injected. */
	@InjectScript("QueryDialog.script")
	public abstract IScript getScript();
	
	protected abstract String getUrl();

}