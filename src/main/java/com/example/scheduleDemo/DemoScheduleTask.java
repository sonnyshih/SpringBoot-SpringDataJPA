package com.example.scheduleDemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DemoScheduleTask {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedDelay = 2000) // fixedDelay = 2000 表示當前方法「執行完畢」後 2秒 後再執行一次 (程式跑完才會再跑一下次)
    public void testFixDelay() {
        logger.info("===fixedDelay: 時間:{}", dateFormat.format(new Date()));
    }

    @Scheduled(fixedRate = 2000) // fixedRate = 2000 表示當前方法「每2秒」會執行一次
    public void testFixedRate() {
        logger.info("===fixedRate: 時間:{}", dateFormat.format(new Date()));
    }


    @Scheduled(initialDelay = 2000, fixedRate = 3000) // initialDelay = 2000 表示延遲 (2秒) 執行第一次任務, 然後「每3秒」執一次
    public void testInitialDelay() {
        logger.info("===initialDelay: 時間:{}", dateFormat.format(new Date()));
    }


    /**
     * 1. * * * * * * : 秒 (0-59), 分 (0 - 59), 時 (0 - 23), 日 (1 - 31), 月 (1 - 12) (or JAN-DEC), 星期 (0 - 7)(0 or 7 is Sunday, or MON-SUN)
     * 2. 每個域可出現的字元型別和各字元的含義
     *  - 秒：可出現: ”, – * /” 左列的四個字元，有效範圍為0-59的整數
     *  - 分：可出現: ”, – * /” 左列的四個字元，有效範圍為0-59的整數
     *  - 時：可出現: ”, – * /” 左列的四個字元，有效範圍為0-23的整數
     *  - 每月第幾天：可出現: ”, – * / ? L W C” 左列的八個字元，有效範圍為0-31的整數
     *  - 月：可出現: ”, – * /” 左列的四個字元，有效範圍為1-12的整數或JAN-DEc
     *  - 星期：可出現: ”, – * / ? L C #” 左列的八個字元，有效範圍為1-7的整數或SUN-SAT兩個範圍。1表示星期天，2表示星期一， 依次類推
     *
     * 3. 每個域可出現的字元型別和各字元的含義
     *  - * : 表示匹配該域的任意值，比如在秒*, 就表示每秒都會觸發事件。；
     *  - ? : 只能用在每月第幾天和星期兩個域。表示不指定值，當2個子表示式其中之一被指定了值以後，為了避免衝突，需要將另一個子表示式的值設為“?”；
     *  - – : 表示範圍，例如在分域使用5-20，表示從5分到20分鐘每分鐘觸發一次
     *  - / : 表示起始時間開始觸發，然後每隔固定時間觸發一次，例如在分域使用5/20,則意味著5分，25分，45分，分別觸發一次.
     *  - , : 表示列出列舉值。例如：在分域使用5,20，則意味著在5和20分時觸發一次。
     *  - L : 表示最後，只能出現在星期和每月第幾天域，如果在星期域使用1L,意味著在最後的一個星期日觸發。
     *  - W : 表示有效工作日(週一到週五),只能出現在每月第幾日域，系統將在離指定日期的最近的有效工作日觸發事件。注意一點，W的最近尋找不會跨過月份
     *  - LW : 這兩個字元可以連用，表示在某個月最後一個工作日，即最後一個星期五。
     *  - # : 用於確定每個月第幾個星期幾，只能出現在每月第幾天域。例如在1#3，表示某月的第三個星期日。
     */
    @Scheduled(cron = "0 0/1 * * * ?") // 根據cron表示式確定定時規則 (秒, 分, 時, 日, 月, 星期) 0 0/1 * * * ? 表示從每小時的第0分開始，每一分鐘執行一次
    public void testCron() {
        logger.info("===cron: 時間:{}", dateFormat.format(new Date()));
    }
}
