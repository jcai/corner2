// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2005-11-30
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

package corner.orm.hibernate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 抽象的DateTimeGenerator对象.
 * 
 * @author	<a href="http://wiki.java.net/bin/view/People/JunTsai">Jun Tsai</a>
 * @version	$Revision:3677 $
 * @since	2005-11-30
 */
public abstract class AbstractDateTimeIDGenerator {
	protected String upTime = null;
	/* get now time formatted*/
	protected synchronized String getNowTimeFormatted() {
		Calendar rightNow = Calendar.getInstance();
		DateFormat formator = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String currentTime = formator.format(rightNow.getTime());
		if (currentTime.equals(upTime)) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
				//log.error(e.getMessage(), e);
			}
			currentTime = getNowTimeFormatted();
		}
		upTime=currentTime;
		return currentTime;
	}
	
}
