# ChatApp
 A chat app written in java.

 This project consist of 3500 lines of code which took 26 hours to write.

![alt text](https://i.imgur.com/CfG9PzX.jpeg)

# The Server App:
-Supports up to 128 clients (limited and can be extended to 256)

-Network communication can be encrypted and compressed (has a switch).

-Communication protocol is sent to clients, so that they know is the data encrypted or not.

-Has SQLite database for logging chat messages with activities and for employees.

-Uses Jetty Webserver for backdoor access.

-Employees can be added using the backdoor with a GET request.

-Backdoor menu access is granted by the server using the admins list in DB.

-Assigns random temporary colors to all clients when they join.

-Sends online users to all clients when someone joins or leaves.

-Supports private and global messages.

-Registration and logging in process Is handled and validated by the server.

-Responds to following network opcodes:
```
 register@@@email;password
 
 login@@@email;password
 
 private@@@senderMail;receiverMail;content
 
 global@@@senderMail;content
 ```
# Clients:
-Users can register only if they are an employee.

-Can see who is online and send private messages.

-Has chat notification sound which can be turned on/off.

-If the user is an admin, it can see the backdoor button. (superuser@chat.com, pass: root)

-Clients reconnect back when a network interrupt occurs or server restarts.

-Clients send login request to server with last credentials automatically after reconnection.

-Responds to following network opcodes:
```
 users@@@name,mail;name,mail
 
 private@@@time;sender;content;userColor
 
 global@@@time;sender;content;userColor
 
 auth@@@status;message
 
 privateSent@@@time;receiver;content;userColor 
 
 cmd@@@admin;true
```
