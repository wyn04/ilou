package com.yinan.iloubackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yinan.iloubackend.model.domain.Tag;

import java.util.List;

/**
* @author wangyinan
* @description 针对表【tag(标签)】的数据库操作Service
* @createDate 2023-04-07 11:41:38
*/
public interface TagService extends IService<Tag> {
    List<String> getAllTagNames();

}
