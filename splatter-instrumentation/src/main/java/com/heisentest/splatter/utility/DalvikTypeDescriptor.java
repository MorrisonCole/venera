package com.heisentest.splatter.utility;

public class DalvikTypeDescriptor {

    public static String typeDescriptorForClass(Class<?> clazz) {
        String clazzName = clazz.getName();

        return String.format("L%s;", clazzName.replace(".", "/"));
    }
}
