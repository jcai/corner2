/*		
 * Copyright 2007 The Beijing Maxinfo Technology Ltd. 
 * site: http://www.bjmaxinfo.com
 * file: $Id: IPdfPageSpecification.java 7039 2007-07-09 07:44:26Z jcai $
 * created at:2007-04-09
 */

package corner.orm.tapestry.pdf;

import java.util.List;
import java.util.Map;

import org.apache.tapestry.spec.IComponentSpecification;

/**
 * 对Pdf模板定义的接口
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 2.3.7
 */
public interface IPdfPageSpecification extends IComponentSpecification{

	/**
	 * 设定页数
	 * @param numberOfPages
	 */
	void setNumberOfPage(int numberOfPages);

	/**
	 * 设定页面中包含的字段。
	 * @param pageFields 页面包含的字段
	 */
	void setPageFieldsMap(Map<Integer, List<PdfBlock>> pageFields);

	/**
	 * @return Returns the numberOfPage.
	 */
	public int getNumberOfPage();

	/**
	 * @return Returns the pageFieldsMap.
	 */
	public Map<Integer, List<PdfBlock>> getPageFieldsMap();
	

}
