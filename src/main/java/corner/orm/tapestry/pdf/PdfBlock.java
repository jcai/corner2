/*		
 * Copyright 2007 The Beijing Maxinfo Technology Ltd. 
 * site: http://www.bjmaxinfo.com
 * file: $Id$
 * created at:2007-04-07
 */

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
