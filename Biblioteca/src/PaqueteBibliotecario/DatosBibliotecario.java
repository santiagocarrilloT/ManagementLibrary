package PaqueteBibliotecario;

import javax.swing.JFrame;
import java.io.Serializable;
import java.util.HashMap;
public class DatosBibliotecario extends JFrame implements Serializable {
    private static final long serialVersionUID = 7L;
    private String nombre;
    private String contrasena;
    private String rol;
    public static HashMap<Integer, DatosBibliotecario> datosB = new HashMap<>();

    public DatosBibliotecario(String nom, String contra, String rol){
            this.nombre = nom;
            this.contrasena = contra;
            this.rol = rol;
    }

    public String getNombre(){return this.nombre;}
    public String getcontra(){
        return this.contrasena;}
    public String getRol(){
        return this.rol;
    }
}
