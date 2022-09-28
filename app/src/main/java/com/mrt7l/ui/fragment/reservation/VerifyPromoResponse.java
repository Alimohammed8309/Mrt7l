package com.mrt7l.ui.fragment.reservation;

import java.util.Observable;

public class VerifyPromoResponse extends Observable {

    private static VerifyPromoResponse instance;
    /**
     * mrt7al : {"success":true,"msg":"تم اضافة الرمز الترويجى بنجاح","data":{"id":1,"promoCode":"promo-25","expireTime":"1604091600","company_id":1,"discountPrice":10}}
     */


    public static VerifyPromoResponse getInstance(){
        if (instance == null){
            instance = new VerifyPromoResponse();
        }
        return instance;
    }
    private Mrt7alBean mrt7al;

    public Mrt7alBean getMrt7al() {
        return mrt7al;
    }

    public void setMrt7al(Mrt7alBean mrt7al) {
        this.mrt7al = mrt7al;
        setChanged();
    }


    public static class Mrt7alBean extends Observable{
        /**
         * success : true
         * msg : تم اضافة الرمز الترويجى بنجاح
         * data : {"id":1,"promoCode":"promo-25","expireTime":"1604091600","company_id":1,"discountPrice":10}
         */

        private boolean success;
        private String msg;
        private DataBean data;

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
            setChanged();
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
            setChanged();
        }

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
            setChanged();
        }

        public static class DataBean extends Observable{
            /**
             * id : 1
             * promoCode : promo-25
             * expireTime : 1604091600
             * company_id : 1
             * discountPrice : 10
             */

            private int id;
            private String promoCode;
            private String expireTime;
            private int company_id;
            private int discountPrice;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
                setChanged();
            }

            public String getPromoCode() {
                return promoCode;
            }

            public void setPromoCode(String promoCode) {
                this.promoCode = promoCode;
                setChanged();
            }

            public String getExpireTime() {
                return expireTime;
            }

            public void setExpireTime(String expireTime) {
                this.expireTime = expireTime;
                setChanged();
            }

            public int getCompany_id() {
                return company_id;
            }

            public void setCompany_id(int company_id) {
                this.company_id = company_id;
                setChanged();
            }

            public int getDiscountPrice() {
                return discountPrice;
            }

            public void setDiscountPrice(int discountPrice) {
                this.discountPrice = discountPrice;
                setChanged();
            }
        }
    }
}
