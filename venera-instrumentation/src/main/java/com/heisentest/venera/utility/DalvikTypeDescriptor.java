package com.heisentest.venera.utility;

public class DalvikTypeDescriptor {

    public static String typeDescriptorForClass(Class<?> clazz) {
        String className = clazz.getName();

        return typeDescriptorForClassWithName(className);
    }

    public static String typeDescriptorForClassWithName(String className) {
        return String.format("L%s;", className.replace(".", "/"));
    }
}
