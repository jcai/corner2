/*		
 * Copyright 2007 The Beijing Maxinfo Technology Ltd. 
 * site: http://www.bjmaxinfo.com
 * file: $Id$
 * created at:2007-04-09
 */

package corner.orm.tapestry.pdf;

import java.util.List;
import java.util.Map;

import org.apache.tapestry.spec.ComponentSpecification;

/**
 * pdf页面的定义
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 2.3.7
 */
public class PdfPageSpecification extends ComponentSpecification implements IPdfPageSpecification{

	/** 页面包含的字段 **/
	private Map<Integer, List<PdfBlock>> pageFieldsMap;
	/** 总页数 **/
	private int numberOfPage;

	/**
	 * @return Returns the numberOfPage.
	 */
	public int getNumberOfPage() {
		return numberOfPage;
	}

	/**
	 * @return Returns the pageFieldsMap.
	 */
	public Map<Integer, List<PdfBlock>> getPageFieldsMap() {
		return pageFieldsMap;
	}

	public void setNumberOfPage(int numberOfPages) {
		this.numberOfPage=numberOfPages;
		
	}

	public void setPageFieldsMap(Map<Integer, List<PdfBlock>> pageFields) {
		this.pageFieldsMap=pageFields;
		
	}

}
