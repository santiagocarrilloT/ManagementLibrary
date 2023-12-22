package PaqueteLibroGenero;


import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class ContactoGL {
    private FileOutputStream guardar;
    private ObjectOutputStream exportar;
    private FileInputStream leer;
    private ObjectInputStream recuperaDatos;

    public void crearGL (String archivo) throws IOException {
        guardar = new FileOutputStream(archivo);
        exportar = new ObjectOutputStream(guardar);
    }

    public void escribirGL (Object objeto) throws IOException{
        exportar.writeObject(objeto);
    }

    public void cerrarGL () throws IOException {
        if (exportar != null) {
            exportar.close();
        }
    }

    public void abrirGL (String archivo)throws IOException{
        leer = new FileInputStream(archivo);
        recuperaDatos = new ObjectInputStream(leer);
    }

    public Libro leerL () throws IOException, ClassNotFoundException{
        Libro libro = null;

        if (recuperaDatos != null) {
            try{
                libro = (Libro) recuperaDatos.readObject();
            }catch (EOFException eof){}
        }
        return libro;
    }
    public Genero leerG () throws IOException, ClassNotFoundException{
        Genero genero = null;

        if (recuperaDatos != null) {
            try{
                genero = (Genero) recuperaDatos.readObject();
            }catch (EOFException eof){}
        }
        return genero;
    }

    public void lecturaLibros (){
        try{
            int indice = 0;
            HashMap<Integer, Libro> datosL = new HashMap<>();
            Libro libro;
            abrirGL("libro.ser");
            do{
                libro = leerL();
                datosL.put(indice, libro);
                indice++;
            }while (libro  != null);
            for (int i=0;i<indice-1;i++){
                String genLi = datosL.get(i).getGenero();
                String nomLi = datosL.get(i).getNombre();
                String estado = datosL.get(i).getDisponibilidad();
                Libro.libros.put(i, new Libro(genLi, nomLi, estado));
            }
        } catch (IOException e) {throw new RuntimeException(e);}
        catch (ClassNotFoundException e) {throw new RuntimeException(e);}
    }

    public void lecturaGeneros (){
        try{
            int indice = 0;
            HashMap<Integer, Genero> datosG = new HashMap<>();
            Genero genero;
            abrirGL("genero.ser");
            do{
                genero = leerG();
                datosG.put(indice, genero);
                indice++;
            }while (genero  != null);
            for (int i=0;i<indice-1;i++){
                String genLi = datosG.get(i).getGenero();
                Genero.generos.put(i, genLi);
            }
        } catch (IOException e) {throw new RuntimeException(e);}
        catch (ClassNotFoundException e) {throw new RuntimeException(e);}
    }
}
