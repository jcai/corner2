//==============================================================================
//file :       $Id$
//project:     corner
//
//last change:	date:       $Date$
//           	by:         $Author$
//           	revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Wang&Tsai Studio http://www.wtstudio.org
//==============================================================================

package corner.orm.tapestry.page;

import java.text.Format;
import java.text.SimpleDateFormat;

import org.apache.tapestry.IAsset;
import org.apache.tapestry.IPage;
import org.apache.tapestry.annotations.InjectObject;
import org.apache.tapestry.html.BasePage;
import org.apache.tapestry.services.DataSqueezer;

import corner.model.IBlobModel;
import corner.orm.hibernate.v3.HibernateObjectRelativeUtils;
import corner.orm.tapestry.page.relative.IPageRooted;
import corner.orm.tapestry.service.blob.BlobAsset;
import corner.orm.tapestry.service.blob.IBlobPageDelegate;
import corner.orm.tapestry.service.blob.SqueezeBlobPageDelegate;
import corner.service.EntityService;

/**
 * 抽象的实体类.
 * <p>
 * 实现了抽象实体基本的方法。
 *
 * @author jcai
 * @version $Revision$
 * @since 2006-3-5
 * @param <T>
 *            当前抽象的实体。
 */
public abstract class AbstractEntityPage<T> extends BasePage implements
		EntityPage<T>,IBlobPage {
	/** 对日期类型的格式化 * */
	private SimpleDateFormat _dateFormat;




	/**
	 * 保存和更新实体。
	 */
	protected void saveOrUpdateEntity() {
		getEntityService().saveOrUpdateEntity(getEntity());

	}

	/**
	 * 得到日期格式化对象.
	 *
	 * @return 日期格式化对象.
	 */
	public Format getDateFormat() {
		if (_dateFormat == null)
			_dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return _dateFormat;
	}

	/**
	 * 通过给定的日期格式化字符串,来得到一个格式化对象.
	 *
	 * @param formatStr
	 *            格式化模式.
	 * @return 格式化对象.
	 */
	public Format getDateFormat(String formatStr) {
		return new SimpleDateFormat(formatStr);
	}

	/**
	 *
	 * 对hibernate进行flush操作。
	 *
	 * @since 2.0.3
	 */
	protected void flushHibernate() {
		((HibernateObjectRelativeUtils) this.getEntityService()
				.getObjectRelativeUtils()).getHibernateTemplate().flush();
	}

	/**
	 * 得到tapestry的datasqueezer。
	 *
	 * @return datasqueezer
	 * @since 2.0.3
	 */
	@InjectObject("service:tapestry.data.DataSqueezer")
	public abstract DataSqueezer getDataSqueezer();

	/**
	 * 提供entity页面的跳转
	 * @see corner.orm.tapestry.page.EntityPage#goEntityPage(java.lang.Object, java.lang.String)
	 * @since 2.1
	 */
	@SuppressWarnings("unchecked")
	public <E> EntityPage<E> goEntityPage(E e,String pageName){
		EntityPage<E> page=(EntityPage<E>) this.getRequestCycle().getPage(pageName);
		return this.goEntityPageByPage(e, page);
	}
	
	
	/**
	 * 提供entity页面的跳转
	 * 
	 * @since 2.1
	 * @see corner.orm.tapestry.page.EntityPage#goEntityPageByPage(java.lang.Object, corner.orm.tapestry.page.EntityPage)
	 */
	public <E> EntityPage<E> goEntityPageByPage(E e, EntityPage<E> page) {
		if(page==null){
			throw new IllegalArgumentException("待跳转的页面为空!");
		}
		page.setEntity(e);
		return page;
	}
	/**
	 * 返回到关联对象的列表页。
	 * @param t 实体对象。one那一端的实体对象
	 * @param listPath 列表页面。
	 * @return 列表页面。
	 * @since 2.1
	 */
	@SuppressWarnings("unchecked")
	public IPage doViewRelativeEntityListAction(Object t,String listPageName){
		IPageRooted<Object,Object> page= (IPageRooted<Object,Object>) this.getRequestCycle().getPage(listPageName);
		page.setRootedObject(t);
		return page;
	}
	/**
	 * 显示的图像Asset对象.
	 * @return 供显示图片数据的对象.
	 */
	public IAsset getChartImageAsset(Object obj) {
		return new BlobAsset(this.getBlobService(),getRequestCycle(),obj);
	}
	/**
	 * @deprecated
	 * @return
	 */
	public String getCurrentPagePath() {
		String thisPageName = this.getPageName();
		StringBuffer sb = new StringBuffer();
		sb.append(thisPageName.substring(0, thisPageName.lastIndexOf("/")));
		sb.append("/");
		return sb.toString();
	}
	
	/**
	 * 保存Blob实体
	 * @param <B> 待保存的blob实体。
	 * @param blobEntity blob实体
	 * @since 2.2.2
	 */
	@SuppressWarnings("unchecked")
	public <B extends IBlobModel> void saveBlobData(B blobEntity){
		IBlobPageDelegate<B> delegate = new SqueezeBlobPageDelegate<B>(
				EntityService.getEntityClass(blobEntity), getUploadFile(), blobEntity, this
						.getEntityService());
		delegate.save();
	}

}
