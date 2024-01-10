/**
 * @autor Santiago Anibal Carrillo Torres
 * @version 1.0
 * @date 21/12/2023
 */
package PaqueteLibroGenero;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Clase del objeto genero
 */
public class Genero implements Serializable {
    private static final long serialVersionUID = -5L;
    public String genero;
    public static HashMap<Integer, String> generos = new HashMap<>();

    /**
     * Constructor de la clase genero
     * @param gen nombre del genero
     */
    public Genero(String gen){
        this.genero = gen;
    }

    /**
     * Metodo para obtener el genero del libro
     * @return genero del libro
     */
    public String getGenero(){
        return this.genero;
    }
}
