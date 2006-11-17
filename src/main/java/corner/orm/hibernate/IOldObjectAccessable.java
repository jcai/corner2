package corner.orm.hibernate;

/**
 * 对hibernate的旧对象可读.
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.3
 */
public interface IOldObjectAccessable {
	/**
	 * 当load完毕实体的进行的操作
	 *
	 */
	public void initOldObject();
}
