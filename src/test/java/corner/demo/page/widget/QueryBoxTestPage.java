package corner.demo.page.widget;

import org.apache.tapestry.IPage;
import org.apache.tapestry.html.BasePage;

public abstract class QueryBoxTestPage extends BasePage{
	public abstract String getInputValue();
	public void doSaveEntityAction(){
		System.out.println("input value ========="+this.getInputValue());
	}
	public IPage doPopQueryPage(String pageName){
		return this.getRequestCycle().getPage(pageName);
	}
	
	public IPage doWinPopQueryPage(){
		return this.getRequestCycle().getPage("widget/WinSelectionListPage");
	}
}
