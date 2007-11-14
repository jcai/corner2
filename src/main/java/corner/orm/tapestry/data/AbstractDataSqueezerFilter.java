// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2006-05-26
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