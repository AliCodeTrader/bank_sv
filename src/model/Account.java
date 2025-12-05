// مدل حساب بانکی → اطلاعات پایه + لیست تراکنش‌ها
package model;

import java.util.ArrayList;
import java.util.List;

// مدل حساب بانکی — شامل شماره حساب، کارت، موجودی و لیست تراکنش‌ها
public class Account {

    // اطلاعات اصلی حساب 
    private String id;             // شناسه حساب
    private String cardNumber;     // شماره کارت (مثلاً 6037...)
    private String accountNumber;  // شماره حساب
    private int balance;           // موجودی فعلی حساب
    private List<Transaction> transactions; // لیست تراکنش‌ها

    // سازنده کامل → موقع ساخت حساب جدید، اطلاعاتشو می‌گیریم
    public Account(String id, String cardNumber, String accountNumber, int balance) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.transactions = new ArrayList<>(); // در ابتدا تراکنش خالی است
    }

    // گترها → فقط می‌خونیم (کاریشون نداریم)
    public String getId() {
        return id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public int getBalance() {
        return balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    // اضافه کردن تراکنش جدید و تغییر موجودی بر اساس نوعش
    public void addTransaction(Transaction t) {
        transactions.add(t);
        // اگه تراکنش واریز بود، موجودی زیاد میشه، اگه برداشت بود کم میشه
        if (t.getType().equals("deposit")) {
            balance += t.getAmount();
        } else if (t.getType().equals("withdraw")) {
            balance -= t.getAmount();
        }
    }
}
