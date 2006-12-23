//==============================================================================
// file :       $Id$
// project:     corner
//
// last change: date:       $Date$
//              by:         $Author$
//              revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

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
		T t=this.get(index);
		if(t==null||t.toString().trim().length()==0){
			return 0;
		}
		return Double.parseDouble(t.toString());
	}
	
	/**
	 * 以double类型返回matrix中的一个对象,当matrix中对象为空时,返回defaultValue
	 * @param index
	 * @param defaultValue
	 * @return
	 */
	public double getDouble(int index, double defaultValue){
		T t=this.get(index);
		if(t==null){
			return defaultValue;
		}
		return Double.parseDouble(t.toString());		
	}
}
