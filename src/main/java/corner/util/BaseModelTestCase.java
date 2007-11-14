// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2007-09-28
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

package corner.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import corner.orm.spring.SpringContainer;

/**
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision$
 * @since 2.3.7
 */
public class BaseModelTestCase extends Assert {
	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactory
			.getLog(BaseModelTestCase.class);

	protected org.hibernate.classic.Session session;

	private long end;

	private long star;

	private HibernateTransactionManager transactionManager;

	// 默认的事物定义
	private TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();

	private TransactionStatus status;

	@BeforeMethod(groups = "model")
	public void initSessionFactory() {
		star = System.currentTimeMillis();
		if (transactionManager == null) {
			initSpring();
		}
		status = transactionManager.getTransaction(transactionDefinition);
		session = transactionManager.getSessionFactory().getCurrentSession();

	}

	@AfterMethod(groups = "model")
	public void closeSessionFactory() {
		transactionManager.commit(status);
		end = System.currentTimeMillis();
		logger.info("共耗时：" + (end - star) + "毫秒"); //$NON-NLS-1$
	}

	protected <T> T saveOrUpdate(T obj) {
		session.saveOrUpdate(obj);
		return obj;

	}

	@SuppressWarnings("unchecked")
	protected <T> T load(Class<T> clazz, Serializable key) {
		return (T) session.load(clazz, key);
	}

	/**
	 * 清空hibernate的缓存。
	 * 
	 */
	protected void flushAndClearSession() {
		session.flush();
		session.clear();
	}

	/**
	 * 得到映射的文件目录
	 * 
	 * @return 文件映射文件目录.
	 */
	protected String[] getMappingDirectoryLocations() {
		return new String[] {};
	}

	protected String[] getMappingResources() {
		return null;
	}

	private Resource[] getMappingDirectoryResources() {
		if (this.getMappingDirectoryLocations() == null) {
			return new Resource[0];
		}
		List<Resource> list = new ArrayList<Resource>();
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		for (int i = 0; i < getMappingDirectoryLocations().length; i++) {
			list.add(resolver
					.getResource(this.getMappingDirectoryLocations()[i]));

		}
		return list.toArray(new Resource[0]);
	}

	private void initSpring() {
		//xf 机器已经更新，采用完全模式进行测试
		//if ("true".equals(System.getProperty("test.single"))) {
		if(2>1){
			transactionManager = (HibernateTransactionManager) SpringContainer.getInstance()
					.getApplicationContext().getBean("transactionManager");

		} else {
			SpringContainer container = SpringContainer
					.getInstance("classpath:/config/spring/application-database.xml");

			AnnotationSessionFactoryBean factoryBean = new org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean();
			factoryBean.setDataSource((DataSource) container
					.getApplicationContext().getBean("dataSource"));
			Properties ps = new Properties();
			ps.put("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
			ps.put("hibernate.cache.use_second_level_cache", "false");
			factoryBean.setHibernateProperties(ps);
			factoryBean.setConfigLocation(new ClassPathResource(
					"hibernate.cfg.xml"));
			factoryBean
					.setMappingDirectoryLocations(getMappingDirectoryResources());
			factoryBean.setLobHandler((LobHandler) container
					.getApplicationContext().getBean("defaultLobHandler"));
			if (getMappingResources() != null) {
				factoryBean.setMappingResources(getMappingResources());
			}
			try {
				factoryBean.afterPropertiesSet();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}

			SessionFactory factory = (SessionFactory) factoryBean.getObject();

			transactionManager = new org.springframework.orm.hibernate3.HibernateTransactionManager();
			transactionManager.setSessionFactory(factory);
		}
	}

}