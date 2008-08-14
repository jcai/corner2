// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2006-05-24
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

package corner.orm.tapestry.table;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tapestry.contrib.table.model.IBasicTableModel;
import org.apache.tapestry.contrib.table.model.ITableColumn;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.util.Assert;

import corner.service.EntityService;

/**
 * 基于IPersistent的的table模型。
 *
 * @author Jun Tsai
 * @version $Revision:3677 $
 * @since 2006-5-24
 */
public class PersistentBasicTableModel implements IBasicTableModel {
	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactory.getLog(PersistentBasicTableModel.class);

	// 实体服务类
	private EntityService entityService;

	private IPersistentQueriable callback;
	/** 对行的数目进行了缓存**/
	private int rows = -1;

	private boolean isRewinding=false;
	
	//查询结果的缓存
	private List  resultList=null;

	/**
	 * 根据EntityService还有一个可查询的回掉类来构造一个table model.
	 * @param entityService 实体服务类.
	 * @param callback 查询回掉类.
	 */
	public PersistentBasicTableModel(EntityService entityService,
			IPersistentQueriable callback) {
		Assert.notNull(callback);
		Assert.notNull(entityService);
		this.entityService = entityService;
		this.callback = callback;
	}
	/**
	 * 根据EntityService还有一个可查询的回掉类来构造一个table model.
	 * @param entityService 实体服务类.
	 * @param callback 查询回掉类.
	 */
	public PersistentBasicTableModel(EntityService entityService,
			IPersistentQueriable callback,boolean isRewinding) {
		Assert.notNull(callback);
		Assert.notNull(entityService);
		this.entityService = entityService;
		this.callback = callback;
		this.isRewinding=isRewinding;
	}

	/**
	 *
	 * @see org.apache.tapestry.contrib.table.model.IBasicTableModel#getRowCount()
	 */
	public int getRowCount() {
		if(isRewinding){
			return rows;
		}
		if (rows == -1) {
			rows=((Integer)this.entityService.execute(new HibernateCallback(){

						public Object doInHibernate(Session session) throws HibernateException, SQLException {
							Criteria criteria=callback.createCriteria(session);
							callback.appendCriteria(criteria);
							criteria.setProjection(Projections.rowCount());

							return criteria.list().iterator().next();
						}})).intValue();

		}
		return rows;

	}
	/**
	 *
	 * @see org.apache.tapestry.contrib.table.model.IBasicTableModel#getCurrentPageRows(int, int, org.apache.tapestry.contrib.table.model.ITableColumn, boolean)
	 */
	public Iterator getCurrentPageRows(final int nFirst, final int nPageSize,
			final ITableColumn column, final boolean sort) {
		if(isRewinding){
			if (logger.isDebugEnabled()) {
				logger.debug("is rewinding ,return false;");
			}

			return null;
		}
		if(this.resultList==null){
			resultList =  this.entityService.executeFind(new HibernateCallback(){

					public Object doInHibernate(Session session) throws HibernateException, SQLException {
						Criteria criteria=callback.createCriteria(session);
						callback.appendCriteria(criteria);

						if (column != null) {
							
							String columnName = column.getColumnName();
							if(columnName.indexOf(".")>0){
								Criteria newCriteria = null;
								String[] props = columnName.split("\\.");
								int index = props.length -1;
								String orderColumn = props[index];
								for(int i=0;i< props.length-1 ;i++){
									if(newCriteria == null){
										newCriteria = criteria.createCriteria(props[i]);
									} else {
										newCriteria = newCriteria.createCriteria(props[i]);
									}
								}
								if(newCriteria != null){
									newCriteria.addOrder(sort ? Order.desc(orderColumn) : Order
											.asc(orderColumn));
								} else {
									criteria.addOrder(sort ? Order.desc(column.getColumnName()) : Order
											.asc(column.getColumnName()));
								}
							} else {
								criteria.addOrder(sort ? Order.desc(column.getColumnName()) : Order
										.asc(column.getColumnName()));
							}
							
						}

						callback.appendOrder(criteria);//增加排序 since 2.2.1
						
						criteria.setFirstResult(nFirst);
						criteria.setMaxResults(nPageSize);

						return criteria.list();
					}});
		}
		return resultList.iterator();

	}

}
