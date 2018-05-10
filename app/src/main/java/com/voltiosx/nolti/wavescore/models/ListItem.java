package com.voltiosx.nolti.wavescore.models;

public abstract class ListItem {
    public static final int TYPE_TITLE = 0;
	public static final int TYPE_TABLE = 1;
    abstract public int getType();
} 