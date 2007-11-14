// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2006-12-10
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
