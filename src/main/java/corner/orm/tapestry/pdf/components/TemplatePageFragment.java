/*		
 * Copyright 2007 The Beijing Maxinfo Technology Ltd. 
 * site: http://www.bjmaxinfo.com
 * file: $Id: TemplatePageFragment.java 7039 2007-07-09 07:44:26Z jcai $
 * created at:2007-04-27
 */

package corner.orm.tapestry.pdf.components;


/**
 * 定义一个分页时候显示用的模板类
 * 
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 2.3.7
 */
public abstract class TemplatePageFragment extends PageFragment {
	/**
	 * @see com.bjmaxinfo.piano.tapestry.pdf.components.AbstractPdfComponent#isTemplateFragment()
	 */
	public boolean isTemplateFragment() {
		return true;
	}
}
