package com.league.blockchain.core.model;

import com.league.blockchain.common.CommonUtil;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Table(name = "sync")
public class SyncEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     *  已同步的区块hash
     */
    private String hash;
    /**
     *  创建时间
     */
    private Long createTime = CommonUtil.getNow();

    @Override
    public String toString() {
        return "SyncEntity{" +
                "id=" + id +
                ", hash='" + hash + '\'' +
                ", createTime=" + createTime +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
