package com.qa.Todo.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

public class TodoBeanUtils {

    public static void mergeNotNull(Object source, Object target) {
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }

    // this method allows your update() method to use all the source object's values as default
    // for instance, if updating a guitarist or band, i only need to include the values i want to change, e.g. "name":"new value"
    private static String[] getNullPropertyNames(Object source) {
        final BeanWrapper wrappedSourceObject = new BeanWrapperImpl(source);

        Set<String> propertyNames = new HashSet<>();
        for (PropertyDescriptor propertyDescriptors : wrappedSourceObject.getPropertyDescriptors()) {
            if (wrappedSourceObject.getPropertyValue(propertyDescriptors.getName()) == null)
                propertyNames.add(propertyDescriptors.getName());
        }
        return propertyNames.toArray(new String[propertyNames.size()]);
    }
}
