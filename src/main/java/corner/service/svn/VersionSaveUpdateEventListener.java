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

package corner.service.svn;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.event.SaveOrUpdateEvent;
import org.hibernate.event.def.DefaultSaveOrUpdateEventListener;

import corner.orm.spring.SpringContainer;
import corner.service.EntityService;
import corner.util.BeanUtils;

/**
 * 保存或者更新时候的event监听器.
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.5
 */
public class VersionSaveUpdateEventListener extends DefaultSaveOrUpdateEventListener {

	public final static  String UNREVISION_VERSION="*";
	/**
	 * 
	 */
	private static final long serialVersionUID = -1075078528571943074L;

	/**
	 * @see org.hibernate.event.SaveOrUpdateEventListener#onSaveOrUpdate(org.hibernate.event.SaveOrUpdateEvent)
	 */
	public void onSaveOrUpdate(SaveOrUpdateEvent event)
			throws HibernateException {
		super.onSaveOrUpdate(event);
		final Session session = event.getSession();
		
		Object obj=event.getEntity();
		event.getResultId();
		if(obj instanceof IVersionable){
			IVersionable tmpObj=(IVersionable) obj;
			IVersionService service=(IVersionService) SpringContainer.getInstance().getApplicationContext().getBean(IVersionProvider.VERSION_SPRING_BEAN_NAME);
			long revision = service.checkin(tmpObj);
			if(revision>0){ //当为大于0的版本
				tmpObj.setRevision(String.valueOf(revision));
				
				updateGroupModelRevision(String.valueOf(revision),tmpObj,session);
				
			}else if(tmpObj.getRevision()!=null&&tmpObj.getRevision().indexOf(UNREVISION_VERSION)==-1){
				//当且仅当有了版本号而没有*的时候特别处理一下.
				String modifiedVersion = tmpObj.getRevision().trim()+UNREVISION_VERSION;
				tmpObj.setRevision(modifiedVersion);
				updateGroupModelRevision(modifiedVersion,tmpObj,session);
			}
		}
	}
	private void updateGroupModelRevision(String revision,IVersionable entity,Session session){
		//更新 Group的版本号
		Class<?> entityClass = EntityService.getEntityClass(entity);
		VersionGroup versionGroup=entityClass.getAnnotation(VersionGroup.class);
		if(versionGroup!=null&&!entityClass.equals(versionGroup.groupClass())){
			Class<? extends IVersionable> groupClass = versionGroup.groupClass();
			String groupId = (String) BeanUtils.getProperty(entity, versionGroup.groupIdExp());
			
			//当且仅当父类的ID有值的时候
			if(groupId!=null&&groupId.trim().length()>0){
				IVersionable group=(IVersionable) session.load(groupClass, groupId);
				group.setRevision(String.valueOf(revision));
			}
		}
	}
}
