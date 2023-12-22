package PaqueteBibliotecario;

import PaqueteVentanaPrin.BibliotecaGUI;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Registro extends JFrame{
    //Ventana de bibliotecario
    JLabel lblTitulo = new JLabel("BIBLIOTECA U");
    ImageIcon imagen = new ImageIcon("logo.png");
    JLabel lblimagen = new JLabel();
    private JTextField campoNombre;
    private JPasswordField campoContrasena;
    private JRadioButton radioJefe;
    private JRadioButton radioNormal;
    private JPanel panel;
    public Registro() {
        setTitle("Registros bibliotecario");
        setSize(340,460);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        Componentes();
        CamposTexto();
        ColocarTexto();
        ColocarBoton();
        ColocarRadio();
    }

    public void Componentes(){
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);
        getContentPane().add(panel);
    }
    public void CamposTexto (){
        campoNombre = new JTextField();
        campoNombre.setBounds(20,235, 170,20);
        panel.add(campoNombre);

        campoContrasena = new JPasswordField();
        campoContrasena.setBounds(20,295,170,20);
        panel.add(campoContrasena);
    }
    public void ColocarTexto () {
        JLabel ingresaNombre = new JLabel();
        ingresaNombre.setText("Nombre:");
        ingresaNombre.setBounds(20, 220, 65, 10);
        panel.add(ingresaNombre);

        lblTitulo.setBounds(120,10,90,25);
        panel.add(lblTitulo);

        lblimagen.setBounds(100,50,140,160);
        lblimagen.setIcon(new ImageIcon(imagen.getImage().getScaledInstance(140,160, Image.SCALE_SMOOTH)));
        panel.add(lblimagen);

        JLabel ingresaContrasena = new JLabel();
        ingresaContrasena.setText("Contrasena:");
        ingresaContrasena.setBounds(20,280,80,10);
        panel.add(ingresaContrasena);
    }

    public void ColocarRadio(){
        radioJefe = new JRadioButton();
        radioJefe.setText("Jefe");
        radioJefe.setBackground(Color.LIGHT_GRAY);
        radioJefe.setBounds(45, 330, 70, 25);
        panel.add(radioJefe);

        radioNormal = new JRadioButton();
        radioNormal.setText("Normal");
        radioNormal.setBackground(Color.LIGHT_GRAY);
        radioNormal.setBounds(200, 330,80, 25);
        panel.add(radioNormal);

        ButtonGroup agrupaRadio = new ButtonGroup();
        agrupaRadio.add(radioJefe);
        agrupaRadio.add(radioNormal);
    }
    public void ColocarBoton (){
        //Inicio sesion del bibliotecario jefe
        JButton botonRegistrar = new JButton();
        botonRegistrar.setText("Crear Cuenta");
        botonRegistrar.setBounds(20, 370, 120, 25);
        panel.add(botonRegistrar);

        //Agrego la acción al botón
        ActionListener confirmarUsuario = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if (campoNombre.getText().isEmpty() || campoContrasena.getText().isEmpty())
                {
                    JOptionPane.showMessageDialog(null,"Digite información correcta en los campos");
                }
                else{
                    ContactoDatos contactoDatos = new ContactoDatos();
                    try {
                        contactoDatos.crear("bibliotecario.ser");
                        for (Map.Entry<Integer, DatosBibliotecario> h: DatosBibliotecario.datosB.entrySet()){
                            String nom = h.getValue().getNombre();
                            String con = h.getValue().getcontra();
                            String rol = h.getValue().getRol();
                            contactoDatos.escribir(new DatosBibliotecario(nom, con, rol));
                        }
                        if (radioJefe.isSelected()){
                            contactoDatos.escribir(new DatosBibliotecario(campoNombre.getText(), campoContrasena.getText(), "jefe"));
                            DatosBibliotecario.datosB.put(DatosBibliotecario.datosB.size(), new DatosBibliotecario(campoNombre.getText(), campoContrasena.getText(), "jefe"));
                            campoContrasena.setText("");
                            campoNombre.setText("");
                        }
                        else{
                            contactoDatos.escribir( new DatosBibliotecario(campoNombre.getText(), campoContrasena.getText(), "comun"));
                            DatosBibliotecario.datosB.put(DatosBibliotecario.datosB.size(), new DatosBibliotecario(campoNombre.getText(), campoContrasena.getText(), "comun"));
                            campoContrasena.setText("");
                            campoNombre.setText("");
                        }
                        contactoDatos.cerrar();
                    } catch (IOException ex) {throw new RuntimeException(ex);}

                    /*campoContrasena.setText("");
                    campoNombre.setText("");*/
                }
            }
        };
        //Agrego la acción
        botonRegistrar.addActionListener(confirmarUsuario);


        //Inicia sesión un nuevo bibliotecario
        JButton botonSesion = new JButton();
        botonSesion.setText("Iniciar Sesion");
        botonSesion.setBounds(190, 200 ,120, 25);
        panel.add(botonSesion);

        ActionListener cuentaSecundaria = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = campoNombre.getText();
                String contrasena = campoContrasena.getText();
                Integer [] datosValidos = new Integer[1];

                try {
                    int indice = 0;
                    HashMap<Integer, DatosBibliotecario> datosB = new HashMap<>();
                    DatosBibliotecario bibliotecario;
                    ContactoDatos contactoDatos = new ContactoDatos();
                    contactoDatos.abrir("bibliotecario.ser");
                    do {
                        bibliotecario = contactoDatos.leer();
                        datosB.put(indice, bibliotecario);
                        indice++;
                    } while(bibliotecario != null);

                    for (int i = 0; i<indice-1;i++) {
                        if (datosB.get(i).getNombre().contains(nombre) && datosB.get(i).getcontra().contains(contrasena) && (!campoContrasena.getText().isEmpty() || !campoNombre.getText().isEmpty())) {
                            //Si los datos coinciden con los datos ingresados se envía la señal con la variable datosValidos
                            if (datosB.get(i).getRol().equals("jefe")){
                                datosValidos[0] = 1;
                                break;
                            }
                            else{
                                datosValidos[0] = 2;
                                break;
                            }
                        }
                        else{
                            datosValidos[0] = 3;
                        }
                    }
                } catch (IOException exception) {throw new RuntimeException(exception);}
                catch (ClassNotFoundException exception) {throw new RuntimeException(exception);}

                //Si los datosValidos es verdadero entonces se abre la ventana
                if (datosValidos[0] == 1) {
                    BibliotecaGUI.habilitaMenu = true;
                    new BibliotecaGUI();
                    dispose();

                } else if (datosValidos[0] == 2) {
                    BibliotecaGUI.habilitaMenu = false;
                    new BibliotecaGUI();
                    dispose();
                }
                //Si no envía un mensaje de aviso
                else {
                    JOptionPane.showMessageDialog(null, "Por favor, verifique los datos");
                }

            }
        };
        //Agrego la acción al botón
        botonSesion.addActionListener(cuentaSecundaria);


        JButton botonEliminar = new JButton();
        botonEliminar.setText("Eliminar Cuenta");
        botonEliminar.setBounds(170, 370, 130, 25);
        panel.add(botonEliminar);

        ActionListener eliminarCuentas = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int cuentaRol = 0;
                Iterator<Integer> iterator = DatosBibliotecario.datosB.keySet().iterator();
                while (iterator.hasNext()) {
                    int llave = iterator.next();
                    String rol = DatosBibliotecario.datosB.get(llave).getRol();
                    if (rol.equals("jefe")) {
                        cuentaRol++;
                    }
                }

                ContactoDatos contactoDatos = new ContactoDatos();
                try {
                    contactoDatos.crear("bibliotecario.ser");
                    Iterator<Integer> iterator1 = DatosBibliotecario.datosB.keySet().iterator();
                    while (iterator1.hasNext()){
                        int llave = iterator1.next();
                        String nom = DatosBibliotecario.datosB.get(llave).getNombre();
                        String con = DatosBibliotecario.datosB.get(llave).getcontra();
                        String rol = DatosBibliotecario.datosB.get(llave).getRol();
                        if ((nom.equals(campoNombre.getText()) && con.equals(campoContrasena.getText()))){
                            if (cuentaRol >=2){
                                JOptionPane.showMessageDialog(null, "Eliminado correctamente");
                                iterator1.remove();
                            }
                            else{
                                contactoDatos.escribir(new DatosBibliotecario(nom, con, rol));
                                System.out.println(nom +" "+ con +" "+ rol);
                            }
                        }else{
                            contactoDatos.escribir(new DatosBibliotecario(nom, con, rol));
                            System.out.println(nom +" "+ con +" "+ rol);
                        }

                    }
                    contactoDatos.cerrar();

                } catch (IOException ex) {throw new RuntimeException(ex);}
            }
        };
        botonEliminar.addActionListener(eliminarCuentas);
    }

    public static void main(String[] args) {
        //Instancia de la clase bibliotecario
        new Registro();
    }
}
