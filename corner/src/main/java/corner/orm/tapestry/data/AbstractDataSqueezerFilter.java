package corner.orm.tapestry.data;

import org.apache.tapestry.services.DataSqueezer;

/**
 * 抽象的数据序列化。
 * @author Jun Tsai
 * @version $Revision$
 * @since 2.0.5
 */
public abstract class AbstractDataSqueezerFilter implements DataSqueezerFilter
{
    public String[] squeeze( Object[] objects, DataSqueezer next )
    {
        final String[] squeezed = new String[objects.length];
        for( int i = 0; i < squeezed.length; i++ )
        {
            squeezed[i] = squeeze( objects[i], next );
        }
        return squeezed;
    }

    public Object[] unsqueeze( String[] strings, DataSqueezer next )
    {
        final Object[] unsqueezed = new Object[strings.length];
        for( int i = 0; i < unsqueezed.length; i++ )
        {
            unsqueezed[i] = unsqueeze( strings[i], next );
        }
        return unsqueezed;
    }
}