/**
 * @autor Santiago Anibal Carrillo Torres
 * @version 1.0
 * @date 21/12/2023
 */
package PaqueteVentanaPrin;

import PaqueteBibliotecario.IniciarSesion;
import PaqueteBibliotecario.Registro;
import PaqueteLibroGenero.AggGL;
import PaqueteLibroGenero.ContactoGL;
import PaqueteLibroGenero.Genero;
import PaqueteLibroGenero.Libro;
import PaquetePrestamo.ContactoPres;
import PaquetePrestamo.InfoPrestamo;
import PaquetePrestamo.PrestamoGUI;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

/**
 * Clase para la ventana principal de la biblioteca
 */
public  class BibliotecaGUI extends JFrame{
    // Atributos
    public static String nomLibro;
    public static String genElegido;
    public static boolean habilitaMenu = true;

    //Componentes de la ventana
    private JPanel panel;
    public static JComboBox comboGenero;
    public JTable areaLibro;
    public JTable areaPrestamo;

    /**
     * Constructor de BibliotecaGUI
     */
    public  BibliotecaGUI(){
        setTitle("Biblioteca");
        setSize(700,600);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        ComponentesVentana();
        tablaPrestamo();

    }

    /**
     * Método para colocar los componentes de la ventana
     */
    public void ComponentesVentana(){
        ColocarPanel();
        ColocarLabel();
        ColocarPanel();
        ColocarLabel();
        ColocarItem();
        ColocarCombo();
        ColocarButton();

        tablaLibros();
    }

    /**
     * Método para configurar el panel de la ventana
     */
    public void ColocarPanel(){
        //Panel para todos los componentes de la ventana
        panel = new JPanel();
        panel.setBackground(Color.LIGHT_GRAY);
        panel.setLayout(null);
        getContentPane().add(panel);
    }

    /**
     * Método para colocar los items del menú
     */
    public void ColocarItem(){
        //Se crea un JMenuBar para agregar a los nuevos usuarios de la biblioteca
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu estadoDeLosLibros = new JMenu("Estado de los libros");
        menuBar.add(estadoDeLosLibros);
        JMenuItem estadoLibros = new JMenuItem();
        estadoLibros.setText("Consultar");
        estadoDeLosLibros.add(estadoLibros);

        JMenu clieMora = new JMenu("Clientes en mora");
        JMenuItem menuitemClieMo;
        menuitemClieMo = new JMenuItem("Consultar Clientes");
        clieMora.add(menuitemClieMo);
        menuBar.add(clieMora);

        JMenu menuRegistro = new JMenu("Registro");
        menuRegistro.enable(habilitaMenu);
        JMenuItem menuitemregistro;
        menuitemregistro = new JMenuItem("Editar Cuentas");
        menuRegistro.add(menuitemregistro);
        menuBar.add(menuRegistro);

        JMenu menuSalir = new JMenu("Salir");
        JMenuItem menuitemnew;
        menuitemnew = new JMenuItem("Iniciar Sesion con Cuenta");
        menuSalir.add(menuitemnew);
        JMenuItem menuitemsalir;
        menuitemsalir = new JMenuItem("Salir");
        menuSalir.add(menuitemsalir);
        menuBar.add(menuSalir);

        menuitemregistro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Registro();
                dispose();
            }
        });

        menuitemnew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new IniciarSesion();
                dispose();
            }
        });

        menuitemsalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        menuitemClieMo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, InfoPrestamo.VerificaDeuda());
            }
        });

        estadoLibros.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, Libro.mostrarEstado((String)comboGenero.getSelectedItem()));
            }
        });
    }

    /**
     * Método para colocar etiquetas de la ventana
     */
    public void ColocarLabel(){
        //Agrego las textos
        JLabel texto1 = new JLabel();
        texto1.setText("Genero:");
        texto1.setBounds(30,20,110,20);
        panel.add(texto1);

        JLabel texto2 = new JLabel();
        texto2.setText("Libro:");
        texto2.setBounds(240,20, 90,20);
        panel.add(texto2);

        JLabel texto3 = new JLabel();
        texto3.setText("Para prestar un libro,");
        texto3.setOpaque(true);
        texto3.setHorizontalAlignment(SwingConstants.CENTER);
        texto3.setBackground(Color.ORANGE);
        texto3.setForeground(Color.darkGray);
        texto3.setBounds(15,155,210, 20);
        panel.add(texto3);

        JLabel texto4 = new JLabel();
        texto4.setText("elige el genero correspondiente");
        texto4.setOpaque(true);
        texto4.setHorizontalAlignment(SwingConstants.CENTER);
        texto4.setBackground(Color.ORANGE);
        texto4.setForeground(Color.darkGray);
        texto4.setBounds(15,170,210, 20);
        panel.add(texto4);
    }

    /**
     * Método para colocar botones de la ventana
     */
    public void ColocarButton(){
        //boton para realizar un préstamo
        JButton prestamoLibro = new JButton();
        prestamoLibro.setBackground(Color.green);
        prestamoLibro.setText("Prestar libro");
        prestamoLibro.setBounds(270,225,140,25);
        panel.add(prestamoLibro);

        //Boton para devolución del libro
        JButton devolver = new JButton();
        devolver.setBackground(Color.green);
        devolver.setText("Devoluciones");
        devolver.setBounds(490, 225, 140, 25);
        panel.add(devolver);

        JButton agregaLG = new JButton();
        agregaLG.setBackground(Color.green);
        agregaLG.setText("Editar Libro/Genero");
        agregaLG.setBounds(40, 225, 160, 25);
        panel.add(agregaLG);


        ActionListener guardaPrestamo = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Byte[] senal = new Byte[1];
                senal[0] = 0;
                try{
                    int fila = areaLibro.getSelectedRow();
                    String libroSelect = areaLibro.getValueAt(fila, 1).toString();
                    String estadoA = areaLibro.getValueAt(fila, 2).toString();
                    for (int llave : Libro.libros.keySet()) {
                        String nomL = Libro.libros.get(llave).getNombre();
                        String genL = Libro.libros.get(llave).getGenero();
                        if (genL.equals(comboGenero.getSelectedItem().toString()) && estadoA.equals("Disponible")) {
                            if (nomL.equals(libroSelect)) {
                                nomLibro = nomL;
                                genElegido = genL;
                                senal[0] = 1;
                                break;
                            } else {
                                senal[0] = 2;
                            }
                        }
                    }
                    if (senal[0] == 1){
                        JOptionPane.showMessageDialog(null, "Prestamo realizado correctamente");
                        PrestamoGUI.botonDevolver = false;
                        PrestamoGUI.botonPrestamo = true;
                        new PrestamoGUI();
                        dispose();
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Por favor, verifica los datos");
                    }
                }catch (Exception io){
                    JOptionPane.showMessageDialog(null,"Elige un libro");
                }
            }
        };
        prestamoLibro.addActionListener(guardaPrestamo);


        ActionListener devolucion = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PrestamoGUI.botonDevolver = true;
                PrestamoGUI.botonPrestamo = false;
                new PrestamoGUI();
                dispose();
            }
        };
        devolver.addActionListener(devolucion);

        ActionListener abrirEditarGL = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AggGL();
                dispose();
            }
        };
        agregaLG.addActionListener(abrirEditarGL);
    }

    /**
     * Método para colocar el comboBox de la ventana
     */
    public void ColocarCombo(){
        comboGenero = new JComboBox();
        comboGenero.setBounds(30,40,150, 20);

        if (AggGL.habilitaLecturaG == 0){
            ContactoGL contactoGL = new ContactoGL();
            contactoGL.lecturaGeneros();
            AggGL.habilitaLecturaG++;
        }

        for (int llave : Genero.generos.keySet()) {
            String generos = Genero.generos.get(llave);
            comboGenero.addItem(generos);
        }
        panel.add(comboGenero);
    }

    /**
     * Método para agregar una tabla con los libros, genero y estado
     */
    public void tablaLibros(){
        if (AggGL.habilitaLecturaL == 0){
            ContactoGL contactoGL = new ContactoGL();
            contactoGL.lecturaLibros();
            AggGL.habilitaLecturaL ++;
        }

        String [] columnas = {"Genero", "Nombre", "Estado"};
        String [][] infoLibros = new String[Libro.libros.size()][3];
        int contador = 0;

        for (int llave : Libro.libros.keySet()) {
            String genLi = Libro.libros.get(llave).getGenero();
            String nomLi = Libro.libros.get(llave).getNombre();
            String estado = Libro.libros.get(llave).getDisponibilidad();
            infoLibros[contador][0] = genLi;
            infoLibros[contador][1] = nomLi;
            infoLibros[contador][2] = estado;
            contador++;
        }
        areaLibro = new JTable(infoLibros, columnas);

        JScrollPane listaLibro = new JScrollPane(areaLibro);
        listaLibro.setBounds(240,40, 430,150);
        panel.add(listaLibro);
    }

    /**
     * Método para agregar una tabla con información de prestamos
     */
    public void tablaPrestamo() {
        if (PrestamoGUI.habilitarPrestamo == 0){
            ContactoPres contactoPres = new ContactoPres();
            contactoPres.lecturaPrestamos();
            PrestamoGUI.habilitarPrestamo++;
        }
        String [] columna = {"Nombre", "Genero", "Libro", "Fecha"};
        String [][] prestamosInf = new String[InfoPrestamo.prestamos.size()][4];;
        areaPrestamo = new JTable(prestamosInf, columna);

        int contador = 0;
        for (int llave : InfoPrestamo.prestamos.keySet()) {
            String personaP = InfoPrestamo.prestamos.get(llave).getPersona();
            String generoP = InfoPrestamo.prestamos.get(llave).getGenero();
            String libroP = InfoPrestamo.prestamos.get(llave).getLibro();
            String fechaP = InfoPrestamo.prestamos.get(llave).getFecha();
            prestamosInf[contador][0] = personaP;
            prestamosInf[contador][1] = generoP;
            prestamosInf[contador][2] = libroP;
            prestamosInf[contador][3] = fechaP;
            contador++;
        }
        JScrollPane listaPrestamo = new JScrollPane(areaPrestamo);
        listaPrestamo.setBounds(10, 280, 660, 250);
        panel.add(listaPrestamo);
    }
}
