/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package juegodelaserpiente;

//Cuando un import tiene el * significa se importan todos los elementos de esta biblioteca
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class Paneljuego extends JPanel implements ActionListener{
    static final int ANCHO_PANTALLA = 600;
    static final int ALTO_PANTALLA = 600;
    static final int UNIDAD_MEDIDA = 25;//Si aumentamos la unidad de medida podremos hacer q el juego sea mas corto ya que no habran tantas casillas
    static final int UNIDAD_JUEGO = (ANCHO_PANTALLA*ALTO_PANTALLA)/UNIDAD_MEDIDA;
    static final int VELOCIDAD = 75; //En esta linea podremos modificar la velocidad de la serpiente mientras menor sea el número mas rapido ira
    final int x[]= new int[UNIDAD_JUEGO];
    final int y[]= new int[UNIDAD_JUEGO];
    int partescuerpo = 6;
    int manzanascomidas;
    int manzanaX;
    int manzanaY;
    char direccion = 'D';//D = derecha;
    boolean ejecucion = false;
    Timer reloj;
    Random random;
    
    //Una vez definido el panel comenzaremos a darle forma al juego
    Paneljuego(){
        random = new Random();
        this.setPreferredSize(new Dimension(ANCHO_PANTALLA,ALTO_PANTALLA));
        this.setBackground(Color.black); //En esta linea podemos cambiarle el color de fondo del juego
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        iniciojuego();
    }
    public void iniciojuego(){
        manzananueva();
        ejecucion = true;
        reloj = new Timer(VELOCIDAD,this);
        reloj.start();
    }
    @Override //característica que permite que una subclase o clase secundaria proporcione una implementación específica de un método que ya está provisto por una de sus superclases o clases principales.
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        dibujar(g);
    }
    public void dibujar(Graphics g) {

        if (ejecucion) {
            //Con esto se dibujara una grilla puedes probar comentandola para q quede mas moderno :)
		for(int i=0;i<ALTO_PANTALLA/UNIDAD_MEDIDA;i++) {
                    g.drawLine(i*UNIDAD_MEDIDA, 0, i*UNIDAD_MEDIDA, ALTO_PANTALLA);
                    g.drawLine(0, i*UNIDAD_MEDIDA, ANCHO_PANTALLA, i*UNIDAD_MEDIDA);
		}
            //Esto es para darle color y forma a nuestras manzanas :)
            g.setColor(Color.red);
            g.fillOval(manzanaX, manzanaY, UNIDAD_MEDIDA, UNIDAD_MEDIDA);
            
            //Esto es para darle el color a nuestra serpiente
            
            for (int i = 0; i < partescuerpo; i++) {
                if (i == 0) { //Esto es para darle un color diferente a la cabeza
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIDAD_MEDIDA, UNIDAD_MEDIDA);
                } else {
                    g.setColor(new Color(45, 180, 0));
                    //g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255))); Si quitamos la linea anterior y descomentamos esta la serpiente ira cambiando de color de forma aleatoria
                    g.fillRect(x[i], y[i], UNIDAD_MEDIDA, UNIDAD_MEDIDA);
                }
            }
            
            //Esto es para darle color al puntaje
            
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 40));//Ink free es un tipo de letra pero hay muchos q podemos probar.
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + manzanascomidas, (ANCHO_PANTALLA - metrics.stringWidth("Score: " + manzanascomidas)) / 2, g.getFont().getSize());
        } else {
            juegoterminado(g);
        }
        
    }
    //Esto generara una manzana nueva en una posicion aleatoria
    public void manzananueva(){
        manzanaX = random.nextInt((int)(ANCHO_PANTALLA/UNIDAD_MEDIDA))*UNIDAD_MEDIDA;
        manzanaY = random.nextInt((int)(ALTO_PANTALLA/UNIDAD_MEDIDA))*UNIDAD_MEDIDA;
    }
     public void movimiento(){
        for (int i = partescuerpo; i > 0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        switch(direccion){
            case 'W'://Arriba
                y[0] = y[0] - UNIDAD_MEDIDA;
                break;
            case 'S'://Abajo
                y[0] = y[0] + UNIDAD_MEDIDA;
                break;
            case 'A'://Izquierda
                x[0] = x[0] - UNIDAD_MEDIDA;
                break;
            case 'D'://Derecha
                x[0] = x[0] + UNIDAD_MEDIDA;
                break;
        }
    }
    // Esto verifica si la manzana ha sido comida por la serpiente de ser asi se le sumara una parte al cuerpo de la serpiente y al contador de manzanas comidas y se llamara al subprograma para q cree una nueva 
    public void verificarmanzana(){
        if((x[0] == manzanaX)&&(y[0] == manzanaY)){
            partescuerpo++;
            manzanascomidas++;
            manzananueva();
        }
    }
     public void verificarchoques(){
        //verifica si la cabeza choca contra el cuerpo
        for (int i = partescuerpo; i > 0; i--) {
            if((x[0]==x[i])&&(y[0]==y[i])){
                ejecucion = false;
            }
        }
        //verifica si la cabeza toca el borde izquierdo
        if(x[0] < 0){
            ejecucion = false;
        }
        //verifica si la cabeza toca el borde derecho
        if(x[0] > ANCHO_PANTALLA){
            ejecucion = false;
        }
        //verifica si la cabeza toca el borde superior
        if(y[0] < 0){
            ejecucion = false;
        }
        //verifica si la cabeza toca el borde inferior
        if(y[0] > ALTO_PANTALLA){
            ejecucion = false;
        }
        
        if(!ejecucion){
            reloj.stop();
        }
    }
    //Lo q se va a mostrar si el usuario pierde o gana;
    public void juegoterminado(Graphics g){
        //Puntaje
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD, 75));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: "+manzanascomidas,(ANCHO_PANTALLA - metrics1.stringWidth("Score: "+manzanascomidas))/2,g.getFont().getSize());
        //Texto de juego terminado
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Juego finalizado",(ANCHO_PANTALLA - metrics2.stringWidth("Juego finalizado"))/2,ALTO_PANTALLA/2);
    }
    @Override
    public void actionPerformed(ActionEvent e){
        if(ejecucion){
            movimiento();
            verificarmanzana();
            verificarchoques();
        }
        repaint();
    }
    
    //Con esto podremos usar la serpiente con las flechas del teclado
    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(direccion != 'D'){
                        direccion = 'A';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direccion != 'A'){
                        direccion = 'D';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direccion != 'S'){
                        direccion = 'W';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direccion != 'W'){
                        direccion = 'S';
                    }
                    break;
            }
        }
    }
}
