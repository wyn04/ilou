package com.yinan.iloubackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yinan.iloubackend.service.TagService;
import com.yinan.iloubackend.model.domain.Tag;
import com.yinan.iloubackend.mapper.TagMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
* @author wangyinan
* @description 针对表【tag(标签)】的数据库操作Service实现
* @createDate 2023-04-07 11:41:38
*/
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
    implements TagService {

    @Resource
    TagMapper tagMapper;

    @Override
    public List<String> getAllTagNames() {
        List<Tag> tagList = tagMapper.selectList(null);
        List<String> tagNameList = new ArrayList<>();
        for (Tag tag : tagList) {
            tagNameList.add(tag.getName());
        }
        return tagNameList;
    }
}




