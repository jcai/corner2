/*		
 * Copyright 2007 The Beijing Maxinfo Technology Ltd. 
 * site: http://www.bjmaxinfo.com
 * file: $Id: IPdfComponent.java 7039 2007-07-09 07:44:26Z jcai $
 * created at:2007-04-08
 */

package corner.orm.tapestry.pdf;

import org.apache.tapestry.IComponent;
import org.apache.tapestry.annotations.InjectObject;
import org.apache.tapestry.annotations.Parameter;

import com.lowagie.text.Document;

import corner.orm.tapestry.pdf.service.IFieldCreator;

/**
 * 一个pdf的接口
 * @author jcai
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 2.3.7
 */
public interface IPdfComponent extends IComponent{

	/**
	 * 渲染的pdf组件.
	 * @param writer pdf writer
	 * @param doc pdf文档.
	 */
	public void renderPdf(PdfWriterDelegate writer,Document doc) ;
	
	/**
	 * 判断该组件是否为模板组件
	 * true:是模板组件 false:不是模板组件
	 * @return
	 */
	public boolean isTemplateFragment();
	
	/**得到pdf组件的坐标**/
	@Parameter
	public abstract String getRectangle();
	public abstract void setRectangle(String rec);
	@InjectObject("service:piano.pdf.FieldCreator")
	public abstract IFieldCreator getFieldCreator();
}
