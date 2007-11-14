// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2007-09-20
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

package corner.orm.tapestry.worker;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.apache.hivemind.ApplicationRuntimeException;
import org.apache.hivemind.Resource;
import org.apache.hivemind.service.MethodSignature;
import org.apache.hivemind.util.Defense;
import org.apache.tapestry.annotations.AnnotationUtils;
import org.apache.tapestry.annotations.SecondaryAnnotationWorker;
import org.apache.tapestry.enhance.EnhanceUtils;
import org.apache.tapestry.enhance.EnhancementOperation;
import org.apache.tapestry.spec.IComponentSpecification;

import com.google.inject.Injector;

/**
 * 对InjectGuice进行增强.
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.3.7
 * @see InjectGuice
 */
public class InjectGuiceWorker implements SecondaryAnnotationWorker{

	private Injector injector;
	/**
	 * 
	 * @see org.apache.tapestry.annotations.SecondaryAnnotationWorker#canEnhance(java.lang.reflect.Method)
	 */
	public boolean canEnhance(Method method) {
		return method.getAnnotation(InjectGuice.class) != null;
	}
	public void setInjector(Injector injector){
		this.injector=injector;
	}
	/**
	 * 
	 * @see org.apache.tapestry.annotations.SecondaryAnnotationWorker#peformEnhancement(org.apache.tapestry.enhance.EnhancementOperation, org.apache.tapestry.spec.IComponentSpecification, java.lang.reflect.Method, org.apache.hivemind.Resource)
	 */
	public void peformEnhancement(EnhancementOperation op, IComponentSpecification spec, Method method, Resource classResource) {
		
		Defense.notNull(this.injector,"guice容器");
		
		InjectGuice io = method.getAnnotation(InjectGuice.class);
        Class<?> object = io.value();

        String propertyName = AnnotationUtils.getPropertyName(method);
        Class propertyType = op.getPropertyType(propertyName);
        if (propertyType == null)
            propertyType = Object.class;

        op.claimReadonlyProperty(propertyName);
        
        Object injectedValue =injector.getInstance(object);

        if (injectedValue == null)
            throw new ApplicationRuntimeException("不能从guice中获得对象",spec.getLocation(), null);

        if (!propertyType.isInstance(injectedValue))
            throw new ApplicationRuntimeException("错误的对象类型,属性类型为:"+propertyType.getName()+" injectValue:"+injectedValue.getClass().getName(),
                                                  spec.getLocation(), null);

        String fieldName = op.addInjectedField("_$" + propertyName,
                                               propertyType, injectedValue);

        String methodName = EnhanceUtils.createAccessorMethodName(propertyName);

        op.addMethod(Modifier.PUBLIC, new MethodSignature(propertyType,
                                                          methodName, null, null), "return " + fieldName + ";", spec.getLocation());
        
        
	}

}
