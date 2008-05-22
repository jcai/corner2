package corner.demo.page.palette;

import java.util.Date;
import java.util.List;

import org.apache.tapestry.IAsset;
import org.apache.tapestry.annotations.Asset;
import org.apache.tapestry.html.BasePage;

public abstract class PaletteResults extends BasePage
{
    public abstract void setSelectedColors(List list);
    
    public abstract Date getCurrentTime();
    public abstract void setCurrentTime(Date date);
    
    @Asset("classpath:Back.gif")
    public abstract IAsset getBackImage();
    
    @Asset("classpath:Back-focus.gif")
    public abstract IAsset getBackFocusImage();
}