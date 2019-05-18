package com.drkiettran.jms.greetings;

import javax.jms.JMSException;

import org.apache.activemq.ActiveMQConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Client extends Thread {
	private static final Logger LOGGER = LoggerFactory.getLogger(Client.class);
	private String fromServerQueue;

	public Client(String toServerQueue, String fromServerQueue, com.drkiettran.jms.greetings.Message msg)
			throws JMSException, InterruptedException {
		this.fromServerQueue = fromServerQueue;

		this.start();

		Sender sender = new Sender(ActiveMQConnection.DEFAULT_BROKER_URL, toServerQueue);

		sender.send(msg);
		LOGGER.info("Sent '{}'", msg);

		synchronized (this) {
			this.wait();
		}

		sender.close();
	}

	public void run() {
		try {
			Receiver receiver = new Receiver(ActiveMQConnection.DEFAULT_BROKER_URL, fromServerQueue);
			com.drkiettran.jms.greetings.Message msg = receiver.receive();
			LOGGER.info("Received '{}'", msg);
			receiver.close();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		synchronized (this) {
			this.notify();
		}
	}
}
