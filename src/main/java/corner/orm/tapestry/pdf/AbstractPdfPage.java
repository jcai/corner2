/*		
 * Copyright 2007 The Beijing Maxinfo Technology Ltd. 
 * site: http://www.bjmaxinfo.com
 * file: $Id: AbstractPdfPage.java 7039 2007-07-09 07:44:26Z jcai $
 * created at:2007-04-09
 */

package corner.orm.tapestry.pdf;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.tapestry.AbstractPage;
import org.apache.tapestry.util.ContentType;

import com.lowagie.text.Document;

import corner.orm.tapestry.component.checkbox.StringCheckbox;
import corner.util.Constants;

/**
 * 抽象的PDF页面类
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 2.3.7
 */
public abstract class AbstractPdfPage extends AbstractPage implements IPdfPage{

	/**
	 * 将传入的日期d格式化成'yyyy-MM-dd'的格式
	 * <p> 注意，此方法在参数d为空的时候返回空字符串
	 * @param d
	 * @return 'yyyy-MM-dd'格式的{@link String}类型日期
	 */
	public String getFormatDate(Date d) {
		if(d == null){
			return "";//返回空字符串
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(d); 
	}
	
	/**
	 * 将传入的日期d格式化成format参数指定的格式的格式
	 * <p> 注意，此方法在参数d为空的时候返回空字符串,当format为null的时候为'yyyy-MM-dd'格式
	 * 
	 * @param d
	 * @param format 
	 * @return format参数指定格式的{@link String}类型日期
	 */
	public String getFormatDate(Date d, Format format){
		if(d == null){
			return "";//返回空字符串
		}
		if(format == null){
			format = new SimpleDateFormat("yyyy-MM-dd");
		}
		
		return format.format(d);
	}

	/**
	 * 通过给定的日期格式化字符串,来得到一个格式化对象.
	 *
	 * @param formatStr
	 *            格式化模式.
	 * @return 格式化对象.
	 */
	public Format getDateFormat(String formatStr) {
		return new SimpleDateFormat(formatStr);
	}
	
	/**
	 * 默认的ContentType为application/pdf
	 * @see org.apache.tapestry.IPage#getResponseContentType()
	 */
	public ContentType getResponseContentType() {
		return CONTENT_TYPE;
	}
	
	/**
	 * 针对{@link StringCheckbox}的回显方法
	 * "0":否 "1":是
	 * @param s
	 * @return {@link String}
	 */
	public static final String displayCheckbox(String s){
		if(s == null || s.trim().length() == 0){
			return "";//返回空字符串
		} else{
			if(Constants.PDF_PAGE_STRING_CHECKBOX_TRUE_FLAG.equals(s)){
				return Constants.PDF_PAGE_CHECKBOX_TRUE_STR;
			} else{
				return Constants.PDF_PAGE_CHECKBOX_FALSE_STR;
			}
		}
	}
	
	/**
	 * 针对{@link org.apache.tapestry.form.Checkbox}的回显方法
	 * true:是 false:否
	 * @param b
	 * @return {@link String}
	 */
	public static final String displayCheckbox(boolean b){
		if(Constants.PDF_PAGE_BOOLEAN_CHECKBOX_TRUE_FLAG){
			return Constants.PDF_PAGE_CHECKBOX_TRUE_STR;
		} else{
			return Constants.PDF_PAGE_CHECKBOX_FALSE_STR;
		}
	}

	/**
	 * 逐一的渲染组件.
	 * @see corner.orm.tapestry.pdf.IPdfComponent#renderPdf(corner.orm.tapestry.pdf.PdfWriterDelegate, com.lowagie.text.Document)
	 */
	@SuppressWarnings("unchecked")
	public void renderPdf(PdfWriterDelegate writer,Document doc){
		
		//do nothing 能够用来打开pdf之用.
	}
}
