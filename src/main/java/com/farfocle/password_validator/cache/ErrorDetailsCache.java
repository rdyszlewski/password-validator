package com.farfocle.password_validator.cache;

import com.farfocle.password_validator.models.ErrorDetails;
import com.farfocle.password_validator.models.PasswordRuleResult;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ErrorDetailsCache {

    private final Map<PasswordRuleResult, ErrorDetails> cacheMap;
    private final Queue<PasswordRuleResult> queue;
    private final int size;

    public ErrorDetailsCache(int size) {
        this.size = size;
        this.cacheMap = new ConcurrentHashMap<>(size);
        this.queue = new ConcurrentLinkedQueue<>();
    }

    public ErrorDetails getErrorDetails(PasswordRuleResult ruleResult, CreateErrorDetailsFunction function) {
        int currentSize = cacheMap.size();
        ErrorDetails details = cacheMap.computeIfAbsent(ruleResult, function);
        if (currentSize != cacheMap.size()) {
            queue.add(ruleResult);
        }
        if (cacheMap.size() > this.size) {
            cacheMap.remove(queue.poll());
        }
        return details;
    }

    public boolean isPresent(PasswordRuleResult ruleResult) {
        return cacheMap.containsKey(ruleResult);
    }

    public int getSize() {
        return this.cacheMap.size();
    }


}
