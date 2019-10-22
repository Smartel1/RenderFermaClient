## RenderFerma client
Simple command-line http-client

---

### Installation
Run boot application using gradle:
```shell script
 ./gradlew clean bootRun --console=plain
```

---

### Usage
Available commands: 
```
login <username> <password> // set credentials
reg <username> <password> // register user and set credentials
mk // make task
ls // list tasks
Exit by Ctrl+C
```

Example:
```
Enter command: ls
Command handle went wrong: Login first // Authenticate first!

Enter command: login ueee
Command supplies 2 args!  // Validation example

Enter command: login aaa ppp // wrong credentials
Credentials have been saved
Enter command: ls
Command handle went wrong: 401 Unauthorized // wrong credentials lead to 401

Enter command: login user pas
Credentials have been saved
Enter command: ls
Task id: 1, status: COMPLETE
Task id: 2, status: COMPLETE
Task id: 3, status: COMPLETE
Task id: 9, status: COMPLETE
```

