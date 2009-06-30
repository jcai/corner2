// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2006-03-05
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package corner.orm.tapestry.page;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Iterator;

import org.apache.tapestry.IAsset;
import org.apache.tapestry.IBinding;
import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IPage;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.annotations.InjectObject;
import org.apache.tapestry.binding.LiteralBinding;
import org.apache.tapestry.components.ILinkComponent;
import org.apache.tapestry.dojo.html.Dialog;
import org.apache.tapestry.engine.ILink;
import org.apache.tapestry.html.BasePage;
import org.apache.tapestry.link.ILinkRenderer;
import org.apache.tapestry.services.DataSqueezer;
import corner.model.IBlobModel;
import corner.orm.tapestry.RawURLLinkRenderer;
import corner.orm.tapestry.page.relative.IPageRooted;
import corner.orm.tapestry.service.blob.BlobAsset;
import corner.orm.tapestry.service.blob.IBlobPageDelegate;
import corner.orm.tapestry.service.blob.SqueezeBlobPageDelegate;
import corner.orm.tapestry.state.IContextAccessible;
import corner.service.EntityService;
import corner.util.BeanUtils;
import corner.util.CornerUtils;

/**
 * 抽象的实体类.
 * <p>
 * 实现了抽象实体基本的方法。
 *
 * @author jcai
 * @version $Revision:3677 $
 * @since 2006-3-5
 * @param <T>
 *            当前抽象的实体。
 */
public abstract class AbstractEntityPage<T> extends BasePage implements
		EntityPage<T>,IBlobPage,IContextAccessible {
	
	/**
	 * @see corner.orm.tapestry.page.IDialogAction#isDoDialogAction(java.lang.Object)
	 */
	public boolean isDoDialogAction(T entity) {
		return false;
	}

	/**
	 * @see corner.orm.tapestry.page.IDialogAction#doDialogAction(java.lang.Object)
	 */
	public IPage doDialogAction(T entity) {
		this.isDoDialogAction(entity);
		this.showDialog();
		return this;
	}
	
	/**
	 * @see corner.orm.tapestry.page.IDialogAction#showDialog()
	 */
	public void showDialog() {
		showDialog("showMessage");
	}
	
	/**
	 * 
	 * @see corner.orm.tapestry.page.IDialogAction#showDialog(java.lang.String)
	 */
	public void showDialog(String componentId){
		Dialog dlg = (Dialog) getComponent(componentId);
		dlg.show();
	}

	/**
	 * 保存和更新实体。
	 */
	protected void saveOrUpdateEntity() {
		getEntityService().saveOrUpdateEntity(getEntity());

	}
	
	/**
	 * 保存和更新实体。
	 */
	protected void saveOrUpdateEntity(Object entity) {
		getEntityService().saveOrUpdateEntity(entity);
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
		this.getEntityService().flush();
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
	 * 对给定的Object进行序列化.
	 * @param obj 待处理的对象。
	 * @return 序列化后的结果.
	 * @since 2.3
	 */
	public String squeezeObject(Object obj){
		return this.getDataSqueezer().squeeze(obj);
	}
	/**
	 * 给给定的字符串进行处理，提供给传递JS使用的字符串.
	 * @param str 待处理的字符串.
	 * @return 处理完的字符串.
	 * @since 2.3
	 * @see CornerUtils#enquoteString(String)
	 */
	public String enquoteString(String str){
		return CornerUtils.enquoteString(str);
	}

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
	 * @param ifNullDelete 当上传的实体为空的时候是否要删除该实体，如果为True，则删除实体。
	 * @since 2.2.2
	 */
	@SuppressWarnings("unchecked")
	protected <B extends IBlobModel> void saveBlobData(B blobEntity,boolean ifNullDelete){
		IBlobPageDelegate<B> delegate = new SqueezeBlobPageDelegate<B>(
				EntityService.getEntityClass(blobEntity), getUploadFile(), blobEntity, this
						.getEntityService(),ifNullDelete);
		delegate.save();
	}
	/**
	 * 得到对象的属性值，主要提供在页面的调用.
	 * @param rootObject 根对象.
	 * @param pro 属性名.
	 * @return 属性值
	 * @since 2.3.7
	 */
	public final Object getPropertyValue(Object rootObject,String pro){
		if(rootObject == null){
			return null;
		}
		return BeanUtils.getProperty(rootObject,pro);
	}
	
	/**
	 * 向页面写入IFrame
	 * @return
	 */
	public ILinkRenderer getIframeRenderPage() {
		return new ILinkRenderer() {
			public void renderLink(IMarkupWriter writer, IRequestCycle cycle,
					ILinkComponent linkComponent) {
				writer.begin("iframe");
				Iterator i = linkComponent.getBindingNames().iterator();
				while (i.hasNext()) {
					String name = (String) i.next();
					IBinding b = linkComponent.getBinding(name);
					if (b instanceof LiteralBinding
							&& !"src".equalsIgnoreCase(name)
							&& !"page".equalsIgnoreCase(name)) {
						writer.attribute(name, b.getObject().toString());
					}
				}
				ILink l = linkComponent.getLink(cycle);
				writer.attribute("src", l.getURL());
				writer.end("iframe");
			}
		};
	}
	
	/** 对日期类型的格式化 * */
	private SimpleDateFormat _dateFormat;
	
	/** 对url解析路径 * */
	private ILinkRenderer _rawUrlLinkrenderer;
	
	/**
	 * 获得全url路径
	 */
	public ILinkRenderer getRawUrlLinkRenderer(){
		if (_rawUrlLinkrenderer == null)
			_rawUrlLinkrenderer = new RawURLLinkRenderer();
		return _rawUrlLinkrenderer;
	}
}
