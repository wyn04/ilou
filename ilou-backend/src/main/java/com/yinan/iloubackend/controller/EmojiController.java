package com.yinan.iloubackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yinan.iloubackend.common.BaseResponse;
import com.yinan.iloubackend.model.domain.request.DeleteRequest;
import com.yinan.iloubackend.common.ErrorCode;
import com.yinan.iloubackend.common.ResultUtils;
import com.yinan.iloubackend.exception.BusinessException;
import com.yinan.iloubackend.model.domain.Emoji;
import com.yinan.iloubackend.model.domain.request.ReviewEmojiRequest;
import com.yinan.iloubackend.model.domain.request.SearchEmojisRequest;
import com.yinan.iloubackend.service.EmojiService;
import com.yinan.iloubackend.service.IQiniuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/emoji")
@Slf4j
public class EmojiController {
    @Resource
    EmojiService emojiService;

    @Resource
    IQiniuService qiniuService;

    /**
     * 搜索表情
     *
     * @param emojiRequest
     * @return
     */
    @GetMapping("/search")
    public BaseResponse<IPage<Emoji>> searchEmojis(SearchEmojisRequest emojiRequest) {
        if (emojiRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long current = emojiRequest.getCurrent();
        long pageSize = emojiRequest.getPageSize();
        final int MAX_VIEW_NUM = 400;
        if (current * pageSize > MAX_VIEW_NUM) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR.getCode(), "最多可查看 400 条数据");
        }
        IPage<Emoji> emojiPage = emojiService.searchEmojiPageByNameAndTags(emojiRequest.getName(), emojiRequest.getTags(), current, pageSize, emojiRequest.getReviewStatus());
        return ResultUtils.success(emojiPage);
    }

    /**
     * 上传文件
     *
     * @return
     */
    @PostMapping("/upload")
    public BaseResponse<String> uploadFile(MultipartFile file) throws IOException {
        if (file == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        File localFile = File.createTempFile("temp", null);
        String fileName = System.currentTimeMillis() / 1000 + "-" + file.getOriginalFilename();
        try {
            file.transferTo(localFile);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(ErrorCode.FILE_UPLOAD_ERROR);
        }
        String resultPath = qiniuService.uploadFile(localFile, fileName);
        System.out.println("访问地址： " + resultPath);
        //String resultPath = tencentCosManager.uploadFile(fileName, localFile);
        // 清理临时文件
        boolean deleteResult = localFile.delete();
        if (!deleteResult) {
            log.error("fileDelete error", localFile.getName());
        }
        return ResultUtils.success(resultPath);
    }

    /**
     * 插入表情
     *
     * @param emoji
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Integer> saveEmoji(@RequestBody Emoji emoji) {
        if (emoji == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        emojiService.save(emoji);
        return ResultUtils.success(emoji.getId());
    }

    /**
     * 获取所以表情
     *
     * @param
     * @return
     */
    @GetMapping("/searchAll")
    public BaseResponse<List<Emoji>> searchAllEmojis() {
        List<Emoji> emojiList = emojiService.searchAllEmoji();
        return ResultUtils.success(emojiList);
    }

    /**
     * 审核表情
     *
     * @param emojiRequest
     * @return
     */
    @PostMapping("/review")
    public BaseResponse<Boolean> reviewEmoji(@RequestBody ReviewEmojiRequest emojiRequest) {
        if (emojiRequest == null || emojiRequest.getId() <= 0 || emojiRequest.getReviewStatus() < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Emoji emoji = new Emoji();
        emoji.setId(emojiRequest.getId());
        emoji.setReviewstatus(emojiRequest.getReviewStatus());
        return ResultUtils.success(emojiService.updateById(emoji));
    }

    /**
     * 删除表情
     *
     * @param deleteRequest
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteEmoji(@RequestBody DeleteRequest deleteRequest) {
        if (deleteRequest == null || deleteRequest.getId() < 1) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(emojiService.removeById(deleteRequest.getId()));
    }

}
