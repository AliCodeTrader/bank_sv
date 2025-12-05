// مدل ساده‌ی کاربر → فقط برای لاگین و نگهداری اطلاعات پایه
package model;

// مدل کاربر برای لاگین و نگه داشتن اطلاعات ساده‌ی کاربر
public class User {

    // اطلاعات کاربر (خیلی ساده، بدون چیزای پیچیده)
    private String id;        // شناسه کاربر
    private String username;  // نام کاربری
    private String password;  // رمز عبور

    // سازنده‌ی کامل کاربر → وقتی کاربر جدید می‌سازیم ازش استفاده می‌کنیم
    // سازنده کامل
    public User(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    // گترها (فقط می‌خونیم، کاریشون نمی‌کنیم)
    // گترها
    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    // ست کردن در صورت نیاز (فعلاً نیازی نداریم ولی گذاشتیم که اگه خواستیم تغییرش بدیم)
    // ست یوزر و پس فقط در صورت نیاز، فعلاً نمی‌خواهیم
    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
