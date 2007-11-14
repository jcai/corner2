// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2007-07-31
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package corner.orm.tapestry.pdf.service;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import org.apache.hivemind.ApplicationRuntimeException;
import org.apache.hivemind.Resource;
import org.apache.hivemind.util.PropertyUtils;
import org.apache.tapestry.IAsset;
import org.apache.tapestry.IComponent;
import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IPage;
import org.apache.tapestry.IRender;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.asset.AssetFactory;
import org.apache.tapestry.services.ResponseBuilder;
import org.apache.tapestry.util.exception.ExceptionDescription;
import org.apache.tapestry.util.exception.ExceptionProperty;
import org.apache.tapestry.web.WebResponse;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfWriter;

import corner.orm.tapestry.pdf.IPdfComponent;
import corner.orm.tapestry.pdf.IPdfPage;
import corner.orm.tapestry.pdf.PdfOutputPageEvent;
import corner.orm.tapestry.pdf.PdfUtils;
import corner.orm.tapestry.pdf.PdfWriterDelegate;

/**
 * 对pdf请求响应的构造器.
 * 
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 2.3.7
 */
public class PdfResponseBuilder implements ResponseBuilder {

	/** 保存在上下文中Document实体对象的Attribute名称 * */
	public static final String PDF_DOCUMENT_ATTRIBUTE_NAME = "pdf-document-object";

	private WebResponse response;

	/** pdf 包装类 * */
	private PdfWriterDelegate _writer;

	private Document _doc;

	/**
	 * 构造PdfResponseBuilder
	 * 
	 * @param response
	 * @param factory
	 * @param string
	 */
	public PdfResponseBuilder(WebResponse response, AssetFactory factory,
			String string) {
		this.response = response;
	}

	public void renderResponse(IRequestCycle cycle) throws IOException {
		// 得到当前待渲染的页面
		IPage pdfPage = cycle.getPage();
		// 默认始终为PDF效果展示.
		OutputStream os = response.getOutputStream(IPdfPage.CONTENT_TYPE);
		// 构造一个PDF文档
		_doc = new Document();

		// 申明PDF Writer
		PdfWriter pdfWriter = null;
		try {

			// 构造PDF writer.
			pdfWriter = PdfWriter.getInstance(_doc, os);

			if (pdfPage instanceof IPdfPage) { // 加入是PDF Page页面.
				// 建立解析事件
				PdfOutputPageEvent event = new PdfOutputPageEvent(
						(IPdfPage) pdfPage);
				pdfWriter.setPageEvent(event);
				// 构造Pdf writer的代理类.
				_writer = new PdfWriterDelegate(pdfWriter);
				// 打开pdf文档
				_doc.open();

				// 设定全局的PDF Writer变量.
				cycle.setAttribute(PDF_DOCUMENT_ATTRIBUTE_NAME, _doc);

				// 渲染PDF页面
				cycle.renderPage(this);

				// 删除设定的PDF变量.
				cycle.removeAttribute(PDF_DOCUMENT_ATTRIBUTE_NAME);

				// 关闭PDF文档.
				_doc.close();

			} else if (pdfPage instanceof org.apache.tapestry.pages.Exception) { // 处理异常页面.

				ExceptionDescription[] exceptions = (ExceptionDescription[]) PropertyUtils
						.read(pdfPage, "exceptions");
				if (exceptions == null) {
					throw new ApplicationRuntimeException("UNKOWN Exception!");

				} else { // 组合异常页面的消息.
					StringBuffer sb = new StringBuffer();
					for (int i = 0; i < exceptions.length; i++) {
						sb.append(exceptions[i].getMessage()).append("\n");
						ExceptionProperty[] pros = exceptions[i]
								.getProperties();
						for (ExceptionProperty pro : pros) {
							sb.append(pro.getName()).append(" ").append(
									pro.getValue()).append("\n");
						}
						String[] traces = exceptions[i].getStackTrace();
						if (traces == null) {
							continue;
						}
						for (int j = 0; j < traces.length; j++) {
							sb.append(traces[j]).append("\n");
						}
					}
					throw new Exception(sb.toString());
				}
			}
		} catch (Throwable e) { // 对产生的异常进行处理.
			if (pdfWriter == null) {
				e.printStackTrace();
				return;
			}
			if (!_doc.isOpen()) {
				_doc.open();
			}
			pdfWriter.setPageEvent(null);
			
			_doc.newPage();
			

			// 在PDF上把异常消息打印出来，SO COOL! :)
			try {
				_doc.add(new Phrase("请求pdf发生异常:", PdfUtils
						.createSongLightFont(12)));
				_doc.add(new Paragraph(e.getMessage()));
				_doc.add(new Paragraph(fetchStackTrace(e)));
				_doc.close();
			} catch (DocumentException e1) {
				throw new ApplicationRuntimeException(e1);
			}

		}
	}
	//收集异常消息
	String fetchStackTrace(Throwable e){
		//		 收集异常的堆栈消息
		CharArrayWriter writer = new CharArrayWriter();
		
		 PrintWriter printWriter = new PrintWriter(writer);
		e.printStackTrace(printWriter);
		return writer.toString();
	}
	/**
	 * 
	 * {@inheritDoc}
	 */
	public void render(IMarkupWriter writer, IRender render, IRequestCycle cycle) {
		if(writer==null){
			writer=_writer;
		}
		if (render instanceof IPdfComponent ) {// 当且仅当只有pdf组件进行渲染
			//如果不是模板页面
			if( !((IPdfComponent) render).isTemplateFragment()){
				
				render.render(writer, cycle);
			}
		}
	}

	// ================ 以下代码完全是来自ResponseBuilder接口的实现，无任何意义.
	public void beginBodyScript(IMarkupWriter writer, IRequestCycle cycle) {
		// do nothing

	}

	public boolean contains(IComponent target) {
		// do nothing
		return false;
	}

	public void endBodyScript(IMarkupWriter writer, IRequestCycle cycle) {
		// do nothing

	}

	public boolean explicitlyContains(IComponent target) {
		// do nothing
		return false;
	}

	public IMarkupWriter getWriter() {
		// do nothing
		return null;
	}

	public IMarkupWriter getWriter(String id, String type) {
		// do nothing
		return null;
	}

	public boolean isBodyScriptAllowed(IComponent target) {
		// do nothing
		return false;
	}

	public boolean isDynamic() {
		// do nothing
		return false;
	}

	public boolean isExternalScriptAllowed(IComponent target) {
		// do nothing
		return false;
	}

	public boolean isImageInitializationAllowed(IComponent target) {
		// do nothing
		return false;
	}

	public boolean isInitializationScriptAllowed(IComponent target) {
		// do nothing
		return false;
	}

	public void updateComponent(String id) {
		// do nothing

	}

	public void writeBodyScript(IMarkupWriter writer, IRequestCycle cycle) {
		// do nothing

	}

	public void writeBodyScript(IMarkupWriter writer, String script,
			IRequestCycle cycle) {
		// do nothing

	}

	public void writeExternalScript(IMarkupWriter writer, String url,
			IRequestCycle cycle) {
		// do nothing

	}

	public void writeImageInitializations(IMarkupWriter writer, String script,
			String preloadName, IRequestCycle cycle) {
		// do nothing

	}

	public void writeInitializationScript(IMarkupWriter writer) {
		// do nothing

	}

	public void writeInitializationScript(IMarkupWriter writer, String script) {
		// do nothing

	}

	public String getPreloadedImageReference(String url) {
		// do nothing
		return null;
	}

	public String getPreloadedImageReference(IComponent target, IAsset source) {
		// do nothing
		return null;
	}

	public String getPreloadedImageReference(IComponent target, String url) {
		// do nothing
		return null;
	}

	public void addBodyScript(String script) {
		// do nothing

	}

	public void addBodyScript(IComponent target, String script) {
		// do nothing

	}

	public void addExternalScript(Resource resource) {
		// do nothing

	}

	public void addExternalScript(IComponent target, Resource resource) {
		// do nothing

	}

	public void addInitializationScript(String script) {
		// do nothing

	}

	public void addInitializationScript(IComponent target, String script) {
		// do nothing

	}

	public String getUniqueString(String baseValue) {
		// do nothing
		return null;
	}

	public void addStatusMessage(IMarkupWriter writer, String category, String text) {
		// TODO Auto-generated method stub
		
	}

	public void flush() throws IOException {
		// TODO Auto-generated method stub
		
	}

	public void addScriptAfterInitialization(IComponent arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

}
