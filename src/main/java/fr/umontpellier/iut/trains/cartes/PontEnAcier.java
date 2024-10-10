package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class PontEnAcier extends Carte {
    public PontEnAcier() {
        super("Pont en acier", "Rail", 4, 0, "Verte");
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);

        joueur.prendreFerraille();
        joueur.ajoutPointsRails(1);
    }
}
