// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2006-10-18
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

package corner.orm.hibernate.v3;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Vector;

import corner.util.VectorUtils;

/**
 * 矩阵的行记录.
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.2.2
 */
public class MatrixRow<T> extends Vector<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4481128241802168063L;

	public double getRowSum(){
		Iterator it = this.iterator();
		if(it.hasNext()){
			if(it.next() instanceof String){
				return 0;
			}
		}
		return VectorUtils.sum(this);
		
	}
	public MatrixRow(T ... ts){
		this();
		this.addAll(Arrays.asList(ts));
	}
	public MatrixRow(){
		super();
	}
	
	/**
	 * 以double类型返回matrix中的一个对象
	 * @param index
	 * @return
	 */
	public double getDouble(int index){
		return getDouble(index,0.0);
	}
	
	/**
	 * 以double类型返回matrix中的一个对象,当matrix中对象为空时,返回defaultValue
	 * @param index
	 * @param defaultValue
	 * @return
	 */
	public double getDouble(int index, double defaultValue){
		T t=this.get(index);
		if(t==null||t.toString().trim().length()==0){
			return defaultValue;
		}
		return Double.parseDouble(t.toString());		
	}
	/**
	 * 总是得到一个值，无论是超过边界.
	 * @param index 索引。
	 * @param defaultValue 默认值。
	 * @return 数值.
	 */
	public double getDoubleWithAnyway(int index,double defaultValue){
		if(index<this.size()){
			return getDouble(index,defaultValue);
		}
		return defaultValue;
	}
	
	/**
	 * 根据索引返回集合元素
	 * @param index
	 * @return 指定索引的集合元素,不存在则返回null
	 */
	public String printToPdf(int index){
		
		//有此索引的元素则返回
		if(this.size()>index){
			
			String value = this.get(index).toString().trim();
			//将空字符串("")排除
			if(value != null && value.length()>0){
				return value;
			}
			
		}
		
		return null;
	}
}
