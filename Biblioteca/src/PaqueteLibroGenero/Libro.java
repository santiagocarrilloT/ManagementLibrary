/**
 * @autor Santiago Anibal Carrillo Torres
 * @version 1.0
 * @date 21/12/2023
 */
package PaqueteLibroGenero;

import java.io.Serializable;
import java.util.HashMap;
import javax.swing.JFrame;

/**
 * Clase del objeto libro
 */
public class Libro extends JFrame implements Serializable {

    private static final long serialVersionUID = 2L;
    private String genero;
    private String nombre;
    private String disponibilidad;

    public static HashMap<Integer, Libro> libros = new HashMap<>();

    /**
     * Constructor de la clase libro
     * @param gen genero del libro
     * @param nom nombre del libro
     * @param dispon disponibilidad del libro
     */
    public Libro(String gen, String nom, String dispon){
        this.genero = gen;
        this.nombre = nom;
        this.disponibilidad = dispon;
    }
    public String getGenero(){return this.genero;}
    public String getNombre(){return this.nombre;}
    public String getDisponibilidad(){return this.disponibilidad;}

    /**
     * Metodo para mostrar si un libro está prestado o no
     * @param genero genero del libro
     * @return item matriz con los libros y su estado
     */
    public static String[] mostrarEstado(String genero){
        //Se crea una matriz para imprimir los valores en filas
        String [] item = new String[libros.size()];
        int i = 0;
        for (int llave : libros.keySet()) {
            String nomLibro = libros.get(llave).getNombre();
            String estado = libros.get(llave).getDisponibilidad();
            if (genero.equals(libros.get(llave).getGenero())) {
                item[i] = nomLibro + " - " + estado;
                i++;
            }
        }
        return item;
    }
}
