package com.liyouzhi.dataprocess.dao.jpa.repository;

import com.liyouzhi.dataprocess.dao.jpa.entity.KeyWord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeyRepository extends JpaRepository<KeyWord, Long> {

}
