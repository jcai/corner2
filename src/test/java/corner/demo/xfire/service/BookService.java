//==============================================================================
//file :        BookService.java
//
//last change:	date:       $Date$
//           	by:         $Author$
//           	revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//==============================================================================

package corner.demo.xfire.service;

import java.util.Map;

import corner.demo.model.xfire.Book;

/**
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 0.0.1
 */
public interface BookService {
	public Book[] getBooks();

	public Book findBook(String isbn);

	public Map getBooksMap();
}
