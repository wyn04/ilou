package com.yinan.iloubackend.model.domain.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 表情搜索封装类
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SearchEmojisRequest extends PageRequest {

    /**
     * 名称
     */
    private String name;

    /**
     * 标签
     */
    private List<String> tags;

    /**
     * 审核状态
     */
    private Integer reviewStatus;
}
