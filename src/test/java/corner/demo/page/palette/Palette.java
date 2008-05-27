package corner.demo.page.palette;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.tapestry.IAsset;
import org.apache.tapestry.IPage;
import org.apache.tapestry.annotations.Asset;
import org.apache.tapestry.annotations.InjectPage;
import org.apache.tapestry.event.PageBeginRenderListener;
import org.apache.tapestry.event.PageEvent;
import org.apache.tapestry.form.IPropertySelectionModel;
import org.apache.tapestry.form.StringPropertySelectionModel;
import org.apache.tapestry.html.BasePage;
import org.apache.tapestry.valid.IValidationDelegate;

public abstract class Palette extends BasePage implements PageBeginRenderListener{
	
	/**
	 * @see org.apache.tapestry.event.PageBeginRenderListener#pageBeginRender(org.apache.tapestry.event.PageEvent)
	 */
	public void pageBeginRender(PageEvent event) {
		List list = new ArrayList();
		this.setSelectedColors(Arrays.asList(colors));
	}
	
	public static final SimpleDateFormat TIME_PATTERN = new SimpleDateFormat("hh:mm:ss");

    public abstract List getSelectedColors();
    public abstract void setSelectedColors(List list);

    public abstract String getSort();

    public abstract void setSort(String s);

    public abstract IValidationDelegate getDelegate();
    
    public abstract Date getCurrentTime();
    public abstract void setCurrentTime(Date date);

    @InjectPage("palette/PaletteResults")
    public abstract PaletteResults getResultsPage();

    /**
     * @see org.apache.tapestry.AbstractComponent#finishLoad()
     */
    protected void finishLoad()
    {
        setSort("USER");
    }

    public void doRefresh()
    {
        getDelegate().clearErrors();
    }

    public IPage doAdvance()
    {
        PaletteResults results = getResultsPage();
        results.setSelectedColors(getSelectedColors());
        results.setCurrentTime(this.getCurrentTime());
        return results;
    }

    public IPropertySelectionModel getColorModel()
    {
        if(colorModel == null)
            colorModel = new StringPropertySelectionModel(colors);
        return colorModel;
    }

    private IPropertySelectionModel colorModel;
    private String colors[] = {
        "Red", "Orange", "Yellow", "Green", "Blue", "Indigo", "Violet"
    };
    
    @Asset("classpath:Continue.gif")
    public abstract IAsset getContinueImage();
    
    @Asset("classpath:Update.gif")
    public abstract IAsset getUpdateImage();
}