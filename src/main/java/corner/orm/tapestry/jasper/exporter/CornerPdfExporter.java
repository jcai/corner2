// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2007-09-29
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

package corner.orm.tapestry.jasper.exporter;

import java.awt.Color;
import java.awt.font.TextAttribute;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRAlignment;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRFont;
import net.sf.jasperreports.engine.JRPrintText;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JRTextElement;
import net.sf.jasperreports.engine.base.JRBaseFont;
import net.sf.jasperreports.engine.export.FontKey;
import net.sf.jasperreports.engine.export.JRExportProgressMonitor;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.PdfFont;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRStyledText;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.TextField;

import corner.orm.tapestry.jasper.IJasperMoveXY;
import corner.orm.tapestry.pdf.PdfUtils;

/**
 * 替换Pdf中ColumnText为TextField的exporter
 * <p>
 * jasperreport中，导出Pdf文件的时候，默认使用的是ColumnText显示文本,
 * 使用这个exporter可以替换ColumnText为TextField
 * 
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 0.8.5.1
 */
public class CornerPdfExporter extends JRPdfExporter {

	public static final String FIELD_NAME_PREFIX = "FIELD";
	private int i = 0;
	private List<String> alreadyExistFields = new ArrayList<String>();
	private Map fontMap = null;
	private IJasperMoveXY jasperMoveXY = null;//文本的偏移
	
	public void setJasperMoveXY(IJasperMoveXY jasperMoveXY){
		this.jasperMoveXY = jasperMoveXY;
	}
	
	public IJasperMoveXY getJasperMoveXY(){
		return this.jasperMoveXY;
	}
	
	String createUniqueName() {
		i += 1;
		return FIELD_NAME_PREFIX + i;
	}

	/**
	 * 从父类里拷贝过来.用于getFont的重构.
	 * @see net.sf.jasperreports.engine.export.JRPdfExporter#exportReport()
	 */
	public void exportReport() throws JRException
	{
		registerFonts();

		progressMonitor = (JRExportProgressMonitor)parameters.get(JRExporterParameter.PROGRESS_MONITOR);

		/*   */
		setOffset();

		try
		{
			/*   */
			setExportContext();

			/*   */
			setInput();

			/*   */
			if (!isModeBatch)
			{
				setPageRange();
			}

			isCreatingBatchModeBookmarks = 
				getBooleanParameter(
					JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS,
					JRPdfExporterParameter.PROPERTY_CREATE_BATCH_MODE_BOOKMARKS,
					false
					);

			forceSvgShapes = 
				getBooleanParameter(
					JRPdfExporterParameter.FORCE_SVG_SHAPES,
					JRPdfExporterParameter.PROPERTY_FORCE_SVG_SHAPES,
					false
					);

			isCompressed = 
				getBooleanParameter(
					JRPdfExporterParameter.IS_COMPRESSED,
					JRPdfExporterParameter.PROPERTY_COMPRESSED,
					false
					);

			isEncrypted = 
				getBooleanParameter(
					JRPdfExporterParameter.IS_ENCRYPTED,
					JRPdfExporterParameter.PROPERTY_ENCRYPTED,
					false
					);

			is128BitKey = 
				getBooleanParameter(
					JRPdfExporterParameter.IS_128_BIT_KEY,
					JRPdfExporterParameter.PROPERTY_128_BIT_KEY,
					false
					);

			userPassword = 
				getStringParameter(
					JRPdfExporterParameter.USER_PASSWORD,
					JRPdfExporterParameter.PROPERTY_USER_PASSWORD
					);

			ownerPassword = 
				getStringParameter(
					JRPdfExporterParameter.OWNER_PASSWORD,
					JRPdfExporterParameter.PROPERTY_OWNER_PASSWORD
					);

			Integer permissionsParameter = (Integer)parameters.get(JRPdfExporterParameter.PERMISSIONS);
			if (permissionsParameter != null)
			{
				permissions = permissionsParameter.intValue();
			}

			String strPdfVersion = 
				getStringParameter(
					JRPdfExporterParameter.PDF_VERSION,
					JRPdfExporterParameter.PROPERTY_PDF_VERSION
					);
			pdfVersion = 
				(strPdfVersion == null || strPdfVersion.length() == 0) 
				? null 
				: new Character(strPdfVersion.charAt(0));

			fontMap = (Map) parameters.get(JRExporterParameter.FONT_MAP);

			setSplitCharacter();
			setHyperlinkProducerFactory();

			pdfJavaScript = 
				getStringParameter(
					JRPdfExporterParameter.PDF_JAVASCRIPT,
					JRPdfExporterParameter.PROPERTY_PDF_JAVASCRIPT
					);

			OutputStream os = (OutputStream)parameters.get(JRExporterParameter.OUTPUT_STREAM);
			if (os != null)
			{
				exportReportToStream(os);
			}
			else
			{
				File destFile = (File)parameters.get(JRExporterParameter.OUTPUT_FILE);
				if (destFile == null)
				{
					String fileName = (String)parameters.get(JRExporterParameter.OUTPUT_FILE_NAME);
					if (fileName != null)
					{
						destFile = new File(fileName);
					}
					else
					{
						throw new JRException("No output specified for the exporter.");
					}
				}

				try
				{
					os = new FileOutputStream(destFile);
					exportReportToStream(os);
					os.flush();
				}
				catch (IOException e)
				{
					throw new JRException("Error trying to export to file : " + destFile, e);
				}
				finally
				{
					if (os != null)
					{
						try
						{
							os.close();
						}
						catch(IOException e)
						{
						}
					}
				}
			}
		}
		finally
		{
			resetExportContext();
		}
	}
	
	/**
	 * 重写了此方法.提供判断文字是否溢出从而自动缩小字体的功能.
	 * <p>判断方法:用ColumnText模拟录入下数据,
	 * 若返回状态为NO_MORE_COLUMN即数据溢出没有更多的行放数据,此时用TextField来填充数据.
	 * TextField有自动缩小字体的功能.
	 * @see net.sf.jasperreports.engine.export.JRPdfExporter#exportText(net.sf.jasperreports.engine.JRPrintText)
	 */
	@Override
	protected void exportText(JRPrintText text) throws DocumentException {

		JRStyledText styledText = getStyledText(text, false);

		if (styledText == null) {
			return;
		}

		int textLength = styledText.length();

		int x = text.getX() + getOffsetX();
		int y = text.getY() + getOffsetY();
		int width = text.getWidth();
		int height = text.getHeight();
		int topPadding = text.getTopPadding();
		int leftPadding = text.getLeftPadding();
		int bottomPadding = text.getBottomPadding();
		int rightPadding = text.getRightPadding();

		int xFillCorrection = 0;
		int yFillCorrection = 0;

		double angle = 0;

		switch (text.getRotation()) {
		case JRTextElement.ROTATION_LEFT: {
			y = text.getY() + getOffsetY() + text.getHeight();
			xFillCorrection = 1;
			width = text.getHeight();
			height = text.getWidth();
			int tmpPadding = topPadding;
			topPadding = leftPadding;
			leftPadding = bottomPadding;
			bottomPadding = rightPadding;
			rightPadding = tmpPadding;
			angle = Math.PI / 2;
			break;
		}
		case JRTextElement.ROTATION_RIGHT: {
			x = text.getX() + getOffsetX() + text.getWidth();
			yFillCorrection = -1;
			width = text.getHeight();
			height = text.getWidth();
			int tmpPadding = topPadding;
			topPadding = rightPadding;
			rightPadding = bottomPadding;
			bottomPadding = leftPadding;
			leftPadding = tmpPadding;
			angle = -Math.PI / 2;
			break;
		}
		case JRTextElement.ROTATION_UPSIDE_DOWN: {
			x = text.getX() + getOffsetX() + text.getWidth();
			y = text.getY() + getOffsetY() + text.getHeight();
			int tmpPadding = topPadding;
			topPadding = bottomPadding;
			bottomPadding = tmpPadding;
			tmpPadding = leftPadding;
			leftPadding = rightPadding;
			rightPadding = tmpPadding;
			angle = Math.PI;
			break;
		}
		case JRTextElement.ROTATION_NONE:
		default: {
		}
		}

		AffineTransform atrans = new AffineTransform();
		atrans.rotate(angle, x, jasperPrint.getPageHeight() - y);
		pdfContentByte.transform(atrans);

		if (text.getMode() == JRElement.MODE_OPAQUE) {
			Color backcolor = text.getBackcolor();
			pdfContentByte.setRGBColorStroke(backcolor.getRed(), backcolor
					.getGreen(), backcolor.getBlue());
			pdfContentByte.setRGBColorFill(backcolor.getRed(), backcolor
					.getGreen(), backcolor.getBlue());
			pdfContentByte.setLineWidth(1f);
			pdfContentByte.setLineDash(0f);
			pdfContentByte.rectangle(x + xFillCorrection, jasperPrint
					.getPageHeight()
					- y + yFillCorrection, width - 1, -height + 1);
			pdfContentByte.fillStroke();
		} else {
			/*
			 * pdfContentByte.setRGBColorStroke( text.getForecolor().getRed(),
			 * text.getForecolor().getGreen(), text.getForecolor().getBlue() );
			 * pdfContentByte.setLineWidth(0.1f);
			 * pdfContentByte.setLineDash(0f); pdfContentByte.rectangle(
			 * text.getX() + offsetX, jasperPrint.getPageHeight() - text.getY() -
			 * offsetY, text.getWidth(), - text.getHeight() );
			 * pdfContentByte.stroke();
			 */
		}

		if (textLength > 0) {
			int horizontalAlignment = Element.ALIGN_LEFT;
			switch (text.getHorizontalAlignment()) {
			case JRAlignment.HORIZONTAL_ALIGN_LEFT: {
				if (text.getRunDirection() == JRPrintText.RUN_DIRECTION_LTR) {
					horizontalAlignment = Element.ALIGN_LEFT;
				} else {
					horizontalAlignment = Element.ALIGN_RIGHT;
				}
				break;
			}
			case JRAlignment.HORIZONTAL_ALIGN_CENTER: {
				horizontalAlignment = Element.ALIGN_CENTER;
				break;
			}
			case JRAlignment.HORIZONTAL_ALIGN_RIGHT: {
				if (text.getRunDirection() == JRPrintText.RUN_DIRECTION_LTR) {
					horizontalAlignment = Element.ALIGN_RIGHT;
				} else {
					horizontalAlignment = Element.ALIGN_LEFT;
				}
				break;
			}
			case JRAlignment.HORIZONTAL_ALIGN_JUSTIFIED: {
				horizontalAlignment = Element.ALIGN_JUSTIFIED;
				break;
			}
			default: {
				horizontalAlignment = Element.ALIGN_LEFT;
			}
			}

			float verticalOffset = 0f;
			switch (text.getVerticalAlignment()) {
			case JRAlignment.VERTICAL_ALIGN_TOP: {
				verticalOffset = 0f;
				break;
			}
			case JRAlignment.VERTICAL_ALIGN_MIDDLE: {
				verticalOffset = (height - topPadding - bottomPadding - text
						.getTextHeight()) / 2f;
				break;
			}
			case JRAlignment.VERTICAL_ALIGN_BOTTOM: {
				verticalOffset = height - topPadding - bottomPadding
						- text.getTextHeight();
				break;
			}
			default: {
				verticalOffset = 0f;
			}
			}
            
			float llx = x + leftPadding;
			float lly = jasperPrint.getPageHeight() - y - topPadding
					- verticalOffset - text.getLeadingOffset();
			float urx = x + width - rightPadding;
			float ury = jasperPrint.getPageHeight() - y - height
					+ bottomPadding;
			
			//需要移动文本,则重新构造坐标
			if(this.jasperMoveXY != null){
				 llx = x + leftPadding+jasperMoveXY.getX();
				 lly = jasperPrint.getPageHeight() - y - topPadding
						- verticalOffset - text.getLeadingOffset()-jasperMoveXY.getY();
				 urx = x + width - rightPadding+jasperMoveXY.getX();
				 ury = jasperPrint.getPageHeight() - y - height
						+ bottomPadding-jasperMoveXY.getY();
			}
			
			boolean isOver = false;
			int status = ColumnText.START_COLUMN;
			Phrase phrase = getPhrase(styledText, text);

			ColumnText colText = new ColumnText(pdfContentByte);
			colText.setSimpleColumn(phrase, llx, lly, urx, ury, 0,// text.getLineSpacingFactor(),//
																	// *
																	// text.getFont().getSize(),
					horizontalAlignment);

			colText.setLeading(0, text.getLineSpacingFactor());// *
																// text.getFont().getSize());
			colText
					.setRunDirection(text.getRunDirection() == JRPrintText.RUN_DIRECTION_LTR ? PdfWriter.RUN_DIRECTION_LTR
							: PdfWriter.RUN_DIRECTION_RTL);

			float yLine = colText.getYLine();
			
			// 模拟将ColumnText录入
			while (colText.hasMoreText(status)) {
				status = colText.go(true);
				colText.setYLine(yLine);

				// 如果没有更多的行可以放置文本数据,则跳出循环并设置溢出标记为true
				if (status == ColumnText.NO_MORE_COLUMN) {
					isOver = true;
					break;
				}
			}

			// 如果没有溢出,则将ColumnText写入
			if (!isOver) {
				colText.setText(phrase);
				status = ColumnText.START_COLUMN;
				
				while (colText.hasMoreText(status)) {
					status = colText.go();
					colText.setYLine(yLine);
				}

			} else {
				// 溢出了则用TextField,它来负责自动缩小字体
				String key = text.getKey();// PdfText的唯一id
				if (alreadyExistFields.contains(key)) { // 已经有了
					key = createUniqueName();
				}
				alreadyExistFields.add(key);
				TextField tf = new TextField(pdfContentByte.getPdfWriter(),
						new Rectangle(llx, lly, urx, ury), key);
				tf.setAlignment(horizontalAlignment);
				tf.setText(text.getText());
				tf.setFont(PdfUtils.createSongLightBaseFont());
				
				//标记 如果是styledText 则自动缩小单行,否则为多行
				if(!text.isStyledText()){
					tf.setOptions(TextField.MULTILINE);
				}
				
				try {
					pdfContentByte.getPdfWriter().addAnnotation(
							tf.getTextField());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		atrans = new AffineTransform();
		atrans.rotate(-angle, x, jasperPrint.getPageHeight() - y);
		pdfContentByte.transform(atrans);

		/*   */
		exportBox(text, text);
	}

	/**
	 * 重写此方法,使字体能够加粗,斜体.
	 * @see net.sf.jasperreports.engine.export.JRPdfExporter#getFont(java.util.Map)
	 */
	protected Font getFont(Map attributes)
	{
		JRFont jrFont = new JRBaseFont(attributes);

		Exception initialException = null;

		Color forecolor = (Color)attributes.get(TextAttribute.FOREGROUND);
		/*
		if (forecolor == null)
		{
			forecolor = Color.black;
		}
		*/

		Font font = null;
		PdfFont pdfFont = null;
		FontKey key = new FontKey(jrFont.getFontName(), jrFont.isBold(), jrFont.isItalic());

		if (fontMap != null && fontMap.containsKey(key))
		{
			pdfFont = (PdfFont) fontMap.get(key);
		}
		else
		{
			pdfFont = new PdfFont(jrFont.getPdfFontName(), jrFont.getPdfEncoding(), jrFont.isPdfEmbedded(),jrFont.isBold(),jrFont.isItalic());
		}

		try
		{
			font = FontFactory.getFont(
				pdfFont.getPdfFontName(),
				pdfFont.getPdfEncoding(),
				pdfFont.isPdfEmbedded(),
				jrFont.getFontSize(),
				(pdfFont.isPdfSimulatedBold() ? Font.BOLD : 0)
					| (pdfFont.isPdfSimulatedItalic() ? Font.ITALIC : 0)
					| (jrFont.isUnderline() ? Font.UNDERLINE : 0)
					| (jrFont.isStrikeThrough() ? Font.STRIKETHRU : 0),
				forecolor
				);

			// check if FontFactory didn't find the font
			if (font.getBaseFont() == null && font.family() == Font.UNDEFINED)
			{
				font = null;
			}
		}
		catch(Exception e)
		{
			initialException = e;
		}

		if (font == null)
		{
			byte[] bytes = null;

			try
			{
				bytes = JRLoader.loadBytesFromLocation(pdfFont.getPdfFontName(), classLoader, urlHandlerFactory);
			}
			catch(JRException e)
			{
				throw
					new JRRuntimeException(
						"Could not load the following font : "
						+ "\npdfFontName   : " + pdfFont.getPdfFontName()
						+ "\npdfEncoding   : " + pdfFont.getPdfEncoding()
						+ "\nisPdfEmbedded : " + pdfFont.isPdfEmbedded(),
						initialException
						);
			}

			BaseFont baseFont = null;

			try
			{
				baseFont =
					BaseFont.createFont(
						pdfFont.getPdfFontName(),
						pdfFont.getPdfEncoding(),
						pdfFont.isPdfEmbedded(),
						true,
						bytes,
						null
						);
			}
			catch(DocumentException e)
			{
				throw new JRRuntimeException(e);
			}
			catch(IOException e)
			{
				throw new JRRuntimeException(e);
			}

			font =
				new Font(
					baseFont,
					jrFont.getFontSize(),
					((pdfFont.isPdfSimulatedBold()) ? Font.BOLD : 0)
						| ((pdfFont.isPdfSimulatedItalic()) ? Font.ITALIC : 0)
						| (jrFont.isUnderline() ? Font.UNDERLINE : 0)
						| (jrFont.isStrikeThrough() ? Font.STRIKETHRU : 0),
					forecolor
					);
		}

		return font;
	}
}
