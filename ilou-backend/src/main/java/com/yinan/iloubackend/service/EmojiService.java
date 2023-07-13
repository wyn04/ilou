package com.yinan.iloubackend.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yinan.iloubackend.model.domain.Emoji;

import java.util.List;

/**
* @author wangyinan
* @description 针对表【emoji(表情)】的数据库操作Service
* @createDate 2023-04-07 11:41:38
*/
public interface EmojiService extends IService<Emoji> {
    public IPage<Emoji> searchEmojiPageByNameAndTags(String name, List<String> tags, long current, long pageSize, Integer reviewStatus);
    public List<Emoji> searchAllEmoji();
}
