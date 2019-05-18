# JMS sample project

## Activities

### Start up Apache Active MQ server:

```
cd apache-activemq-5.15.9/bin
./activemq start

```
### Build source code

```
mvn clean package
```

### Run application server

```
java -cp ./target/greetings-jms-jar-with-dependencies.jar com.drkiettran.jms.greetings.Main server
```

## Run client application

```
java -cp ./target/greetings-jms-jar-with-dependencies.jar com.drkiettran.jms.greetings.Main client "John Doe"
```

### Stop Apache Active MQ Server:

```
./activemq stop
```

