package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class Cooperation extends Carte {
    public Cooperation() {
        super("Coopération", "Rail", 5, 0, "Verte");
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);

        joueur.prendreFerraille();
        joueur.ajoutPointsRails(1);
    }
}
