package PaqueteBibliotecario;


import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class ContactoDatos{
    private FileOutputStream guardar;
    private ObjectOutputStream exportar;
    private FileInputStream leer;
    private ObjectInputStream recuperaDatos;

    public void crear (String archivo) throws IOException {
        guardar = new FileOutputStream(archivo);
        exportar = new ObjectOutputStream(guardar);
    }

    public void escribir(Object objeto) throws IOException{
        exportar.writeObject(objeto);
    }

    public void cerrar () throws IOException {
        if (exportar != null) {
            exportar.close();
        }
    }

    public void abrir (String archivo)throws IOException{
        leer = new FileInputStream(archivo);
        recuperaDatos = new ObjectInputStream(leer);
    }

    public DatosBibliotecario leer () throws IOException, ClassNotFoundException{
        DatosBibliotecario datosBibliotecario = null;

        if (recuperaDatos != null) {
            try{
                datosBibliotecario = (DatosBibliotecario) recuperaDatos.readObject();
            }catch (EOFException eof){}
        }
        return datosBibliotecario;
    }

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
        } catch (IOException exception) {throw new RuntimeException(exception);}
        catch (ClassNotFoundException exception) {throw new RuntimeException(exception);}
    }
}
