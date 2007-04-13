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