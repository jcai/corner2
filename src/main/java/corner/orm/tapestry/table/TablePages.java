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
}
