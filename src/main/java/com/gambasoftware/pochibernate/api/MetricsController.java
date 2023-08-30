package com.gambasoftware.pochibernate.api;

import com.gambasoftware.pochibernate.observability.MetricsService;
import net.sf.ehcache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MetricsController {

    private MetricsService metricsService;

    public MetricsController(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @GetMapping("/metrics")
    public ResponseEntity getMetrics() {
        return ResponseEntity.ok(metricsService.getMetricsFromDatabase());
    }

    @GetMapping("/cache/{cacheName}")
    public ResponseEntity getCacheStats(@PathVariable String cacheName) {
        return ResponseEntity.ok(CacheManager.ALL_CACHE_MANAGERS.get(0).getCache(cacheName).getSize());
    }
}
