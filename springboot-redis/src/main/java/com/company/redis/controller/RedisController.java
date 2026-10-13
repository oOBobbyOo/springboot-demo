package com.company.redis.controller;

import com.company.redis.utils.RedisUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Henry
 */
@RestController
@RequestMapping("/api/redis")
public class RedisController {

    private final RedisUtil redisUtil;

    public RedisController(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    // ==================== 健康检查 ====================

    /** GET /api/redis/healthy  Redis 可用返回 200 与 UP，否则返回 503 */
    @GetMapping("/healthy")
    public ResponseEntity<Map<String, String>> healthy() {
        try {
            String pong = redisUtil.ping();
            if ("PONG".equalsIgnoreCase(pong)) {
                return ResponseEntity.ok(Map.of("status", "UP"));
            }
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(Map.of("status", "DOWN", "detail", String.valueOf(pong)));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(Map.of("status", "DOWN", "detail", String.valueOf(e.getMessage())));
        }
    }

    // ==================== 通用 ====================

    /** GET /api/redis/keys/{key}/exists */
    @GetMapping("/keys/{key}/exists")
    public boolean hasKey(@PathVariable String key) {
        return redisUtil.hasKey(key);
    }

    /** DELETE /api/redis/keys/{key} */
    @DeleteMapping("/keys/{key}")
    public boolean delete(@PathVariable String key) {
        return redisUtil.delete(key);
    }

    /** PUT /api/redis/keys/{key}/expire?seconds=60 */
    @PutMapping("/keys/{key}/expire")
    public boolean expire(@PathVariable String key, @RequestParam long seconds) {
        return redisUtil.expire(key, Duration.ofSeconds(seconds));
    }

    /** GET /api/redis/keys/{key}/ttl */
    @GetMapping("/keys/{key}/ttl")
    public long getExpire(@PathVariable String key) {
        return redisUtil.getExpire(key);
    }

    /** PUT /api/redis/keys/{key}/persist */
    @PutMapping("/keys/{key}/persist")
    public boolean persist(@PathVariable String key) {
        return redisUtil.persist(key);
    }

    // ==================== String ====================

    /** PUT /api/redis/string/{key}?ttlSeconds=60  请求体为任意 JSON 值 */
    @PutMapping("/string/{key}")
    public void set(@PathVariable String key,
                    @RequestBody Object value,
                    @RequestParam(required = false) Long ttlSeconds) {
        if (ttlSeconds == null) {
            redisUtil.set(key, value);
        } else {
            redisUtil.set(key, value, Duration.ofSeconds(ttlSeconds));
        }
    }

    /** GET /api/redis/string/{key} */
    @GetMapping("/string/{key}")
    public Object get(@PathVariable String key) {
        return redisUtil.get(key);
    }

    /** POST /api/redis/string/{key}/increment?delta=1 */
    @PostMapping("/string/{key}/increment")
    public long increment(@PathVariable String key, @RequestParam(defaultValue = "1") long delta) {
        return redisUtil.increment(key, delta);
    }

    /** POST /api/redis/string/{key}/decrement?delta=1 */
    @PostMapping("/string/{key}/decrement")
    public long decrement(@PathVariable String key, @RequestParam(defaultValue = "1") long delta) {
        return redisUtil.decrement(key, delta);
    }

    // ==================== Hash ====================

    /** PUT /api/redis/hash/{key}/{hashKey}  请求体为任意 JSON 值 */
    @PutMapping("/hash/{key}/{hashKey}")
    public void hSet(@PathVariable String key, @PathVariable String hashKey, @RequestBody Object value) {
        redisUtil.hSet(key, hashKey, value);
    }

    /** PUT /api/redis/hash/{key}  请求体为 JSON 对象 */
    @PutMapping("/hash/{key}")
    public void hSetAll(@PathVariable String key, @RequestBody Map<String, Object> map) {
        redisUtil.hSetAll(key, map);
    }

    /** GET /api/redis/hash/{key}/{hashKey} */
    @GetMapping("/hash/{key}/{hashKey}")
    public Object hGet(@PathVariable String key, @PathVariable String hashKey) {
        return redisUtil.hGet(key, hashKey);
    }

    /** POST /api/redis/hash/{key}/multi-get  请求体为 hashKey 数组 */
    @PostMapping("/hash/{key}/multi-get")
    public List<Object> hMultiGet(@PathVariable String key, @RequestBody List<String> hashKeys) {
        return redisUtil.hMultiGet(key, hashKeys);
    }

    /** GET /api/redis/hash/{key} */
    @GetMapping("/hash/{key}")
    public Map<Object, Object> hEntries(@PathVariable String key) {
        return redisUtil.hEntries(key);
    }

    /** GET /api/redis/hash/{key}/{hashKey}/exists */
    @GetMapping("/hash/{key}/{hashKey}/exists")
    public boolean hHasKey(@PathVariable String key, @PathVariable String hashKey) {
        return redisUtil.hHasKey(key, hashKey);
    }

    /** DELETE /api/redis/hash/{key}/{hashKey} */
    @DeleteMapping("/hash/{key}/{hashKey}")
    public long hDelete(@PathVariable String key, @PathVariable String hashKey) {
        return redisUtil.hDelete(key, hashKey);
    }

    /** POST /api/redis/hash/{key}/{hashKey}/increment?delta=1 */
    @PostMapping("/hash/{key}/{hashKey}/increment")
    public long hIncrement(@PathVariable String key, @PathVariable String hashKey,
                           @RequestParam(defaultValue = "1") long delta) {
        return redisUtil.hIncrement(key, hashKey, delta);
    }

    /** GET /api/redis/hash/{key}/size */
    @GetMapping("/hash/{key}/size")
    public long hSize(@PathVariable String key) {
        return redisUtil.hSize(key);
    }

    // ==================== List ====================

    /** POST /api/redis/list/{key}/left  请求体为任意 JSON 值 */
    @PostMapping("/list/{key}/left")
    public long lLeftPush(@PathVariable String key, @RequestBody Object value) {
        return redisUtil.lLeftPush(key, value);
    }

    /** POST /api/redis/list/{key}/right  请求体为任意 JSON 值 */
    @PostMapping("/list/{key}/right")
    public long lRightPush(@PathVariable String key, @RequestBody Object value) {
        return redisUtil.lRightPush(key, value);
    }

    /** DELETE /api/redis/list/{key}/left */
    @DeleteMapping("/list/{key}/left")
    public Object lLeftPop(@PathVariable String key) {
        return redisUtil.lLeftPop(key);
    }

    /** DELETE /api/redis/list/{key}/right */
    @DeleteMapping("/list/{key}/right")
    public Object lRightPop(@PathVariable String key) {
        return redisUtil.lRightPop(key);
    }

    /** GET /api/redis/list/{key}?start=0&end=-1 */
    @GetMapping("/list/{key}")
    public List<Object> lRange(@PathVariable String key,
                               @RequestParam(defaultValue = "0") long start,
                               @RequestParam(defaultValue = "-1") long end) {
        return redisUtil.lRange(key, start, end);
    }

    /** GET /api/redis/list/{key}/size */
    @GetMapping("/list/{key}/size")
    public long lSize(@PathVariable String key) {
        return redisUtil.lSize(key);
    }

    // ==================== Set ====================

    /** POST /api/redis/set/{key}  请求体为数组 */
    @PostMapping("/set/{key}")
    public long sAdd(@PathVariable String key, @RequestBody List<Object> values) {
        return redisUtil.sAdd(key, values.toArray());
    }

    /** GET /api/redis/set/{key} */
    @GetMapping("/set/{key}")
    public Set<Object> sMembers(@PathVariable String key) {
        return redisUtil.sMembers(key);
    }

    /** GET /api/redis/set/{key}/members/{value}/exists */
    @GetMapping("/set/{key}/members/{value}/exists")
    public boolean sIsMember(@PathVariable String key, @PathVariable String value) {
        return redisUtil.sIsMember(key, value);
    }

    /** POST /api/redis/set/{key}/remove  请求体为数组 */
    @PostMapping("/set/{key}/remove")
    public long sRemove(@PathVariable String key, @RequestBody List<Object> values) {
        return redisUtil.sRemove(key, values.toArray());
    }

    /** GET /api/redis/set/{key}/size */
    @GetMapping("/set/{key}/size")
    public long sSize(@PathVariable String key) {
        return redisUtil.sSize(key);
    }

    // ==================== ZSet ====================

    /** PUT /api/redis/zset/{key}/{value}?score=1.0 */
    @PutMapping("/zset/{key}/{value}")
    public boolean zAdd(@PathVariable String key, @PathVariable String value, @RequestParam double score) {
        return redisUtil.zAdd(key, value, score);
    }

    /** GET /api/redis/zset/{key}?start=0&end=-1 按 score 升序 */
    @GetMapping("/zset/{key}")
    public Set<Object> zRange(@PathVariable String key,
                              @RequestParam(defaultValue = "0") long start,
                              @RequestParam(defaultValue = "-1") long end) {
        return redisUtil.zRange(key, start, end);
    }

    /** GET /api/redis/zset/{key}/reverse?start=0&end=-1 按 score 降序 */
    @GetMapping("/zset/{key}/reverse")
    public Set<Object> zReverseRange(@PathVariable String key,
                                     @RequestParam(defaultValue = "0") long start,
                                     @RequestParam(defaultValue = "-1") long end) {
        return redisUtil.zReverseRange(key, start, end);
    }

    /** GET /api/redis/zset/{key}/members/{value}/score */
    @GetMapping("/zset/{key}/members/{value}/score")
    public Double zScore(@PathVariable String key, @PathVariable String value) {
        return redisUtil.zScore(key, value);
    }

    /** POST /api/redis/zset/{key}/members/{value}/increment?delta=1.0 */
    @PostMapping("/zset/{key}/members/{value}/increment")
    public double zIncrementScore(@PathVariable String key, @PathVariable String value,
                                  @RequestParam double delta) {
        return redisUtil.zIncrementScore(key, value, delta);
    }

    /** DELETE /api/redis/zset/{key}/members/{value} */
    @DeleteMapping("/zset/{key}/members/{value}")
    public long zRemove(@PathVariable String key, @PathVariable String value) {
        return redisUtil.zRemove(key, value);
    }

    /** GET /api/redis/zset/{key}/size */
    @GetMapping("/zset/{key}/size")
    public long zSize(@PathVariable String key) {
        return redisUtil.zSize(key);
    }
}
