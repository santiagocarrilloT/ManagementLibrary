/**
 * @version 1.0
 * @author Santiago Anibal Carrillo Torres
 * @date 21/12/2023
 */
package PaqueteBibliotecario;


import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

/**
 * Clase que permite la lectura y escritura de los datos de los bibliotecarios
 */
public class ContactoDatos{
    private FileOutputStream guardar;
    private ObjectOutputStream exportar;
    private FileInputStream leer;
    private ObjectInputStream recuperaDatos;

    /**
     * Metodo que permite la creacion de un archivo serializado
     * @param archivo Nombre del archivo a crear
     * @throws IOException Excepcion en caso de que el archivo no se pueda crear
     */
    public void crear (String archivo) throws IOException {
        guardar = new FileOutputStream(archivo);
        exportar = new ObjectOutputStream(guardar);
    }

    /**
     * Metodo que permite escribir el archivo a serializar
     * @param objeto Objeto a escribir
     * @throws IOException Excepcion en caso de que el archivo no se pueda escribir
     */
    public void escribir(Object objeto) throws IOException{
        exportar.writeObject(objeto);
    }

    /**
     * Metodo que permite cerrar un archivo
     * @throws IOException Excepcion en caso de que el archivo no se pueda cerrar
     */
    public void cerrar () throws IOException {
        if (exportar != null) {
            exportar.close();
        }
    }

    /**
     * Metodo que permite abrir un archivo serializado
     * @param archivo Nombre del archivo a abrir
     * @throws IOException Excepcion en caso de que el archivo no se pueda abrir
     */
    public void abrir (String archivo)throws IOException{
        leer = new FileInputStream(archivo);
        recuperaDatos = new ObjectInputStream(leer);
    }

    /**
     * Metodo que permite leer un archivo serializado
     * @return Devuelve un objeto de tipo DatosBibliotecario
     * @throws IOException Excepcion en caso de que el archivo no se pueda leer
     * @throws ClassNotFoundException Excepcion en caso de que la clase no se encuentre
     */
    public DatosBibliotecario leer () throws IOException, ClassNotFoundException{
        DatosBibliotecario datosBibliotecario = null;

        if (recuperaDatos != null) {
            try{
                datosBibliotecario = (DatosBibliotecario) recuperaDatos.readObject();
            }catch (EOFException eof){}
        }
        return datosBibliotecario;
    }

    /**
     * Metodo que permite agregar el archivo al HashMap
     */
    public void lecturaBibliot (){
        try {
            int indice = 0;
            HashMap<Integer, DatosBibliotecario> extraeDatos = new HashMap<>();
            DatosBibliotecario bibliotecario;
            ContactoDatos contactoDatos = new ContactoDatos();
            contactoDatos.abrir("bibliotecario.ser");

            do {
                bibliotecario = contactoDatos.leer();
                extraeDatos.put(indice, bibliotecario);
                indice++;
            } while(bibliotecario != null);
            for (int r = 0; r<indice-1;r++){
                DatosBibliotecario datosBibliotecario;
                datosBibliotecario = new DatosBibliotecario(extraeDatos.get(r).getNombre(), extraeDatos.get(r).getcontra(), extraeDatos.get(r).getRol());
                DatosBibliotecario.datosB.put(r, datosBibliotecario);
            }
        } catch (IOException | ClassNotFoundException exception) {throw new RuntimeException(exception);}
    }
}
