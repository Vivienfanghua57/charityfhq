package com.util;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;

import com.entity.ForumThreadExtendEntity;
import com.entity.MemberEntity;

public class MyApp extends Application {
    private int mLock = 0;
    private MemberEntity member;
    private List<ForumThreadExtendEntity> threads = new ArrayList<ForumThreadExtendEntity>();
    private MemoryCache memoryCache;

    public void setMember(MemberEntity member) {
        this.member = member;
    }

    public MemberEntity getMember() {
        return member;
    }

    public void setThreads(List<ForumThreadExtendEntity> threads) {
        this.threads = threads;
    }

    public List<ForumThreadExtendEntity> getThreads() {
        return threads;
    }

    public void setMemoryCache(MemoryCache memoryCache) {
        this.memoryCache = memoryCache;
    }

    public MemoryCache getMemoryCache() {
        return memoryCache;
    }

    // 此方法解决了使用System.exit(0)退出应用导致闪退的一个问题，并实现了APP完全退出
    @Override
    public void onCreate() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity,
                                          Bundle savedInstanceState) {
                mLock++;
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                try {
                    // 模拟Activity Destroyed耗时过长，增大问题出现的概率
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                }
                mLock--;
                if (mLock <= 0) {
                    exit();
                }
            }

            @Override
            public void onActivityStarted(Activity activity) {
                // TODO 自动生成的方法存根

            }

            @Override
            public void onActivityResumed(Activity activity) {
                // TODO 自动生成的方法存根

            }

            @Override
            public void onActivityPaused(Activity activity) {
                // TODO 自动生成的方法存根

            }

            @Override
            public void onActivityStopped(Activity activity) {
                // TODO 自动生成的方法存根

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity,
                                                    Bundle outState) {
                // TODO 自动生成的方法存根

            }

        });
    }

    public void exit() {
        System.exit(0);
    }

    @Override
    public void onTerminate() {

        super.onTerminate();

    }
}
