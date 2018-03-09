package io.recommendation.persist.hook;

public interface StreamHook {
    void process(String value);
}
