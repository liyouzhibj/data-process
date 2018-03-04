package com.liyouzhi.dataprocess.dao.jpa.entity;

import javax.persistence.*;

@Entity
@Table(name = "keys")
public class Key {
    public Key(){}

    private long id;
    private String key;
    private int count = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "key", unique = true, nullable = false)
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Column(name = "count", nullable = false)
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
