/**
 * @version 1.0
 * @date 21/12/2023
 * @autor Santiago Anibal Carrillo Torres
 */

package PaqueteBibliotecario;

import javax.swing.JFrame;
import java.io.Serializable;
import java.util.HashMap;


/**
 * Clase que permite la lectura de los datos de los bibliotecarios
 */
public class DatosBibliotecario extends JFrame implements Serializable {
    private static final long serialVersionUID = 7L;
    private String nombre;
    private String contrasena;
    private String rol;
    public static HashMap<Integer, DatosBibliotecario> datosB = new HashMap<>();

    /**
     * Constructor de la clase
     * @param nom Nombre del bibliotecario
     * @param contra Contrasena del bibliotecario
     * @param rol Rol del bibliotecario
     */
    public DatosBibliotecario(String nom, String contra, String rol){
            this.nombre = nom;
            this.contrasena = contra;
            this.rol = rol;
    }

    /**
     * Metodo que permite obtener el nombre del bibliotecario
     * @return Nombre del bibliotecario
     */
    public String getNombre(){return this.nombre;}

    /**
     * Metodo que permite obtener la contrasena del bibliotecario
     * @return Contrasena del bibliotecario
     */
    public String getcontra(){ return this.contrasena;}

    /**
     * Metodo que permite obtener el rol del bibliotecario
     * @return Rol del bibliotecario
     */
    public String getRol(){
        return this.rol;
    }
}
