//package com.mrt7l.ui.activity;
//
//import java.util.Observable;
//
//public class CancelOderResponse extends Observable {
//
//    private static CancelOderResponse instance;
//    /**
//     * mrt7al : {"success":true,"msg":"تم الغاء حجزك بنجاح","data":{"created":"2020-09-08T12:53:27+03:00","id":45,"status":"canceled"}}
//     */
//
//    private Mrt7alBean mrt7al;
//
//    public static CancelOderResponse getInstance(){
//        if (instance == null){
//            instance = new CancelOderResponse();
//        }
//        return instance;
//    }
//
//    public Mrt7alBean getMrt7al() {
//        return mrt7al;
//    }
//
//    public void setMrt7al(Mrt7alBean mrt7al) {
//        this.mrt7al = mrt7al;
//    }
//
//
//    public static class Mrt7alBean {
//        /**
//         * success : true
//         * msg : تم الغاء حجزك بنجاح
//         * data : {"created":"2020-09-08T12:53:27+03:00","id":45,"status":"canceled"}
//         */
//
//        private boolean success;
//        private String msg;
//        private DataBean data;
//
//        public boolean isSuccess() {
//            return success;
//        }
//
//        public void setSuccess(boolean success) {
//            this.success = success;
//        }
//
//        public String getMsg() {
//            return msg;
//        }
//
//        public void setMsg(String msg) {
//            this.msg = msg;
//        }
//
//        public DataBean getData() {
//            return data;
//        }
//
//        public void setData(DataBean data) {
//            this.data = data;
//        }
//
//        public static class DataBean {
//            /**
//             * created : 2020-09-08T12:53:27+03:00
//             * id : 45
//             * status : canceled
//             */
//
//            private String created;
//            private int id;
//            private String status;
//
//            public String getCreated() {
//                return created;
//            }
//
//            public void setCreated(String created) {
//                this.created = created;
//            }
//
//            public int getId() {
//                return id;
//            }
//
//            public void setId(int id) {
//                this.id = id;
//            }
//
//            public String getStatus() {
//                return status;
//            }
//
//            public void setStatus(String status) {
//                this.status = status;
//            }
//        }
//    }
//}
