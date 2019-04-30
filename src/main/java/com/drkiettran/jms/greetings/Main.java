package com.drkiettran.jms.greetings;

import java.net.UnknownHostException;

import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
	private static final String TO_SERVER_QUEUE = "TO_SERVER_QUEUE";
	private static final String FROM_SERVER_QUEUE = "FROM_SERVER_QUEUE";

	public static void main(String... args) throws JMSException, InterruptedException, UnknownHostException {
		if (args.length < 1) {
			LOGGER.info("**** java com.drkiettran.jms.greetings.Main server ****");
			return;
		}

		LOGGER.info("mode: {}", args[0]);
		if (args[0].equalsIgnoreCase("client")) {
			if (args.length < 2) {
				LOGGER.info("**** java com.drkiettran.jms.greetings.Main client <text> ****");
				return;
			}
			new Client(TO_SERVER_QUEUE, FROM_SERVER_QUEUE, args[1]);
		} else if (args[0].equalsIgnoreCase("server")) {
			new Server(TO_SERVER_QUEUE, FROM_SERVER_QUEUE);
		} else {
			LOGGER.info(">>> java com.drkiettran.jms.greetings..Main server | client <text> <<<");
		}
	}
}
