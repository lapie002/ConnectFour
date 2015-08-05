/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package puissance4cs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import javax.swing.JFrame;

/**
 *
 * @author Staik
 */
public abstract class Jeu extends JFrame {
    
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;

    public static Joueur currentPlayer;
    protected Canevas canevas;
    public static EtatDuJeu etat = EtatDuJeu.PARTIE_EN_COURS;

    public static Joueur getCurrentPlayer() {
        return currentPlayer;
    }

    public static void setCurrentPlayer(Joueur currentPlayer) {
        Jeu.currentPlayer = currentPlayer;
    }

    public static enum EtatDuJeu {

        PARTIE_EN_COURS, MATCHNUL, ROUGE_GAGNE, JAUNE_GAGNE
    }

    public Jeu() {
        this.canevas = new Canevas();
        final Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(canevas, BorderLayout.CENTER);
        cp.add(canevas.barreDeDialogue, BorderLayout.PAGE_END);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void miseAjour(Jeton jeton) {
        if (partieGagnante()) {  // vérifier si y a une victoire
            etat = (jeton.getCouleur() == Jeton.Couleur.ROUGE) ? EtatDuJeu.JAUNE_GAGNE : EtatDuJeu.ROUGE_GAGNE;
        } else if (partieEstNulle()) {  // vérifier si y a une partie nulle
            etat = EtatDuJeu.MATCHNUL;
        }
        if (etat == EtatDuJeu.PARTIE_EN_COURS) {
            canevas.barreDeDialogue.setForeground(Color.BLACK);
            canevas.barreDeDialogue.setText(currentPlayer.getName() + " joue.");
        } else if (etat == EtatDuJeu.MATCHNUL) {
            canevas.barreDeDialogue.setForeground(Color.BLUE);
            canevas.barreDeDialogue.setText("c'est un match nul, cliquez pour demarrer une nouvelle partie.");
        } else if (etat == EtatDuJeu.ROUGE_GAGNE) {
            canevas.barreDeDialogue.setForeground(Color.RED);
            canevas.barreDeDialogue.setText(Server.joueur.getName() + " gagne, cliquez pour demarrer une nouvelle partie.");
        } else if (etat == EtatDuJeu.JAUNE_GAGNE) {
            canevas.barreDeDialogue.setForeground(Color.ORANGE);
            canevas.barreDeDialogue.setText(Client.joueur.getName() + " gagne, cliquez pour demarrer une nouvelle partie.");
        }
        // Sinon , aucun changement à l'état actuel (toujours EtatDuJeu.PARTIE_EN_COURS).
    }

    public boolean partieGagnante() {   // verification  pour savoir si un joueur a gagné la partie

        for (int ligne = 0; ligne < Canevas.LIGNES; ligne++) {     // parcours de toutes les rangées
            for (int colonne = 0; colonne < Canevas.COLONNES; colonne++) {   // parcours de toutes les colonnes
                if (((colonne + 3 < Canevas.COLONNES // vérification horizontale =>verification à colonne + 3 maximum ( à partir du point de départ c'est pour ne pas sortir du tableau 
                        && canevas.grille[ligne][colonne].getCouleur() != Jeton.Couleur.VIDE && canevas.grille[ligne][colonne + 1].getCouleur() != Jeton.Couleur.VIDE
                        && canevas.grille[ligne][colonne + 2].getCouleur() != Jeton.Couleur.VIDE && canevas.grille[ligne][colonne + 3].getCouleur() != Jeton.Couleur.VIDE)// vérification de la présence de jetons
                        && (canevas.grille[ligne][colonne].getCouleur() == canevas.grille[ligne][colonne + 1].getCouleur()
                        && canevas.grille[ligne][colonne + 1].getCouleur() == canevas.grille[ligne][colonne + 2].getCouleur()//nous vérifions si les cases  ont la même couleur de jeton
                        && canevas.grille[ligne][colonne + 2].getCouleur() == canevas.grille[ligne][colonne + 3].getCouleur()))
                        || ((ligne + 3 < Canevas.LIGNES //vérification verticale=> verification à ligne + 3 maximum ( à partir du point de départ c)pour ne pas sortir du tableau 
                        && canevas.grille[ligne][colonne].getCouleur() != Jeton.Couleur.VIDE && canevas.grille[ligne + 1][colonne].getCouleur() != Jeton.Couleur.VIDE
                        && canevas.grille[ligne + 2][colonne].getCouleur() != Jeton.Couleur.VIDE && canevas.grille[ligne + 3][colonne].getCouleur() != Jeton.Couleur.VIDE) // vérification de la présence de jetons
                        && (canevas.grille[ligne][colonne].getCouleur() == canevas.grille[ligne + 1][colonne].getCouleur()
                        && canevas.grille[ligne + 1][colonne].getCouleur() == canevas.grille[ligne + 2][colonne].getCouleur()//nous vérifions si les cases  ont la même couleur de jeton
                        && canevas.grille[ligne + 2][colonne].getCouleur() == canevas.grille[ligne + 3][colonne].getCouleur()))
                        || ((ligne + 3 < Canevas.LIGNES && colonne + 3 < Canevas.COLONNES // vérification diagonale "descendante"=> vérification a ligne+3 et colonne+3 maximum( à partir des coordonnees l et c)pour ne pas sortir du tableau
                        && canevas.grille[ligne][colonne].getCouleur() != Jeton.Couleur.VIDE && canevas.grille[ligne + 1][colonne + 1].getCouleur() != Jeton.Couleur.VIDE //
                        && canevas.grille[ligne + 2][colonne + 2].getCouleur() != Jeton.Couleur.VIDE && canevas.grille[ligne + 3][colonne + 3].getCouleur() != Jeton.Couleur.VIDE)// vérification de la présence de jetons
                        && (canevas.grille[ligne][colonne].getCouleur() == canevas.grille[ligne + 1][colonne + 1].getCouleur()
                        && canevas.grille[ligne + 1][colonne + 1].getCouleur() == canevas.grille[ligne + 2][colonne + 2].getCouleur()
                        && canevas.grille[ligne + 2][colonne + 2].getCouleur() == canevas.grille[ligne + 3][colonne + 3].getCouleur()))//nous vérifions si les cases  ont la même couleur de jeton
                        || ((ligne + 3 < Canevas.LIGNES && colonne + 3 < Canevas.COLONNES // vérification diagonale "montante"=> vérification a ligne+3 et colonne+3 maximum( à partir des coordonnees l et c)pour ne pas sortir du tableau
                        && canevas.grille[ligne + 3][colonne].getCouleur() != Jeton.Couleur.VIDE && canevas.grille[ligne + 2][colonne + 1].getCouleur() != Jeton.Couleur.VIDE
                        && canevas.grille[ligne + 1][colonne + 2].getCouleur() != Jeton.Couleur.VIDE && canevas.grille[ligne][colonne + 3].getCouleur() != Jeton.Couleur.VIDE)// vérification de la présence de jetons 
                        && (canevas.grille[ligne + 3][colonne].getCouleur() == canevas.grille[ligne + 2][colonne + 1].getCouleur()
                        && canevas.grille[ligne + 2][colonne + 1].getCouleur() == canevas.grille[ligne + 1][colonne + 2].getCouleur() //nous vérifions si les cases  ont la même couleur de jeton
                        && canevas.grille[ligne + 1][colonne + 2].getCouleur() == canevas.grille[ligne][colonne + 3].getCouleur()))) {
                    return true;  // si une de ces 4 conditions est remplie alors on retourne true
                }
            }
        }
        return false;  // sinon false
    }

    public boolean partieEstNulle() {
        for (int ligne = 0; ligne < Canevas.LIGNES; ligne++) {
            for (int colonne = 0; colonne < Canevas.COLONNES; colonne++) {
                if (canevas.grille[ligne][colonne].getCouleur() == Jeton.Couleur.VIDE) {
                    return false; // si une cellule vide trouvée, alors le match n'est pas nul, car la partie n'est pas fini.
                }
            }
        }
        return true;  // aucune cellule vide trouvée: la partie est nulle.
    }
    
    public abstract void start();

}