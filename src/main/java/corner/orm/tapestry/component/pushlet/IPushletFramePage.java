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

package corner.orm.tapestry.component.pushlet;

import org.hibernate.criterion.DetachedCriteria;

import corner.orm.hibernate.IPersistModel;
import corner.orm.tapestry.page.EntityPage;

/**
 * 使用{@link PushletFrame}组件的页面都应该实现该接口
 * <p>用于提供PushletService查询当前消息的方法
 * 
 * @author <a href="mailto:ghostbb@bjmaxinfo.com">Ghostbb</a>
 * @version $Revision$
 * @since 2.5
 */
public interface IPushletFramePage<T extends IPersistModel> extends EntityPage<T> {

	/**
	 * 增加对查询信息的过滤
	 * @param criteria
	 * @return 带有页面提供的查询条件的{@link DetachedCriteria}
	 */
	public DetachedCriteria addCriteria(DetachedCriteria criteria);
}
