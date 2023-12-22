package PaqueteLibroGenero;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.JFrame;

public class Libro extends JFrame implements Serializable {

    private static final long serialVersionUID = 2L;
    private String genero;
    private String nombre;
    private String disponibilidad;

    public static HashMap<Integer, Libro> libros = new HashMap<>();
    public Libro(String gen, String nom, String dispon){
        this.genero = gen;
        this.nombre = nom;
        this.disponibilidad = dispon;
    }
    public String getGenero(){return this.genero;}
    public String getNombre(){return this.nombre;}
    public String getDisponibilidad(){return this.disponibilidad;}
    public static String[] mostrarEstado(String genero){
        //Se crea una matriz para imprimir los valores en filas
        String [] item = new String[libros.size()];
        int i = 0;
        Iterator<Integer> iterator = libros.keySet().iterator();
        while(iterator.hasNext()){
            int llave = iterator.next();
            String nomLibro = libros.get(llave).getNombre();
            String estado = libros.get(llave).getDisponibilidad();
            if (genero.equals(libros.get(llave).getGenero())){
                item[i] = nomLibro + " - " + estado;
                i++;
            }
        }
        return item;
    }
}
