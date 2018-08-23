package com.qchery.kada.descriptor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Chery
 * @date 2018/8/23 00:00
 */
public class Space {

    private static final Map<Integer, String> SPACE_MAP = new HashMap<Integer, String>() {{
        put(4, "    ");
        put(5, "     ");
        put(6, "      ");
        put(8, "        ");
        put(16, "                ");
    }};

    private static String of(int count) {
        return SPACE_MAP.get(count);
    }

    public static String ofFour() {
        return of(4);
    }

    public static String ofSix() {
        return of(6);
    }

    public static String ofEight() {
        return of(8);
    }

    public static String ofFive() {
        return of(5);
    }

    public static String ofSixteen() {
        return of(16);
    }
}
