import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;

public class Receiver {
	private ConnectionFactory factory = null;
	private Connection connection = null;
	private Session session = null;
	private Destination destination = null;
	private MessageConsumer consumer = null;

	public Receiver() {

	}  

	public void receiveMessage() {
		try {
			factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
			connection = factory.createConnection();

			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			destination = session.createQueue("EROP");
			consumer = session.createConsumer(destination);

			while (true) {
				Message message = consumer.receive();
				
				if (message instanceof ActiveMQTextMessage) {
					ActiveMQTextMessage text = (ActiveMQTextMessage) message;
					System.out.println("Message received from"+text.getProducerId()+" \t Message is : " + text.getText());
				}
			}
		} catch (

		JMSException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Receiver receiver = new Receiver();
		receiver.receiveMessage();
	}
}