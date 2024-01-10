/**
 * @autor Santiago Anibal Carrillo Torres
 * @version 1.0
 * @date 21/12/2023
 */
package PaqueteBibliotecario;
import PaqueteVentanaPrin.BibliotecaGUI;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import static PaqueteVentanaPrin.BibliotecaGUI.habilitaMenu;

/**
 * Clase que permite iniciar sesion de un bibliotecario
 */
public class IniciarSesion extends JFrame{

    //Ventana de bibliotecario
    JLabel lblTitulo = new JLabel("BIBLIOTECA U");
    ImageIcon Imagen = new ImageIcon("logo.png");
    JLabel lblImagen = new JLabel();
    public JTextField campoNombre;
    public JPasswordField campoContrasena;
    private JPanel panel;
    public JButton botonConfirmar;

    public static int habilitalecturaB = 0;

    /**
     * Constructor de la clase
     */
    public IniciarSesion(){
        login();
    }

    /**
     * Metodo para la creacion de la ventana
     */
    public void login (){
        setTitle("Inicio de sesion: Biblioteca");
        setSize(340,460);
        setLocationRelativeTo(null);
        Componentes();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Accion();
    }

    /**
     * Metodo que permite agregar los componentes a la ventana
     */
    public void Componentes(){
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);
        getContentPane().add(panel);


        CamposTexto();
        ColocarTexto();
        ColocarBoton();
    }

    /**
     * Metodo que permite agregar los campos de texto a la ventana
     */
    public void CamposTexto (){
        campoNombre = new JTextField();
        campoNombre.setBounds(20,235, 170,20);
        panel.add(campoNombre);

        campoContrasena = new JPasswordField();
        campoContrasena.setBounds(20,295,170,20);
        panel.add(campoContrasena);
    }

    /**
     * Metodo que permite agregar etiquetas a la ventana
     */
    public void ColocarTexto () {
        JLabel ingresaNombre = new JLabel();
        ingresaNombre.setText("Nombre:");
        ingresaNombre.setBounds(20, 220, 65, 10);
        panel.add(ingresaNombre);

        lblTitulo.setBounds(120,10,90,25);
        panel.add(lblTitulo);

        lblImagen.setBounds(100,50,140,160);
        lblImagen.setIcon(new ImageIcon(Imagen.getImage().getScaledInstance(140,160, Image.SCALE_SMOOTH)));
        panel.add(lblImagen);

        JLabel ingresaContrasena = new JLabel();
        ingresaContrasena.setText("Contrasena:");
        ingresaContrasena.setBounds(20,280,80,10);
        panel.add(ingresaContrasena);
    }

    /**
     * Metodo para el boton de iniciar sesion
     */
    public void ColocarBoton (){
        //Inicio sesion del bibliotecario jefe
        botonConfirmar = new JButton();
        botonConfirmar.setText("Iniciar Sesion");
        botonConfirmar.setBounds(100, 350, 120, 25);
        panel.add(botonConfirmar);
    }

    /**
     * Metodo que permite realizar una accion al presionar el boton
     */
    public void Accion (){
        ActionListener confirmarUsuario = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Creo variables que alojan el texto agregado en el texfield
                String nombre = campoNombre.getText();
                String contrasena = campoContrasena.getText();
                Integer [] datosValidos = new Integer[1];
                datosValidos[0] = -1;

                if (habilitalecturaB == 0){
                    ContactoDatos contactoDatos = new ContactoDatos();
                    contactoDatos.lecturaBibliot();
                    habilitalecturaB++;
                }

                for (Map.Entry<Integer, DatosBibliotecario> h: DatosBibliotecario.datosB.entrySet()){
                    if (h.getValue().getNombre().equals(nombre) && h.getValue().getcontra().equals(contrasena)){
                        if (h.getValue().getRol().equals("jefe")){
                            datosValidos[0] = 1;
                            break;
                        } else{
                            datosValidos[0] = 2;
                            break;
                        }
                    }else{
                        datosValidos[0] = 3;
                    }
                }

                if (datosValidos[0] == 1) {
                    habilitaMenu = true;
                    new BibliotecaGUI();
                    dispose();
                }
                else if (datosValidos[0] == 2) {
                    habilitaMenu = false;
                    new BibliotecaGUI();
                    dispose();
                } else{
                    JOptionPane.showMessageDialog(null, "Por favor, verifique los datos");
                }
            }
        };
        //Agrego la acción al botón
        botonConfirmar.addActionListener(confirmarUsuario);
    }

    /**
     * Metodo main (principal del programa)
     * @param args argumentos de la linea de comandos
     */
    public static void main(String[] args) {
        //Instancia de la clase bibliotecario
        new IniciarSesion();
    }
}