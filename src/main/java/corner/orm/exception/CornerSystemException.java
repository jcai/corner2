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
