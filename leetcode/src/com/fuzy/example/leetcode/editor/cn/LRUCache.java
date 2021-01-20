package com.fuzy.example.leetcode.editor.cn;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName LRU
 * @Description 最近最少使用算法 LRU
 * @Author fuzy
 * @Date 2021/1/20 21:43
 * @Version 1.0
 */
public class LRUCache<K,V> extends LinkedHashMap<K,V> {

    //设定最大缓存空间 MAX_ENTRIES = 3;
    private static final int MAX_ENTRIES = 3;

    public LRUCache(){
        super(MAX_ENTRIES,0.75f,true);
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size()>MAX_ENTRIES;
    }

    public static void main(String[] args){
        LRUCache<Integer, Integer> cache = new LRUCache<>();
        cache.put(1, 1);
        cache.put(2, 2);
        cache.put(3, 3);
        cache.get(1);
        cache.put(4, 4);
        System.out.println(cache.keySet());
    }
}
