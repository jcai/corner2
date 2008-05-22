// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id: NumTranslator.java 4015 2008-04-22 07:42:14Z lsq $
// created at:2006-12-14
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

package corner.orm.tapestry.translator;

import org.apache.tapestry.form.translator.DateTranslator;

/**
 * 定义一个常用的
 * 
 * @author <a href="mailto:Ghostbb@bjmaxinfo.com">Ghostbb</a>
 * @version $Revision$
 * @since 2.5
 */
public class SimpleDateTranslator extends DateTranslator {
    /**
     * corner中日期类型使用的pattern
     */
    private static final String CORNER_DATE_PATTERN = "yyyy-MM-dd";

    /**
     * @see org.apache.tapestry.form.translator.DateTranslator#defaultPattern()
     */
    @Override
    protected String defaultPattern() {
        return CORNER_DATE_PATTERN;
    }
}
