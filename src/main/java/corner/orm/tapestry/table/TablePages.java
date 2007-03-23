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
import org.apache.tapestry.annotations.InitialValue;
import org.apache.tapestry.annotations.InjectObject;
import org.apache.tapestry.contrib.table.model.common.TableModelHook;
import org.apache.tapestry.services.DataSqueezer;
import org.apache.tapestry.util.ComponentAddress;



/**
 * 个性化设置table分页的展示
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 1.0.8
 */
public abstract class TablePages extends org.apache.tapestry.contrib.table.components.TablePages{

	public int getPageCount(){
		return this.getTableModelSource().getTableModel().getPageCount();
	}
	public String getFirstLinkClass(){
		return this.getCondBack()?"nextpage":"disablepage";
	}
	public String getLastLinkClass(){
		return this.getCondFwd()?"nextpage":"disablepage";
	}
	public int getRowCount(){
		return TableModelHook.getAboutRowCount(this.getTableModelSource().getTableModel());
	}
	public  abstract Integer getPn();
	
	public abstract String getComponentAddress();
	public abstract void setComponentAddress(String c);
	@InjectObject("service:tapestry.data.DataSqueezer")
	public abstract DataSqueezer getDataSqueezer();
	
	public String getDefaultComponentAddress(){
		ComponentAddress objAddress = new ComponentAddress(getTableModelSource());
		return getDataSqueezer().squeeze(objAddress);
	}
	public void goPage(IRequestCycle objCycle){
		objCycle.setListenerParameters(new Object[]{getDataSqueezer().unsqueeze(getComponentAddress()),getPn()});
		super.changePage(objCycle);
	}
}
