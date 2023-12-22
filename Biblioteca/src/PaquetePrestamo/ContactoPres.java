package PaquetePrestamo;


import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class ContactoPres {
    private FileOutputStream guardar;
    private ObjectOutputStream exportar;
    private FileInputStream leer;
    private ObjectInputStream recuperaDatos;

    public void crearP (String archivo) throws IOException {
        guardar = new FileOutputStream(archivo);
        exportar = new ObjectOutputStream(guardar);
    }

    public void escribirP (Object objeto) throws IOException{
        exportar.writeObject(objeto);
    }

    public void cerrarP () throws IOException {
        if (exportar != null) {
            exportar.close();
        }
    }

    public void abrirP (String archivo)throws IOException{
        leer = new FileInputStream(archivo);
        recuperaDatos = new ObjectInputStream(leer);
    }

    public InfoPrestamo leerP () throws IOException, ClassNotFoundException{
        InfoPrestamo infoPrestamo = null;

        if (recuperaDatos != null) {
            try{
                infoPrestamo = (InfoPrestamo) recuperaDatos.readObject();
            }catch (EOFException eof){}
        }
        return infoPrestamo;
    }

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
        } catch (IOException e) {throw new RuntimeException(e);}
        catch (ClassNotFoundException e) {throw new RuntimeException(e);}
    }
}
