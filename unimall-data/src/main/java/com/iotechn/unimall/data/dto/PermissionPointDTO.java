package com.iotechn.unimall.data.dto;

import java.util.List;

public class PermissionPointDTO {
    private String id;
    private String label;
    private String api;
    private List<PermissionPointDTO> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getApi() {
        return api;
    }

    public List<PermissionPointDTO> getChildren() {
        return children;
    }

    public void setChildren(List<PermissionPointDTO> children) {
        this.children = children;
    }

}
