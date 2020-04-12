package com.voltiosx.wavescore.models;

public abstract class FixtureItem {
    public static final int TYPE_HEADER = 0;
	public static final int TYPE_RIDER = 1;
    abstract public int getType();
} 