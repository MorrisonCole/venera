package com.heisentest.splatter;

import com.google.common.collect.ArrayListMultimap;

import java.util.HashMap;

public class Descriptors {

    // Insert a new parameter as the first parameter
    public static String insertParamAtStart(String desc, String param) {
        StringBuilder newDesc = new StringBuilder(desc);

        int index = advanceOne(desc, 0);

        newDesc.insert(index, param);

        return newDesc.toString();
    }

    // Get number of parameters from method description string
    public static int numParamRegisters(String desc) {
        int index = advanceOne(desc, 0); // Skip the return type
        int params = 0;

        while (index < desc.length()) {
            char c = desc.charAt(index);
            params++;
            if (c == 'J' || c == 'D') // Extra register for longs and doubles
                params++;
            index = advanceOne(desc, index);
        }

        return params;
    }

    // Get number of parameters from method description string
    public static int numPrimitiveParams(String desc) {
        int index = advanceOne(desc, 0); // Skip the return type.
        int primitiveParams = 0;

        while (index < desc.length()) {
            char c = desc.charAt(index);
            if (c != 'L') {
                primitiveParams++;
            }

            index = advanceOne(desc, index);
        }

        return primitiveParams;
    }

    // Get index of next parameter in description string
    public static int advanceOne(String desc, int index) {
        char c = desc.charAt(index);

        if (c == 'L') {
            index = desc.indexOf(';', index);
        } else if (c == '[') {
            do {
                index++;
                c = desc.charAt(index);
            } while (c == '[');
        }

        if (c == 'L') {
            index = desc.indexOf(';', index);
        }
        index++;

        return index;
    }

    public static ArrayListMultimap<Character, Integer> mappedParams(String desc) {
        int index = advanceOne(desc, 0); // Skip the return type.
        int relativeParameterIndex = 0;
        ArrayListMultimap<Character, Integer> map = ArrayListMultimap.create();

        while (index < desc.length()) {
            char c = desc.charAt(index);
            map.put(c, relativeParameterIndex);

            relativeParameterIndex++;
            if (c == 'J' || c == 'D') {
                relativeParameterIndex++; // Extra register for longs and doubles
            }

            index = advanceOne(desc, index);
        }

        return map;
    }

    public static int numParams(String desc) {
        int index = advanceOne(desc, 0); // Skip the return type.
        int params = 0;

        while (index < desc.length()) {
            params++;
            index = advanceOne(desc, index);
        }

        return params;
    }
}
