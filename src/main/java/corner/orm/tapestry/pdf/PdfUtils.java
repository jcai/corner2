/*		
 * Copyright 2007 The Beijing Maxinfo Technology Ltd. 
 * site: http://www.bjmaxinfo.com
 * file: $Id$
 * created at:2007-04-10
 */

package corner.orm.tapestry.pdf;

import java.io.IOException;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;

/**
 * 在pdf创建时候常用的方法
 * 
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 2.3.7
 */
public final class PdfUtils {

	/**
	 * 创建默认基础字体
	 * @return
	 */
	public static final BaseFont createSongLightBaseFont() {
		BaseFont bfChinese;
		try {
			bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
					BaseFont.EMBEDDED);
			return bfChinese;
		} catch (DocumentException e) {
			throw new PdfSystemException(e);
		} catch (IOException e) {
			throw new PdfSystemException(e);
		}

	}
	/**
	 * 创建宋体字体.
	 * @param size 大小
	 * @return
	 */
	public static final Font createSongLightFont(int size){
		BaseFont bf=createSongLightBaseFont();
		if(size<=0){
			size=Font.UNDEFINED;
		}
		Font font=new Font(bf,size);
		return font;
		
	}
	
	/**
	 * 创建宋体标题字(在宋体上加粗)
	 * @param size
	 * @return
	 */
	public static final Font createHeaderSongLightFont(int size){
		BaseFont bf=createSongLightBaseFont();
		if(size<=0){
			size=Font.UNDEFINED;
		}
		Font font=new Font(bf,size,Font.BOLD);
		return font;
	}
	
}
