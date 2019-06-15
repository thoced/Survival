package com.mygdx.lights;

public class BDLIGHT {

    public static final short CATEGORY_BALL = 1;
    public static final short CATEGORY_LINE = 2;
    public static final short CATEGORY_WALL = 4;
    public static final short CATEGORY_SQUARE_OBJECTIVE = 8;
    public static final short CATEGORY_TRIANGLE_OBJECTIVE = 16;
    public static final short CATEGORY_LIGHT = 32;

    public static final short LIGHT_GROUP = 1;

    public static final short MASK_BALL =
            CATEGORY_LINE |
                    CATEGORY_WALL |
                    CATEGORY_SQUARE_OBJECTIVE |
                    CATEGORY_TRIANGLE_OBJECTIVE;

    public static final short MASK_LIGHT =
            CATEGORY_LINE |
                    CATEGORY_SQUARE_OBJECTIVE |
                    CATEGORY_TRIANGLE_OBJECTIVE |
                    CATEGORY_WALL;

    public static final short MASK_SQUARE =
            CATEGORY_BALL |
                    CATEGORY_LIGHT;

    public static final short MASK_WALL = -1;
}
