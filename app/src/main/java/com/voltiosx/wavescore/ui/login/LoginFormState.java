package com.voltiosx.wavescore.ui.login;

import androidx.annotation.Nullable;

/**
 * Data validation state of the login form.
 */
class LoginFormState {
    @Nullable
    private Integer usernameError;
    @Nullable
    private Integer useremailError;
    @Nullable
    private Integer passwordError;

    private boolean isDataValid;

    LoginFormState(@Nullable Integer usernameError, @Nullable Integer useremailError, @Nullable Integer passwordError) {
        this.usernameError = usernameError;
        this.useremailError = useremailError;
        this.passwordError = passwordError;
        this.isDataValid = false;
    }

    LoginFormState(boolean isDataValid) {
        this.usernameError = null;
        this.useremailError = null;
        this.passwordError = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    Integer getUsernameError() {
        return usernameError;
    }

    @Nullable
    Integer getUseremailError() {
        return useremailError;
    }

    @Nullable
    Integer getPasswordError() {
        return passwordError;
    }

    boolean isDataValid() {
        return isDataValid;
    }
}
