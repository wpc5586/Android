package com.aaron.interview.bean;

import com.aaron.aaronlibrary.bean.BaseBean;

/**
 * 登录Bean
 * Created by Aaron on 2017/11/1 0001.
 */
public class LoginBean extends BaseBean {

    private Obj obj;

    public Obj getObj() {
        return obj;
    }

    public void setObj(Obj obj) {
        this.obj = obj;
    }

    public class Obj {
        private User user;

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public class User {

            private String userName;

            private String role;

            private String cityCode;

            private String cityName;

            private String userSex;

            private String userPhone;

            private String userEmail;

            private String createDate;

            private String createUser;

            private String updateDate;

            private String updateUser;

            private String isUsable;

            private String remark;

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName == null ? null : userName.trim();
            }

            public String getRole() {
                return role;
            }

            public void setRole(String role) {
                this.role = role == null ? null : role.trim();
            }

            public String getCityCode() {
                return cityCode;
            }

            public void setCityCode(String cityCode) {
                this.cityCode = cityCode == null ? null : cityCode.trim();
            }

            public String getCityName() {
                return cityName;
            }

            public void setCityName(String cityName) {
                this.cityName = cityName == null ? null : cityName.trim();
            }

            public String getUserSex() {
                return userSex;
            }

            public void setUserSex(String userSex) {
                this.userSex = userSex == null ? null : userSex.trim();
            }

            public String getUserPhone() {
                return userPhone;
            }

            public void setUserPhone(String userPhone) {
                this.userPhone = userPhone == null ? null : userPhone.trim();
            }

            public String getUserEmail() {
                return userEmail;
            }

            public void setUserEmail(String userEmail) {
                this.userEmail = userEmail == null ? null : userEmail.trim();
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public String getCreateUser() {
                return createUser;
            }

            public void setCreateUser(String createUser) {
                this.createUser = createUser == null ? null : createUser.trim();
            }

            public String getUpdateDate() {
                return updateDate;
            }

            public void setUpdateDate(String updateDate) {
                this.updateDate = updateDate;
            }

            public String getUpdateUser() {
                return updateUser;
            }

            public void setUpdateUser(String updateUser) {
                this.updateUser = updateUser == null ? null : updateUser.trim();
            }

            public String getIsUsable() {
                return isUsable;
            }

            public void setIsUsable(String isUsable) {
                this.isUsable = isUsable == null ? null : isUsable.trim();
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark == null ? null : remark.trim();
            }
        }
    }
}
