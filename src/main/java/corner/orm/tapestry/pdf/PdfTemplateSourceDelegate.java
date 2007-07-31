/*		
 * Copyright 2007 The Beijing Maxinfo Technology Ltd. 
 * site: http://www.bjmaxinfo.com
 * file: $Id: PdfTemplateSourceDelegate.java 7039 2007-07-09 07:44:26Z jcai $
 * created at:2007-04-09
 */

package corner.orm.tapestry.pdf;

import java.util.List;
import java.util.Locale;

import org.apache.hivemind.ApplicationRuntimeException;
import org.apache.hivemind.Resource;
import org.apache.tapestry.IComponent;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.engine.ITemplateSourceDelegate;
import org.apache.tapestry.parse.ComponentTemplate;
import org.apache.tapestry.parse.ITemplateParser;
import org.apache.tapestry.parse.ITemplateParserDelegate;
import org.apache.tapestry.parse.TemplateParseException;
import org.apache.tapestry.parse.TemplateParser;
import org.apache.tapestry.parse.TemplateToken;
import org.apache.tapestry.resolver.ComponentSpecificationResolver;
import org.apache.tapestry.services.ComponentPropertySource;
import org.apache.tapestry.services.impl.DefaultParserDelegate;

import com.lowagie.text.Rectangle;

import corner.orm.tapestry.pdf.components.PdfDisplayArea;
import corner.orm.tapestry.pdf.components.PdfOvTable;
import corner.orm.tapestry.pdf.service.AbstractPdfService;

/**
 * 处理pdf的模板,动态的构造模板,然后让Tapestry自己去解析这个模板文件.
 * 
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 2.3.7
 */
public class PdfTemplateSourceDelegate implements ITemplateSourceDelegate {
	/**
	 * 页面类型定义字段 
	 */
	public  static final String PDF_PAGE_TYPE_DEFINE_FIELD_NAME = "page-type";
	/**
	 * 模板页面的定义
	 */
	public static final String TEMPLATE_PAGE_TYPE = "template";

	private ITemplateParser _parser;

	private ComponentPropertySource _componentPropertySource;

	private ComponentSpecificationResolver _componentSpecificationResolver;

	/**
	 * 构造pdf文档
	 * 
	 * @see org.apache.tapestry.engine.ITemplateSourceDelegate#findTemplate(org.apache.tapestry.IRequestCycle,
	 *      org.apache.tapestry.IComponent, java.util.Locale)
	 */
	public synchronized ComponentTemplate findTemplate(IRequestCycle cycle,
			IComponent component, Locale locale) {
		if (component.getNamespace().getId().equals(
				AbstractPdfService.PDF_PAGE_NAMESPACE)) { // 仅仅是pdf处理.
			IPdfPageSpecification spc = (IPdfPageSpecification) component
			.getSpecification();
			return constructTemplateInstance(cycle, parsePdfTemplate(spc)
					.toCharArray(), spc.getSpecificationLocation(), component);
		}
		return null;
	}

	//解析pdf文档为模板字符串
	String parsePdfTemplate(IPdfPageSpecification spc) {
		
		// 构造模板文件
		StringBuffer sb = new StringBuffer();
		StringBuffer ovTable = new StringBuffer();
		sb.append("<div>");
		int insertPageTypeP =0;
		String pageType=null;
		for (int i = 1; i <= spc.getNumberOfPage(); i++) {
			ovTable.setLength(0); // 清空OverflowTable的长度.
			pageType="@PageFragment"; //还原pageType为普通的类型
			sb.append("<div jwcid=\"page");
			sb.append(i);
			
			insertPageTypeP = sb.length(); //记录插入模板类型的位置
			
			sb.append("\" pageNum=\"");
			sb.append(i);
			sb.append("\">\n");
			
			List<PdfBlock> list = spc.getPageFieldsMap().get(i);
			if (list != null) {
				for (PdfBlock block : list) {
					if (block.getName().indexOf(PdfOvTable.COMPONENT_NAME) > -1 || block.getName().indexOf(PdfDisplayArea.COMPONENT_NAME)>-1) { //ov table
						createTemplateByBlock(block, ovTable);
						
					}else if(PDF_PAGE_TYPE_DEFINE_FIELD_NAME.equalsIgnoreCase(block.getName())){ //pdf page type define
						if(block.getValue().equalsIgnoreCase(TEMPLATE_PAGE_TYPE))
							pageType = "@TemplatePageFragment";
						
					}else{
						createTemplateByBlock(block, sb);
					}
				}
			}
			
			sb.insert(insertPageTypeP,pageType);
			// 增加OverFlowTable组件.
			sb.append(ovTable.toString());
			sb.append("</div>");
		}
		sb.append("</div>");
		return sb.toString();
	}

	private void createTemplateByBlock(PdfBlock block, StringBuffer sb) {
		sb.append("<span jwcid=\"");
		sb.append(block.getName());
		sb.append("\" ");

		// rectangle
		sb.append("rectangle=\"literal:");
		Rectangle r = block.getRectangle();
		sb.append(r.left()).append(",").append(r.bottom()).append(",").append(
				r.right()).append(",").append(r.top());
		sb.append("\" ");

		// other parameter
		if (block.getValue() != null)
			sb.append(block.getValue());

		sb.append("/>\n");
	}

	/**
	 * 
	 * 以下代码摘自Tapestry的{@link org.apache.tapestry.services.impl.TemplateSourceImpl#constructTemplateInstance}
	 * This method is currently synchronized, because {@link TemplateParser}is
	 * not threadsafe. Another good candidate for a pooling mechanism,
	 * especially because parsing a template may take a while.
	 */

	private synchronized ComponentTemplate constructTemplateInstance(
			IRequestCycle cycle, char[] templateData, Resource resource,
			IComponent component) {
		String componentAttributeName = _componentPropertySource
				.getComponentProperty(component,
						"org.apache.tapestry.jwcid-attribute-name");

		ITemplateParserDelegate delegate = new DefaultParserDelegate(component,
				componentAttributeName, cycle, _componentSpecificationResolver);

		TemplateToken[] tokens;

		try {
			tokens = _parser.parse(templateData, delegate, resource);
		} catch (TemplateParseException ex) {
			throw new ApplicationRuntimeException("不能解析:" + resource, ex);
		}

		return new ComponentTemplate(templateData, tokens);
	}

	/** @since 0.7.5 */

	public void setComponentSpecificationResolver(
			ComponentSpecificationResolver resolver) {
		_componentSpecificationResolver = resolver;
	}

	public void setParser(ITemplateParser parser) {
		_parser = parser;
	}

	/** @since 0.7.5 */
	public void setComponentPropertySource(
			ComponentPropertySource componentPropertySource) {
		_componentPropertySource = componentPropertySource;
	}
}
