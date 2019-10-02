package com.example.nolti.wavescore.models;

import androidx.annotation.NonNull;

public class InstanceItem extends ListItem {

	@NonNull
	private String instancename;

	public InstanceItem(@NonNull String instancename) {
		this.instancename = instancename;
	}

	@NonNull
	public String getInstancename() {
		return instancename;
	}

	@Override
	public int getType() {
		return TYPE_TITLE;
	}

}