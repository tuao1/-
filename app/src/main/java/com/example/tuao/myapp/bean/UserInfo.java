package com.example.tuao.myapp.bean;

public class UserInfo {

    /**
     * reqType : 1
     * user_info : {"account":"1072459221","password":"Tu991210","name":"涂奥","sex":"男","phone":"17683864079","adress":"武汉科技大学","imgurl":"null","money":"0"}
     */

    private int reqType;
    private UserInfoBean user_info;

    public int getReqType() {
        return reqType;
    }

    public void setReqType(int reqType) {
        this.reqType = reqType;
    }

    public UserInfoBean getUser_info() {
        return user_info;
    }

    public void setUser_info(UserInfoBean user_info) {
        this.user_info = user_info;
    }

    public static class UserInfoBean {
        /**
         * account : 1072459221
         * password : Tu991210
         * name : 涂奥
         * sex : 男
         * phone : 17683864079
         * adress : 武汉科技大学
         * imgurl : null
         * money : 0
         */

        private String account;
        private String password;
        private String name;
        private String sex;
        private String phone;
        private String adress;
        private String imgurl;
        private String money;

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAdress() {
            return adress;
        }

        public void setAdress(String adress) {
            this.adress = adress;
        }

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }
    }
}
