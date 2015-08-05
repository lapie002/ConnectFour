/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package puissance4cs;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Staik
 */
/**
 * Inner class DessinerCanevas (extends JPanel) utilisée pour le dessin
 * graphique personnalisé.
 */
public class Canevas extends JPanel {
    public static final int LIGNES = 6;  // LIGNES et COLONNES pour les CASES du jeu
    public static final int COLONNES = 7;

    // Le nom des constantes des différentes dimensions utilisées pour le dessin graphique
    public static final int TAILLE_CASE = 100; // largeur et hauteur de la cellule (carré)
    public static final int LARGEUR_CANEVAS = TAILLE_CASE * COLONNES;  // dessin pour le canevas
    public static final int HAUTEUR_CANEVAS = TAILLE_CASE * LIGNES;
    
    public static final int LARGEUR_GRILLE = 8;                   // La largeur de la grille 
    public static final int LARGEUR_DEMI_GRILLE = LARGEUR_GRILLE / 2; // Demi-largeur de la grille
    public static final int REMPLISSAGE_CASE = TAILLE_CASE / 6;
    public static final int TAILLE_SYMBOLE = TAILLE_CASE - REMPLISSAGE_CASE * 2; // largeur/hauteur
    public static final int LARGEUR_TRAIT_SYMBOLE = 8; // la largeur de trait du crayon


    protected Jeton[][] grille;
    protected JLabel barreDeDialogue;  // barre de dialogue dans le jeu qui affiche le tour des joueur...

    @Override
    public void paintComponent(Graphics g) {  // invoqué via repaint()
        super.paintComponent(g);    // remplir l'arrière-plan
        setBackground(Color.WHITE); // etablissement de la couleur d'arrière-plan

        // Dessiner les lignes de la grille
        g.setColor(Color.LIGHT_GRAY);
        for (int ligne = 1; ligne < LIGNES; ligne++) {
            g.fillRoundRect(0, TAILLE_CASE * ligne - LARGEUR_DEMI_GRILLE,
                    LARGEUR_CANEVAS - 1, LARGEUR_GRILLE, LARGEUR_GRILLE, LARGEUR_GRILLE);
        }
        for (int colonne = 1; colonne < COLONNES; colonne++) {
            g.fillRoundRect(TAILLE_CASE * colonne - LARGEUR_DEMI_GRILLE, 0,
                    LARGEUR_GRILLE, HAUTEUR_CANEVAS - 1, LARGEUR_GRILLE, LARGEUR_GRILLE);
        }

         // Dessiner les jetons de toutes les cellules si elles ne sont pas vides
        // Utilisez Graphics2D qui nous permet de régler la taille du crayon
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(LARGEUR_TRAIT_SYMBOLE, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND));  // Graphics2D seulement
        for (int ligne = 0; ligne < LIGNES; ligne++) {
            for (int colonne = 0; colonne < COLONNES; colonne++) {
                int x1 = colonne * TAILLE_CASE + REMPLISSAGE_CASE;
                int y1 = ligne * TAILLE_CASE + REMPLISSAGE_CASE;
                if (grille[ligne][colonne].getCouleur() == Jeton.Couleur.ROUGE) {
                    g2d.setColor(Color.RED);
                    g2d.drawOval(x1, y1, TAILLE_SYMBOLE, TAILLE_SYMBOLE);
                } else if (grille[ligne][colonne].getCouleur() == Jeton.Couleur.JAUNE) {
                    g2d.setColor(Color.YELLOW);
                    g2d.drawOval(x1, y1, TAILLE_SYMBOLE, TAILLE_SYMBOLE);
                }
            }
        }
    }

    public Canevas() {
        this.setPreferredSize(new Dimension(LARGEUR_CANEVAS, HAUTEUR_CANEVAS));
        // Configuration de la barre d'état (JLabel) pour afficher un message d'état
        barreDeDialogue = new JLabel("  ");
        barreDeDialogue.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 15));
        barreDeDialogue.setBorder(BorderFactory.createEmptyBorder(2, 5, 4, 5));
        grille = new Jeton[LIGNES][COLONNES];
        initGrille();
    }
    
    public void ajouterJeton(Jeton jeton, int ligne, int colonne) {
        grille[ligne][colonne].setCouleur(jeton.getCouleur());
    }
    
    public void initGrille(){
        for (int ligne = 0; ligne < LIGNES; ligne++) {
            for (int colonne = 0; colonne < COLONNES; colonne++) {
                grille[ligne][colonne] = new Jeton(Jeton.Couleur.VIDE); // toutes les cellules sont initialisées a vides au debut
            }
        }
    }
}
