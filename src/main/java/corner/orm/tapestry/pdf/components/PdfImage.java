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

package corner.orm.tapestry.pdf.components;

import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.hivemind.util.Defense;
import org.apache.tapestry.annotations.Parameter;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;

import corner.orm.exception.CornerSystemException;
import corner.orm.tapestry.pdf.PdfWriterDelegate;
import corner.util.Constants;

/**
 * 提供一个Pdf上显示图片的组件
 * 
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 2.3.7
 */
public abstract class PdfImage extends AbstractPdfComponent {

	/**
	 * 取得Image图片的byte[]
	 * 
	 */
	@Parameter(required = true)
	public abstract byte[] getValue();

	/**
	 * 判断图片是否自动适应PDF模版上的矩形区域 true:自适应 false:不自适应
	 * 
	 * @return 一个boolean值，根据这个值判断是否让图片自适应
	 */
	@Parameter(defaultValue = "true")
	public abstract boolean getAutoSize();

	/**
	 * 指定图片缩放类型
	 * 
	 * @return 返回一个String类型的变量 percent 或 border,用以判断是使用百分比缩放还是按照矩形边框缩放
	 */
	@Parameter(defaultValue = "literal:percent")
	public abstract String getScaleType();

	/**
	 * @see corner.orm.tapestry.pdf.components.AbstractPdfComponent#renderPdf(corner.orm.tapestry.pdf.PdfWriterDelegate, com.lowagie.text.Document)
	 */
	@Override
	public void renderPdf(PdfWriterDelegate writer, Document doc) {
		Defense.notNull(getRectangle(), "TextField的位置");
		String[] p = getRectangle().split(",");
		try {
			Image image = Image.getInstance(getValue());
			image.setAbsolutePosition(Float.valueOf(p[0]), Float.valueOf(p[1]));// 设置图片出现的位置
			if (getAutoSize()) {
				if (Constants.PDFIMAGE_COMPONENT_SCALE_TYPE_BORDER
						.equals(getScaleType())) {
					image.scaleAbsolute((Float.valueOf(p[2]) - Float
							.valueOf(p[0])), (Float.valueOf(p[3]) - Float
							.valueOf(p[1])));// 设置图片的大小在矩形内部
				} else {
					float picHeight = image.plainHeight();// 图片高
					float picWeight = image.plainWidth();// 图片宽
					float borderHeight = Float.valueOf(p[3])
							- Float.valueOf(p[1]);// 矩形高
					float borderWeight = Float.valueOf(p[2])
							- Float.valueOf(p[0]);// 矩形宽

					float heightPer = borderHeight / picHeight * 100;
					float widthPer = borderWeight / picWeight * 100;

					// 选择最小的百分比，保证整个图片可以完整显示
					if (heightPer > widthPer) {
						image.scalePercent(widthPer);
					} else {
						image.scalePercent(heightPer);
					}
				}
			}
			doc.add(image);
		} catch (BadElementException e) {
			throw new CornerSystemException(e);
		} catch (MalformedURLException e) {
			throw new CornerSystemException(e);
		} catch (IOException e) {
			throw new CornerSystemException(e);
		} catch (DocumentException e) {
			throw new CornerSystemException(e);
		}
	}
}
