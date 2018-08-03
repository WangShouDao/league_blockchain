package com.league.blockchain.core.repository;

import com.league.blockchain.core.model.SyncEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SyncRepository extends JpaRepository<SyncEntity, Long> {
}
