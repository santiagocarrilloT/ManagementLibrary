/**
 * @autor Santiago Anibal Carrillo Torres
 * @version 1.0
 * @date 21/12/2023
 */
package PaquetePrestamo;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

/**
 * Clase para la serializacion de archivos de prestamos
 */
public class ContactoPres {
    private FileOutputStream guardar;
    private ObjectOutputStream exportar;
    private FileInputStream leer;
    private ObjectInputStream recuperaDatos;

    /**
     * Metodo para crear un archivo de prestamos
     * @param archivo nombre del archivo
     * @throws IOException Excepcion en caso de que no se pueda crear el archivo
     */
    public void crearP (String archivo) throws IOException {
        guardar = new FileOutputStream(archivo);
        exportar = new ObjectOutputStream(guardar);
    }

    /**
     * Metodo para escribir en un archivo de prestamos
     * @param objeto objeto a escribir
     * @throws IOException Excepcion en caso de que no se pueda escribir en el archivo
     */
    public void escribirP (Object objeto) throws IOException{
        exportar.writeObject(objeto);
    }

    /**
     * Metodo para cerrar un archivo de prestamos
     * @throws IOException Excepcion en caso de que no se pueda cerrar el archivo
     */
    public void cerrarP () throws IOException {
        if (exportar != null) {
            exportar.close();
        }
    }

    /**
     * Metodo para abrir un archivo de prestamos
     * @param archivo nombre del archivo
     * @throws IOException Excepcion en caso de que no se pueda abrir el archivo
     */
    public void abrirP (String archivo)throws IOException{
        leer = new FileInputStream(archivo);
        recuperaDatos = new ObjectInputStream(leer);
    }

    /**
     * Metodo para leer un archivo de prestamos
     * @return objeto Infoprestamo
     * @throws IOException Excepcion en caso de que no se pueda leer el archivo
     * @throws ClassNotFoundException Excepcion en caso de que no se encuentre la clase
     */
    public InfoPrestamo leerP () throws IOException, ClassNotFoundException{
        InfoPrestamo infoPrestamo = null;

        if (recuperaDatos != null) {
            try{
                infoPrestamo = (InfoPrestamo) recuperaDatos.readObject();
            }catch (EOFException eof){}
        }
        return infoPrestamo;
    }

    /**
     * Metodo para guardar los prestamos en un HashMap
     */
    public void lecturaPrestamos (){
        try{
            int indice = 0;
            HashMap<Integer, InfoPrestamo> datosP = new HashMap<>();
            InfoPrestamo infoPrestamo;
            abrirP("prestamo.ser");
            do{
                infoPrestamo = leerP();
                datosP.put(indice, infoPrestamo);
                indice++;
            }while (infoPrestamo != null);
            for (int i=0;i<indice-1;i++){
                String persona = datosP.get(i).getPersona();
                String fecha = datosP.get(i).getFecha();
                String nomLi = datosP.get(i).getLibro();
                String genLi = datosP.get(i).getGenero();
                InfoPrestamo.prestamos.put(i, new InfoPrestamo(persona, fecha, nomLi, genLi));
            }
        } catch (IOException | ClassNotFoundException e) {throw new RuntimeException(e);}
    }
}
