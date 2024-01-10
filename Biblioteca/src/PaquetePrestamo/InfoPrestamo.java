/**
 * @autor Santiago Anibal Carrillo Torres
 * @version 1.0
 * @date 21/12/2023
 */
package PaquetePrestamo;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Clase para la informacion de los prestamos
 */
public class InfoPrestamo implements Serializable {
    private static final long serialVersionUID = 3L;
    private String nombrePerson;
    private String fechaPrestado;
    private String libroPrestado;
    private String genero;
    public static HashMap <Integer, InfoPrestamo> prestamos = new HashMap<>();

    /**
     * Constructor de la clase
     * @param persona nombre de la persona que solicito el prestamo
     * @param fecha fecha en la que se solicito el prestamo
     * @param libro nombre del libro que se solicito
     * @param gen genero del libro que se solicito
     */
    public InfoPrestamo(String persona, String fecha, String libro, String gen){
        this.nombrePerson = persona;
        this.fechaPrestado = fecha;
        this.libroPrestado = libro;
        this.genero = gen;
    }

    /**
     * Metodo getter para obtener a la persona que solicito el prestamo
     */
    public String getPersona (){
        return this.nombrePerson;
    }

    /**
     * Metodo getter para obtener la fecha del prestamo
     */
    public String getFecha (){
        return this.fechaPrestado;
    }

    /**
     * Metodo getter para obtener el libro que se solicito
     */
    public String getLibro(){
        return this.libroPrestado;
    }

    /**
     * Metodo getter para obtener el genero del libro que se solicito
     */
    public String getGenero(){
        return this.genero;
    }

    /**
     * Metodo para verificar si una persona debe pagar una multa
     * @return arreglo con las personas que deben pagar una multa
     *
     */
    public static String[] VerificaDeuda(){
        String [] mostrarDeudores = new String[InfoPrestamo.prestamos.size()];
        int contador = 0;

        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fechaActual = LocalDate.now();

        for (int llave : InfoPrestamo.prestamos.keySet()) {
            String personaP = InfoPrestamo.prestamos.get(llave).getPersona();
            String fechaP = InfoPrestamo.prestamos.get(llave).getFecha();
            LocalDate ingresoPrestamo = LocalDate.parse(fechaP, formatter2);
            long diasTranscurridos = ChronoUnit.DAYS.between(ingresoPrestamo, fechaActual);
            int calculoMulta = (int) ((diasTranscurridos - 7) * 100);
            if (diasTranscurridos > 7) {
                mostrarDeudores[contador] = personaP + " tiene una de multa: " + calculoMulta;
                contador++;
            }
        }
        return mostrarDeudores;
    }
}
