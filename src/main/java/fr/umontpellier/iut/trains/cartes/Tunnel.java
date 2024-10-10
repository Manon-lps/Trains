package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class Tunnel extends Carte {
    public Tunnel() {
        super("Tunnel", "Rail", 5, 0, "Verte");
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);

        joueur.prendreFerraille();
        joueur.ajoutPointsRails(1);
    }
}
