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

import corner.orm.hibernate.IPersistModel;

/**
 * 发送消息的实体必须实现该接口
 * <p>用于抽取消息标题
 * 
 * @author <a href="mailto:Ghostbb@bjmaxinfo.com">Ghostbb</a>
 * @version $Revision$
 * @since 2.5
 */
public interface IMessageModel extends IPersistModel {

    public String getMessageTitle();
    public void setMessageTitle(String title);
}
