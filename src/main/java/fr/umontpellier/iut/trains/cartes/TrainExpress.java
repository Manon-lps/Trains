package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class TrainExpress extends Carte {
    public TrainExpress() {
        super("Train express", "Train", 3, 2, "Bleue");
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);
    }
}
