package commons;

import java.util.Random;
import java.util.UUID;

public class DataHelper {

    // 1. Sinh số ngẫu nhiên theo giới hạn
    public static int getRandomNumber() {
        return new Random().nextInt(99999);
    }

    // 2. Sinh Email động siêu an toàn (kết hợp Thread ID + UUID để 0-conflict khi chạy song song)
    public static String getRandomEmail() {
        long threadId = Thread.currentThread().getId();
        String uuid = UUID.randomUUID().toString().substring(0, 5);
        return "auto_t" + threadId + "_" + uuid + "@qa.team";
    }

    // 3. Sinh Email dạng custom theo tên truyền vào
    public static String getCustomEmail(String prefix) {
        return prefix + getRandomNumber() + "@gmail.com";
    }

    // 4. Hàm tạm dừng luồng (ngắn gọn)
    public static void sleepInSecond(long timeInSecond) {
        try {
            Thread.sleep(timeInSecond * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}