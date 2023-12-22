package PaquetePrestamo;

import PaqueteLibroGenero.ContactoGL;
import PaqueteLibroGenero.Libro;
import PaqueteVentanaPrin.BibliotecaGUI;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Iterator;


public class PrestamoGUI extends JFrame {
    //Ventana componente
    JPanel panel;
    JComboBox comboDevolucion;
    private JTextField fechatxt;
    private JTextField nombrePrestamista;
    private JTextField nomDeudor;
    public static int habilitarPrestamo = 0;
    public static boolean botonDevolver=true;
    public static boolean botonPrestamo=true;
    public PrestamoGUI(){
        configuracion();

    }
    //Ventana de préstamo
    public void configuracion (){
        setTitle("Préstamos");
        setSize(550,270);
        setLocationRelativeTo(null);
        componentes();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void componentes(){
        panel = new JPanel();
        panel.setLayout(null);
        setBackground(Color.lightGray);
        getContentPane().add(panel);

        colocarTexto();
        camposTexto();
        colocarBoton();
        colocarCombo();
    }

    public void colocarTexto(){
        JLabel texto1 = new JLabel();
        texto1.setText("Fecha de prestamo:");
        texto1.setBounds(10,40, 120,20);
        panel.add(texto1);

        JLabel texto2 = new JLabel();
        texto2.setText("Nombre del Cliente:");
        texto2.setBounds(10, 80, 120,20);
        panel.add(texto2);

        JLabel texto3 = new JLabel();
        texto3.setText("Libros prestados:");
        texto3.setBounds(350,15, 100,20);
        panel.add(texto3);

        JLabel texto4 = new JLabel();
        texto4.setText("Digitar Deudor");
        texto4.setBounds(350, 120,120,20);
        panel.add(texto4);
    }

    public void camposTexto(){
        fechatxt = new JTextField();
        fechatxt.setBounds(135, 40, 120, 20);
        panel.add(fechatxt);

        nombrePrestamista = new JTextField();
        nombrePrestamista.setBounds(135, 80, 120,20);
        panel.add(nombrePrestamista);

        nomDeudor = new JTextField();
        nomDeudor.setBounds(350, 145, 120,20);
        panel.add(nomDeudor);
    }

    public void colocarBoton(){
        JButton prestarLibro = new JButton();
        prestarLibro.setBounds(40, 120, 150, 20);
        prestarLibro.setEnabled(botonPrestamo);
        prestarLibro.setText("Hacer prestamo");
        panel.add(prestarLibro);

        ActionListener guardarDatos = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                habilitarPrestamo++;
                String fechaIngresada = fechatxt.getText();
                String nombreCliente = nombrePrestamista.getText();
                try {
                    InfoPrestamo.prestamos.put(InfoPrestamo.prestamos.size(), new InfoPrestamo(nombreCliente, fechaIngresada,BibliotecaGUI.nomLibro,BibliotecaGUI.genElegido));
                    ContactoPres contactoPres = new ContactoPres();
                    contactoPres.crearP("prestamo.ser");
                    Iterator <Integer> iterator1 = InfoPrestamo.prestamos.keySet().iterator();
                    while (iterator1.hasNext()){
                        int llave = iterator1.next();
                        String persona = InfoPrestamo.prestamos.get(llave).getPersona();
                        String fecha = InfoPrestamo.prestamos.get(llave).getFecha();
                        String libroP = InfoPrestamo.prestamos.get(llave).getLibro();
                        String generoP = InfoPrestamo.prestamos.get(llave).getGenero();
                        contactoPres.escribirP(new InfoPrestamo(persona,fecha,libroP,generoP));
                    }
                    //contactoPres.escribirP(new InfoPrestamo(nombreCliente, fechaIngresada,BibliotecaGUI.nomLibro,BibliotecaGUI.genElegido));
                    contactoPres.cerrarP();

                    ContactoGL contactoGL = new ContactoGL();
                    Iterator <Integer> iterator = Libro.libros.keySet().iterator();
                    while (iterator.hasNext()){
                        int llave = iterator.next();
                        String genL = Libro.libros.get(llave).getGenero();
                        String nomL = Libro.libros.get(llave).getNombre();
                        if (nomL.equals(BibliotecaGUI.nomLibro)){
                            Libro.libros.replace(llave,new Libro(genL, nomL, "No Disponible"));
                        }
                    }

                    contactoGL.crearGL("libro.ser");
                    Iterator <Integer> iterator3 = Libro.libros.keySet().iterator();
                    while (iterator3.hasNext()){
                        int llave = iterator3.next();
                        String genL = Libro.libros.get(llave).getGenero();
                        String nomL = Libro.libros.get(llave).getNombre();
                        String estado = Libro.libros.get(llave).getDisponibilidad();
                        contactoGL.escribirGL(new Libro(genL,nomL,estado));
                    }
                    contactoGL.cerrarGL();
                }catch (IOException ex) {throw new RuntimeException(ex);}

                new BibliotecaGUI();
                dispose();
            }
        };
        prestarLibro.addActionListener(guardarDatos);

        JButton devolverLibro = new JButton();
        devolverLibro.setText("Devolver Libro");
        devolverLibro.setEnabled(botonDevolver);
        devolverLibro.setBounds(360,78,130,20);
        panel.add(devolverLibro);

        ActionListener devuelveLibros = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String libroSelect = comboDevolucion.getSelectedItem().toString();

                try{
                    ContactoPres contactoPres = new ContactoPres();
                    contactoPres.crearP("prestamo.ser");
                    Iterator<Integer> iterator1 = InfoPrestamo.prestamos.keySet().iterator();
                    while (iterator1.hasNext()){
                        int llave = iterator1.next();
                        String personaP = InfoPrestamo.prestamos.get(llave).getPersona();
                        String fechaP = InfoPrestamo.prestamos.get(llave).getFecha();
                        String libroPrestado = InfoPrestamo.prestamos.get(llave).getLibro();
                        String generoP = InfoPrestamo.prestamos.get(llave).getGenero();
                        if (libroPrestado.equals(libroSelect)){
                            InfoPrestamo.prestamos.replace(llave, new InfoPrestamo(personaP,fechaP,"Devuelto", "Ninguno"));
                            contactoPres.escribirP(new InfoPrestamo(personaP,fechaP,"Devuelto", "Ninguno"));
                        }
                        else{
                            contactoPres.escribirP(new InfoPrestamo(personaP,fechaP,libroPrestado, generoP));
                        }
                    }

                    ContactoGL contactoGL = new ContactoGL();
                    Iterator <Integer> iterator = Libro.libros.keySet().iterator();
                    while (iterator.hasNext()){
                        int llave = iterator.next();
                        String genL = Libro.libros.get(llave).getGenero();
                        String nomL = Libro.libros.get(llave).getNombre();
                        if (nomL.equals(libroSelect)){
                            Libro.libros.replace(llave,new Libro(genL, nomL, "Disponible"));
                        }
                    }
                    contactoGL.crearGL("libro.ser");
                    Iterator <Integer> iterator3 = Libro.libros.keySet().iterator();
                    while (iterator3.hasNext()){
                        int llave = iterator3.next();
                        String genL = Libro.libros.get(llave).getGenero();
                        String nomL = Libro.libros.get(llave).getNombre();
                        String estado = Libro.libros.get(llave).getDisponibilidad();
                        contactoGL.escribirGL(new Libro(genL,nomL,estado));
                    }
                    contactoGL.cerrarGL();
                } catch (IOException ex) {throw new RuntimeException(ex);}
                comboDevolucion.removeItem(libroSelect);
                JOptionPane.showMessageDialog(null, "Devuelto con exito");
            }
        };
        devolverLibro.addActionListener(devuelveLibros);

        JButton pagaMulta = new JButton();
        pagaMulta.setText("Pagar Multa");
        pagaMulta.setEnabled(botonDevolver);
        pagaMulta.setBounds(350,170,130,20);
        panel.add(pagaMulta);

        ActionListener pagomultas = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Iterator<Integer> iterator = InfoPrestamo.prestamos.keySet().iterator();
                while (iterator.hasNext()){
                    int llave = iterator.next();
                    String persona = InfoPrestamo.prestamos.get(llave).getPersona();
                    String libro = InfoPrestamo.prestamos.get(llave).getLibro();
                    if (persona.equals(nomDeudor.getText()) && libro.equals("Devuelto")){
                        iterator.remove();
                        JOptionPane.showMessageDialog(null, "Pagado con exito");
                    }
                }

                ContactoPres contactoPres = new ContactoPres();
                try {
                    contactoPres.crearP("prestamo.ser");
                    Iterator<Integer> iterator2 = InfoPrestamo.prestamos.keySet().iterator();
                    while (iterator2.hasNext()) {
                        int llave = iterator2.next();
                        String persona = InfoPrestamo.prestamos.get(llave).getPersona();
                        String fecha = InfoPrestamo.prestamos.get(llave).getFecha();
                        String libro = InfoPrestamo.prestamos.get(llave).getLibro();
                        String genero = InfoPrestamo.prestamos.get(llave).getGenero();
                        contactoPres.escribirP(new InfoPrestamo(persona,fecha,libro,genero));
                    }
                    contactoPres.cerrarP();
                } catch (IOException ex) {throw new RuntimeException(ex);}
            }
        };
        pagaMulta.addActionListener(pagomultas);

        JButton volver = new JButton();
        volver.setText("Volver");
        volver.setBounds(10, 200, 90,20);
        panel.add(volver);

        ActionListener volverPrincipal = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BibliotecaGUI();
                dispose();
            }
        };
        volver.addActionListener(volverPrincipal);
    }

    public void colocarCombo(){
        comboDevolucion = new JComboBox();
        comboDevolucion.setBounds(350,40,150, 20);

        Iterator<Integer> iterator = InfoPrestamo.prestamos.keySet().iterator();
        while (iterator.hasNext()){
            int llave = iterator.next();
            String libros = InfoPrestamo.prestamos.get(llave).getLibro();
            if (!libros.equals("Devuelto")){
                comboDevolucion.addItem(libros);
            }
        }
        panel.add(comboDevolucion);
    }


    /*public void cargarPrestamos(){
        try {
            ContactoPres contactoPres = new ContactoPres();
            contactoPres.crearP("prestamo.ser");

            contactoPres.escribirP(new InfoPrestamo("q","r","t", "d"));

            int indice = 0;
            HashMap<Integer, InfoPrestamo> datosP = new HashMap<>();
            InfoPrestamo infoPrestamo;
            contactoPres.abrirP("prestamo.ser");
            do{
                infoPrestamo = contactoPres.leerP();
                datosP.put(indice, infoPrestamo);
                indice++;
            }while (infoPrestamo!=null);
            for (int i=0; i<indice-1; i++){
                String nomP = datosP.get(i).getPersona();
                String fecha = datosP.get(i).getFecha();
                String libroP =  datosP.get(i).getLibro();
                String genP = datosP.get(i).getGenero();
                InfoPrestamo.prestamos.put(i, new InfoPrestamo(nomP, fecha, libroP, genP));
            }
        } catch (IOException ex) {throw new RuntimeException(ex);}
        catch (ClassNotFoundException e) {throw new RuntimeException(e);}
    }*/

    public static void main(String[] args) {
        new PrestamoGUI();
    }
}
