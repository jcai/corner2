// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2007-07-31
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

package corner.orm.tapestry.pdf;

import com.lowagie.text.Rectangle;



/**
 * pdf块的定义。
 * @author jcai
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 2.3.7
 */
public class PdfBlock {
	
		
		private String name;
		private String value;
		private int pageNum;
		private Rectangle rectangle;
		/**
		 * 设定位置的同时，把页数得到
		 * @param fieldPositions
		 */
		public void setPosition(float[] fieldPositions) {
			pageNum=(int) fieldPositions[0];
			rectangle=new Rectangle(fieldPositions[1], fieldPositions[2], fieldPositions[3], fieldPositions[4]);
			
		}
		/**
		 * @return Returns the name.
		 */
		public String getName() {
			return name;
		}
		/**
		 * @param name The name to set.
		 */
		public void setName(String name) {
			this.name = name;
		}
		/**
		 * @return Returns the value.
		 */
		public String getValue() {
			return value;
		}
		/**
		 * @param value The value to set.
		 */
		public void setValue(String value) {
			this.value = value;
		}
		/**
		 * @return Returns the position.
		 */
		public Rectangle getRectangle() {
			return rectangle;
		}
		public int getPageNum() {
			if(pageNum<0){
				throw new PdfSystemException("改block还没有初始化位置，请先设定位置setPosition!");
			}
			return pageNum;
		}
		/**
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return this.getName();
		}
		/**
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if(this.name==null){
				return  obj==null;
			}else{
				return this.name.equals(obj);
			}
			
		}
		


}
