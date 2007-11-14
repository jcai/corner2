// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2007-04-02
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

package corner.orm.tapestry.page;

import org.apache.tapestry.IPage;

/**
 * Dialog的接口
 * @author xiafei
 * @version $Revision$
 * @since 2.3.7
 */
public interface IDialogAction<T> {

	/**
	 * 设置Dialog弹出消息
	 */
	public abstract void setDialogMessage(String dialogMessage);

	/**
	 * 获得Dialog消息
	 */
	public abstract String getDialogMessage();

	/**
	 * Dialog中要执行的业务逻辑
	 */
	public abstract boolean isDoDialogAction(T entity);
	
	/**
	 * 默认显示操作 
	 */
	public abstract IPage doDialogAction(T entity);
	
	/**
	 * 显示Dialog
	 */
	public abstract void showDialog();
	
	/**
	 * 根据给定的Componnet的名称来显示Dialog
	 */
	public abstract void showDialog(String componentId);
}