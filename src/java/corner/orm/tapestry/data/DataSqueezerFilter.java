package corner.orm.tapestry.data;

import org.apache.tapestry.services.DataSqueezer;

public interface  DataSqueezerFilter {
	String squeeze( Object object, DataSqueezer next );

    String[] squeeze( Object[] objects, DataSqueezer next );

    Object unsqueeze( String string, DataSqueezer next );

    Object[] unsqueeze( String[] strings, DataSqueezer next );
}
