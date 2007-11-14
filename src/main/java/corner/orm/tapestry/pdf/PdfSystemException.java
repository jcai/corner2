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

package corner.orm.tapestry.pdf;

import corner.orm.exception.CornerSystemException;


/**
 * PDF处理时候抛出的基础异常类.
 * @author jcai
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 2.3.7
 */
public class PdfSystemException extends CornerSystemException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7388458930742094569L;

	/**
	 * @param cause
	 */
	public PdfSystemException(Throwable cause) {
		super(cause);
	}

	public PdfSystemException(String message) {
		super(message);
	}
}
