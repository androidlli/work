package com.cango.adpickcar.model;

import com.cango.adpickcar.util.CommUtil;

import java.util.List;

/**
 * Created by cango on 2017/10/14.
 */

public class CarFilesInfo {

    /**
     * Code : 200
     * Msg : string
     * Data : {"SurfaceFileList":[{"PicFileID":0,"DisCarPicID":0,"PicPath":"","ThumbPath":"","PicInst":"","PicDtlDesc":"","SubCategory":"","SubID":"","SubName":"","IsRequire":false}],"DetailList":[{"PicFileID":0,"DisCarPicID":0,"PicPath":"","ThumbPath":"","PicInst":"","PicDtlDesc":"","SubCategory":"","SubID":"","SubName":"","IsRequire":false}],"SupplementList":[{"PicFileID":0,"DisCarPicID":0,"PicPath":"","ThumbPath":"","PicInst":"","PicDtlDesc":"","SubCategory":"","SubID":"","SubName":"","IsRequire":false}],"TakeCarList":[{"PicFileID":0,"DisCarPicID":0,"PicPath":"","ThumbPath":"","PicInst":"","PicDtlDesc":"","SubCategory":"","SubID":"","SubName":"","IsRequire":false}]}
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
        private List<SurfaceFileListBean> SurfaceFileList;
        private List<SurfaceFileListBean> DetailList;
        private List<SurfaceFileListBean> SupplementList;
        private List<SurfaceFileListBean> TakeCarList;

        public List<SurfaceFileListBean> getSurfaceFileList() {
            return SurfaceFileList;
        }

        public void setSurfaceFileList(List<SurfaceFileListBean> SurfaceFileList) {
            this.SurfaceFileList = SurfaceFileList;
        }

        public List<SurfaceFileListBean> getDetailList() {
            return DetailList;
        }

        public void setDetailList(List<SurfaceFileListBean> DetailList) {
            this.DetailList = DetailList;
        }

        public List<SurfaceFileListBean> getSupplementList() {
            return SupplementList;
        }

        public void setSupplementList(List<SurfaceFileListBean> SupplementList) {
            this.SupplementList = SupplementList;
        }

        public List<SurfaceFileListBean> getTakeCarList() {
            return TakeCarList;
        }

        public void setTakeCarList(List<SurfaceFileListBean> TakeCarList) {
            this.TakeCarList = TakeCarList;
        }

        public static class SurfaceFileListBean {
            /**
             * PicFileID : 0
             * DisCarPicID : 0
             * PicPath :
             * ThumbPath :
             * PicInst :
             * PicDtlDesc :
             * SubCategory :
             * SubID :
             * SubName :
             * IsRequire : false
             */

            private int PicFileID;
            private int DisCarPicID;
            private String PicPath;
            private String ThumbPath;
            private String PicInst;
            private String PicDtlDesc;
            private String SubCategory;
            private String SubID;
            private String SubName;
            private boolean IsRequire;

            public int getPicFileID() {
                return PicFileID;
            }

            public void setPicFileID(int PicFileID) {
                this.PicFileID = PicFileID;
            }

            public int getDisCarPicID() {
                return DisCarPicID;
            }

            public void setDisCarPicID(int DisCarPicID) {
                this.DisCarPicID = DisCarPicID;
            }

            public String getPicPath() {
                return PicPath;
            }

            public void setPicPath(String PicPath) {
                if (CommUtil.checkIsNull(PicPath))
                    PicPath = "";
                this.PicPath = PicPath;
            }

            public String getThumbPath() {
                return ThumbPath;
            }

            public void setThumbPath(String ThumbPath) {
                if (CommUtil.checkIsNull(ThumbPath))
                    ThumbPath = "";
                this.ThumbPath = ThumbPath;
            }

            public String getPicInst() {
                return PicInst;
            }

            public void setPicInst(String PicInst) {
                if (CommUtil.checkIsNull(PicInst))
                    PicInst = "";
                this.PicInst = PicInst;
            }

            public String getPicDtlDesc() {
                return PicDtlDesc;
            }

            public void setPicDtlDesc(String PicDtlDesc) {
                if (CommUtil.checkIsNull(PicDtlDesc))
                    PicDtlDesc = "";
                this.PicDtlDesc = PicDtlDesc;
            }

            public String getSubCategory() {
                return SubCategory;
            }

            public void setSubCategory(String SubCategory) {
                if (CommUtil.checkIsNull(SubCategory))
                    SubCategory = "";
                this.SubCategory = SubCategory;
            }

            public String getSubID() {
                return SubID;
            }

            public void setSubID(String SubID) {
                this.SubID = SubID;
            }

            public String getSubName() {
                return SubName;
            }

            public void setSubName(String SubName) {
                if (CommUtil.checkIsNull(SubName))
                    SubName = "";
                this.SubName = SubName;
            }

            public boolean isIsRequire() {
                return IsRequire;
            }

            public void setIsRequire(boolean IsRequire) {
                this.IsRequire = IsRequire;
            }
        }
    }
}
