package corner.orm.tapestry.component.tree;

import java.util.List;
import org.apache.tapestry.IPage;
import org.apache.tapestry.json.JSONObject;
import corner.model.tree.ITree;
import corner.model.tree.ITreeAdaptor;

/**
 * @author <a href="mailto:xf@bjmaxinfo.com">xiafei</a>
 * @version $Revision$
 * @since 2.5
 */
public interface ITreeSelectModel extends ITree{
	/**
	 * 获得下一个深度
	 * @return
	 */
	public abstract int getNextDepth();
	
	/**
	 * @return Returns the queryClassName.
	 */
	public abstract String getQueryClassName();

	/**
	 * @param queryClassName The queryClassName to set.
	 */
	public abstract void setQueryClassName(String queryClassName);

	/**
	 * @return Returns the depends.
	 */
	public abstract String[] getDepends();

	/**
	 * @param depends The depends to set.
	 */
	public abstract void setDepends(String[] depends);

	/**
	 * @return Returns the parentPage.
	 */
	public abstract IPage getParentPage();

	/**
	 * @param parentPage The parentPage to set.
	 */
	public abstract void setParentPage(IPage parentPage);

	/**
	 * @return Returns the returnValues.
	 */
	public abstract JSONObject getReturnValues();

	/**
	 * @param returnValues The returnValues to set.
	 */
	public abstract void setReturnValues(JSONObject returnValues);

	/**
	 * @return Returns the treeList.
	 */
	public abstract List<? extends ITreeAdaptor> getTreeList();

	/**
	 * @param treeList The treeList to set.
	 */
	public abstract void setTreeList(List<? extends ITreeAdaptor> treeList);

	/* bean properties begin */
	public static final String LEFT_PRO_NAME = "left";
	public static final String RIGHT_PRO_NAME = "right";
	public static final String DEPTH_PRO_NAME = "depth";
	/* bean properties end */

}