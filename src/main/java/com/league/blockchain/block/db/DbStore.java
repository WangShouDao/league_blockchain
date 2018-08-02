package com.league.blockchain.block.db;

/**
 *  key-value型DB数据库操作接口
 */
public interface DbStore {
    /**
     *  数据库key value
     * @param key
     * @param value
     */
    void put(String key, String value);

    /**
     *  get by key
     * @param key
     * @return
     */
    String get(String key);

    /**
     *  remove by key
     * @param key
     */
    void remove(String key);
}
