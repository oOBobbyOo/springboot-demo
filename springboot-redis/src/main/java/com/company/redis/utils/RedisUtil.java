package com.company.redis.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Henry
 */
@Component
@RequiredArgsConstructor
public class RedisUtil {

    private final RedisTemplate<String, Object> redisTemplate;

    // ==================== 通用 ====================

    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    public boolean delete(String key) {
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    public long delete(Collection<String> keys) {
        Long count = redisTemplate.delete(keys);
        return count == null ? 0 : count;
    }

    public boolean expire(String key, Duration timeout) {
        return Boolean.TRUE.equals(redisTemplate.expire(key, timeout));
    }

    /**
     * @return 剩余存活秒数；-1 表示永久，-2 表示 key 不存在
     */
    public long getExpire(String key) {
        Long ttl = redisTemplate.getExpire(key);
        return ttl == null ? -2 : ttl;
    }

    public boolean persist(String key) {
        return Boolean.TRUE.equals(redisTemplate.persist(key));
    }

    public String ping() {
        return redisTemplate.execute((RedisCallback<String>) RedisConnection::ping);
    }

    // ==================== String ====================

    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void set(String key, Object value, Duration timeout) {
        redisTemplate.opsForValue().set(key, value, timeout);
    }

    /**
     * 仅当 key 不存在时写入（分布式锁等场景）
     */
    public boolean setIfAbsent(String key, Object value, Duration timeout) {
        return Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(key, value, timeout));
    }

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public <T> T get(String key, Class<T> type) {
        Object value = redisTemplate.opsForValue().get(key);
        return value == null ? null : type.cast(value);
    }

    public long increment(String key, long delta) {
        Long result = redisTemplate.opsForValue().increment(key, delta);
        return result == null ? 0 : result;
    }

    public long decrement(String key, long delta) {
        Long result = redisTemplate.opsForValue().decrement(key, delta);
        return result == null ? 0 : result;
    }

    // ==================== Hash ====================

    public void hSet(String key, String hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    public void hSetAll(String key, Map<String, Object> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    public Object hGet(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    public List<Object> hMultiGet(String key, Collection<String> hashKeys) {
        return redisTemplate.opsForHash().multiGet(key, List.copyOf(hashKeys));
    }

    public Map<Object, Object> hEntries(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    public boolean hHasKey(String key, String hashKey) {
        return Boolean.TRUE.equals(redisTemplate.opsForHash().hasKey(key, hashKey));
    }

    public long hDelete(String key, Object... hashKeys) {
        Long count = redisTemplate.opsForHash().delete(key, hashKeys);
        return count == null ? 0 : count;
    }

    public long hIncrement(String key, String hashKey, long delta) {
        Long result = redisTemplate.opsForHash().increment(key, hashKey, delta);
        return result == null ? 0 : result;
    }

    public long hSize(String key) {
        Long size = redisTemplate.opsForHash().size(key);
        return size == null ? 0 : size;
    }

    // ==================== List ====================

    public long lLeftPush(String key, Object value) {
        Long size = redisTemplate.opsForList().leftPush(key, value);
        return size == null ? 0 : size;
    }

    public long lRightPush(String key, Object value) {
        Long size = redisTemplate.opsForList().rightPush(key, value);
        return size == null ? 0 : size;
    }

    public Object lLeftPop(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    public Object lRightPop(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }

    /**
     * @param end -1 表示到列表末尾
     */
    public List<Object> lRange(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    public long lSize(String key) {
        Long size = redisTemplate.opsForList().size(key);
        return size == null ? 0 : size;
    }

    // ==================== Set ====================

    public long sAdd(String key, Object... values) {
        Long count = redisTemplate.opsForSet().add(key, values);
        return count == null ? 0 : count;
    }

    public Set<Object> sMembers(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    public boolean sIsMember(String key, Object value) {
        return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(key, value));
    }

    public long sRemove(String key, Object... values) {
        Long count = redisTemplate.opsForSet().remove(key, values);
        return count == null ? 0 : count;
    }

    public long sSize(String key) {
        Long size = redisTemplate.opsForSet().size(key);
        return size == null ? 0 : size;
    }

    // ==================== ZSet ====================

    public boolean zAdd(String key, Object value, double score) {
        return Boolean.TRUE.equals(redisTemplate.opsForZSet().add(key, value, score));
    }

    /**
     * @param end -1 表示到集合末尾，按 score 升序
     */
    public Set<Object> zRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().range(key, start, end);
    }

    /**
     * @param end -1 表示到集合末尾，按 score 降序
     */
    public Set<Object> zReverseRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().reverseRange(key, start, end);
    }

    public Double zScore(String key, Object value) {
        return redisTemplate.opsForZSet().score(key, value);
    }

    public double zIncrementScore(String key, Object value, double delta) {
        Double score = redisTemplate.opsForZSet().incrementScore(key, value, delta);
        return score == null ? 0 : score;
    }

    public long zRemove(String key, Object... values) {
        Long count = redisTemplate.opsForZSet().remove(key, values);
        return count == null ? 0 : count;
    }

    public long zSize(String key) {
        Long size = redisTemplate.opsForZSet().size(key);
        return size == null ? 0 : size;
    }
}
