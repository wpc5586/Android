package com.aaron.interview.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 工作简历类型Bean
 * Created by Aaron on 2017/11/1 0001.
 */
public class ResumeTypeVo {

    private String menuTitle;

    private List<Type> types = new ArrayList<>();

    public ResumeTypeVo(String menuTitle, String[] index, String[] names) {
        this.menuTitle = menuTitle;
        for (int i = 0; i < index.length; i++) {
            Type type = new Type(index[i], names[i]);
            types.add(type);
        }
    }

    public String getMenuTitle() {
        return menuTitle;
    }

    public void setMenuTitle(String menuTitle) {
        this.menuTitle = menuTitle;
    }

    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }

    public class Type {
        private String index;
        private String name;

        public Type(String index, String name) {
            this.index = index;
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIndex() {
            return index;
        }

        public void setIndex(String index) {
            this.index = index;
        }
    }
}
