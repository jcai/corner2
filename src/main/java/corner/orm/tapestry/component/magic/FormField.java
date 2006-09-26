//==============================================================================
// file :       $Id$
// project:     corner
//
// last change: date:       $Date$
//              by:         $Author$
//              revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.orm.tapestry.component.magic;

import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.hivemind.ApplicationRuntimeException;
import org.apache.hivemind.Location;
import org.apache.tapestry.IBinding;
import org.apache.tapestry.form.AbstractFormComponent;
import org.apache.tapestry.form.IFormComponent;
import org.apache.tapestry.spec.BindingSpecification;
import org.apache.tapestry.spec.BindingType;
import org.apache.tapestry.spec.ContainedComponent;
import org.apache.tapestry.spec.IBindingSpecification;
import org.apache.tapestry.spec.IContainedComponent;

/**
 * 构造一个能够自适应的表单组件
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.2.1
 */
public abstract class FormField extends AbstractFormComponent {
	public FormField(){
		System.out.println(this.getSpecification());
	}
	public abstract IContainedComponent getDelegateComponent();
	public abstract void setDelegateComponent(IContainedComponent cc);
	/**
	 * @see org.apache.tapestry.AbstractComponent#setBinding(java.lang.String, org.apache.tapestry.IBinding)
	 */
	@Override
	public void setBinding(String component, IBinding binding) {
		if("type".equalsIgnoreCase(component)){
			IContainedComponent cc = new ContainedComponent();

//	        cc.setInheritInformalParameters(component.inheritInformalParameters());
	        cc.setType((String) binding.getObject());
	        
	        cc.setLocation(binding.getLocation());

	        this.getSpecification().addComponent(this.getId()+"child",cc);
	        
	        for(Iterator it=this.getBindings().entrySet().iterator();it.hasNext();){
	        	Entry entry=(Entry) it.next();
	        	cc.setBinding((String)entry.getKey(),(IBindingSpecification)entry.getValue());
	        }
	        
	        this.setDelegateComponent(cc);
	        	        
		}
		if(this.getDelegateComponent()!=null){
//			this.getDelegateComponent().setBinding(component,binding);
		}
		
//		super.setBinding(arg0, arg1);
	}
	
	}
