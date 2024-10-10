package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class TrainDirect extends Carte {
    public TrainDirect() {
        super("Train direct", "Train", 6, 3, "Bleue");
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);
    }
}
