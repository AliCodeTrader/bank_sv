// مدل تراکنش → هر پولی که جا به جا میشه تو سیستم، از این جنس ساخته میشه :)
package model;

import java.util.Date;

// مدل تراکنش — هر حرکت مالی (واریز / برداشت / انتقال) روی حساب
public class Transaction {

    // اطلاعات پایه‌ی تراکنش 
    private String id;      // شناسه‌ی تراکنش
    private String type;    // نوع تراکنش: deposit, withdraw, transfer
    private int amount;     // مبلغ تراکنش
    private Date date;      // تاریخ انجام تراکنش
    private String note;    // توضیح کوتاه (مثلاً "ATM" یا "Rent")

    // سازنده‌ی کامل → موقع ساخت تراکنش جدید همه اطلاعاتش رو می‌گیریم
    public Transaction(String id, String type, int amount, Date date, String note) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.date = date;
        this.note = note;
    }

    // گترها → فقط می‌خونیم (کاریشون نداریم)
    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }

    public String getNote() {
        return note;
    }

    // اگه خواستیم بعداً توضیح تراکنش رو عوض کنیم از این ست استفاده می‌کنیم
    public void setNote(String note) {
        this.note = note;
    }
}
