// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2005-10-18
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

package corner.util.crypto;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * 加密算法的key产生器.
 * @author <a href="http://wiki.java.net/bin/view/People/JunTsai">Jun Tsai</a>
 * @version $Revision:3677 $
 */
public class CryptoKeyGenerator {
	/**
	 * 产生加密的key.
	 * @param algorithm 算法名称.
	 * @param fileName 加密产生文件的名称.
	 */
	public static void generateKey(String algorithm, String fileName) {
		SecretKey key = null;

		try {

			//	  指定算法,这里为DES;如果想用Blowfish算法,则用 getInstance("Blowfish")
			//	  BouncyCastle基本上支持所有通用标准算法
			KeyGenerator keygen = KeyGenerator.getInstance(algorithm);
			//	  指定密钥长度,长度越高,加密强度越大
			keygen.init(56);
			//	  产生密钥
			key = keygen.generateKey();
			//	  构造输出文件,这里的目录是动态的,根据用户名称来构造目录
			ObjectOutputStream keyFile =
				new ObjectOutputStream(new FileOutputStream(fileName));
			keyFile.writeObject(key);
			keyFile.close();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}
}
