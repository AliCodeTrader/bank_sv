// هندل‌کننده هر کلاینت → هر کسی وصل میشه، یک ترد جدا می‌گیره
package net;

import service.BankService;

import java.io.*;
import java.net.Socket;

// رسیدگی به هر کلاینت به صورت جداگانه در یک ترد
public class ClientHandler implements Runnable {

    // سوکت مربوط به همین کاربر
    private final Socket socket;
    // سرویسی که کارای بانکی رو انجام میده
    private final BankService bankService;

    // سازنده: سوکت و سرویس بانک رو می‌گیریم
    public ClientHandler(Socket socket, BankService bankService) {
        this.socket = socket;
        this.bankService = bankService;
    }

    // این متد داخل ترد اجرا میشه و دائم درخواست‌های این کلاینت رو می‌خونه
    @Override
    public void run() {
        try (
                // ورودی و خروجی رو آماده می‌کنیم برای خوندن/نوشتن JSON
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream())
                );
                BufferedWriter out = new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream())
                )
        ) {
            // تا زمانی که کلاینت پیام میفرسته (هر خط یک JSON)
            String line;
            while ((line = in.readLine()) != null) {
                // فقط برای دیباگ → ببینیم چی از سمت کلاینت میاد
                System.out.println("Request from client: " + line);

                // درخواست رو می‌فرستیم به سرویس بانک و JSON جواب رو می‌گیریم
                String responseJson = bankService.handleRequest(line);

                // جواب رو دوباره به کلاینت می‌فرستیم
                out.write(responseJson);
                out.newLine();
                out.flush();
            }

        // اگه کاربر قطع شد یا خطایی رخ داد اینجا میاد
        } catch (IOException e) {
            System.out.println("Client error: " + e.getMessage());
        } finally {
            try {
                // آخر کار سوکت رو می‌بندیم (کار این کلاینت تموم شده)
                socket.close();
            } catch (IOException ignored) {
            }
        }
    }
}