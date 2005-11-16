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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;
import java.util.List;

import corner.orm.hibernate.ObjectRelativeUtils;
import corner.util.PaginationBean;

/**
 * 
 * Base Service.
 * @author	<a href="http://wiki.java.net/bin/view/People/JunTsai">Jun Tsai</a>
 * @version	$Revision$
 * @since	2005-11-2
 */
public class EntityService {
	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactory.getLog(EntityService.class);

	protected ObjectRelativeUtils oru;
	public void setObjectRelativeUtils(ObjectRelativeUtils oru){
		this.oru=oru;
	}
	
	public<T> Serializable saveEntity(T entity){
		return oru.save(entity);
	}
	public <T> void updateEntity(T entity){
		oru.update(entity);
	}
	public <T> T loadEntity(Class<T> clazz,Serializable keyValue){
		return oru.load(clazz,keyValue);
	}
	public <T>int count(Class<T> clazz){
		return oru.count("select count(*) from "+clazz.getName());
	}
	public <T>List<T> find(Class<T> clazz,PaginationBean pb){
		return oru.find("from "+clazz.getName(),pb);
	}
	public <T>List<T> find(Class<T> clazz,PaginationBean pb,String columnName,boolean flag){
		return oru.find("from "+clazz.getName()+" entity order by entity."+columnName+(flag?" desc":""),pb);
	}
	public<T> void  deleteEntities(T ... ts){
		if (logger.isDebugEnabled()) {
			logger.debug("deleteEntities(List),delete entity list size ["+ts.length+"]"); //$NON-NLS-1$
		}
		
		for(T entity:ts){
			oru.delete(entity);
		}
	}
	
}
