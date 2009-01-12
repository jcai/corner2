//==============================================================================
//file :        BookServiceImpl.java
//
//last change:	date:       $Date$
//           	by:         $Author$
//           	revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//==============================================================================

package corner.demo.xfire.service.impl;

import java.util.HashMap;
import java.util.Map;

import corner.demo.model.xfire.Book;
import corner.demo.xfire.service.BookService;

/**
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 0.0.1
 */
public class BookServiceImpl implements BookService { 
	
	private Book onlyBook;
 
	public BookServiceImpl() {
		onlyBook = new Book();
		onlyBook.setAuthor("Dan Diephouse");
		onlyBook.setTitle("Using XFire");
		onlyBook.setIsbn("0123456789");
	}

	public Book[] getBooks() {
		return new Book[] { onlyBook }; 
	}

	public Book findBook(String isbn) {
		if (isbn.equals(onlyBook.getIsbn()))
			return onlyBook;

		return null;
	}

	public Map getBooksMap() {
		Map result = new HashMap();
		result.put(onlyBook.getIsbn(), onlyBook);
		return result;
	}
}
