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
	@InjectObject("service:corner.pdf.FieldCreator")
	public abstract IFieldCreator getFieldCreator();
}
