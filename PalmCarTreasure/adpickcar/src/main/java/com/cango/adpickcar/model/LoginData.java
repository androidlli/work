package com.cango.adpickcar.model;

/**
 * Created by cango on 2017/10/12.
 */

public class LoginData {

    /**
     * Code : 200
     * Msg : string
     * Data : {"Token":"string","UserID":"string","Mobile":"string"}
     */

    private String Code;
    private String Msg;
    private DataBean Data;

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String Msg) {
        this.Msg = Msg;
    }

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean Data) {
        this.Data = Data;
    }

    public static class DataBean {
        /**
         * Token : string
         * UserID : string
         * Mobile : string
         */

        private String Token;
        private String UserID;
        private String Mobile;

        public String getToken() {
            return Token;
        }

        public void setToken(String Token) {
            this.Token = Token;
        }

        public String getUserID() {
            return UserID;
        }

        public void setUserID(String UserID) {
            this.UserID = UserID;
        }

        public String getMobile() {
            return Mobile;
        }

        public void setMobile(String Mobile) {
            this.Mobile = Mobile;
        }
    }
}
