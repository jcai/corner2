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

package corner.orm.exception;

/**
 * Corner系统中使用的Exception,通常抛出此异常一般与客户的操作无关，通常为系统内部错误. 通常也是不可预知的异常。
 * 譬如：ClassNotFoundException 等.
 * 
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 2.3.7
 */
public class CornerSystemException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8992659260127813029L;

	/**
	 * 
	 */
	public CornerSystemException() {
		super();

	}

	/**
	 * @param message
	 * @param cause
	 */
	public CornerSystemException(String message, Throwable cause) {
		super(message, cause);

	}

	/**
	 * @param message
	 */
	public CornerSystemException(String message) {
		super(message);

	}

	/**
	 * @param cause
	 */
	public CornerSystemException(Throwable cause) {
		super(cause);

	}
}
