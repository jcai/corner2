package corner.service.tree.tafeltree;

import org.apache.tapestry.json.JSONArray;
import org.apache.tapestry.json.JSONObject;

/**
 * 取的和 tafelTree控件相关的json字符串对象
 * @author <a href=mailto:wlh@bjmaxinfo.com>wlh</a>
 * @version $Revision$
 * @since 2.5.2
 */
public interface ITafelTreeStructService {
	
	/**
	 * 获得树型结构json串
	 * @return
	 */
	public JSONArray getTreeStruct();
	
	/**
	 * 获得树型结构的一些配置信息
	 * @return
	 */
	public JSONObject getTreeConfig();
}
