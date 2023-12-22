package PaquetePrestamo;


import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Iterator;

public class InfoPrestamo implements Serializable {
    private static final long serialVersionUID = 3L;
    private String nombrePerson;
    private String fechaPrestado;
    private String libroPrestado;
    private String genero;
    private int multa;
    public static HashMap <Integer, InfoPrestamo> prestamos = new HashMap<>();

    public InfoPrestamo(String persona, String fecha, String libro, String gen){
        this.nombrePerson = persona;
        this.fechaPrestado = fecha;
        this.libroPrestado = libro;
        this.genero = gen;
    }

    public String getPersona (){
        return this.nombrePerson;
    }
    public String getFecha (){
        return this.fechaPrestado;
    }
    public String getLibro(){
        return this.libroPrestado;
    }
    public String getGenero(){
        return this.genero;
    }

    public static String[] VerificaDeuda(){
        String [] mostrarDeudores = new String[InfoPrestamo.prestamos.size()];
        int contador = 0;

        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fechaActual = LocalDate.now();

        Iterator<Integer> iterator = InfoPrestamo.prestamos.keySet().iterator();
        while (iterator.hasNext()){
            int llave = iterator.next();
            String personaP = InfoPrestamo.prestamos.get(llave).getPersona();
            String fechaP = InfoPrestamo.prestamos.get(llave).getFecha();
            LocalDate ingresoPrestamo = LocalDate.parse(fechaP, formatter2);
            long diasTranscurridos = ChronoUnit.DAYS.between(ingresoPrestamo, fechaActual);
            int calculoMulta = (int) ((diasTranscurridos-7)*100);
            if (diasTranscurridos > 7) {
                mostrarDeudores[contador] = personaP + " tiene una de multa: " + calculoMulta;
                contador++;
            }
        }
        return mostrarDeudores;
    }
}
