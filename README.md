🏦 Simple Bank System (Java Socket + Flutter Client)

This project is a basic banking system with a Java server and a Flutter (macOS) client.
The communication between client and server is done using JSON over Socket.

⸻

🔵 Server (Java)
	•	Built using ServerSocket
	•	Handles:
✔ User Login
✔ Fetching Accounts
✔ Account Transactions (Deposit/Withdraw)
	•	Models: User, Account, Transaction

How to Run
cd bank_server
javac src/model/*.java src/service/*.java src/net/*.java
java -cp src net.BankServer
You should see:
Starting bank server on port 4040

🟣 Client (Flutter macOS)
	•	Connects to the Java server using Socket (dart:io)
	•	Login with username/password
	•	Loads accounts and balances from server

Run on macOS:
cd flutter_application_1
flutter run -d macos
⚠ Not supported on Web (because Socket is used).

📌 JSON Example

Client request:
{"action":"LOGIN","username":"ali","password":"1234"}
Server response:
{"status":"ok","username":"ali"}

**{"action":"LOGIN","username":"ali","password":"1234"}**
