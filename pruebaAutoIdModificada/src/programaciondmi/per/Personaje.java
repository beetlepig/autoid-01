package programaciondmi.per;

import processing.core.PApplet;

public class Personaje {
	public int x;
	public int y;
	public int vel;
	public int dir;
	PApplet app;
	public Personaje(int x, int y, PApplet app){
	    this.x=x;
		this.y=y;
		this.app=app;
		vel = 5;
	}
	
	public void pintar(){
		
		app.ellipse(this.x, this.y, 50, 50);
		moverBall();
	}
	
	
	public void cambiarDireccion(){
		x += dir * vel;
	}
	
	public void moverBall(){
		if(app.keyPressed){
			 if (app.key ==app.CODED) {
				    if(app.keyCode == app.UP){
				    	y-=3;
				    } else if(app.keyCode == app.DOWN){
				    	y+=3;
				    }
				    }
		}
	}
	

}
