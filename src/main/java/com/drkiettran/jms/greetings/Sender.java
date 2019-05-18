package com.drkiettran.jms.greetings;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Sender {
	private static final Logger LOGGER = LoggerFactory.getLogger(Sender.class);
	private Connection connection;
	private MessageProducer producer;
	private Session session;

	public Sender(String url, String subject) throws JMSException {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
		connection = connectionFactory.createConnection();

		connection.start();

		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination destination = session.createQueue(subject);
		producer = session.createProducer(destination);
	}

	public void send(String msg) throws JMSException {
		TextMessage message = session.createTextMessage(msg);

		producer.send(message);
		LOGGER.info("Sent '{}'", message.getText());
	}

	public void send(Message msg) throws JMSException {
		ObjectMessage message = session.createObjectMessage(msg);
		producer.send(message);
		LOGGER.info("Sent '{}'", msg.toString());
	}

	public void close() throws JMSException {
		connection.close();
	}
}
