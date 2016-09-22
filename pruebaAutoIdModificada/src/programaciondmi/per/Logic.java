package programaciondmi.per;

import java.util.Observable;
import java.util.Observer;

import processing.core.PApplet;

public class Logic implements Observer {
	private PApplet app;
	private CommunicationManager com;
    Personaje per;
	// Positions and direction for drawing the ball;
	
	;
	
	// Indicates the number of connected members
	int connnected;

	// Indicates if the ball is currently in my screen
	boolean canDraw;

	public Logic(PApplet app) {
	    per= new Personaje(0,0, app);
		this.app = app;
		this.com = new CommunicationManager();
		com.addObserver(this);
		new Thread(com).start();
		
		
		canDraw = false;
		
		if (com.getIdentifier() == 3) {
			// Send data to the first screen to start
			int receiver =1;
			int position = 0;
			ContentMessage message = new ContentMessage(com.getIdentifier(), receiver, position, 100);
			System.out.println("Sending content to " + receiver);
			com.sendObjectMessage(message);
		}
	}

	public void paint() {
		app.background(155);
		// Draw Identifier
		drawIdentifier();

		// Draw the bouncing ball
		drawBall();
		
		// Evaluate position an send messages
		evaluatePosition();
	}

	private void drawIdentifier() {
		app.fill(255, 0, 0);
		app.ellipse(app.width / 2, 10, 20, 20);
		app.fill(255);
		app.rectMode(PApplet.CENTER);
		app.text(com.getIdentifier(), app.width / 2, 10);
	}

	private void drawBall() {
		if (canDraw) {
			per.pintar();
			//cambio direccion
			per.cambiarDireccion();
			
		}else{
			app.rectMode(PApplet.CENTER);
			app.text("waiting the ball", app.width/2, app.height/2);
		}

	}

	private void evaluatePosition() {
		// Control what happen when the ball reaches the limits
		if ((per.x > app.width || per.x < 0) && canDraw) {
			ContentMessage message;
			int receiver;
			int position;
			switch (com.getIdentifier()) {
			case 1:
				if (per.dir > 0) {
					// Send a message to the next screen
					receiver = 2;
					position = 0;
					message = new ContentMessage(com.getIdentifier(), receiver, position, per.y);
					System.out.println("Sending content to " + receiver);
					com.sendObjectMessage(message);
					canDraw = false;
				} else if (per.dir < 0) {
					// bounce
					per.dir = 1;
				}
				break;
			case 2:
				if (per.dir > 0) {
					receiver = 3;
					position = 0;

				} else {
					receiver = 1;
					position = app.width;
				}
				message = new ContentMessage(com.getIdentifier(), receiver, position, per.y);
				System.out.println("Sending content to " + receiver);
				com.sendObjectMessage(message);
				canDraw = false;
				break;
			case 3:
				if (per.dir > 0) {
					// bounce
					per.dir = -1;
				} else if (per.dir < 0) {
					// Send a message to the next screen
					receiver = 2;
					position = app.width;
					message = new ContentMessage(com.getIdentifier(), receiver, position, per.y);
					System.out.println("Sending content to " + receiver);
					com.sendObjectMessage(message);
					canDraw = false;
				}
				break;
			default:
				break;
			}
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		
		if (arg instanceof ContentMessage) {
			ContentMessage message = (ContentMessage) arg;
			System.out.println("Content received from " + message.getSender());
			if (com.getIdentifier() == message.getReceiver()) {
				switch (com.getIdentifier()) {
				case 1:
					if (message.getSender() == 2) {
						canDraw = true;
						per.dir = -1;
						per.x = message.getX();
						per.y = message.getY();
					}
					if (message.getSender() == 3) {
						canDraw = true;
						per.dir = 1;
						per.x = message.getX();
						per.y = message.getY();
					}
					break;
				case 2:
					if (message.getSender() == 1) {
						canDraw = true;
						per.dir = 1;
						per.x = message.getX();
						per.y = message.getY();
					}
					if (message.getSender() == 3) {
						canDraw = true;
						per.dir = -1;
						per.x = message.getX();
						per.y = message.getY();
					}
					break;
				case 3:
					if (message.getSender() == 2) {
						canDraw = true;
						per.dir = 1;
						per.x = message.getX();
						per.y = message.getY();
					}
					break;
				default:
					break;
				}
			}
		}
	}

}