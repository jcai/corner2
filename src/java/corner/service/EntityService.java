//==============================================================================
//file :       $Id$
//project:     corner
//
//last change:	date:       $Date$
//           	by:         $Author$
//           	revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	China Java Users Group http://cnjug.dev.java.net
//License:		the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.service;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.jdbc.core.JdbcTemplate;

import org.hibernate.ObjectNotFoundException;


import javax.sql.DataSource;

import corner.orm.hibernate.ObjectRelativeUtils;
import corner.orm.hibernate.v3.HibernateObjectRelativeUtils;
import corner.util.PaginationBean;

/**
 * 
 * Base Service.
 * 
 * @author <a href="http://wiki.java.net/bin/view/People/JunTsai">Jun Tsai</a>
 * @version $Revision$
 * @since 2005-11-2
 */
public class EntityService {
	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactory.getLog(EntityService.class);

	protected ObjectRelativeUtils oru;

	protected DataSource dataSource = null;

	public void setObjectRelativeUtils(ObjectRelativeUtils oru) {
		this.oru = oru;
	}

	public ObjectRelativeUtils getObjectRelativeUtils() {
		return oru;
	}

	public <T> Serializable saveEntity(T entity) {
		return oru.save(entity);
	}

	public <T> void saveOrUpdateEntity(T entity) {
		oru.saveOrUpdate(entity);
	}

	public <T> void updateEntity(T entity) {
		oru.update(entity);
	}

	public <T> T loadEntity(Class<T> clazz, Serializable keyValue) {
		return oru.load(clazz, keyValue);
	}

	public <T> int count(Class<T> clazz) {
		return oru.count("select count(*) from " + clazz.getName());
	}

	public <T> List<T> find(Class<T> clazz, PaginationBean pb) {
		return oru.find("from " + clazz.getName(), pb);
	}

	public <T> List<T> find(Class<T> clazz, PaginationBean pb,
			String columnName, boolean flag) {
		return oru.find("from " + clazz.getName() + " entity order by entity."
				+ columnName + (flag ? " desc" : ""), pb);
	}

	public <T> void deleteEntities(T... ts) {
		if (logger.isDebugEnabled()) {
			logger
					.debug("deleteEntities(List),delete entity list size [" + ts.length + "]"); //$NON-NLS-1$
		}

		for (T entity : ts) {
			try{
				
				oru.delete(entity);
			}catch(Exception e){
				logger.warn(e.getMessage());
				//donoting
			}
		}
	}


	public JdbcTemplate getJdbcTemplate() {
		return new JdbcTemplate(this.getDataSource());
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}


	public <T> void deleteEntityById(Class<T> name,Serializable keyValue) {
			T tmp=this.loadEntity(name,keyValue);
			if(tmp!=null){
				this.deleteEntities(tmp);
			}
	}
}
