package com.gambasoftware.pochibernate.data.repositories;

import com.gambasoftware.pochibernate.data.entities.Metrics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetricsRepository extends JpaRepository<Metrics, Long> {
}

