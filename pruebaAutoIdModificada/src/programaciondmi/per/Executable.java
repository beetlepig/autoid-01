package programaciondmi.per;

import processing.core.PApplet;

public class Executable extends PApplet {
	private Logic log;

	public static void main(String[] args) {
		PApplet.main("programaciondmi.per.Executable", args);
	}
	
	public void settings(){
		size(500, 500);
	}
	
	public void setup(){
		log = new Logic(this);
	}
	
	public void draw(){
		background(255);
		log.paint();
	}

}