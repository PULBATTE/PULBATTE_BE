package com.pulbatte.pulbatte.plantSearch.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import lombok.Getter;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Getter
    public class CustomPageImpl<T> extends PageImpl<T> {

    @JsonCreator
    public CustomPageImpl(List content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    @JsonCreator
    public CustomPageImpl(List content) {
        super(content);
    }

    @JsonGetter(value = "contents")
    @Override
    public List getContent() {
        return super.getContent();
    }
}
