package corner.orm.tapestry.page.wml;

import org.apache.tapestry.util.ContentType;

import corner.orm.tapestry.page.AbstractEntityFormPage;


/**
 * wml
 * 对实体的表单操作。
 * @author Jun Tsai
 * @version $Revision:3677 $
 * @since 2006-5-23
 */
public abstract class PoWmlFormPage extends AbstractEntityFormPage<Object> {
	
    /**
     * @see org.apache.tapestry.html.BasePage#getResponseContentType()
     */
	@Override
    public ContentType getResponseContentType()
    {
        return new ContentType("text/vnd.wap.wml");
    }
}
