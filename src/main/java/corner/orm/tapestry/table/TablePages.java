//==============================================================================
//file :        TablePages.java
//
//last change:	date:       $Date$
//           	by:         $Author$
//           	revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//==============================================================================

package corner.orm.tapestry.table;

import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.annotations.InjectObject;
import org.apache.tapestry.services.DataSqueezer;
import org.apache.tapestry.util.ComponentAddress;

import corner.util.CornerMessages;



/**
 * 个性化设置table分页的展示
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 1.0.8
 */
public abstract class TablePages extends org.apache.tapestry.contrib.table.components.TablePages{

	/** 得到页数 **/
	public int getPageCount(){
		return this.getTableModelSource().getTableModel().getPageCount();
	}
	/** 得到给定的样式**/
	public String getFirstLinkClass(){
		return this.getCondBack()?"nextpage":"disablepage";
	}
	public String getLastLinkClass(){
		return this.getCondFwd()?"nextpage":"disablepage";
	}
	/**
	 * 得到行数的消息
	 * @return 得到行数
	 */
	public String getRowCountMessage(){
		return "共"+getRowCount()+"条";
	}
	int getRowCount(){
		return this.getTableModelSource().getTableModel().getRowCount();
	}
	/** 记录前端输入多少页数 **/
	public  abstract Integer getPn();
	
	public abstract String getComponentAddress();
	public abstract void setComponentAddress(String c);
	@InjectObject("service:tapestry.data.DataSqueezer")
	public abstract DataSqueezer getDataSqueezer();
	
	/**
	 * 记录组件地址
	 * @return
	 */
	public String getDefaultComponentAddress(){
		ComponentAddress objAddress = new ComponentAddress(getTableModelSource());
		return getDataSqueezer().squeeze(objAddress);
	}
	/** 响应前端点击GO **/
	public void goPage(IRequestCycle objCycle){
		objCycle.setListenerParameters(new Object[]{getDataSqueezer().unsqueeze(getComponentAddress()),getPn()});
		super.changePage(objCycle);
	}
}
