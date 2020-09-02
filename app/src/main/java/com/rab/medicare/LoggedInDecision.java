package com.rab.medicare;

public class LoggedInDecision
{
    private static String LoginStatus = null;

    public String getLoginStatus()
    {
        return LoginStatus;
    }

    public void setLoginStatus(String loginStatus)
    {
        LoggedInDecision.LoginStatus = loginStatus;
    }
}
