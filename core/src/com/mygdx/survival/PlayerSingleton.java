package com.mygdx.survival;

public class PlayerSingleton {
    private static PlayerSingleton ourInstance = new PlayerSingleton();

    public static PlayerSingleton getInstance() {
        return ourInstance;
    }

    public OperatorFree player;

    private PlayerSingleton() {
    }
}
