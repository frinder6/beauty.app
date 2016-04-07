package com.beauty.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Select implements Serializable {

    /**
     * @Fields serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    private String id;
    private String text;

    private List<Select> children;


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public String getText() {
        return text;
    }


    public void setText(String text) {
        this.text = text;
    }

    public List<Select> getChildren() {
        return children;
    }

    public void setChildren(List<Select> children) {
        this.children = children;
    }
}
