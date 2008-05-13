package corner.demo.page.palette;

import java.util.List;
import org.apache.tapestry.html.BasePage;

public abstract class PaletteResults extends BasePage
{
    public abstract void setSelectedColors(List list);
}