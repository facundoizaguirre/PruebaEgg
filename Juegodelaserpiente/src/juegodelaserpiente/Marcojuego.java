/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package juegodelaserpiente;

import javax.swing.JFrame;

public class Marcojuego extends JFrame {
    Marcojuego(){
        this.add(new Paneljuego());
        this.setTitle("Serpiente");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        
    }
}