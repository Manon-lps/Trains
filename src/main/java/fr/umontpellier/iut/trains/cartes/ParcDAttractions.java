package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParcDAttractions extends Carte{
    public ParcDAttractions() {
        super("Parc d'attractions", "Action", 4, 1, "Rouge");
    }

    public void acheter(Joueur joueur) {
        super.acheter(joueur);
    }

    public void jouer(Joueur joueur) {
        super.jouer(joueur);
        String choix = joueur.choisir("Choisissez une carte Train dans la zone de jeu et gagner sa valeur en argent", joueur.carteJeu("Train"),null,false);
        joueur.log("Vous avez choisi "+ choix + "vous gagner " + joueur.getCartesEnJeu().getCarte(choix).getValeur()+ " en plus");
        joueur.setArgent(joueur.getArgent() + joueur.getCartesEnJeu().getCarte(choix).getValeur());
    }
}
