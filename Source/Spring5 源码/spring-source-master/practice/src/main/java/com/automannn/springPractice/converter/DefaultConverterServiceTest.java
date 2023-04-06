package com.automannn.springPractice.converter;

import org.springframework.core.ResolvableType;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;

import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;

import java.util.Collections;
import java.util.Set;

/**
 * @author chenkh
 * @time 2021/9/22 10:02
 */
public class DefaultConverterServiceTest {
    public static void main(String[] args) {
        DefaultConversionService defaultConversionService = new DefaultConversionService();

        MyBeanConverter myBeanConverter = new MyBeanConverter();
        myBeanConverter.setConversionService(defaultConversionService);
        defaultConversionService.addConverter(myBeanConverter);
        ResolvableType resolvableTypeTarget = ResolvableType.forClass(MyBean.class);
        ResolvableType resolvableTypeSource = ResolvableType.forClass(String.class);
        TypeDescriptor sourceDescriptor = new TypeDescriptor(resolvableTypeSource,null,null);
        TypeDescriptor targetDescriptor = new TypeDescriptor(resolvableTypeTarget,null,null);

        Object result= defaultConversionService.convert("test",sourceDescriptor,targetDescriptor);
        System.out.println(result);

    }

    static final class MyBean{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    static final class MyBeanConverter implements ConditionalGenericConverter {
        private ConversionService conversionService;



        public Set<ConvertiblePair> getConvertibleTypes() {
            return Collections.singleton(new ConvertiblePair(String.class, MyBean.class));
        }

        public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
            if (targetType.getType()==MyBean.class){
                MyBean myBean = new MyBean();
                myBean.setName((String) source);
                return myBean;
            }
            return null;
        }

        public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
            return canConvertElements(sourceType, targetType.getElementTypeDescriptor(),
                    this.conversionService);
        }

        public boolean canConvertElements(@Nullable TypeDescriptor sourceElementType,
                                                 @Nullable TypeDescriptor targetElementType, ConversionService conversionService) {

            if (targetElementType == null) {
                // yes
                return true;
            }
            if (sourceElementType == null) {
                // maybe
                return true;
            }
            if (conversionService.canConvert(sourceElementType, targetElementType)) {
                // yes
                return true;
            }
            if (ClassUtils.isAssignable(sourceElementType.getType(), targetElementType.getType())) {
                // maybe
                return true;
            }
            // no
            return false;
        }

        public ConversionService getConversionService() {
            return conversionService;
        }

        public void setConversionService(ConversionService conversionService) {
            this.conversionService = conversionService;
        }
    }
}
