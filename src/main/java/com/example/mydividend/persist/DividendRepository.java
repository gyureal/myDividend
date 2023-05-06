package com.example.mydividend.persist;

import com.example.mydividend.persist.entity.DividendEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DividendRepository extends JpaRepository<DividendEntity, Long> {
}
