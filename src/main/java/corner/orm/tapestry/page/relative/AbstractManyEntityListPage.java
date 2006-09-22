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

package corner.orm.tapestry.page.relative;

import corner.orm.tapestry.page.AbstractEntityListPage;

/**
 * 提供从实体列表页面直接跳转到向对应的关联页面.
 * 抽象实体列表页面
 * <p>扩展了AbstractEntityListPage类,主要提供了从实体列表页面直接跳转的ReflectRelativeEntityFormPage和ReflectRelativeEntityListPage页面的方法
 * @author Ghost
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.1
 * @deprecated 方法都已经重构到AbstractEntityListPage里面，请直接使用AbstractEntityListPage
 */
public abstract class AbstractManyEntityListPage<T> extends AbstractEntityListPage<T> implements IRelativeObjectOperatorSupport{

	
}
