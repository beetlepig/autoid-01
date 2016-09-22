import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import com.example.a1107513806.myapplication.Usuario;

public class Logica implements Observer{
	private Comunicacion com;
	private ArrayList<Usuario> usuariosRegistrados;
	public Logica() {
		com= new Comunicacion();
		com.addObserver(this);
		new Thread(com).start();
		usuariosRegistrados= new ArrayList<>();
	}
	
	public void update(Observable o, Object ob) {
		System.out.println("objeto recibido "+ob.getClass());
		Object recibido = ob;
		System.out.println("usuario registrados: "+ usuariosRegistrados.size());
		
		if(recibido instanceof Usuario){
			
			Usuario usu= (Usuario) recibido;
			System.out.println("usuario nombre:"+ usu.nombre);
			if(usu.esRegistro){
				System.out.println("peticion de registro recibida");
				usuariosRegistrados.add(usu);
				String aprovadon= new String("AprobadoRegistro");
				com.mandarMensajote(aprovadon);
				
				
			}else if(!usuariosRegistrados.isEmpty()){
			for (int i = 0; i < usuariosRegistrados.size(); i++) {
				Usuario usuregis = usuariosRegistrados.get(i);
				
				if(usuregis.nombre.equals(usu.nombre) & usuregis.contrasena.equals(usu.contrasena)){
					System.out.println("peticion de inicio aprovada");
					//mando aprovacion de inicio de sesion
					String aprovadon= new String("AprobadoLogin");
					com.mandarMensajote(aprovadon);
					
				}else if(!usu.esRegistro){
					System.out.println("contraseņa o usuario incorecto");
					String aprovadon= new String("NoAprobadoLogin");
					com.mandarMensajote(aprovadon);
				}
			}
		}else{
			System.out.println("Usuario no registrado");
			String aprovadon= new String("NoAprobadoLogin");
			com.mandarMensajote(aprovadon);
		}
			
		}
		
		
	}
	


}
