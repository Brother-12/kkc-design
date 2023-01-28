package com.kerco.kkc.community.service;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * 搜索 接口
 */
public interface SearchService {
    /**
     * 关键字搜索
     * @param key 关键字
     */
    Map<String,Object> searchKey(String key, Integer page) throws ExecutionException, InterruptedException;

    /**
     * 标签搜索
     * @param id 标签id
     * @param page 当前页
     * @return 搜索结果
     */
    Map<String, Object> searchTag(Integer id, Integer page) throws ExecutionException, InterruptedException;
}
