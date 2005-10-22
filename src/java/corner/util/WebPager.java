//==============================================================================
//file :       $Id: WebPager.java,v 1.3 2005/05/12 07:21:58 acai Exp $
//project:     pcrm
//
//last change:	date:       $Date: 2005/05/12 07:21:58 $
//           	by:         $Author: acai $
//           	revision:   $Revision: 1.3 $
//------------------------------------------------------------------------------
//copyright:	China Java Users Group(http://cnjug.dev.java.net).
//license:	Apache License(http://www.apache.org/licenses/LICENSE-2.0)
//==============================================================================

package corner.util;

/**
 * 
 * @author <a href="http://wiki.java.net/bin/view/People/JunTsai">Jun Tsai</a>
 * @version $Revision: 1.3 $
 * @since 2005-3-5
 */

public class WebPager implements Pager {
    private static final int PER_PAGE_RECORDS_DEFAULT = 10;
    private static final int PAGES_DISPLAYED_DEFAULT = 7;
    
    private int totalRecords;
    private int perPageRecords=PER_PAGE_RECORDS_DEFAULT;
    private int currentPage;
    private int pagesDisplayed=PAGES_DISPLAYED_DEFAULT;
    private int displayPage;
    public WebPager(){
    	
    }
    public WebPager(int totalRecords,int perPageRecords,int pagesDisplayed){
        this.totalRecords=totalRecords;
        this.perPageRecords=(perPageRecords>0?perPageRecords:PER_PAGE_RECORDS_DEFAULT);
        this.pagesDisplayed=(pagesDisplayed>0?pagesDisplayed:PAGES_DISPLAYED_DEFAULT);
        
    }
    public WebPager(int totalRecords){
        this.totalRecords=totalRecords;
        
    }
    public int getDisplayPage(){
        return this.displayPage;
    }
    public void setDisplayPage(int displayPage){
        this.displayPage=displayPage;
    }
    /**
     * @see org.cnjug.weed.entity.Pager#getCurrentPage()
     */
    public int getCurrentPage() {
        if(currentPage==0){
            currentPage=1;
        }
        return currentPage;
    }
    /**
     * @see org.cnjug.weed.entity.Pager#setCurrentPage(int)
     */
    public void setCurrentPage(int currentPage) {
        this.currentPage=currentPage;
    }
    /**
     * @see org.cnjug.weed.entity.Pager#getTotalPages()
     */
    public int getTotalPages() {
        int temp=totalRecords/perPageRecords;
        return ((totalRecords % perPageRecords)>0?temp+1:temp);
    }
    /**
     * @see org.cnjug.weed.entity.Pager#getPerPageRecords()
     */
    public int getPerPageRecords() {
        return perPageRecords;
    }
    /**
     * @see org.cnjug.weed.entity.Pager#getTotalRecords()
     */
    public int getTotalRecords() {
        return totalRecords;
    }
    /**
     * @see org.cnjug.weed.entity.Pager#setTotalRecords(int)
     */
    public void setTotalRecords(int totalRecords) {
        this.totalRecords=totalRecords;
    }
    /**
     * @see org.cnjug.weed.entity.Pager#getStartPageDisplayed()
     */
    public int getStartPageDisplayed() {
        return ((currentPage-1)/pagesDisplayed)*pagesDisplayed+1;
    }
    /**
     * @see org.cnjug.weed.entity.Pager#getLastPageDisplayed()
     */
    public int getLastPageDisplayed() {
        return Math.min(getTotalPages(),getStartPageDisplayed()+pagesDisplayed-1);
    }
    /**
     * @see org.cnjug.weed.entity.Pager#getPagesDisplayed()
     */
    public int getPagesDisplayed() {
        return this.pagesDisplayed;
    }
    /**
     * @see org.cnjug.weed.entity.Pager#getPageDisplayedList()
     */
    public Integer[] getPageDisplayedList() {
        int start=getStartPageDisplayed();
        int end=getLastPageDisplayed();
        
        Integer [] pageList=new Integer[end-start+1];
        
        for(int i=0;i<pageList.length;i++,start++){
            pageList[i]=new Integer(start);
        }
        return pageList;
    }
    /**
     * @see org.cnjug.weed.entity.Pager#isDisplayFirstPageLink()
     */
    public boolean isDisplayFirstPageLink() {
        return getStartPageDisplayed()>pagesDisplayed;
    }
    /**
     * @see org.cnjug.weed.entity.Pager#isDisplayLastPageLink()
     */
    public boolean isDisplayLastPageLink() {
        return getLastPageDisplayed()<getTotalPages();
    }
    /**
     * @see org.cnjug.weed.entity.Pager#getPosition()
     */
    public int getPosition() {
        return (getCurrentPage()-1)*this.getPerPageRecords();
    }
    /**
     * @see org.cnjug.weed.entity.Pager#getMaxResults()
     */
    public int getMaxResults() {
        return this.getPerPageRecords();
    }
    /**
     * @see org.cnjug.weed.entity.Pager#getFirstResult()
     */
    public int getFirstResult() {
        return (currentPage-1)*perPageRecords;
    }
}
