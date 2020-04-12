package com.voltiosx.wavescore.io;

import org.json.JSONObject;

public interface AsyncResult {
    void onResult(JSONObject object);
}