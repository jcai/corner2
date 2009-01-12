//==============================================================================
//file :        BookServiceTest.java
//
//last change:	date:       $Date$
//           	by:         $Author$
//           	revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//==============================================================================

package corner.demo.xfire.client;

import java.net.MalformedURLException;

import org.codehaus.xfire.client.Client;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import corner.demo.model.xfire.Book;
import corner.demo.xfire.service.BookService;

/**
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 0.0.1
 */
public class BookServiceTest extends Assert {

	@Test
	public void testBookService(){
		String serviceURL = "http://127.0.0.1:8080/BookService.xfire";
        Service serviceModel = new ObjectServiceFactory().create(BookService.class,null,serviceURL,null);
        XFireProxyFactory serviceFactory = new XFireProxyFactory();
        try
        {
        	BookService service = (BookService) serviceFactory.create(serviceModel, serviceURL);
            Client client = Client.getInstance(service);
            Book[] books = service.getBooks();
            System.out.println("BOOKS:");
            for (int i = 0; i < books.length; i++)
            {
                System.out.println(books[i].getTitle());
            }
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
	}
}
