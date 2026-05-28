package com.sailthru.client.params;

import com.google.gson.annotations.SerializedName;

public enum SendKey {
    @SerializedName("profile_id") PROFILE_ID,
    @SerializedName("sid") SID,
    @SerializedName("device_id") DEVICE_ID
}
