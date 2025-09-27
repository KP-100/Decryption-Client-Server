# Secret Agent Communication Terminal
COP2805 Class Project
A simple **Java client-server decryption program** with a Swing GUI.  
The client loads encrypted text from a file and sends it to the server,  
which processes the message using a Caesar cipher and returns the result.

---

## Features
- Swing-based GUI with file selection and translation buttons
- Client-server communication via TCP sockets
- Simple Caesar cipher transformation (shift by +10)
- Shutdown command to stop the server

---

## How to Run

### 1. Start the Server
```bash
javac cop2805/DecryptionServer.java
java cop2805.DecryptionServer
java -cp src cop2805.DecryptionServer
java -cp src cop2805.DecryptionClient

