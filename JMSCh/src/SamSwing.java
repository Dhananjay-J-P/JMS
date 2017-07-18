import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.GraphicsConfiguration;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;

/**
 * 
 */

/**
 * @author Dhananjay P
 *
 */
public class SamSwing extends JFrame implements ActionListener {

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;

	JTextArea chat = new JTextArea();
	JTextArea message = new JTextArea();
	JButton send = new JButton();

	private ConnectionFactory factory = null;
	private Connection connection = null;
	private Session session = null;

	private Destination destinationProducer = null;
	private MessageProducer producer = null;

	/**
	 * @throws HeadlessException
	 */
	public SamSwing() throws HeadlessException {
		this.getContentPane().setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		this.setSize(400, 500);

		String[] ss=new String[0];
		
		JPanel s = new JPanel(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();

		chat.setBackground(Color.GRAY);
		chat.setSize(400, 400);
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.ipady = 300;
		c.weighty = 1.0;

		c.gridy = 0;

		JScrollPane sp = new JScrollPane(chat); 
		s.add(sp, c);


		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.5;
		c.ipady = 40;
		c.gridx = 0;
		c.gridy = 1;

		s.add(message, c);

		send.setText("Send");
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 2;
		send.setMnemonic(KeyEvent.VK_ENTER);
		send.addActionListener(this);
		s.add(send, c);

		this.add(s);
		this.setTitle("Sam");
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);

		try {
			connection = factory.createConnection();
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			destinationProducer = session.createQueue("ROBIN");
			producer = session.createProducer(destinationProducer);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new SamReceiver(chat).start();
	}

	/**
	 * @param gc
	 */
	public SamSwing(GraphicsConfiguration gc) {
		super(gc);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param title
	 * @param gc
	 */
	public SamSwing(String title, GraphicsConfiguration gc) {
		super(title, gc);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param title
	 * @throws HeadlessException
	 */
	public SamSwing(String title) throws HeadlessException {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		switch (1) {
		case 1:
		case 2:
		case 0:
		default:
		case 4:
		}
		SamSwing frame = new SamSwing();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == send) {
			synchronized (chat) {
				chat.append("\nYou:" + message.getText());
				TextMessage text;
				try {
					text = session.createTextMessage();
					text.setText(message.getText());
					text.setStringProperty("PRODUCER_NAME", "Sam");
					producer.send(text);
					message.setText("");
					message.requestFocusInWindow();
				} catch (JMSException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		}
	}

}

class SamReceiver extends Thread {
	JTextArea chat;
	private ConnectionFactory factory = null;
	private Connection connection = null;
	private Session session = null;
	private Destination destinationConsumer = null;

	private MessageConsumer consumer = null;

	public SamReceiver(JTextArea chat) {
		this.chat = chat;
		factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);

		try {
			connection = factory.createConnection();
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void initMQ() {
		try {

			destinationConsumer = session.createQueue("SAM");
			consumer = session.createConsumer(destinationConsumer);

		} catch (

		JMSException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		initMQ();
		while (true) {
			Message message;
			try {
				message = consumer.receive();

				if (message instanceof ActiveMQTextMessage) {
					ActiveMQTextMessage text = (ActiveMQTextMessage) message;
					synchronized (chat) {

						chat.append("\n" + text.getStringProperty("PRODUCER_NAME") + ":" + text.getText());

					}
				}

			} catch (JMSException e) {

			}

		}
	}

}
