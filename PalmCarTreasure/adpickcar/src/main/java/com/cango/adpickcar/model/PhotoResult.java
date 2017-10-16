package com.cango.adpickcar.model;

/**
 * Created by cango on 2017/10/16.
 */

public class PhotoResult {

    /**
     * Msg : 操作成功
     * Code : 200
     * Data : {"PicFileID":178,"PicPath":"/VPIC/CAR_00056/1016160459099_1508141085089.jpg","ThumbPath":"/VPIC/CAR_00056/1016160459139_thumb_1508141085089.jpg"}
     */

    private String Msg;
    private String Code;
    private DataBean Data;

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String Msg) {
        this.Msg = Msg;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean Data) {
        this.Data = Data;
    }

    public static class DataBean {
        /**
         * PicFileID : 178
         * PicPath : /VPIC/CAR_00056/1016160459099_1508141085089.jpg
         * ThumbPath : /VPIC/CAR_00056/1016160459139_thumb_1508141085089.jpg
         */

        private int PicFileID;
        private String PicPath;
        private String ThumbPath;

        public int getPicFileID() {
            return PicFileID;
        }

        public void setPicFileID(int PicFileID) {
            this.PicFileID = PicFileID;
        }

        public String getPicPath() {
            return PicPath;
        }

        public void setPicPath(String PicPath) {
            this.PicPath = PicPath;
        }

        public String getThumbPath() {
            return ThumbPath;
        }

        public void setThumbPath(String ThumbPath) {
            this.ThumbPath = ThumbPath;
        }
    }
}
