package com.example.artemqa.formloggingonelab.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by artemqa on 28.10.2017.
 */

public class User extends RealmObject {
    @PrimaryKey @Required
    private String mLogin;
    private String mPassword;
    private boolean mBlocked;
    private boolean mPasLimitation;

   public User() {

    }

    public User(String login, String password, boolean blocked, boolean pasLimitation) {
        this.mLogin = login;
        this.mPassword = password;
        this.mBlocked = blocked;
        this.mPasLimitation = pasLimitation;
    }

    public String getLogin() {
        return mLogin;
    }

    public void setLogin(String mLogin) {
        this.mLogin = mLogin;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public boolean isBlocked() {
        return mBlocked;
    }

    public void setBlocked(boolean mBlocked) {
        this.mBlocked = mBlocked;
    }

    public boolean isPasLimitation() {
        return mPasLimitation;
    }

    public void setPasLimitation(boolean mPasLimitation) {
        this.mPasLimitation = mPasLimitation;
    }
}
