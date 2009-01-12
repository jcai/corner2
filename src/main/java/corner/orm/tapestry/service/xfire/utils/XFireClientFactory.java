package corner.orm.tapestry.service.xfire.utils;

import java.net.MalformedURLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.xfire.annotations.AnnotationServiceFactory;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;
import org.springframework.util.Assert;

/**
 * XFire Client 工厂类.
 * <p/>
 * <p>封装了XFire有点奇怪的根据Web服务接口类和Service URL 生成客户端Proxy类的代码. 同时使用泛型,减少了返回时的强制类型转换.</p>
 *
 * @author calvin
 */
@SuppressWarnings("unchecked")
public class XFireClientFactory {
	private static XFireProxyFactory serviceFactory = new XFireProxyFactory();

	private static final Log log = LogFactory.getLog(XFireClientFactory.class);

	private XFireClientFactory() {
	}

	/**
	 * 获得POJO形式的Web服务的客户端Proxy类.
	 *
	 * @param serviceURL   Web ServiceURL
	 * @param serviceClass Web Service接口类
	 * @return 类型为Web Service接口的客户端Proxy类
	 */
	public static <T> T getClient(String serviceURL, Class<T> serviceClass) {
		Assert.notNull(serviceURL);
		Assert.notNull(serviceClass);
		Service serviceModel = new ObjectServiceFactory().create(serviceClass);
		try {
			return (T) serviceFactory.create(serviceModel, serviceURL);
		} catch (MalformedURLException e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * 获得JSR181形式定义的Web服务的客户端Proxy类. 注意和普通的生成客户端方法不同,需要多一个implClass参数.
	 *
	 * @param serviceURL   Web ServiceURL.
	 * @param serviceClass Web Service接口类.
	 * @param implClass	Web Service街口的服务端实现类.
	 * @return 类型为Web Service接口的客户端Proxy类.
	 */
	public static <T> T getJSR181Client(String serviceURL, Class<T> serviceClass, Class implClass) {
		Assert.notNull(serviceURL);
		Assert.notNull(serviceClass);
		Service serviceModel = new AnnotationServiceFactory().create(implClass);
		try {
			return (T) serviceFactory.create(serviceModel, serviceURL);
		} catch (MalformedURLException e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}
}
