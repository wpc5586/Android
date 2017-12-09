package com.aaron.interview.bean;

import com.aaron.aaronlibrary.bean.BaseBean;

import java.util.List;

/**
 * 工作简历Bean
 * Created by Aaron on 2017/11/1 0001.
 */
public class WorkBean extends BaseBean {

    private Obj obj;

    public Obj getObj() {
        return obj;
    }

    public void setObj(Obj obj) {
        this.obj = obj;
    }

    public class Obj {
        private List<WorkList> resumeList;
        private int totalCount;

        public List<WorkList> getResumeList() {
            return resumeList;
        }

        public void setResumeList(List<WorkList> resumeList) {
            this.resumeList = resumeList;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public class WorkList {

            private String resumeId;

            private String resumeTitle;

            private String createDate;

            private String updateDate;

            private String imageUrl;

            public String getResumeId() {
                return resumeId;
            }

            public void setResumeId(String resumeId) {
                this.resumeId = resumeId;
            }

            public String getResumeTitle() {
                return resumeTitle;
            }

            public void setResumeTitle(String resumeTitle) {
                this.resumeTitle = resumeTitle;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public String getUpdateDate() {
                return updateDate;
            }

            public void setUpdateDate(String updateDate) {
                this.updateDate = updateDate;
            }

            public String getImageUrl() {
                return imageUrl;
            }

            public void setImageUrl(String imageUrl) {
                this.imageUrl = imageUrl;
            }
        }
    }
}
