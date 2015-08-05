/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package puissance4cs;

public class Joueur {
    
    private String name;
    private Jeton.Couleur couleur;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Jeton.Couleur getCouleur() {
        return couleur;
    }

    public void setCouleur(Jeton.Couleur couleur) {
        this.couleur = couleur;
    }

    public Joueur(String name, Jeton.Couleur couleur) {
        this.name = name;
        this.couleur = couleur;
    }
    
    
    
}
