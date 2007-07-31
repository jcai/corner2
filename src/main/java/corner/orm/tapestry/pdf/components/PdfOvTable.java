/*		
 * Copyright 2007 The Beijing Maxinfo Technology Ltd. 
 * site: http://www.bjmaxinfo.com
 * file: $Id: PdfOvTable.java 7039 2007-07-09 07:44:26Z jcai $
 * created at:2007-04-11
 */

package corner.orm.tapestry.pdf.components;

import java.util.List;

import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.annotations.Parameter;

import corner.orm.tapestry.pdf.IPdfComponent;
import corner.orm.tapestry.pdf.PdfWriterDelegate;

/**
 * 需要溢出的表格.
 * 
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 2.3.7
 */
public abstract class PdfOvTable extends AbstractPdfTableDisplay {

	public final static String COMPONENT_NAME = "PdfOvTable";
	static final String CURRENT_TEMPLATE_OV_STEP = "pdf-current-template-ov-step";
	/** 对表格的定义 * */
	@Parameter(required = true)
	public abstract IPdfTableModel<Object> getModel();

	@Parameter(required = true)
	public abstract List getSource();

	/**
	 * 
	 * 得到循环的页码，默认为当前页
	 * 
	 * @return 循环的模板页码
	 */
	@Parameter(name = "lp")
	public abstract int getLoopTemplatePage();

	/**
	 * 得到终止结束模板的页码,默认为当前页
	 * 
	 * @return 终止结束页码
	 */
	@Parameter(name = "ep")
	public abstract int getEndTemplatePage();

	@Override
	protected String getStepAttributeName(){
		return CURRENT_TEMPLATE_OV_STEP;
	}
	@Override
	protected List getDefaultSource() {
		return this.getSource();
	}
	@Override
	protected IPdfTableModel<Object> getDisplayTableModel() {
		return getModel();
	}
	@Override
	protected void doRenderOverflowContent(IMarkupWriter writer,boolean isFirstPage) {
		if (this.getLoopTemplatePage() == 0) {// 只在当前页面进行循环
			List sourceObj = getRecord(CURRENT_SOURCE, List.class);
			if(!this.nextIsLastPage((PdfWriterDelegate) writer, sourceObj)){
				//当前组件所在的页面
				IPdfComponent currentComponent = getRecord(
						PageFragment.CURRENT_PAGE_FRAGMENT_ATTRIBUTE_NAME,
						IPdfComponent.class);
				currentComponent.render(writer, this.getCycle());// 自身作为模板递归渲染
			}
		} else { //调用循环的页面.
			renderTemplatePage(writer,this.getLoopTemplatePage());
		}
		
		//已经到了最后一个页面
		if(this.getEndTemplatePage()>0){ //如果定义了最后页模板
			renderTemplatePage(writer,this.getEndTemplatePage());
		}else if(this.getLoopTemplatePage() > 0 ){ //采用当前页模板进行循环
			renderTemplatePage(writer,this.getLoopTemplatePage());
		}else if(isFirstPage){
			//当前组件所在的页面
			IPdfComponent currentComponent = getRecord(
					PageFragment.CURRENT_PAGE_FRAGMENT_ATTRIBUTE_NAME,
					IPdfComponent.class);
			currentComponent.render(writer, this.getCycle());// 自身作为模板递归渲染
		}
	}
	
	//对模板文件进行渲染
	private void renderTemplatePage(IMarkupWriter writer,int pageNum){
		record(OV_TABLE_MODE, this.getModel());

		TemplatePageFragment template = (TemplatePageFragment) this
				.getPage()
				.getComponent("page" + String.valueOf(pageNum));
		template.renderComponent(writer, getCycle());
		rollback(OV_TABLE_MODE, null);  
	}
}
