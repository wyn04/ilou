package com.yinan.iloubackend.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yinan.iloubackend.common.BaseResponse;
import com.yinan.iloubackend.common.ErrorCode;
import com.yinan.iloubackend.common.ResultUtils;
import com.yinan.iloubackend.exception.BusinessException;
import com.yinan.iloubackend.model.domain.Emoji;
import com.yinan.iloubackend.model.domain.Tag;
import com.yinan.iloubackend.model.domain.request.SearchEmojisRequest;
import com.yinan.iloubackend.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/tag")
@Slf4j
public class TagController {
    @Resource
    TagService tagService;

    /**
     * 获取所以标签
     *
     * @param
     * @return
     */
    @GetMapping("/getAll")
    public BaseResponse<List<String>> getAllTags() {
        List<String> allTagNames = tagService.getAllTagNames();
        return ResultUtils.success(allTagNames);
    }
}
