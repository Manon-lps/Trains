package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class TGV extends Carte {
    public TGV() {
        super("TGV", "Train", 2, 1, "Bleue");
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);
        if (joueur.carteJeu().contains("Train omnibus")) {
            joueur.setArgent(joueur.getArgent() + 1);
        }
    }
}
