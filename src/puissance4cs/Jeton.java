/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package puissance4cs;

import java.io.Serializable;

/**
 *
 * @author Staik
 */
public class Jeton implements Serializable{
    public static enum Couleur{JAUNE, ROUGE, VIDE};
    
    private Couleur couleur;
    
    public Jeton(Couleur couleur){
        this.couleur = couleur;
    }

    public Couleur getCouleur() {
        return couleur;
    }

    public void setCouleur(Couleur couleur) {
        this.couleur = couleur;
    }
    
    
    
}