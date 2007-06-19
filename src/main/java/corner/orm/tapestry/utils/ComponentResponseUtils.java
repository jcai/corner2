/*		
 * Copyright 2006-2007 The Beijing Maxinfo Technology Ltd. 
 * site:http://www.bjmaxinfo.com
 * file : $Id$
 * created at:2007-6-13
 */

package corner.orm.tapestry.utils;

import java.io.IOException;
import java.util.Calendar;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.net.URLCodec;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.web.WebResponse;

/**
 * 提供corner中自定义组件的一些通用的工具方法
 * 
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 0.8.5.1
 */
public class ComponentResponseUtils {
	
	private static final Log log=LogFactory.getLog(ComponentResponseUtils.class);
	/**
	 * 生成Excel时候，指定数据的生成类型
	 * 
	 * full:当前查询的全部数据 page:当前查询，当前页面显示的数据
	 */
	public static final String EXCEL_DATA_GENERATE_TYPE_FULL = "full";// 全部数据

	public static final String EXCEL_DATA_GENERATE_TYPE_PAGE = "page";// 当前页面的数据

	/** 附件的定义 * */
	private final static String ATTACHEMENT_FILE = "attachment; filename=\"%s\";";

	/** firefox 下文件采用base64进行编码 * */
	private final static String MOZILLA_DOWNLOAD_FILE_NAME = "=?UTF-8?B?%s?=";

	// 对下载的文件名进行encode，
	// 参考：http://eddysheng.javaeye.com/blog/50414
	private static String processFileName(String fileName, String agent)
			throws IOException {
		String codedfilename = fileName;
		if (null != agent && -1 != agent.indexOf("MSIE")) {// IE
			
			//采用apache的codeC，见:
			//http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6437829
			URLCodec codec=new URLCodec("UTF-8");
			try {
				codedfilename = codec.encode(fileName);
			} catch (EncoderException e) {
				log.warn(e);
			}
		} else if (null != agent && -1 != agent.indexOf("Mozilla")) { // Mozilla
			// firefox
			codedfilename = String
					.format(MOZILLA_DOWNLOAD_FILE_NAME, new String(Base64
							.encodeBase64(fileName.getBytes("UTF-8"))));
		} 
		return codedfilename;
	}
	
	/**
	 * 根据给定的文件名称构造可以下载文件的resoonse
	 * 
	 * @param fileName
	 * @throws IOException
	 */
	public static void constructResponse(String fileName, String extensionName, IRequestCycle requestCycle, WebResponse response) throws IOException {
		if (fileName == null || fileName.length() == 0) {// 如果没有定义文件名称，使用时间作为文件名称
			Calendar c=Calendar.getInstance();
			fileName=String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS%1$tL",c);
		}
		// 加上后缀
		fileName += extensionName;
		// 得到用户的agent,用来判断浏览器的类型
		String userAgent = requestCycle.getInfrastructure().getRequest()
				.getHeader("USER-AGENT");
		response.setHeader("Content-Disposition", String.format(
				ATTACHEMENT_FILE, processFileName(fileName, userAgent)));
	}
}
