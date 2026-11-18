# TP2 — UDP Client-Server with Java

## Part 1 — Creating a UDP Client-Server

We used Java 21 and IntelliJ IDEA to create a UDP client and server. We also used GitHub to share our code.

### Step 1 — UDP Server

First, we created a Java class called `UDPServer.java`.

The server uses `DatagramSocket` to receive UDP messages. We set the default port to 8080 and the buffer size to 1024 bytes.

We also added two constructors, a `launch()` method, a `toString()` method, and a `main()` method.

When we run the server, it waits for messages from clients. When it receives a message, it displays the client's IP address, port number, and message.

**Result:**

<img width="400" height="140" alt="image" src="https://github.com/user-attachments/assets/048e9e52-798b-40ce-b012-57ac4cc947fe" />
Figure 1 — UDPServer running in IntelliJ IDEA

### Step 2 — Test the UDP Server

We tried to test our server with Ncat using this command:

```bash
ncat -u localhost 8080
```

However, Ncat was not installed on our Windows computer. The terminal could not recognize the command.

So we decided to use our own Java UDP client in Step 3 to test the server.

**[Screenshot 2 — Ncat command not recognized]**

### Step 3 — UDP Client

Next, we created another Java class called `UDPClient.java`.

The client reads messages from the keyboard and sends them to the server using UDP.

At first, we used `localhost` and port `8080` because the client and server were running on the same computer.

We opened two programs in IntelliJ IDEA. One was the server and the other was the client.

We sent some messages:

```text
hello
This is my UDP client
how are you
```

The server received all these messages and displayed the client's IP address and port.

**Result:**

```text
/127.0.0.1:56265 -> hello
/127.0.0.1:56265 -> This is my UDP client
/127.0.0.1:56265 -> how are you
```

This shows that our UDP client and server can communicate on the same computer.

**[Screenshot 3 — UDPServer receiving messages from UDPClient]**

### Step 4 — Test Between Two Computers

In this step, we tried to communicate between two computers, one using Windows and the other using Linux.

First, we used `ping` to test the network connection.

On Windows, we used:

```cmd
ipconfig
```

On Linux, we used:

```bash
hostname -I
```

We found these IP addresses on the ENSEA network:

- Windows: `10.10.24.246`
- Linux: `10.10.26.72`

We tried to send UDP messages between the two computers, but the server did not receive them.

We also tried to ping the other computer, but the test failed.

**[Screenshot 4 — Ping failed on the ENSEA network]**

We think the ENSEA or eduroam network may have network isolation or firewall restrictions. This may stop direct communication between student computers.

So we tried another solution. We connected both computers to the same smartphone hotspot and checked their new IP addresses.

**[Screenshot 5 — Network test using the smartphone hotspot, if available]**

We also need to test two UDP clients sending messages to the same server at the same time.

The server can receive messages from different clients and show their IP addresses and port numbers.

### Part 1 — Conclusion

In this part, we created a basic UDP client and server using Java.

We successfully sent and received UDP messages on the same computer.

We also learned how to use IP addresses and port numbers to communicate between different computers.

One important thing is that UDP does not establish a connection before sending data. Also, sending a message does not guarantee that the server will receive it.
