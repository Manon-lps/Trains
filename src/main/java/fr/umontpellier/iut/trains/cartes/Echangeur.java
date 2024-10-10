package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class Echangeur extends Carte {
    public Echangeur() {
        super("Échangeur", "Action", 3, 1, "Rouge");
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);
        String choix = joueur.choisir("Choisissez une carte Train dans la zone de jeu à remettre dans votre deck", joueur.carteJeu("Train"),null,false);
        joueur.log("Vous avez choisi "+ choix);
        joueur.getPioche().add(0,joueur.getCartesEnJeu().getCarte(choix));
        joueur.getCartesEnJeu().retirer(choix);
    }
}
