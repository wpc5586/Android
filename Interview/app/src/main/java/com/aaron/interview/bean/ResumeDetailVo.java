package com.aaron.interview.bean;

import java.util.List;

/**
 * 工作简历详情Bean
 * Created by Aaron on 2017/11/1 0001.
 */
public class ResumeDetailVo {

    private List<ResumeDetailList> resumeDetailList;

    public List<ResumeDetailList> getResumeList() {
        return resumeDetailList;
    }

    public void setResumeList(List<ResumeDetailList> resumeDetailList) {
        this.resumeDetailList = resumeDetailList;
    }

    public class ResumeDetailList {

        private String resumeTitle;

        private String imageUrl;

    }
}
