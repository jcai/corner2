//==============================================================================
//file :       $Id: Pager.java,v 1.2 2005/05/07 03:08:03 acai Exp $
//project:     weed
//
//last change:	date:       $Date: 2005/05/07 03:08:03 $
//           	by:         $Author: acai $
//           	revision:   $Revision: 1.2 $
//------------------------------------------------------------------------------
//copyright:	China Java Users Group(http://cnjug.dev.java.net).
//license:	Apache License(http://www.apache.org/licenses/LICENSE-2.0)
//==============================================================================

package corner.util;

/**
 * 
 * @author <a href="http://wiki.java.net/bin/view/People/JunTsai">Jun Tsai</a>
 * @version $Revision: 1.2 $
 * @since 2004-12-31
 */

public interface Pager {
    /**
     * 得到当前需要显示的页数.
     * @return 当前页.
     */
    public int getCurrentPage();
    /**
     * 设定当前页.
     * @param currentPage 当前页.
     */
    public void setCurrentPage(int currentPage);
    /**
     * 得到总页数.
     * @return 总页数.
     */
    public int getTotalPages();
    /**
     * 得到每页显示的记录数.
     * @return 每页显示的记录数.
     */
    public int getPerPageRecords();
    /**
     * 得到总记录数.
     * @return 得到总记录数.
     */
    public int getTotalRecords();
    /**
     * 得到开始显示的页数.
     * @return 开始显示的页数.
     */
    public int getStartPageDisplayed();
    /**
     * 得到最后显示的页数.
     * @return 最后显示的页数.
     */
    public int getLastPageDisplayed();
    /**
     * 得到需要显示的总页数.
     * @return 需要显示的总页数.
     */
    public int getPagesDisplayed();
    /**
     * 得到显示的页数数组.
     * @return 显示的页数数组.
     */
    public Integer [] getPageDisplayedList();
    /**
     * 是否需要显示第一页的连接.
     * @return 是否需要显示第一页连接.
     */
    public boolean isDisplayFirstPageLink();
    /**
     * 是否需要显示最后页的连接.
     * @return 是否需要显示最后页的连接.
     */
    public boolean isDisplayLastPageLink();
    public int getPosition();
    public int getMaxResults();
    
    /**
     * 得到抓取记录的位置,在结果中包含该条记录.
     * @return 抓取记录的开始位置.
     */
    public int getFirstResult();
    /**
     * 设定总记录数
     * @param totalRecords 设定总记录数
     */
    public void setTotalRecords(int totalRecords);
    
    
}
