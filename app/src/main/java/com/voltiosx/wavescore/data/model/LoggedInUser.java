package com.voltiosx.wavescore.data.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private String userId;
    private String userEmail;
    private String displayName;

    public LoggedInUser(String userId, String userEmail, String displayName) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.displayName = displayName;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsermail() {
        return userEmail;
    }

    public String getDisplayName() {
        return displayName;
    }
}
