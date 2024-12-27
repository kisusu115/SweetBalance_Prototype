package com.sweetbalance.backend.repository;

import com.sweetbalance.backend.entity.RefreshEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshRepository extends JpaRepository<RefreshEntity, Long> {

    boolean existsByRefresh(String refresh);

    @Transactional
    void deleteByRefresh(String refresh);
}