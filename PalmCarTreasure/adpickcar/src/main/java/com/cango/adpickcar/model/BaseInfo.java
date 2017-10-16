package com.cango.adpickcar.model;

import com.cango.adpickcar.util.CommUtil;

import java.util.List;

/**
 * Created by cango on 2017/10/12.
 */

public class BaseInfo {
    /**
     * Code : 200
     * Msg : string
     * Data : {"CTSID":0,"UCarID":0,"CTTaskID":0,"KeyNmb":0,"MileAgeReg":0,"CarInfoDesc":"","HasDrvLic":"","CustGpsScan":"","HxGpsInstall":"","BatteryPower":"","Locker":"","Antitowing":"","RegMemo":"","Status":"","AuditFlag":"","CarinfoExam":"","CarState":"","WhPosition":"","InCarDlvComp":"","InCarDlvNO":"","InCarNmb":0,"InCarList":"","InCarUsualList":"","InPicFileList":[{"PicFileID":0,"PicPath":"","ThumbPath":"","MiniPath":""}],"HasDriverCardList":[{"Id":"","Value":""}],"CustGpssCanList":[{"Id":"","Value":""}],"HxGpsInstallList":[{"Id":"","Value":""}],"BatteryPowerList":[{"Id":"","Value":""}],"LockerList":[{"Id":"","Value":""}],"CarInfoExamList":[{"Id":"","Value":""}],"CarStateList":[{"Id":"","Value":""}],"StatusList":[{"Id":"","Value":""}],"AuditFlagList":[{"Id":"","Value":""}]}
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
        public String getUserID() {
            return UserID;
        }

        public void setUserID(String userID) {
            UserID = userID;
        }

        /**
         * CTSID : 0
         * UCarID : 0
         * CTTaskID : 0
         * KeyNmb : 0
         * MileAgeReg : 0
         * CarInfoDesc :
         * HasDrvLic :
         * CustGpsScan :
         * HxGpsInstall :
         * BatteryPower :
         * Locker :
         * Antitowing :
         * RegMemo :
         * Status :
         * AuditFlag :
         * CarinfoExam :
         * CarState :
         * WhPosition :
         * InCarDlvComp :
         * InCarDlvNO :
         * InCarNmb : 0
         * InCarList :
         * InCarUsualList :
         * InPicFileList : [{"PicFileID":0,"PicPath":"","ThumbPath":"","MiniPath":""}]
         * HasDriverCardList : [{"Id":"","Value":""}]
         * CustGpssCanList : [{"Id":"","Value":""}]
         * HxGpsInstallList : [{"Id":"","Value":""}]
         * BatteryPowerList : [{"Id":"","Value":""}]
         * LockerList : [{"Id":"","Value":""}]
         * CarInfoExamList : [{"Id":"","Value":""}]
         * CarStateList : [{"Id":"","Value":""}]
         * StatusList : [{"Id":"","Value":""}]
         * AuditFlagList : [{"Id":"","Value":""}]
         */

        private String UserID;

        private int CTSID;
        private int UCarID;
        private int CTTaskID;
        private int KeyNmb;
        private String MileAgeReg;
        private String CarInfoDesc;

        public String getHasDrvLic() {
            return HasDrvLic;
        }

        public void setHasDrvLic(String hasDrvLic) {
            if (CommUtil.checkIsNull(hasDrvLic))
                hasDrvLic = "";
            HasDrvLic = hasDrvLic;
        }

        private String HasDrvLic;
        private String CustGpsScan;
        private String HxGpsInstall;
        private String BatteryPower;
        private String Locker;
        private String Antitowing;
        private String RegMemo;
        private String Status;
        private String AuditFlag;
        private String CarinfoExam;
        private String CarState;
        private String WhPosition;
        private String InCarDlvComp;
        private String InCarDlvNO;
        private int InCarNmb;
        private String InCarList;
        private List<CarUsualListBean> InCarUsualList;
        private List<InPicFileListBean> InPicFileList;
        private List<BaseSpinnerListBean> HasDriverCardList;
        private List<BaseSpinnerListBean> CustGpssCanList;
        private List<BaseSpinnerListBean> HxGpsInstallList;
        private List<BaseSpinnerListBean> BatteryPowerList;
        private List<BaseSpinnerListBean> LockerList;
        private List<BaseSpinnerListBean> CarInfoExamList;
        private List<BaseSpinnerListBean> CarStateList;
        private List<BaseSpinnerListBean> StatusList;
        private List<BaseSpinnerListBean> AuditFlagList;

        public int getCTSID() {
            return CTSID;
        }

        public void setCTSID(int CTSID) {
            this.CTSID = CTSID;
        }

        public int getUCarID() {
            return UCarID;
        }

        public void setUCarID(int UCarID) {
            this.UCarID = UCarID;
        }

        public int getCTTaskID() {
            return CTTaskID;
        }

        public void setCTTaskID(int CTTaskID) {
            this.CTTaskID = CTTaskID;
        }

        public int getKeyNmb() {
            return KeyNmb;
        }

        public void setKeyNmb(int KeyNmb) {
            this.KeyNmb = KeyNmb;
        }

        public String getMileAgeReg() {
            return MileAgeReg;
        }

        public void setMileAgeReg(String MileAgeReg) {
            if (CommUtil.checkIsNull(MileAgeReg))
                MileAgeReg = "";
            this.MileAgeReg = MileAgeReg;
        }

        public String getCarInfoDesc() {
            return CarInfoDesc;
        }

        public void setCarInfoDesc(String CarInfoDesc) {
            if (CommUtil.checkIsNull(CarInfoDesc))
                CarInfoDesc = "";
            this.CarInfoDesc = CarInfoDesc;
        }

        public String getCustGpsScan() {
            return CustGpsScan;
        }

        public void setCustGpsScan(String CustGpsScan) {
            if (CommUtil.checkIsNull(CustGpsScan))
                CustGpsScan = "";
            this.CustGpsScan = CustGpsScan;
        }

        public String getHxGpsInstall() {
            return HxGpsInstall;
        }

        public void setHxGpsInstall(String HxGpsInstall) {
            if (CommUtil.checkIsNull(HxGpsInstall))
                HxGpsInstall = "";
            this.HxGpsInstall = HxGpsInstall;
        }

        public String getBatteryPower() {
            return BatteryPower;
        }

        public void setBatteryPower(String BatteryPower) {
            if (CommUtil.checkIsNull(BatteryPower))
                BatteryPower = "";
            this.BatteryPower = BatteryPower;
        }

        public String getLocker() {
            return Locker;
        }

        public void setLocker(String Locker) {
            if (CommUtil.checkIsNull(Locker))
                Locker = "";
            this.Locker = Locker;
        }

        public String getAntitowing() {
            return Antitowing;
        }

        public void setAntitowing(String Antitowing) {
            if (CommUtil.checkIsNull(Antitowing))
                Antitowing = "";
            this.Antitowing = Antitowing;
        }

        public String getRegMemo() {
            return RegMemo;
        }

        public void setRegMemo(String RegMemo) {
            if (CommUtil.checkIsNull(RegMemo))
                RegMemo = "";
            this.RegMemo = RegMemo;
        }

        public String getStatus() {
            return Status;
        }

        public void setStatus(String Status) {
            if (CommUtil.checkIsNull(Status))
                Status = "";
            this.Status = Status;
        }

        public String getAuditFlag() {
            return AuditFlag;
        }

        public void setAuditFlag(String AuditFlag) {
            if (CommUtil.checkIsNull(AuditFlag))
                AuditFlag = "";
            this.AuditFlag = AuditFlag;
        }

        public String getCarinfoExam() {
            return CarinfoExam;
        }

        public void setCarinfoExam(String CarinfoExam) {
            if (CommUtil.checkIsNull(CarinfoExam))
                CarinfoExam = "";
            this.CarinfoExam = CarinfoExam;
        }

        public String getCarState() {
            return CarState;
        }

        public void setCarState(String CarState) {
            if (CommUtil.checkIsNull(CarState))
                CarState = "";
            this.CarState = CarState;
        }

        public String getWhPosition() {
            return WhPosition;
        }

        public void setWhPosition(String WhPosition) {
            if (CommUtil.checkIsNull(WhPosition))
                WhPosition = "";
            this.WhPosition = WhPosition;
        }

        public String getInCarDlvComp() {
            return InCarDlvComp;
        }

        public void setInCarDlvComp(String InCarDlvComp) {
            if (CommUtil.checkIsNull(InCarDlvComp))
                InCarDlvComp = "";
            this.InCarDlvComp = InCarDlvComp;
        }

        public String getInCarDlvNO() {
            return InCarDlvNO;
        }

        public void setInCarDlvNO(String InCarDlvNO) {
            if (CommUtil.checkIsNull(InCarDlvNO))
                InCarDlvNO = "";
            this.InCarDlvNO = InCarDlvNO;
        }

        public int getInCarNmb() {
            return InCarNmb;
        }

        public void setInCarNmb(int InCarNmb) {
            this.InCarNmb = InCarNmb;
        }

        public String getInCarList() {
            return InCarList;
        }

        public void setInCarList(String InCarList) {
            if (CommUtil.checkIsNull(InCarList))
                InCarList = "";
            this.InCarList = InCarList;
        }

        public List<CarUsualListBean> getInCarUsualList() {
            return InCarUsualList;
        }

        public void setInCarUsualList(List<CarUsualListBean> InCarUsualList) {
            this.InCarUsualList = InCarUsualList;
        }

        public List<InPicFileListBean> getInPicFileList() {
            return InPicFileList;
        }

        public void setInPicFileList(List<InPicFileListBean> InPicFileList) {
            this.InPicFileList = InPicFileList;
        }

        public List<BaseSpinnerListBean> getHasDriverCardList() {
            return HasDriverCardList;
        }

        public void setHasDriverCardList(List<BaseSpinnerListBean> HasDriverCardList) {
            this.HasDriverCardList = HasDriverCardList;
        }

        public List<BaseSpinnerListBean> getCustGpssCanList() {
            return CustGpssCanList;
        }

        public void setCustGpssCanList(List<BaseSpinnerListBean> CustGpssCanList) {
            this.CustGpssCanList = CustGpssCanList;
        }

        public List<BaseSpinnerListBean> getHxGpsInstallList() {
            return HxGpsInstallList;
        }

        public void setHxGpsInstallList(List<BaseSpinnerListBean> HxGpsInstallList) {
            this.HxGpsInstallList = HxGpsInstallList;
        }

        public List<BaseSpinnerListBean> getBatteryPowerList() {
            return BatteryPowerList;
        }

        public void setBatteryPowerList(List<BaseSpinnerListBean> BatteryPowerList) {
            this.BatteryPowerList = BatteryPowerList;
        }

        public List<BaseSpinnerListBean> getLockerList() {
            return LockerList;
        }

        public void setLockerList(List<BaseSpinnerListBean> LockerList) {
            this.LockerList = LockerList;
        }

        public List<BaseSpinnerListBean> getCarInfoExamList() {
            return CarInfoExamList;
        }

        public void setCarInfoExamList(List<BaseSpinnerListBean> CarInfoExamList) {
            this.CarInfoExamList = CarInfoExamList;
        }

        public List<BaseSpinnerListBean> getCarStateList() {
            return CarStateList;
        }

        public void setCarStateList(List<BaseSpinnerListBean> CarStateList) {
            this.CarStateList = CarStateList;
        }

        public List<BaseSpinnerListBean> getStatusList() {
            return StatusList;
        }

        public void setStatusList(List<BaseSpinnerListBean> StatusList) {
            this.StatusList = StatusList;
        }

        public List<BaseSpinnerListBean> getAuditFlagList() {
            return AuditFlagList;
        }

        public void setAuditFlagList(List<BaseSpinnerListBean> AuditFlagList) {
            this.AuditFlagList = AuditFlagList;
        }

        public static class InPicFileListBean {
            /**
             * PicFileID : 0
             * PicPath :
             * ThumbPath :
             * MiniPath :
             */

            private int PicFileID;
            private String PicPath;
            private String ThumbPath;
            private String MiniPath;

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

            public String getMiniPath() {
                return MiniPath;
            }

            public void setMiniPath(String MiniPath) {
                this.MiniPath = MiniPath;
            }
        }

        public static class BaseSpinnerListBean {
            private String Id;
            private String Value;

            @Override
            public String toString() {
                return Value;
            }

            public String getId() {
                return Id;
            }

            public void setId(String Id) {
                this.Id = Id;
            }

            public String getValue() {
                return Value;
            }

            public void setValue(String Value) {
                this.Value = Value;
            }
        }

        public static class CarUsualListBean {

            /**
             * UsualNO : 1
             * UsualName : 银行卡
             * IsChecked : 1
             */

            private String UsualNO;
            private String UsualName;
            private String IsChecked;

            public String getUsualNO() {
                return UsualNO;
            }

            public void setUsualNO(String UsualNO) {
                this.UsualNO = UsualNO;
            }

            public String getUsualName() {
                return UsualName;
            }

            public void setUsualName(String UsualName) {
                if (!CommUtil.checkIsNull(UsualName))
                    UsualName = "";
                this.UsualName = UsualName;
            }

            public String getIsChecked() {
                return IsChecked;
            }

            public void setIsChecked(String IsChecked) {
                this.IsChecked = IsChecked;
            }
        }
    }
}
