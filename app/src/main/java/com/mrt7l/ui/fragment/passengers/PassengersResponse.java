package com.mrt7l.ui.fragment.passengers;

import java.util.ArrayList;
import java.util.Observable;

public class PassengersResponse extends Observable {

    private static PassengersResponse instance;
    private Mrt7alBean mrt7al;

    public static PassengersResponse getInstance(){
        if (instance == null){
            instance = new PassengersResponse();
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

    public static class Mrt7alBean extends Observable {
        private Boolean success;
        private String msg;
        private ArrayList<DataBean> data;
        private PaginationBean pagination;

        public Boolean getSuccess() {
            return success;
        }

        public void setSuccess(Boolean success) {
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

        public PaginationBean getPagination() {
            return pagination;
        }

        public void setPagination(PaginationBean pagination) {
            this.pagination = pagination;
            setChanged();
        }

        public static class PaginationBean extends Observable{
            private int count;
            private int current;
            private int perPage;
            private int page;
            private int requestedPage;
            private int pageCount;
            private int start;
            private int end;
            private Boolean prevPage;
            private Boolean nextPage;
            private String sort;
            private String direction;
            private Boolean sortDefault;
            private Boolean directionDefault;
            private ArrayList<?> completeSort;
            private String limit;
            private String scope;
            private String finder;

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
                setChanged();
            }

            public int getCurrent() {
                return current;
            }

            public void setCurrent(int current) {
                this.current = current;
                setChanged();
            }

            public int getPerPage() {
                return perPage;
            }

            public void setPerPage(int perPage) {
                this.perPage = perPage;
                setChanged();
            }

            public int getPage() {
                return page;
            }

            public void setPage(int page) {
                this.page = page;
                setChanged();
            }

            public int getRequestedPage() {
                return requestedPage;
            }

            public void setRequestedPage(int requestedPage) {
                this.requestedPage = requestedPage;
                setChanged();
            }

            public int getPageCount() {
                return pageCount;
            }

            public void setPageCount(int pageCount) {
                this.pageCount = pageCount;
                setChanged();
            }

            public int getStart() {
                return start;
            }

            public void setStart(int start) {
                this.start = start;
                setChanged();
            }

            public int getEnd() {
                return end;
            }

            public void setEnd(int end) {
                this.end = end;
                setChanged();
            }

            public Boolean getPrevPage() {
                return prevPage;
            }

            public void setPrevPage(Boolean prevPage) {
                this.prevPage = prevPage;
                setChanged();
            }

            public Boolean getNextPage() {
                return nextPage;
            }

            public void setNextPage(Boolean nextPage) {
                this.nextPage = nextPage;
                setChanged();
            }

            public String getSort() {
                return sort;
            }

            public void setSort(String sort) {
                this.sort = sort;
                setChanged();
            }

            public String getDirection() {
                return direction;
            }

            public void setDirection(String direction) {
                this.direction = direction;
                setChanged();
            }

            public Boolean getSortDefault() {
                return sortDefault;
            }

            public void setSortDefault(Boolean sortDefault) {
                this.sortDefault = sortDefault;
                setChanged();
            }

            public Boolean getDirectionDefault() {
                return directionDefault;
            }

            public void setDirectionDefault(Boolean directionDefault) {
                this.directionDefault = directionDefault;
                setChanged();
            }

            public ArrayList<?> getCompleteSort() {
                return completeSort;
            }

            public void setCompleteSort(ArrayList<?> completeSort) {
                this.completeSort = completeSort;
                setChanged();
            }

            public String getLimit() {
                return limit;
            }

            public void setLimit(String limit) {
                this.limit = limit;
                setChanged();
            }

            public String getScope() {
                return scope;
            }

            public void setScope(String scope) {
                this.scope = scope;
                setChanged();
            }

            public String getFinder() {
                return finder;
            }

            public void setFinder(String finder) {
                this.finder = finder;
                setChanged();
            }
        }

        public static class DataBean extends Observable{
            private boolean isSelected;
            private int id;
            private int user_id;
            private String full_name;
            private String date_of_birth;
            private int nationality_id;
            private String gender;
            private String passport_id;
            private String passport_file;
            private String mobile;
            private String created;
            private String modifed;
            private String passangerType;
            private int city_id;
            private int is_active;
            private String isMain;
            //            private String city;
            private NationalityBean nationality;


            public DataBean(){};
            public DataBean(Integer id, Integer id1, String username, String date_of_birth, String passport_id, String mobile, String gender) {
                this.id = id;
                this.full_name = full_name;
                this.date_of_birth = date_of_birth;
                this.gender = gender;
                this.passport_id = passport_id;
                this.passport_file = passport_file;
                this.mobile = mobile;
                this.created = created;
                this.modifed = modifed;
                this.passangerType = passangerType;
                this.isMain = isMain;
//                this.city = city;
            }

            public boolean isSelected() {
                return isSelected;
            }

            public void setSelected(boolean selected) {
                isSelected = selected;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
                setChanged();
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
                setChanged();
            }

            public String getFull_name() {
                return full_name;
            }

            public void setFull_name(String full_name) {
                this.full_name = full_name;
                setChanged();
            }

            public String getDate_of_birth() {
                return date_of_birth;
            }

            public void setDate_of_birth(String date_of_birth) {
                this.date_of_birth = date_of_birth;
                setChanged();
            }

            public int getNationality_id() {
                return nationality_id;
            }

            public void setNationality_id(int nationality_id) {
                this.nationality_id = nationality_id;
                setChanged();
            }

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
                setChanged();
            }

            public String getPassport_id() {
                return passport_id;
            }

            public void setPassport_id(String passport_id) {
                this.passport_id = passport_id;
                setChanged();
            }

            public String getPassport_file() {
                return passport_file;
            }

            public void setPassport_file(String passport_file) {
                this.passport_file = passport_file;
                setChanged();
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
                setChanged();
            }

            public String getCreated() {
                return created;
            }

            public void setCreated(String created) {
                this.created = created;
                setChanged();
            }

            public String getModifed() {
                return modifed;
            }

            public void setModifed(String modifed) {
                this.modifed = modifed;
                setChanged();
            }

            public String getPassangerType() {
                return passangerType;
            }

            public void setPassangerType(String passangerType) {
                this.passangerType = passangerType;
                setChanged();
            }

            public int getCity_id() {
                return city_id;
            }

            public void setCity_id(int city_id) {
                this.city_id = city_id;
                setChanged();
            }

            public int getIs_active() {
                return is_active;
            }

            public void setIs_active(int is_active) {
                this.is_active = is_active;
                setChanged();
            }

            public String getIsMain() {
                return isMain;
            }

            public void setIsMain(String isMain) {
                this.isMain = isMain;
                setChanged();
            }

//            public String getCity() {
//                return city;
//            }
//
//            public void setCity(String city) {
//                this.city = city;
//                setChanged();
//            }

            public NationalityBean getNationality() {
                return nationality;
            }

            public void setNationality(NationalityBean nationality) {
                this.nationality = nationality;
                setChanged();
            }

            public static class NationalityBean extends Observable {
                private String name;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                    setChanged();
                }
            }
        }
    }
}
