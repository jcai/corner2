// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2006-07-05
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

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;
import org.hibernate.util.EqualsHelper;

import corner.util.VectorUtils;

/**
 * 
 * 实现一个vector的数据持久华类型。
 * @author Jun Tsai
 * @version $Revision$
 * @since 2.0.5
 */
public class VectorType implements UserType {
	public static final String SEGMENT=",";
	/**
	 * @see org.hibernate.usertype.UserType#sqlTypes()
	 */
	public int[] sqlTypes() {
		return new int[] {Types.VARCHAR};
	}

	/**
	 * @see org.hibernate.usertype.UserType#returnedClass()
	 */
	public Class returnedClass() {

		return MatrixRow.class;
	}



	/**
	 * @see org.hibernate.usertype.UserType#nullSafeGet(java.sql.ResultSet, java.lang.String[], java.lang.Object)
	 */
	public Object nullSafeGet(ResultSet rs, String[] names, Object owner)
			throws HibernateException, SQLException {
		String str=rs.getString(names[0]);
		MatrixRow<Object> v=new MatrixRow<Object>();
		if(str!=null&&str.trim().length()>0){
			v.addAll(VectorUtils.VectorSplit(str,SEGMENT));
		}
		return v;
	}

	/**
	 * @see org.hibernate.usertype.UserType#nullSafeSet(java.sql.PreparedStatement, java.lang.Object, int)
	 */
	@SuppressWarnings("unchecked")
	public void nullSafeSet(PreparedStatement st, Object value, int index)
			throws HibernateException, SQLException {
		if(value!=null){
			MatrixRow<Object> v = (MatrixRow<Object>) value;
			StringBuffer sb=new StringBuffer();
			for(Object str:v){
				sb.append(str!=null?str.toString():"");
				sb.append(SEGMENT);
			}
			if(sb.length()>0){
				sb.setLength(sb.length()-SEGMENT.length()); //删除最后的分割符
			}
			String str=sb.toString();
			if(str.length()>0){
				st.setString(index,sb.toString());
				return;
			}
		}
		st.setString(index,null);
	}

	/**
	 * This implementation returns false.
	 */
	public boolean isMutable() {
		return false;
	}

	/**
	 * This implementation delegates to the Hibernate EqualsHelper.
	 * @see org.hibernate.util.EqualsHelper#equals
	 */
	public boolean equals(Object x, Object y) throws HibernateException {
		return EqualsHelper.equals(x, y);
	}

	/**
	 * This implementation returns the hashCode of the given objectz.
	 */
	public int hashCode(Object x) throws HibernateException {
		return x.hashCode();
	}

	/**
	 * This implementation returns the passed-in value as-is.
	 */
	public Object deepCopy(Object value) throws HibernateException {
		return value;
	}

	/**
	 * This implementation returns the passed-in value as-is.
	 */
	public Serializable disassemble(Object value) throws HibernateException {
		return (Serializable) value;
	}

	/**
	 * This implementation returns the passed-in value as-is.
	 */
	public Object assemble(Serializable cached, Object owner) throws HibernateException {
		return cached;
	}

	/**
	 * This implementation returns the passed-in original as-is.
	 */
	public Object replace(Object original, Object target, Object owner) throws HibernateException {
		return original;
	}

}
