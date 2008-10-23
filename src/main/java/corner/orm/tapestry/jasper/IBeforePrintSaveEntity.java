package corner.orm.tapestry.jasper;

/**
 * 在打印报表之前是否保存实体
 * @author <a href="mailto:zsl@bjmaxinfo.com">zsl</a>
 * @version $Revision$
 * @since 2.5.1
 */
public interface IBeforePrintSaveEntity {
	
	
	/**
	 * 用于报表答应前保存当前实体
	 * 超链接的，文字或者图片要用@Image组件
	 * @return
	 */
	public Object getBeforePrintSaveEntity();
}
