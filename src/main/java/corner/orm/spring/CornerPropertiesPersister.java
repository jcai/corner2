//==============================================================================
//file :        CornerPropertiesPersister.java
//
//last change:	date:       $Date: 2006-07-03 07:17:35Z $
//           	by:         $Author: jcai $
//           	revision:   $Revision: 210 $
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//==============================================================================

package corner.orm.spring;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderSupport;
import org.springframework.util.DefaultPropertiesPersister;

/**
 * 对属性文件能够进行导入处理
 * @author jcai
 * @version $Revision$
 * @since 2.0.5
 */
public class CornerPropertiesPersister extends DefaultPropertiesPersister
		implements ApplicationContextAware {
	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactory
			.getLog(CornerPropertiesPersister.class);
	/**
	 * spring application context
	 */
	private ApplicationContext ctx;
	/**
	 * 
	 * @see org.springframework.util.PropertiesPersister#load(java.util.Properties, java.io.InputStream)
	 */
	public void load(Properties props, InputStream is) throws IOException {
		props.load(is);
		importConfig(props);
	}
	/**
	 * 
	 * @see org.springframework.util.PropertiesPersister#load(java.util.Properties, java.io.Reader)
	 */
	public void load(Properties props, Reader reader) throws IOException {
		super.load(props, reader);
		importConfig(props);


	}
	/**
	 * 导入可配置的属性。
	 * @param props 属性
	 * @throws IOException 加入发生文件的读操作异常。
	 */
	private void importConfig(Properties props) throws IOException{
		Enumeration propertyNames = props.propertyNames();

		while (propertyNames.hasMoreElements()) {
			String propertyName = (String) propertyNames.nextElement();
			if (propertyName.startsWith("config.import")) {
				Resource location = this.ctx.getResource(props.getProperty(propertyName));
				loadImportConfigFile(props, location);
			}

		}
	}
	/**
	 * 通过给定的resoure和属性集合来装载配置文件。
	 * @param props 属性集合。
	 * @param location 资源路径。
	 * @throws IOException 加入发生文件读异常。
	 */
	private void loadImportConfigFile(Properties props, Resource location)
			throws IOException {
		InputStream is = null;
		try {
			is = location.getInputStream();
			if (location.getFilename().endsWith(
					PropertiesLoaderSupport.XML_FILE_EXTENSION)) {
				this.loadFromXml(props, is);
			} else {

				this.load(props, is);

			}
		} catch (IOException ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("Could not load properties from " + location + ": "
						+ ex.getMessage());
			}

		} finally {
			if (is != null) {
				is.close();
			}
		}

	}

	/**
	 * 
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	public void setApplicationContext(ApplicationContext ctx)
			throws BeansException {
		this.ctx = ctx;

	}
}
