// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id: OneSessionPerServletRequestFilter.java 3678 2007-11-14 04:43:52Z jcai $
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

package corner.orm.tapestry.format;

import java.text.Format;

import org.apache.hivemind.Location;
import org.apache.hivemind.util.Defense;
import org.apache.tapestry.binding.AbstractBinding;
import org.apache.tapestry.coerce.ValueConverter;

/**
 * 实现对数字格式化的操作
 * @author <a href="mailto:Ghostbb@bjmaxinfo.com">Ghostbb</a>
 * @version $Revision: 9474 $
 * @since 0.7.5
 */
public class DateBinding extends AbstractBinding{
	
	private final Format _format;

    public DateBinding(String description, ValueConverter valueConverter, Location location, Format format)
    {
        super(description, valueConverter, location);

        Defense.notNull(format, "format");

        _format = format;
    }

    /**
     * Returns the translator.
     */

    public Object getObject()
    {
        return _format;
    }
}
