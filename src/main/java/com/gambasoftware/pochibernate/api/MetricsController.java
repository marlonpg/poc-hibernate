package com.gambasoftware.pochibernate.api;

import com.gambasoftware.pochibernate.observability.MetricsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MetricsController {

    @GetMapping("/metrics")
    public ResponseEntity getMetrics(){
        return ResponseEntity.ok(MetricsService.getMetrics());
    }
}
