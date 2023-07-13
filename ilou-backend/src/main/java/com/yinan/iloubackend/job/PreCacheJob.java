package com.yinan.iloubackend.job;

import org.springframework.scheduling.annotation.Scheduled;

public class PreCacheJob {
    @Scheduled(cron = "0 58 23 * * *")
    public void doCacheRecommendEmoji(){

    }
}
