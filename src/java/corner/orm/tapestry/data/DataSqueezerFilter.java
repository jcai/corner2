package corner.orm.tapestry.data;

import org.apache.tapestry.services.DataSqueezer;

/**
 * 重构部分代码，使其能够采用一串的datasqueezer。
 * @author Jun Tsai
 * @version $Revision$
 * @since 2006-5-29
 */
public interface  DataSqueezerFilter {
	String squeeze( Object object, DataSqueezer next );

    String[] squeeze( Object[] objects, DataSqueezer next );

    Object unsqueeze( String string, DataSqueezer next );

    Object[] unsqueeze( String[] strings, DataSqueezer next );
}
