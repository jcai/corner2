//==============================================================================
//file :        MagicFieldWorker.java
//
//last change:	date:       $Date$
//           	by:         $Author$
//           	revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Wang Tsai Studio http://www.wtstudio.org
//==============================================================================

package corner.orm.tapestry.worker;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.MappedSuperclass;

import org.apache.hivemind.ApplicationRuntimeException;
import org.apache.hivemind.Location;
import org.apache.hivemind.Resource;
import org.apache.hivemind.service.MethodSignature;
import org.apache.tapestry.annotations.AnnotationUtils;
import org.apache.tapestry.annotations.Component;
import org.apache.tapestry.annotations.SecondaryAnnotationWorker;
import org.apache.tapestry.enhance.EnhancementOperation;
import org.apache.tapestry.spec.BindingSpecification;
import org.apache.tapestry.spec.BindingType;
import org.apache.tapestry.spec.ContainedComponent;
import org.apache.tapestry.spec.IBindingSpecification;
import org.apache.tapestry.spec.IComponentSpecification;
import org.apache.tapestry.spec.IContainedComponent;
import org.apache.tapestry.spec.IPropertySpecification;
import org.apache.tapestry.util.DescribedLocation;
import org.hibernate.reflection.ReflectionManager;
import org.hibernate.reflection.XClass;
import org.hibernate.reflection.XProperty;
import org.hibernate.reflection.java.JavaXFactory;
import org.hibernate.validator.Max;

/**
 * 对tapestry的根据模型自动产生Component
 * 
 * <p>
 * 采用Hibernate的反射管理器和ejb3.0中的实体bean注释，动态生成tapestry组件。<br/>
 * 需要注意的是：页面类中使用注释{@link MagicField}，其中{@link MagicField#entity()}参数为页面类中实体对应的属性值.
 *    
 * @author jcai
 * @version $Revision$
 * @since 2.3
 */
public class MagicFieldWorker implements SecondaryAnnotationWorker {
	/**
	 * 反射管理器。thanks hibernate
	 */
	private  ReflectionManager reflectionManager=new JavaXFactory();
	/** 从初始化值中得到类的名称 **/
	private static final String GET_CLASS_NAME_REG = "\\s([\\w\\.]+)\\s*\\(\\)";

	

	/**
	 * 是否能够增强
	 * @see org.apache.tapestry.annotations.SecondaryAnnotationWorker#canEnhance(java.lang.reflect.Method)
	 */
	public boolean canEnhance(Method method) {
		return method.getAnnotation(MagicField.class) != null;
	}

	/**
	 * 对使用了{@link MagicField}的注释进行增强。
	 * @see org.apache.tapestry.annotations.SecondaryAnnotationWorker#peformEnhancement(org.apache.tapestry.enhance.EnhancementOperation, org.apache.tapestry.spec.IComponentSpecification, java.lang.reflect.Method, org.apache.hivemind.Resource)
	 */
	public void peformEnhancement(EnhancementOperation op,
			IComponentSpecification spec, Method method, Resource resource) {
		
		MagicField magicField = method.getAnnotation(MagicField.class);
		String entityName = magicField.entity();
		IPropertySpecification pSpec = spec
				.getPropertySpecification(entityName);// 获取对应属性的配置说明
		if (pSpec.getInitialValue() == null) {
			return;
		}

		String initialValue = pSpec.getInitialValue();// 得到初始值的字符串
		String className = this.getClassNameFromInitialValue(initialValue);
		if (className == null) {
			throw new ApplicationRuntimeException("不能解析给定的初始化值:[" + initialValue
					+ "]");
		}
		try {
			Class<?> clazz = Class.forName(className);
			XClass xclazz=reflectionManager.toXClass(clazz);
			List<XProperty> list=new ArrayList<XProperty>();
			getElementsToProcess(xclazz,list);//得到所有处理的属性名称
			Location location = AnnotationUtils.buildLocationForAnnotation(method, magicField, resource);
			
			for(XProperty pro:list){
				Location cLocation=new DescribedLocation(resource,String.format("类%s的属性%s",className,pro.getName()));
				createContainerComponent(op,pro,spec,cLocation); //通过给定的属性名称来
			}
			//不能直接在页面类中调用magicField的属性,
			//TODO 改为加在页面的类中.
			op.addMethod(Modifier.PUBLIC, new MethodSignature(method), "throw new UnsupportedOperationException(\"不能调用此方法！此方法仅仅用来动态生成组件使用!\");", location);
		} catch (ClassNotFoundException e) {
			throw new ApplicationRuntimeException(e);
		}
		
	}

	private void createContainerComponent(EnhancementOperation op,XProperty pro, IComponentSpecification spec,Location location) {
		IContainedComponent cc = new ContainedComponent();
		Component c=pro.getAnnotation(Component.class);
		if(c!=null){
			cc.setInheritInformalParameters(c.inheritInformalParameters());
			cc.setType(c.type());//根据给定的的类型
			for (String binding : c.bindings())
	        {
	            addBinding(cc, binding, location);
	        }
		}else{
			cc.setType("TextField");//默认的组件类型
		}
        cc.setLocation(location);
        cc.setPropertyName(pro.getName()+"Field");
        
        if(cc.getBinding("value")==null){
        	addValueBinding(cc,pro,location);//加入默认的value属性binding
        }
        //加入Hibernate的验证
        StringBuffer sb=new StringBuffer();
        for(Annotation annotation:pro.getAnnotations()){
        	//for max
        	if(annotation.annotationType().equals(Max.class)){
        		sb.append("max="+((Max) annotation).value());
        	}
        }
        
        spec.addComponent(pro.getName()+"Field",cc);
		
	}

	private void addValueBinding(IContainedComponent cc,XProperty pro,Location location) {
		IBindingSpecification bs = new BindingSpecification();
		bs.setType(BindingType.PREFIXED);
		bs.setValue("entity."+pro.getName());
		bs.setLocation(location);
		cc.setBinding("value",bs);
		
	}

	String getClassNameFromInitialValue(String value) {
		Matcher matcher = Pattern.compile(GET_CLASS_NAME_REG).matcher(value);
		if (matcher.find()) {
			return matcher.group(1);
		}

		return null;

	}

	void getElementsToProcess(XClass clazz, List<XProperty> list) {
		if (!reflectionManager.equals(clazz, Object.class) && clazz != null) {
			list.addAll(0, clazz.getDeclaredProperties(XClass.ACCESS_PROPERTY));
			XClass superClass = clazz.getSuperclass();
			if (superClass != null
					&& superClass.isAnnotationPresent(MappedSuperclass.class)) {
				getElementsToProcess(clazz.getSuperclass(), list);
			}
		}
	}
    void addBinding(IContainedComponent component, String binding, Location location)
    {
        int equalsx = binding.indexOf('=');

        if (equalsx < 1)
            invalidBinding(binding);

        if (equalsx + 1 >= binding.length())
            invalidBinding(binding);

        String name = binding.substring(0, equalsx).trim();
        String value = binding.substring(equalsx + 1).trim();

        IBindingSpecification bs = new BindingSpecification();
        bs.setType(BindingType.PREFIXED);
        bs.setValue(value);
        bs.setLocation(location);

        component.setBinding(name, bs);
    }

	private void invalidBinding(String binding) {
		throw new ApplicationRuntimeException("错误的binding"+binding);
		
	}    
}
