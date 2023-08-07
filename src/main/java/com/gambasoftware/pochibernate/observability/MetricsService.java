package com.gambasoftware.pochibernate.observability;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicLong;

public class MetricsService {
    private static final Map<String, ConcurrentLinkedQueue<AtomicLong>> mapCounters = new ConcurrentHashMap<>();

    public static boolean saveMetric(String classMethod, AtomicLong value) {
        if (mapCounters.containsKey(classMethod)) {
            mapCounters.get(classMethod).add(value);
        } else {
            ConcurrentLinkedQueue<AtomicLong> list = new ConcurrentLinkedQueue<>();
            list.add(value);
            mapCounters.put(classMethod, list);
        }
        return true;
    }

    public static String getMetrics() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String object : mapCounters.keySet()) {
            ConcurrentLinkedQueue<AtomicLong> atomicLongs = mapCounters.get(object);
            Optional<AtomicLong> maxValue = atomicLongs.stream().reduce((atomicLong, atomicLong2) ->
                    new AtomicLong(Math.max(atomicLong.longValue(), atomicLong2.longValue())));
            Optional<AtomicLong> minValue = atomicLongs.stream().reduce((atomicLong, atomicLong2) ->
                    new AtomicLong(Math.min(atomicLong.longValue(), atomicLong2.longValue())));
            Optional<AtomicLong> meanValue = atomicLongs.stream().reduce((atomicLong, atomicLong2) ->
                    new AtomicLong(atomicLong.addAndGet(atomicLong2.longValue())));
            stringBuilder.append("\n" + object + " meanValue " + meanValue.get().intValue()/atomicLongs.size() + " ms");
            stringBuilder.append("\n" + object + " maxValue " + maxValue.get() + " ms");
            stringBuilder.append("\n" + object + " minValue " + minValue.get() + " ms");
        }
        return stringBuilder.toString();
    }
}
