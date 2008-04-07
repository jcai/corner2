// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2007-09-14
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

package corner.orm.tapestry.jasper;

import java.util.Collection;
import java.util.Iterator;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

import org.apache.tapestry.IBinding;
import org.apache.tapestry.IPage;
import org.apache.tapestry.binding.BindingConstants;
import org.apache.tapestry.binding.BindingSource;

/**
 * 利用ognl来实现的Jasper的datasource.
 * 
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.3.7
 */
public class JREntityDataSource implements JRDataSource{
	private int i=0;
	private BindingSource bindingSource;
	private IPage page;
	private Iterator source;
	private String objRefer;
	public JREntityDataSource(BindingSource source,IPage page){
		this.bindingSource=source;
		this.page = page;
	}
	public JREntityDataSource(BindingSource source,IPage page,String sourceRefer,String rowObjectRefer){
		this.bindingSource=source;
		this.objRefer = rowObjectRefer;
		this.page = page;
		if(sourceRefer != null){
			IBinding binding = getOrCreateBinding("source ognl",sourceRefer);
			this.source = ((Collection) binding.getObject()).iterator();
		}
	}
	/**
	 * 
	 * @see net.sf.jasperreports.engine.JRDataSource#getFieldValue(net.sf.jasperreports.engine.JRField)
	 */
	public Object getFieldValue(JRField jrField) throws JRException {
	 IBinding binding = getOrCreateBinding(jrField.getName(),jrField.getDescription());
	  return binding.getObject();
	}
	private IBinding getOrCreateBinding(String description,String reference){
		
		IBinding binding = page.getBinding(reference);
		if(binding == null){
			binding = bindingSource.createBinding(page,description,reference,BindingConstants.OGNL_PREFIX,page.getLocation());
			//缓存此binding.
			page.setBinding(reference, binding);
		}
		return binding;
	}
	/**
	 * 
	 * @see net.sf.jasperreports.engine.JRDataSource#next()
	 */
	public boolean next() throws JRException {
		if(source == null ){
			return i++==0;
		}
		if(!this.source.hasNext()){
			return false;
		}
		Object obj = this.source.next();
		if(this.objRefer!=null){
			IBinding binding = getOrCreateBinding("row object reference", objRefer);
			binding.setObject(obj);
		}
		return true;
	}
}
