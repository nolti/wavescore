package com.example.nolti.wavescore.io;

import org.json.JSONObject;

public interface AsyncResult {
    void onResult(JSONObject object);
}