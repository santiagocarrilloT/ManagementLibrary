package PaqueteLibroGenero;

import java.io.Serializable;
import java.util.HashMap;

public class Genero implements Serializable {
    private static final long serialVersionUID = -5L;
    public String genero;
    public static HashMap<Integer, String> generos = new HashMap<>();
    public Genero(String gen){
        this.genero = gen;
    }
    public String getGenero(){
        return this.genero;
    }
}
