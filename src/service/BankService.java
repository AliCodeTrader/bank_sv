package service;

// این بالا چیزایی که لازم داریم رو ایمپورت می‌کنیم (مدل‌ها)
import model.User;
import model.Account;
import model.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// کلاس اصلی سرویس بانک
// اینجا کل درخواست‌هایی که از سمت کلاینت میاد پردازش می‌کنیم
// سرویس اصلی بانک - منطق ساده برای هندل کردن درخواست‌ها
public class BankService {

    // یه لیست برای نگه داشتن کاربرا داریم
    private List<User> users;       
    // یه لیست هم برای حساب‌های بانکیشون
    private List<Account> accounts; 

    // سازنده سرویس → فعلاً داده‌ها رو فیک می‌ریزیم اینجا (بعداً از فایل JSON لود می‌کنیم)
    public BankService() {
        // اول لیست‌ها رو می‌سازیم (هنوز توشون چیزی نیست)
        users = new ArrayList<>();
        accounts = new ArrayList<>();

        // دو تا کاربر فیک می‌ریزیم داخل سیستم که بتونیم تست کنیم
        User u1 = new User("u1", "ali", "1234");
        User u2 = new User("u2", "sara", "9999");
        users.add(u1);
        users.add(u2);

        // دو تا حساب هم براشون می‌سازیم، هرکدوم یه موجودی اولیه دارن
        Account a1 = new Account("a1", "6037991111111111", "123-1", 1000);
        Account a2 = new Account("a2", "6037992222222222", "123-2", 500);
        accounts.add(a1);
        accounts.add(a2);

        // یه دونه تراکنش اولیه هم می‌زنیم که حسابا خالی نباشن
        a1.addTransaction(new Transaction("t1", "deposit", 1000, new Date(), "initial"));
        a2.addTransaction(new Transaction("t2", "deposit", 500, new Date(), "initial"));
    }

    // متدی که از کلاینت یک JSON می‌گیرد و بر اساس action جواب مناسب می‌سازد
    // ورودی: رشته JSON از کلاینت
    // خروجی: رشته JSON جواب
    public String handleRequest(String requestJson) {
        // اگه درخواست تهی باشه، خب نمی‌تونیم کاری کنیم :)
        if (requestJson == null) {
            return error("empty_request");
        }

        // درخواست تست ارتباط با سرور → فقط میگه سرور آنلاینه یا نه
        // درخواست تست
        if (requestJson.contains("\"action\":\"PING\"")) {
            // جواب پینگ: فقط میگه "من زنده‌ام!"
            return "{ \"status\": \"ok\", \"message\": \"pong\" }";
        }

        // درخواست لاگین  (فقط یوزرنیم و پسورد رو چک می‌کنیم)
        // لاگین ساده: {"action":"LOGIN","username":"ali","password":"1234"}
        if (requestJson.contains("\"action\":\"LOGIN\"")) {
            // از JSON، یوزرنیم و پسورد رو جدا می‌کنیم
            String username = extractValue(requestJson, "username");
            String password = extractValue(requestJson, "password");
            boolean ok = checkLogin(username, password);
            if (ok) {
                // اگه لاگین درست بود، یه جواب ok می‌فرستیم
                return "{ \"status\": \"ok\", \"username\": \"" + username + "\" }";
            } else {
                return error("invalid_credentials");
            }
        }

        // درخواست گرفتن لیست حساب‌های کاربر (فعلاً بدون فیلتر کاربر)
        // گرفتن لیست حساب‌ها: {"action":"GET_ACCOUNTS"}
        if (requestJson.contains("\"action\":\"GET_ACCOUNTS\"")) {
            // لیست حساب‌ها رو تبدیل به JSON می‌کنیم و جواب می‌دیم
            return buildAccountsJson();
        }

        return error("unknown_action");
    }

    // تابع ساده برای چک کردن یوزرنیم و پسورد
    // چک کردن یوزرنیم و پسورد ساده
    private boolean checkLogin(String username, String password) {
        // می‌گردیم ببینیم یوزرنیم و پسورد تو لیست کاربرا هست یا نه
        if (username == null || password == null) return false;
        for (User u : users) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    // ساخت JSON لیست حساب‌ها به صورت دستی (بدون استفاده از کتابخانه)
    // ساخت JSON لیست حساب‌ها (ساده و دستی)
    private String buildAccountsJson() {
        // اینجا دستی JSON می‌سازیم، خیلی و بدون کتابخونه :)
        StringBuilder sb = new StringBuilder();
        sb.append("{ \"status\": \"ok\", \"accounts\": [");
        for (int i = 0; i < accounts.size(); i++) {
            Account a = accounts.get(i);
            sb.append("{");
            sb.append("\"id\":\"").append(a.getId()).append("\",");
            sb.append("\"cardNumber\":\"").append(a.getCardNumber()).append("\",");
            sb.append("\"accountNumber\":\"").append(a.getAccountNumber()).append("\",");
            sb.append("\"balance\":").append(a.getBalance());
            sb.append("}");
            if (i < accounts.size() - 1) {
                sb.append(",");
            }
        }
        sb.append("]}");
        return sb.toString();
    }

    // این متد مقدار یک کلید را از یک رشته JSON در می‌آورد
    // گرفتن مقدار یک کلید ساده از JSON (برای پروژه کافی است)
    private String extractValue(String json, String key) {
        // یه الگو می‌سازیم که مقدار کلید مورد نظرمون رو پیدا کنیم
        String pattern = "\"" + key + "\":\"";
        int idx = json.indexOf(pattern);
        if (idx == -1) return null;
        int start = idx + pattern.length();
        int end = json.indexOf("\"", start);
        if (end == -1) return null;
        return json.substring(start, end);
    }

    // ساخت یک JSON خطا با یک کد مشخص
    // ساخت جواب خطا به صورت JSON
    private String error(String code) {
        // جواب خطا رو به شکل JSON برمی‌گردونیم
        return "{ \"status\": \"error\", \"code\": \"" + code + "\" }";
    }
}