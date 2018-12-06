package com.uncreated.vksimpleapp.model.entity.events;

public class IndexedUrl {
    private Integer index;
    private String url;

    public IndexedUrl(Integer index, String url) {
        this.index = index;
        this.url = url;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
