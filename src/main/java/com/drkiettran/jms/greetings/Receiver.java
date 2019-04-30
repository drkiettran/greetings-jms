package com.drkiettran.jms.greetings;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Receiver {
	private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);
	private MessageConsumer consumer;
	private Connection connection;

	public Receiver(String url, String subject) throws JMSException {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
		connection = connectionFactory.createConnection();

		connection.start();

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination destination = session.createQueue(subject);
		consumer = session.createConsumer(destination);
	}

	public String receive() throws JMSException {
		Message message = consumer.receive();

		if (message instanceof TextMessage) {
			TextMessage textMessage = (TextMessage) message;
			LOGGER.info("Received message '{}'", textMessage.getText());
			return textMessage.getText();
		}
		return "";
	}

	public void close() throws JMSException {
		connection.close();
	}
}
