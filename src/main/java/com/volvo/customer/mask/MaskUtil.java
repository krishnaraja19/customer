package com.volvo.customer.mask;

import java.lang.reflect.Field;

public final class MaskUtil {
    private MaskUtil(){

    }

    public static String maskStringField(Class<?> clss, final String fieldName, final String currValue) {
        for (Field field : clss.getDeclaredFields()) {
            String fName = field.getName();

            if (fieldName.equals(fName)) {
                if (field.isAnnotationPresent(MaskZipCode.class)) {
                    MaskZipCode mask = field.getAnnotation(MaskZipCode.class);
                    if (mask.value() != null && !mask.value().isEmpty()) {
                        return mask.value();
                    }
                } else if (field.isAnnotationPresent(MaskChar.class)) {
                    MaskChar mask = field.getAnnotation(MaskChar.class);
                    if (mask.value() != ' ') {
                        String part1 = currValue.substring(0, currValue.length() - 4).replaceAll("[0-9]",
                                String.valueOf(mask.value()));

                        String part2 = currValue.substring(currValue.length() - 4);

                        return part1 + part2;
                    }
                }
            }
        }

        return currValue;
    }

}
