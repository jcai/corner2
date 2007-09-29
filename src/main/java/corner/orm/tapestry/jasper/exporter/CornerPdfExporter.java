/*		
 * Copyright 2007 The Beijing Maxinfo Technology Ltd. 
 * site:http://www.bjmaxinfo.com
 * file : $Id$
 * created at:2007-9-28
 */




package corner.orm.tapestry.jasper.exporter;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.io.IOException;

import net.sf.jasperreports.engine.JRAlignment;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JRPrintText;
import net.sf.jasperreports.engine.JRTextElement;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRStyledText;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.TextField;

import corner.orm.tapestry.pdf.PdfUtils;

/**
 * 替换Pdf中ColumnText为TextField的exporter
 * <p>jasperreport中，导出Pdf文件的时候，默认使用的是ColumnText显示文本,
 * 使用这个exporter可以替换ColumnText为TextField
 * 
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 0.8.5.1
 */
public class CornerPdfExporter extends JRPdfExporter {

	/**
	 * @see net.sf.jasperreports.engine.export.JRPdfExporter#exportText(net.sf.jasperreports.engine.JRPrintText)
	 */
	@Override
	protected void exportText(JRPrintText text) throws DocumentException {
		JRStyledText styledText = getStyledText(text, false);

		if (styledText == null)
		{
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

		switch (text.getRotation())
		{
			case JRTextElement.ROTATION_LEFT :
			{
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
			case JRTextElement.ROTATION_RIGHT :
			{
				x = text.getX() + getOffsetX() + text.getWidth();
				yFillCorrection = -1;
				width = text.getHeight();
				height = text.getWidth();
				int tmpPadding = topPadding;
				topPadding = rightPadding;
				rightPadding = bottomPadding;
				bottomPadding = leftPadding;
				leftPadding = tmpPadding;
				angle = - Math.PI / 2;
				break;
			}
			case JRTextElement.ROTATION_UPSIDE_DOWN :
			{
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
			case JRTextElement.ROTATION_NONE :
			default :
			{
			}
		}

		AffineTransform atrans = new AffineTransform();
		atrans.rotate(angle, x, jasperPrint.getPageHeight() - y);
		pdfContentByte.transform(atrans);

		if (text.getMode() == JRElement.MODE_OPAQUE)
		{
			Color backcolor = text.getBackcolor();
			pdfContentByte.setRGBColorStroke(
				backcolor.getRed(),
				backcolor.getGreen(),
				backcolor.getBlue()
				);
			pdfContentByte.setRGBColorFill(
				backcolor.getRed(),
				backcolor.getGreen(),
				backcolor.getBlue()
				);
			pdfContentByte.setLineWidth(1f);
			pdfContentByte.setLineDash(0f);
			pdfContentByte.rectangle(
				x + xFillCorrection,
				jasperPrint.getPageHeight() - y + yFillCorrection,
				width - 1,
				- height + 1
				);
			pdfContentByte.fillStroke();
		}
		else
		{
			/*
			pdfContentByte.setRGBColorStroke(
				text.getForecolor().getRed(),
				text.getForecolor().getGreen(),
				text.getForecolor().getBlue()
				);
			pdfContentByte.setLineWidth(0.1f);
			pdfContentByte.setLineDash(0f);
			pdfContentByte.rectangle(
				text.getX() + offsetX,
				jasperPrint.getPageHeight() - text.getY() - offsetY,
				text.getWidth(),
				- text.getHeight()
				);
			pdfContentByte.stroke();
			*/
		}

		if (textLength > 0)
		{
			int horizontalAlignment = Element.ALIGN_LEFT;
			switch (text.getHorizontalAlignment())
			{
				case JRAlignment.HORIZONTAL_ALIGN_LEFT :
				{
					if (text.getRunDirection() == JRPrintText.RUN_DIRECTION_LTR)
					{
						horizontalAlignment = Element.ALIGN_LEFT;
					}
					else
					{
						horizontalAlignment = Element.ALIGN_RIGHT;
					}
					break;
				}
				case JRAlignment.HORIZONTAL_ALIGN_CENTER :
				{
					horizontalAlignment = Element.ALIGN_CENTER;
					break;
				}
				case JRAlignment.HORIZONTAL_ALIGN_RIGHT :
				{
					if (text.getRunDirection() == JRPrintText.RUN_DIRECTION_LTR)
					{
						horizontalAlignment = Element.ALIGN_RIGHT;
					}
					else
					{
						horizontalAlignment = Element.ALIGN_LEFT;
					}
					break;
				}
				case JRAlignment.HORIZONTAL_ALIGN_JUSTIFIED :
				{
					horizontalAlignment = Element.ALIGN_JUSTIFIED;
					break;
				}
				default :
				{
					horizontalAlignment = Element.ALIGN_LEFT;
				}
			}

			float verticalOffset = 0f;
			switch (text.getVerticalAlignment())
			{
				case JRAlignment.VERTICAL_ALIGN_TOP :
				{
					verticalOffset = 0f;
					break;
				}
				case JRAlignment.VERTICAL_ALIGN_MIDDLE :
				{
					verticalOffset = (height - topPadding - bottomPadding - text.getTextHeight()) / 2f;
					break;
				}
				case JRAlignment.VERTICAL_ALIGN_BOTTOM :
				{
					verticalOffset = height - topPadding - bottomPadding - text.getTextHeight();
					break;
				}
				default :
				{
					verticalOffset = 0f;
				}
			}

			Rectangle r = new Rectangle(
					x + leftPadding,
					jasperPrint.getPageHeight()- y- topPadding- verticalOffset- text.getLeadingOffset(),
					x + width - rightPadding,
					jasperPrint.getPageHeight()- y- height+ bottomPadding
				);
			TextField tf = new TextField(pdfContentByte.getPdfWriter(),r,text.getKey());
			tf.setAlignment(horizontalAlignment);
			tf.setText(text.getText());
			tf.setFont(PdfUtils.createSongLightBaseFont());
			tf.setOptions(TextField.MULTILINE);
			try {
				pdfContentByte.getPdfWriter().addAnnotation(tf.getTextField());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		atrans = new AffineTransform();
		atrans.rotate(-angle, x, jasperPrint.getPageHeight() - y);
		pdfContentByte.transform(atrans);

		/*   */
		exportBox(
			text,
			text
			);
	}

}
