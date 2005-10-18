/*
 * Created on 2004-10-24
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package corner.orm.lucene.xerdoc.xdmf.lucene;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;

/**
 * @author Winters.J.Mi
 * @author Jun Tsai
 * 
 */
public class DictionaryFactory implements InitializingBean{
	private Map dictionaries_ = null;
	
	private Resource location;

	

	public  AbstractDictionary getDictionary(String name) {
		return (AbstractDictionary) dictionaries_.get(name);
	}
	public void setDictionariesLocation(Resource location){
		this.location=location;
	}
	public void afterPropertiesSet() throws Exception {
		if(!location.getFile().isDirectory()){
			throw new BeanInitializationException("dictionary path must be directory!");
		}
		dictionaries_ = new HashMap();

		
		File dicDir = location.getFile();

		if (dicDir.isDirectory()) {
			File[] children = dicDir.listFiles(new FilenameFilter() {
				public boolean accept(File dir, String name) {
					return dir.isDirectory();
				}
			});

			if (children != null) {
				int length = children.length;
				for (int i = 0; i < length; i++) {
					File each = children[i];

					String name = each.getName();
					System.out.println("loading dictionary ["+name+"]");
					AbstractDictionary dic = new SimpleDictionary(name, each
							.getAbsolutePath());
					dictionaries_.put(name, dic);
					dic.loadDictionary();
				}
			}
		}

	}
	
}
