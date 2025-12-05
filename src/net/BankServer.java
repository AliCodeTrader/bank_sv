// سرور اصلی بانک → اینجا سوکت باز می‌کنیم و منتظر کلاینت می‌مونیم
package net;

import service.BankService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BankServer {

    public static void main(String[] args) {
        // پورتی که سرور روش گوش می‌ده (هر کلاینت باید به همین پورت وصل شه)
        int port = 4040;
        System.out.println("Starting bank server on port " + port);

        // سرویس اصلی عملیات بانکی → اینو به هر کلاینت می‌دیم
        BankService bankService = new BankService();

        // سوکت سرور رو روی پورت مشخص باز می‌کنیم
        // این تیکه داخل try گذاشتیم که آخر کار خودش بسته بشه
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            // یه حلقه بی‌نهایت → همیشه منتظر کلاینت جدید می‌مونیم
            while (true) {
                // accept یعنی یک کلاینت جدید وصل شد
                Socket client = serverSocket.accept();
                System.out.println("Client connected: " + client.getInetAddress());

                ClientHandler handler = new ClientHandler(client, bankService);
                // برای هر کلاینت یک ترد جدا باز می‌کنیم
                // اینطوری چند نفر همزمان می‌تونن وصل بشن
                new Thread(handler).start();
            }
        // اگه مشکلی توی سرور پیش بیاد اینجا می‌گیریمش
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }
}