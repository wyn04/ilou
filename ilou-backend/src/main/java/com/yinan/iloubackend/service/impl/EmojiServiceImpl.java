package com.yinan.iloubackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yinan.iloubackend.common.ErrorCode;
import com.yinan.iloubackend.common.ResultUtils;
import com.yinan.iloubackend.service.EmojiService;
import com.yinan.iloubackend.model.domain.Emoji;
import com.yinan.iloubackend.mapper.EmojiMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
* @author wangyinan
* @description 针对表【emoji(表情)】的数据库操作Service实现
* @createDate 2023-04-07 11:41:38
*/
@Service
@Slf4j
public class EmojiServiceImpl extends ServiceImpl<EmojiMapper, Emoji>
    implements EmojiService {

    @Resource
    EmojiMapper emojiMapper;

    @Resource
    RedisTemplate redisTemplate;

    @Override
    public IPage<Emoji> searchEmojiPageByNameAndTags(String name, List<String> tags, long current, long pageSize, Integer reviewStatus) {
        String redisKey;
        if (tags != null){
            redisKey = String.format("ilou:emoji:%s:%s:%d:%d:%d", name, String.join(",", tags), current, pageSize, reviewStatus);
        } else {
            redisKey = String.format("ilou:emoji:%s:%d:%d:%d", name, current, pageSize, reviewStatus);
        }
        ValueOperations valueOperations = redisTemplate.opsForValue();
        // 如果有缓存，直接插缓存
        Page<Emoji> emojiPage = (Page<Emoji>) valueOperations.get(redisKey);
        if (emojiPage != null) {
            log.info("get redis success " + redisKey);
            return emojiPage;
        }
        // 没有缓存，查数据库
        QueryWrapper<Emoji> emojiQueryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(name)) {
            emojiQueryWrapper.like("name", name);
        }
        if (tags != null) {
            for (String tag : tags) {
                emojiQueryWrapper.like("tags", tag);
            }
        }
        if (reviewStatus != null) {
            emojiQueryWrapper.eq("reviewStatus", reviewStatus);
        }
        emojiQueryWrapper.orderByDesc("createTime");
        emojiPage = page(new Page<>(current, pageSize), emojiQueryWrapper);
        // 写入缓存
        try {
            log.info("set redis success " + redisKey);
            valueOperations.set(redisKey, emojiPage, 30000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            log.error("redis set emojiPag error: ", e);
        }

        return emojiPage;
    }

    @Override
    public List<Emoji> searchAllEmoji() {
//        String redisKey = "ilou:emoji:all";
//        ValueOperations valueOperations = redisTemplate.opsForValue();
//        // 如果有缓存，直接插缓存
//        List<Emoji> emojiList = (List<Emoji>) valueOperations.get(redisKey);
//        if (emojiList != null) {
//            log.info("get redis success " + redisKey);
//            return emojiList;
//        }
//        // 没有缓存，查数据库
        QueryWrapper<Emoji> emojiQueryWrapper = new QueryWrapper<>();
        emojiQueryWrapper.orderByDesc("createTime");
        List<Emoji> emojiList = list(emojiQueryWrapper);
//        // 写入缓存
//        try {
//            log.info("set redis success " + redisKey);
//            valueOperations.set(redisKey, emojiList, 30000, TimeUnit.MILLISECONDS);
//        } catch (Exception e) {
//            log.error("redis set emojiPag error: ", e);
//        }

        return emojiList;
    }


}




