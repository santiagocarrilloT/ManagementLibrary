/**
 * @version: 1.0
 * @autor: Santiago Anibal Carrillo Torres
 * @date 21/12/2023
 */
package PaqueteLibroGenero;

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
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Clase que permite agregar libros y géneros a la biblioteca
 */
public class AggGL extends JFrame implements Serializable{

    private JComboBox comboCategoria;
    private JPanel panel;
    private JTextField libroIngresado;
    private JTextField nomCategoria;

    public static int habilitaLecturaL = 0;
    public static int habilitaLecturaG = 0;

    /**
     * Constructor de la clase
     */
    public AggGL() {
        agregaGen();
        //cargarLibros();
    }

    /**
     * Metodo que permite la creacion de la ventana
     */
    public void agregaGen() {
        setTitle("Inicio de sesion: Biblioteca");
        setSize(450, 300);
        setLocationRelativeTo(null);
        ComponentesVentana();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     * Metodo que permite agregar componentes de la ventana
     */
    public void ComponentesVentana(){
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);
        getContentPane().add(panel);

        ColocarLabel();
        ColocarTexto();
        ColocarCombo();
        ColocarBoton();
    }

    /**
     * Metodo que permite colocar etiquetas en la ventana
     */
    public void ColocarLabel(){
        JLabel label1 = new JLabel("Categoria");
        label1.setBounds(35,15,100,20);
        panel.add(label1);

        JLabel label2 = new JLabel("Nombre Libro");
        label2.setBounds(296,15,100,20);
        panel.add(label2);

        JLabel label3 = new JLabel("Elegir Genero");
        label3.setBounds(180,155,100,20);
        panel.add(label3);

        JLabel label4 = new JLabel("Selecciona un g�nero para eliminarlo o agregar un libro");
        label4.setBounds(10, 205, 350,20);
        panel.add(label4);
    }

    /**
     * Metodo que permite colocar campos de texto en la ventana
     */
    public void ColocarTexto(){
        libroIngresado = new JTextField();
        libroIngresado.setBounds(293,40,100,20);
        panel.add(libroIngresado);

        nomCategoria = new JTextField();
        nomCategoria.setBounds(35,40,100,20);
        panel.add(nomCategoria);
    }

    /**
     * Metodo que permite colocar botones en la ventana
     */
    public void ColocarBoton(){
        JButton bNuevoLibro = new JButton("Agregar Libro");
        bNuevoLibro.setBounds(280,70,125,20);
        panel.add(bNuevoLibro);

        ActionListener agregaLibros = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String genero = (String)comboCategoria.getSelectedItem();

                ContactoGL contactoGL = new ContactoGL();
                try{
                    int indice = 0;
                    HashMap<Integer, Libro> datosL = new HashMap<>();
                    Libro libro;
                    contactoGL.abrirGL("libro.ser");
                    do{
                        libro = contactoGL.leerL();
                        datosL.put(indice, libro);
                        indice++;
                    }while (libro  != null);
                    for (int i=0;i<indice-1;i++){
                        String genLi = datosL.get(i).getGenero();
                        String nomLi = datosL.get(i).getNombre();
                        String estado = datosL.get(i).getDisponibilidad();
                        Libro.libros.put(i, new Libro(genLi, nomLi, estado));
                    }
                    Libro.libros.put(Libro.libros.size(), new Libro(genero, libroIngresado.getText(), "Disponible"));

                    contactoGL.crearGL("libro.ser");
                    Iterator <Integer> iterator2 = Libro.libros.keySet().iterator();
                    while (iterator2.hasNext()){
                        int llave = iterator2.next();
                        String genLi = Libro.libros.get(llave).getGenero();
                        String nomLi = Libro.libros.get(llave).getNombre();
                        String estado = Libro.libros.get(llave).getDisponibilidad();
                        contactoGL.escribirGL(new Libro(genLi, nomLi, estado));
                    }
                    System.out.println("Agregado");
                    contactoGL.cerrarGL();
                } catch (IOException ex) {throw new RuntimeException(ex);}
                catch (ClassNotFoundException ex) {throw new RuntimeException(ex);}
            }
        };
        bNuevoLibro.addActionListener(agregaLibros);

        JButton bEliminaLibro = new JButton("Eliminar libro");
        bEliminaLibro.setBounds(280,130,125,20);
        panel.add(bEliminaLibro);

        ActionListener eliminaLibros = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean senal = false;
                try {

                    Iterator <Integer> iterator2 = Libro.libros.keySet().iterator();
                    while (iterator2.hasNext()){
                        int llave = iterator2.next();
                        String genLi = Libro.libros.get(llave).getGenero();
                        String nomLi = Libro.libros.get(llave).getNombre();
                        String estado = Libro.libros.get(llave).getDisponibilidad();
                        if (nomLi.equals(libroIngresado.getText()) && estado.equals("Disponible")){
                            senal = true;
                            iterator2.remove();
                        } else if (estado.equals("No Disponible")) {
                            senal = false;
                        }
                    }
                    if (senal){
                        JOptionPane.showMessageDialog(null,"Eliminado con exito");
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"No es posible devolverlo");
                    }


                    ContactoGL contactoGL = new ContactoGL();
                    contactoGL.crearGL("libro.ser");
                    while (iterator2.hasNext()){
                        int llave = iterator2.next();
                        String genLi = Libro.libros.get(llave).getGenero();
                        String nomLi = Libro.libros.get(llave).getNombre();
                        String estado = Libro.libros.get(llave).getDisponibilidad();
                        contactoGL.escribirGL(new Libro(genLi, nomLi, estado));
                    }
                    contactoGL.cerrarGL();
                } catch (IOException ex) {throw new RuntimeException(ex);}
            }
        };
        bEliminaLibro.addActionListener(eliminaLibros);

        JButton bNuevaCat = new JButton("Agregar Genero");
        bNuevaCat.setBounds(20,70,140,20);
        panel.add(bNuevaCat);

        ActionListener agregaGenero = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ContactoGL contactoGL = new ContactoGL();
                try {
                    contactoGL.crearGL("genero.ser");
                    Iterator<Integer> iterator = Genero.generos.keySet().iterator();
                    while (iterator.hasNext()){
                        int llave = iterator.next();
                        String genero = Genero.generos.get(llave);
                        contactoGL.escribirGL(new Genero(genero));
                    }
                    Genero.generos.put(Genero.generos.size(), nomCategoria.getText());
                    contactoGL.escribirGL(new Genero(nomCategoria.getText()));
                    contactoGL.cerrarGL();
                } catch (IOException ex) {throw new RuntimeException(ex);}
                comboCategoria.addItem(nomCategoria.getText());
            }
        };
        bNuevaCat.addActionListener(agregaGenero);

        JButton bEliminaCat = new JButton("Eliminar Genero");
        bEliminaCat.setBounds(20,130,145,20);
        panel.add(bEliminaCat);

        ActionListener eliminarGenero = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ContactoGL contactoGL = new ContactoGL();
                    contactoGL.crearGL("genero.ser");
                    Iterator<Integer> iterator1 = Genero.generos.keySet().iterator();
                    while (iterator1.hasNext()){
                        int llave = iterator1.next();
                        String nomGen = Genero.generos.get(llave);
                        if (comboCategoria.getSelectedItem().toString().equals(nomGen)){
                            iterator1.remove();
                        }
                        else{
                            contactoGL.escribirGL(new Genero(nomGen));
                        }
                    }
                    contactoGL.cerrarGL();

                    contactoGL.crearGL("libro.ser");
                    Iterator <Integer> iterator2 = Libro.libros.keySet().iterator();
                    while (iterator2.hasNext()){
                        int llave = iterator2.next();
                        String genLi = Libro.libros.get(llave).getGenero();
                        String nomLi = Libro.libros.get(llave).getNombre();
                        String estado = Libro.libros.get(llave).getDisponibilidad();
                        if (genLi.equals(comboCategoria.getSelectedItem().toString())){
                            iterator2.remove();
                        }
                        else{
                            contactoGL.escribirGL(new Libro(genLi, nomLi, estado));
                        }
                    }
                    contactoGL.cerrarGL();
                    JOptionPane.showMessageDialog(null, "Eliminado correctamente");
                } catch (IOException ex) {throw new RuntimeException(ex);}

                comboCategoria.removeItem(comboCategoria.getSelectedItem());
            }
        };
        bEliminaCat.addActionListener(eliminarGenero);

        JButton bCerrar = new JButton("Cerrar");
        bCerrar.setBackground(Color.ORANGE);
        bCerrar.setBounds(320,230,90,20);
        panel.add(bCerrar);

        ActionListener cerrarVen = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BibliotecaGUI();
                dispose();
            }
        };
        bCerrar.addActionListener(cerrarVen);
    }

    /**
     * Metodo que permite colocar el comboBox en la ventana
     */
    public void ColocarCombo(){
        comboCategoria = new JComboBox();
        comboCategoria.setBounds(150,175,140,20);

        try{
            //Reiniciar el archivo de generos

            /*ContactoGL contactoGenero = new ContactoGL();
            contactoGenero.crearGL("genero.ser");
            contactoGenero.escribirGL(new Genero("Novelas"));
            contactoGenero.escribirGL(new Genero("Terror"));
            contactoGenero.escribirGL(new Genero("Ingenieria"));
            contactoGenero.cerrarGL();*/

            int indice = 0;
            HashMap<Integer, Genero> datosG = new HashMap<>();
            Genero genero;
            ContactoGL contactoGl = new ContactoGL();
            contactoGl.abrirGL("genero.ser");

            do{
                genero = contactoGl.leerG();
                datosG.put(indice, genero);
                indice++;

            }while(genero != null);
            for (int i=0; i<indice-1;i++){
                Genero.generos.put(i, datosG.get(i).getGenero());
            }
        }catch (IOException exception) {throw new RuntimeException(exception);}
        catch (ClassNotFoundException exception) {throw new RuntimeException(exception);}

        Iterator <Integer> iterator = Genero.generos.keySet().iterator();
        while (iterator.hasNext()){
            int llave = iterator.next();
            String nomG = Genero.generos.get(llave);
            comboCategoria.addItem(nomG);
        }
        panel.add(comboCategoria);
    }

    /**
     * Metodo que permite reiniciar los libros predeterminados de la biblioteca
     */
    public void cargarLibros(){
        /*try {
            ContactoGL contactoGL = new ContactoGL();
            contactoGL.crearGL("libro.ser");

            contactoGL.escribirGL(new Libro("Novelas","Orgullo y Prejuicio","Disponible"));
            contactoGL.escribirGL(new Libro("Novelas","Romeo y Julieta","Disponible"));
            contactoGL.escribirGL(new Libro("Novelas","Los Miserables","Disponible"));
            contactoGL.escribirGL(new Libro("Novelas","Dr�cula","Disponible"));
            contactoGL.escribirGL(new Libro("Novelas","Cumbres Borrascosas","Disponible"));

            contactoGL.escribirGL(new Libro("Terror","La casa infernal","Disponible"));
            contactoGL.escribirGL(new Libro("Terror","Los Cuadernos Lovecraft","Disponible"));
            contactoGL.escribirGL(new Libro("Terror","Wody","Disponible"));
            contactoGL.escribirGL(new Libro("Terror","Temores Crecientes","Disponible"));
            contactoGL.escribirGL(new Libro("Terror","Seria Crave","Disponible"));

            contactoGL.escribirGL(new Libro("Ingenieria","Teorema del Loro","Disponible"));
            contactoGL.escribirGL(new Libro("Ingenieria","Planilandia","Disponible"));
            contactoGL.escribirGL(new Libro("Ingenieria","La Mano que Piensa","Disponible"));
            contactoGL.escribirGL(new Libro("Ingenieria","Continuous Delivery","Disponible"));
            contactoGL.escribirGL(new Libro("Ingenieria","Soft Skills","Disponible"));
            contactoGL.cerrarGL();

            int indice = 0;
            HashMap<Integer, Libro> datosL = new HashMap<>();
            Libro libro;
            contactoGL.abrirGL("libro.ser");
            do{
                libro = contactoGL.leerL();
                datosL.put(indice, libro);
                indice++;
            }while (libro!=null);
            for (int i=0; i<indice-1; i++){
                String genLi = datosL.get(i).getGenero();
                String nomLi = datosL.get(i).getNombre();
                String estado = datosL.get(i).getDisponibilidad();
                Libro.libros.put(i, new Libro(genLi, nomLi, estado));
            }
        } catch (IOException ex) {throw new RuntimeException(ex);}
        catch (ClassNotFoundException e) {throw new RuntimeException(e);}*/
    }
}
