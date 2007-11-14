// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2006-05-16
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

package corner.orm.tapestry.service.blob;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.hivemind.util.Defense;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.engine.IEngineService;
import org.apache.tapestry.engine.ILink;
import org.apache.tapestry.error.RequestExceptionReporter;
import org.apache.tapestry.services.DataSqueezer;
import org.apache.tapestry.services.LinkFactory;
import org.apache.tapestry.util.ContentType;
import org.apache.tapestry.web.WebResponse;

import corner.model.IBlobModel;
import corner.orm.tapestry.utils.ComponentResponseUtils;
import corner.service.EntityService;

/**
 * 
 * 扩展tapestry的EngineService,提供了对blob的处理.
 * <p>
 * 提供了对blob的显示服务.
 * 
 * @author <a href="http://wiki.java.net/bin/view/People/JunTsai">Jun Tsai</a>
 * @version $Revision:3677 $
 * @since 2006-1-19
 * @see IEngineService
 */
public abstract class AbstractBlobService implements IEngineService {
	public static final String SERVICE_NAME = "blob";

	public static final String TABLE_TYPE_VAR = "table_type";

	public static final String TABLE_KEY_VAR = "table_key";

	private static final String ENTITY = "entity_blob";

	

	/** @since 4.0 */
	private RequestExceptionReporter _exceptionReporter;

	/** @since 4.0 */
	private LinkFactory _linkFactory;

	/** @since 4.0 */
	private WebResponse _response;

	private EntityService entityService;

	

	private DataSqueezer dataSqueezer;

	public ILink getLink(boolean post, Object parameter) {
		Defense.isAssignable(parameter, Object[].class, "parameter");

		Object[] blobParameters = (Object[]) parameter;
		Map<String,Object> parameters = new HashMap<String,Object>();
		if(blobParameters.length==1){
			parameters.put(ENTITY, this.dataSqueezer.squeeze(blobParameters[0]));
		}else{
			parameters.put(TABLE_TYPE_VAR, blobParameters[0]);
			parameters.put(TABLE_KEY_VAR, blobParameters[1]);
		}
		return _linkFactory.constructLink(this, false, parameters, false);
	}
	protected abstract Map<String,IBlobProvider> getBlobProviderMap();
	protected abstract Map<String, Class<? extends IBlobModel>> getBlobModelClassesMap();

	public void service(IRequestCycle cycle) throws IOException {
		String objStr=cycle.getParameter(ENTITY);
		if(objStr!=null){
			IBlobModel blob=(IBlobModel) this.dataSqueezer.unsqueeze(objStr);
			if(blob==null){ //纠正blob对象为空的NPE异常
				return;
			}
			ComponentResponseUtils.constructResponse(blob.getBlobName(),null,cycle,_response);
			outputStream(blob.getContentType(),blob.getBlobData());
			return;
		}
		String tableType = cycle.getParameter(TABLE_TYPE_VAR);
		String tableKey = cycle.getParameter(TABLE_KEY_VAR);

		try {
			//从blob提供者map中得到provider.
			IBlobProvider provider = getBlobProviderMap().get(tableType);
			
			if (provider == null) { //针对blob模型的处理
				Class<? extends IBlobModel>clazz=getBlobModelClassesMap().get(tableType);
				provider=new BlobModelBlobProvider(clazz);
			}

			provider.setKeyValue(tableKey);
			provider.setEntityService(entityService);
			String type = provider.getContentType();
			
			outputStream(type,provider.getBlobAsBytes());


		}

		catch (Throwable ex) {
			_exceptionReporter.reportRequestException(
					"Error display blob stream.", ex);

			return;
		}

		return;
	}
	/**
	 * 输出IO流
	 * @param contentType
	 * @param data
	 * @throws IOException
	 */
	private void outputStream(String contentType,byte[] data) throws IOException{
		if (data == null || contentType == null) {
			return;
		}

		OutputStream output = _response.getOutputStream(new ContentType(contentType));
		output.write(data);
	}
	public String getName() {
		return SERVICE_NAME;
	}

	/** @since 4.0 */
	public void setExceptionReporter(RequestExceptionReporter exceptionReporter) {
		_exceptionReporter = exceptionReporter;
	}

	/** @since 4.0 */
	public void setLinkFactory(LinkFactory linkFactory) {
		_linkFactory = linkFactory;
	}

	/** @since 4.0 */
	public void setResponse(WebResponse response) {
		_response = response;
	}

	public void setEntityService(EntityService entityService) {
		this.entityService = entityService;
	}
	/**
	 * 设置hibernate的DataSqueezer
	 * @param filter hibernate filter
	 * @since 2.2.1
	 */
    public void setDataSqueezer(DataSqueezer dataSqueezer){
    	this.dataSqueezer=dataSqueezer;
    }

}
