//==============================================================================
//file :        BlobImageService.java
//project:      poison-system
//
//last change:	date:       $Date$
//           	by:         $Author$
//           	revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	China Java Users Group http://cnjug.dev.java.net
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

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
import org.apache.tapestry.services.LinkFactory;
import org.apache.tapestry.util.ContentType;
import org.apache.tapestry.web.WebResponse;

import corner.orm.tapestry.service.blob.IBlobProvider;
import corner.service.EntityService;
import corner.util.BeanUtils;

/**
 * 
 * 扩展tapestry的EngineService,提供了对blob的处理.
 * <p>
 * 提供了对blob的显示服务.
 * 
 * @author <a href="http://wiki.java.net/bin/view/People/JunTsai">Jun Tsai</a>
 * @version $Revision$
 * @since 2006-1-19
 * @see IEngineService
 */
public abstract class AbstractBlobService implements IEngineService {
	public static final String SERVICE_NAME = "blob";

	public static final String TABLE_TYPE_VAR = "table_type";

	public static final String TABLE_KEY_VAR = "table_key";

	

	/** @since 4.0 */
	private RequestExceptionReporter _exceptionReporter;

	/** @since 4.0 */
	private LinkFactory _linkFactory;

	/** @since 4.0 */
	private WebResponse _response;

	private EntityService entityService;

	public ILink getLink(boolean post, Object parameter) {
		Defense.isAssignable(parameter, Object[].class, "parameter");

		Object[] blobParameters = (Object[]) parameter;
		Map parameters = new HashMap();

		// parameters.put(ServiceConstants.SERVICE, getName());
		parameters.put(TABLE_TYPE_VAR, blobParameters[0]);
		parameters.put(TABLE_KEY_VAR, blobParameters[1]);

		return _linkFactory.constructLink(this, false, parameters, false);
	}
	protected abstract Map<String,Class<?extends IBlobProvider>> getBlobProviderMap();

	public void service(IRequestCycle cycle) throws IOException {
		String tableType = cycle.getParameter(TABLE_TYPE_VAR);
		String tableKey = cycle.getParameter(TABLE_KEY_VAR);

		try {

			Class<? extends IBlobProvider> clazz = getBlobProviderMap()
					.get(tableType);
			if (clazz != null) {
				IBlobProvider provider = BeanUtils.instantiateClass(clazz);
				provider.setKeyValue(tableKey);
				provider.setEntityService(entityService);
				String type = provider.getContentType();
				if (provider.getBlobAsBytes() == null || type == null) {
					return;
				}

				OutputStream output = _response
						.getOutputStream(new ContentType(type));
				output.write(provider.getBlobAsBytes());
			} else {

			}

		}

		catch (Throwable ex) {
			_exceptionReporter.reportRequestException(
					"Error display blob stream.", ex);

			return;
		}

		return;
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
}
