package com.xinfj.common.util;

/**
 * @author PXIN
 * @create 2021-06-11 10:53
 * 雪花算法生成分布式id
 */
public class SnowflakeIdGenerate {

    /**
     * 所占位数、位移、掩码/极大值
     */
    private static final long SEQUENCE_BITS = 12L;
    private static final long SEQUENCE_SHIFT = 0L;
    private static final long SEQUENCE_MASK = ~(-1L << SEQUENCE_BITS);

    private static final long WORKER_ID_BITS = 5L;
    private static final long WORKER_ID_SHIFT = SEQUENCE_BITS;
    private static final long WORKER_ID_MASK = ~(-1L << WORKER_ID_BITS);

    private static final long DATA_CENTER_ID_BITS = 5L;
    private static final long DATA_CENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;
    private static final long DATA_CENTER_ID_MASK = ~(-1L << DATA_CENTER_ID_BITS);

    private static final long TIMESTAMP_BITS = 41L;
    private static final long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATA_CENTER_ID_BITS;
    private static final long TIMESTAMP_MASK = ~(-1L << TIMESTAMP_BITS);

    /**
     * 开始时间截 (2015-01-01 08:00:00)
     * 时间戳=时间戳毫秒值/1000
     */
    private static final long twepoch = 1420070400000L;
    /*
     * Instant instant = Instant.parse("2015-01-01T00:00:00Z");
     * System.out.println(instant.getEpochSecond());
     * System.out.println(instant.toEpochMilli());
     */


    private long sequence = 0L;
    private final long workerId;
    private final long dataCenterId;

    /**
     * 上次生成 ID 的时间截
     */
    private long lastTimestamp = -1L;

    //==============================Constructors=====================================

    public SnowflakeIdGenerate() {
        this(0, 0);
    }

    /**
     * 构造函数
     *
     * @param workerId     工作ID (0~31)
     * @param dataCenterId 数据中心 ID (0~31)
     */
    public SnowflakeIdGenerate(long workerId, long dataCenterId) {
        if (workerId > WORKER_ID_MASK || workerId < 0) {
            throw new IllegalArgumentException(String.format("workerId can't be greater than %d or less than 0", WORKER_ID_MASK));
        }

        if (dataCenterId > DATA_CENTER_ID_MASK || dataCenterId < 0) {
            throw new IllegalArgumentException(String.format("dataCenterId can't be greater than %d or less than 0", DATA_CENTER_ID_MASK));
        }

        this.workerId = workerId;
        this.dataCenterId = dataCenterId;
    }

    // ============================== Methods ==========================================

    /**
     * 获得下一个 ID (该方法是线程安全的，synchronized)
     */
    public synchronized long nextId() {
        long timestamp = timeGen();

        // 如果当前时间小于上一次 ID 生成的时间戳，说明系统时钟回退过，这个时候应当抛出异常。
        // 出现这种原因是因为系统的时间被回拨，或出现闰秒现象。
        // 你也可以不抛出异常，而是调用 tilNextMillis 进行等待
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                    String.format("Clock moved backwards. Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        // 如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            // 相同毫秒内，序列号自增
            sequence = (sequence + 1) & SEQUENCE_MASK;
            // 毫秒内序列溢出，即，同一毫秒的序列数已经达到最大
            if (sequence == 0) {
                // 阻塞到下一个毫秒,获得新的时间戳
                timestamp = tilNextMillis(timeGen(),lastTimestamp);
            }
        }
        // 时间戳改变，毫秒内序列重置
        else {
            sequence = 0L;
        }

        // 将当前生成的时间戳记录为【上次时间戳】。【下次】生成时间戳时要用到。
        lastTimestamp = timestamp;

        // 移位并通过或运算拼到一起组成 64 位的 ID
        return ((timestamp - twepoch) << TIMESTAMP_LEFT_SHIFT) // 时间戳部分
                | (dataCenterId << DATA_CENTER_ID_SHIFT) // 数据中心部分
                | (workerId << WORKER_ID_SHIFT) // 机器标识部分
                | sequence; // 序列号部分
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     *
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     *
     * @param timestamp     当前时间错
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    protected long tilNextMillis(long timestamp, long lastTimestamp) {
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 返回以毫秒为单位的当前时间
     *
     * @return 当前时间(毫秒)
     */
    protected long timeGen() {
        return System.currentTimeMillis();
    }

    //==============================Test=============================================


    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
        SnowflakeIdGenerate idWorker = new SnowflakeIdGenerate();
        long startTime = System.nanoTime();
        for (int i = 0; i < 50000; i++) {
            long id = idWorker.nextId();
            System.out.println(id);
        }
        System.out.println((System.nanoTime() - startTime) / 1000000 + "ms");
    }


}
