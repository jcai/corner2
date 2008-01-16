// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2005-10-18
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

package corner.orm.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 针对Spring的包装。
 * @author	<a href="http://wiki.java.net/bin/view/People/JunTsai">Jun Tsai</a>
 * @version	$Revision:3677 $
 * @since	2005-9-9
 */

public class SpringContainer {

	private static Object obj=new Object();
	private static SpringContainer instance;
	private static final Log log = LogFactory.getLog(SpringContainer.class);
	/** 默认情况下,Spring配置路径.**/
	public static final String DEFAULT_CONFIG_FILES="classpath:/config/spring/application-*.xml";
	private ClassPathXmlApplicationContext applicationContext;
	
	private SpringContainer(){
		initContainer();
	}
	private SpringContainer(String... config) {
		initContainer(config);
	}
	private void initContainer(String... config) {
		log.info("using config file ["+config+"]");
		if(config==null||config.length==0){
			log.info("using default config file ["+DEFAULT_CONFIG_FILES+"]");
			initContainer(DEFAULT_CONFIG_FILES);
		}else{
			this.applicationContext=new ClassPathXmlApplicationContext(config);
		}
		
	}
	/**
	 * 得到默认的容器类.
	 * <p>默认情况下,在classpath:/config/spring/application-*.xml下面查找文件.
	 * @return Spring容器.
	 */
	public static SpringContainer getInstance(){
		if(instance==null){
			synchronized(obj){
				if(instance==null){
					
					if (log.isInfoEnabled()) {
						log.info("inital spring container.");
					}
					instance=new SpringContainer();
				}
			}
		}
		return instance;
		
	}
	/**
	 * 根据给定的配置文件来生成容器..
	 * @param config 配置文件.
	 * @return 实例化的Spring容器.
	 */
	public static SpringContainer getInstance(String ... config){
		if(instance==null){
			synchronized(obj){
				if(instance==null){
					
					if (log.isInfoEnabled()) {
						log.info("inital spring container.");
					}
					instance=new SpringContainer(config);
					
				}
			}
		}
		return instance;
		
	}
	/**
	 * 得到Spring的ApplicationContext。
	 * @return spring的context。
	 */
	public ApplicationContext getApplicationContext(){
		return this.applicationContext;
	}
}
