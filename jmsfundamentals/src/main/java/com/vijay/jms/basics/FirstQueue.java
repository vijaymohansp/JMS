package com.vijay.jms.basics;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class FirstQueue {
	public static void main(String[] args) {
		InitialContext initialContext=null;
		Connection connection=null;
		
		try {
			initialContext=new InitialContext();
			ConnectionFactory connectionFactory=(ConnectionFactory) initialContext.lookup("ConnectionFactory");
			 connection=connectionFactory.createConnection();
			Session session=connection.createSession();
			Queue queue=(Queue) initialContext.lookup("queue/myQueue");
			MessageProducer producer=session.createProducer(queue);
			TextMessage message=session.createTextMessage("Welcome to JMS Learning!!");
			producer.send(message);
			System.out.println("Message Sent: "+message.getText());
			
			MessageConsumer consumer = session.createConsumer(queue);
			connection.start();
			TextMessage messageReceived=(TextMessage) consumer.receive(5000);
			System.out.println("Message Received :"+messageReceived.getText());
			
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (JMSException e) {
			e.printStackTrace();
		} finally {
			if(initialContext!=null) {
				try {
					initialContext.close();
				} catch (NamingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(connection!=null) {
				try {
					connection.close();
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
