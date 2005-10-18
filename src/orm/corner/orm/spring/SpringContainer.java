//==============================================================================
//file :        SpringContainer.java
//project:      poisoning
//
//last change:	date:       $Date$
//           	by:         $Author$
//           	revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	China Java Users Group http://cnjug.dev.java.net
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.orm.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 针对Spring的包装。
 * @author	<a href="http://wiki.java.net/bin/view/People/JunTsai">Jun Tsai</a>
 * @version	$Revision$
 * @since	2005-9-9
 */

public class SpringContainer {

	private static Object obj=new Object();
	private static SpringContainer instance;
	private static final Log log = LogFactory.getLog(SpringContainer.class);
	private static final String DEFAULT_CONFIG_FILES="classpath:/config/spring/application-*.xml";
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
