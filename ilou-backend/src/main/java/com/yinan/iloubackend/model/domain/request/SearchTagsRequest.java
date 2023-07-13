package com.yinan.iloubackend.model.domain.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 标签搜索封装类
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SearchTagsRequest extends PageRequest {

    /**
     * 名称
     */
    private String name;
}
