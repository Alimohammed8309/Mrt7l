package com.mrt7l.ui.fragment.favourite;

import java.util.ArrayList;
import java.util.Observable;

public class MyFavouriteResponse extends Observable {
    
    private static MyFavouriteResponse instance;
    /**
     * mrt7al : {"success":true,"msg":"تم الحفظ بنجاح","data":[{"id":20,"company_id":1,"user_id":2,"status":"like","created":"2020-09-05T21:45:39+03:00","company":{"name":"الشركة الدولية للنقل الجم","logo_pic":"dasdsa.jpg"}},{"id":21,"company_id":2,"user_id":2,"status":"like","created":"2020-09-05T21:45:40+03:00","company":{"name":"شركة GATK","logo_pic":"dasdsa.jpg"}}]}
     */

    private Mrt7alBean mrt7al;

    public static MyFavouriteResponse getInstance(){
        if (instance == null){
            instance = new MyFavouriteResponse();
        }
        return instance;
    }

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
         * msg : تم الحفظ بنجاح
         * data : [{"id":20,"company_id":1,"user_id":2,"status":"like","created":"2020-09-05T21:45:39+03:00","company":{"name":"الشركة الدولية للنقل الجم","logo_pic":"dasdsa.jpg"}},{"id":21,"company_id":2,"user_id":2,"status":"like","created":"2020-09-05T21:45:40+03:00","company":{"name":"شركة GATK","logo_pic":"dasdsa.jpg"}}]
         */

        private boolean success;
        private String msg;
        private ArrayList<DataBean> data;

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

        public ArrayList<DataBean> getData() {
            return data;
        }

        public void setData(ArrayList<DataBean> data) {
            this.data = data;
            setChanged();
        }

        public static class DataBean extends Observable{
            /**
             * id : 20
             * company_id : 1
             * user_id : 2
             * status : like
             * created : 2020-09-05T21:45:39+03:00
             * company : {"name":"الشركة الدولية للنقل الجم","logo_pic":"dasdsa.jpg"}
             */

            private int id;
            private int company_id;
            private int user_id;
            private String status;
            private String created;
            private CompanyBean company;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
                setChanged();
            }

            public int getCompany_id() {
                return company_id;
            }

            public void setCompany_id(int company_id) {
                this.company_id = company_id;
                setChanged();
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
                setChanged();
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
                setChanged();
            }

            public String getCreated() {
                return created;
            }

            public void setCreated(String created) {
                this.created = created;
                setChanged();
            }

            public CompanyBean getCompany() {
                return company;
            }

            public void setCompany(CompanyBean company) {
                this.company = company;
                setChanged();
            }

            public static class CompanyBean extends Observable{
                /**
                 * name : الشركة الدولية للنقل الجم
                 * logo_pic : dasdsa.jpg
                 */

                private String name;
                private String logo_pic;
                private String company_info;

                public String getCompany_info() {
                    return company_info;
                }

                public void setCompany_info(String company_info) {
                    this.company_info = company_info;
                    setChanged();
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                    setChanged();
                }

                public String getLogo_pic() {
                    return logo_pic;
                }

                public void setLogo_pic(String logo_pic) {
                    this.logo_pic = logo_pic;
                    setChanged();
                }
            }
        }
    }
}
