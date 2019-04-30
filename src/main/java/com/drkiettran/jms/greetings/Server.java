package com.drkiettran.jms.greetings;

import java.net.UnknownHostException;

import javax.jms.JMSException;

import org.apache.activemq.ActiveMQConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Server {
	private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);

	public Server(String subject, String responseSubject) throws JMSException, UnknownHostException {
		Receiver receiver = new Receiver(ActiveMQConnection.DEFAULT_BROKER_URL, subject);
		Greetings greetings = new Greetings();

		while (true) {
			String msg = receiver.receive();

			LOGGER.info("Received message '{}'", msg);

			Sender sender = new Sender(ActiveMQConnection.DEFAULT_BROKER_URL, responseSubject);
			String returnMsg = greetings.hello(msg);
			sender.send(returnMsg);
			LOGGER.info("Server responds '{}'", returnMsg);
			sender.close();
			if (msg.equalsIgnoreCase("exit")) {
				break;
			}
		}

		receiver.close();
	}
}
