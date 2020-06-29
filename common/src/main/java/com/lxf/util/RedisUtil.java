//package com.lxf.util;
//
//import org.springframework.data.redis.core.Cursor;
//import org.springframework.data.redis.core.ListOperations;
//import org.springframework.data.redis.core.ScanOptions;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.stereotype.Component;
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.JedisPool;
//
//import javax.annotation.Resource;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.concurrent.TimeUnit;
//
///**
// * @author: lxf
// * @create: 2020-04-19 09:54
// * @description: RedisUtil 工具类
// */
////@Component
//public class RedisUtil {
//    @Resource(name="stringRedisTemplate")
//    private StringRedisTemplate stringRedisTemplate;
//
//    /**
//     * 设置一个值
//     * @param key 键
//     * @param value 值,对象需要实现序列化
//     * @param seconds 过期时间,秒
//     */
//    public void setString(String key, String value, int seconds) {
//        stringRedisTemplate.opsForValue().set(key, value, seconds, TimeUnit.SECONDS);
//    }
//
//    public void setString(String key, String value) {
//        stringRedisTemplate.opsForValue().set(key, value);
//    }
//
//    public Object getString(String key) {
//        return stringRedisTemplate.opsForValue().get(key);
//    }
//
//    /**
//     * 修改名称
//     */
//    public void rename(String key, String newkey){
//        stringRedisTemplate.rename(key, newkey);
//    }
//
//    @Resource(name = "jedisPool")
//    private JedisPool jedisPool;
//
//    /**
//     * 获得key还有多久过期
//     * @param key 键
//     * @return 剩余过期时间(秒)：-2是不存在，-1是过期
//     */
//    public Long ttl(String key) {
//        try (Jedis jedis = jedisPool.getResource()) {
//            return jedis.ttl(key);
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//    /**
//     * 获取分布式锁
//     * 注意搭配finally，在里面删除该锁
//     * @param key 锁名称
//     * @param expireSeconds 自动释放时间
//     * @return true表示获取成功，false表示获取失败
//     */
////    public Boolean distributeLock(String key, int expireSeconds) {
////        try (Jedis jedis = jedisPool.getResource()) {
////            String result = jedis.set(key, null, "NX", "EX", expireSeconds);
////            return "OK".equals(result);
////        } catch (Exception e) {
////            return false;
////        }
////    }
//
//    /**
//     * 重新设置超时时间
//     * @param key key
//     * @param expireSeconds 新的超时时间
//     */
//    public void delayExpireTime(String key, int expireSeconds) {
//        stringRedisTemplate.expire(key, expireSeconds, TimeUnit.SECONDS);
//    }
//
//    /**
//     * 删除某个key
//     * @param key key名
//     */
//    public void delete(String key) {
//        stringRedisTemplate.delete(key);
//    }
//
//    /**
//     * 检查key是否存在
//     * @param key key名
//     * @return boolean
//     */
//    public Boolean exists(String key){
//        return stringRedisTemplate.hasKey(key);
//    }
//
//    /**
//     * 返回所有以 key 开头的key
//     * @param prefix 开头的key
//     * @return true表示存在
//     */
//    public Set<String> getKeysBeginWith(String prefix) {
//        return stringRedisTemplate.keys(prefix + "*");
//    }
//
//    /* ------------------------------ 队列相关 ------------------------------*/
//
//    /**
//     * 添加到队列
//     * @param key 队列的key
//     * @param value 值
//     */
//    public void listPush(String key, String value) {
//        ListOperations<String, String> listOperations = stringRedisTemplate.opsForList();
//        listOperations.leftPush(key, value);
//    }
//
//    public void listPopCopy(String fromKey, String toKey) {
//        ListOperations<String, String> listOperations = stringRedisTemplate.opsForList();
//        listOperations.rightPopAndLeftPush(fromKey, toKey);
//    }
//
//    public String listGet(String key, int index) {
//        ListOperations<String, String> listOperations = stringRedisTemplate.opsForList();
//        return listOperations.index(key, index);
//    }
//
//    /**
//     * 从队列取出
//     * @param key 队列的key
//     * @param seconds 时间
//     * @return 取出的值
//     */
//    public Object listBrPop(String key, int seconds) {
//        ListOperations<String, String> list = stringRedisTemplate.opsForList();
//        return list.rightPop(key, seconds, TimeUnit.SECONDS);
//    }
//
//    /**
//     * 从队列取值（无超时）
//     * @param key 队列的key
//     * @return 结果
//     */
//    public Object listPop(String key) {
//        ListOperations<String, String> list = stringRedisTemplate.opsForList();
//        return list.rightPop(key);
//    }
//
//    /**
//     * 获取队列的长度
//     * @param key 队列的key
//     * @return 取出的值
//     */
//    public Long listSize(String key) {
//        ListOperations<String, String> list = stringRedisTemplate.opsForList();
//        return list.size(key);
//    }
//
//
//    /* ------------------------------ 原子增减 ------------------------------*/
//
//    /**
//     * 增加／减少 原子操作
//     * @return 操作后的值
//     */
//    public double mapIncrBy(String key, double value){
//        return stringRedisTemplate.boundValueOps(key).increment(value);
//    }
//
//    /**
//     * 增加／减少 原子操作
//     * @return 操作后的值
//     */
//    public long mapIncrBy(String key, long value){
//        return stringRedisTemplate.boundValueOps(key).increment(value);
//    }
//
//
//    /* ------------------------------ set相关 ------------------------------*/
//
//    /**
//     * 添加大set集合
//     * @return 添加的数量
//     */
//    public long setAdd(String key, String... values){
//        return stringRedisTemplate.opsForSet().add(key, values);
//    }
//
//    /**
//     * 获取集合的数量
//     */
//    public long setCount(String key) {
//        return stringRedisTemplate.opsForSet().size(key);
//    }
//
//    /**
//     * 集合中是否存在
//     */
//    public boolean setExist(String key, String value) {
//        return stringRedisTemplate.opsForSet().isMember(key, value);
//    }
//
//    /**
//     * 返回集合
//     */
//    public Set<String> setMembers(String key){
//        return stringRedisTemplate.opsForSet().members(key);
//    }
//
//    /**
//     * 关键字查找
//     */
//    public List<String> setMatch(String key, String keyword) {
//        Cursor<String> scan = stringRedisTemplate.opsForSet().scan(key, ScanOptions.scanOptions().match(keyword).count(Integer.MAX_VALUE).build());
//        List<String> retlist = new ArrayList<>();
//        while(scan.hasNext()){
//            retlist.add(scan.next());
//        }
//        return retlist;
//    }
//
//    /**
//     * 移除并返回集合中的一个随机元素
//     */
//    public String setPop(String key) {
//        return stringRedisTemplate.opsForSet().pop(key);
//    }
//
//    /**
//     * 移除集合 key 中的一个或多个 member 元素，不存在的 member 元素会被忽略
//     */
//    public long setDel(String key, String... member){
//        return stringRedisTemplate.opsForSet().remove(key, member);
//    }
//
//    /* ------------------------------ map相关 ------------------------------*/
//
//    /**
//     * 保存map字符串键值对
//     */
//    public void mapAdd(String key, String hkey, String value){
//        stringRedisTemplate.opsForHash().put(key, hkey, value);
//    }
//
//    /**
//     * 删除map中的一个属性
//     */
//    public void mapDel(String key, String hkey) {
//        stringRedisTemplate.opsForHash().delete(key, hkey);
//    }
//
//    /**
//     * 获取map字符串值
//     */
//    public Object mapGet(String key, String hkey) {
//        return stringRedisTemplate.opsForHash().get(key, hkey);
//    }
//
//    /**
//     * map中这个key是否存在
//     */
//    public Boolean mapKeyExist(String key, String hKey) {
//        return stringRedisTemplate.opsForHash().hasKey(key, hKey);
//    }
//
//    public long mapKeyCount(String key) {
//        return stringRedisTemplate.opsForHash().size(key);
//    }
//
//    /**
//     * 获取map
//     */
//    public Map mapGetAll(String key) {
//        return stringRedisTemplate.opsForHash().entries(key);
//    }
//
//    /**
//     * 对map的值做原子增减
//     */
//    public long mapIncrBy(String key, String hkey, long value){
//        return stringRedisTemplate.opsForHash().increment(key, hkey, value);
//    }
//
//    /**
//     * 对map的值做原子增减
//     */
//    public double mapIncrBy(String key, String hkey, double value){
//        return stringRedisTemplate.opsForHash().increment(key, hkey, value);
//    }
//
//    /**
//     * 设置hash，如果属性存在则不会覆盖并返回false
//     */
//    public boolean mapAddNX(String key, String hkey, String value){
//        return stringRedisTemplate.opsForHash().putIfAbsent(key, hkey, value);
//    }
//
//}
