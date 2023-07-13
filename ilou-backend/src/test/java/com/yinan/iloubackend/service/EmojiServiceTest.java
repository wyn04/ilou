package com.yinan.iloubackend.service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yinan.iloubackend.mapper.EmojiMapper;
import com.yinan.iloubackend.model.domain.Emoji;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmojiServiceTest {

    @Resource
    EmojiService emojiService;

    @Test
    void insertTest(){
        for (int i = 100; i < 10000; i++) {
            Emoji emoji = new Emoji();
            emoji.setUrl("https://c.cidianwang.com/file/shufa/xingshu/2014112916434559.png");
            emoji.setName("假"+i);
            emoji.setUserid(null);
            emoji.setTags("[\"假\",\"字\"]");
            emoji.setReviewstatus(1);
            emoji.setCreatetime(new Date());
            emoji.setIsdelete(0);
            emoji.setUpdatetime(new Date());

            emojiService.save(emoji);
        }

    }

    @Test
    void searchTest(){
        ArrayList<String> tags = new ArrayList<>();
        tags.add("可爱");
        Page<Emoji> emojiPage = (Page<Emoji>) emojiService.searchEmojiPageByNameAndTags("", tags, 1, 5, null);
        List<Emoji> emojiList = emojiPage.getRecords();

        for (Emoji emoji : emojiList) {
            System.out.println(emoji);
        }
    }

}