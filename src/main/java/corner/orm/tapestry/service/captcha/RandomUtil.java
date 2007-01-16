//==============================================================================
// file :       $Id$
// project:     corner
//
// last change: date:       $Date$
//              by:         $Author$
//              revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.orm.tapestry.service.captcha;

import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.id.UUIDHexGenerator;

/**
 * 产生随即码的类.
 * @author jcai
 * @version $Revision$
 * @since 2.3
 */
public class RandomUtil {

	private static UUIDHexGenerator GENERATOR=new UUIDHexGenerator();
	/**
	 * 产生一个四位的验证码
	 * @param string 原字符串
	 * @return 验证的字符串
	 */
	public static String encodeStr(String string) {
		String str=DigestUtils.md5Hex(string);
		StringBuffer sb=new StringBuffer();
		sb.append(str.charAt(7));
		sb.append(str.charAt(15));
		sb.append(str.charAt(23));
		sb.append(str.charAt(31));
		return sb.toString();
	}
	/**
	 * 随机产生一个UUID字符串.
	 * @return 随机的UUID字符串.
	 */
	public static String generateUUIDString(){
		return  GENERATOR.generate(null,null).toString();
	}
}
