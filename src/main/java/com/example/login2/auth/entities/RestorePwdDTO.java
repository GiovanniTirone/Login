package com.example.login2.auth.entities;

public class RestorePwdDTO {

    private String newPwd;

    private String resetPwdCode;

    public RestorePwdDTO() {
    }

    public RestorePwdDTO(String newPwd, String resetPwdCode) {
        this.newPwd = newPwd;
        this.resetPwdCode = resetPwdCode;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }

    public String getResetPwdCode() {
        return resetPwdCode;
    }

    public void setResetPwdCode(String resetPwdCode) {
        this.resetPwdCode = resetPwdCode;
    }
}
