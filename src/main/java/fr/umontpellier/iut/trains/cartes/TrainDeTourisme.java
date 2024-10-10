package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class TrainDeTourisme extends Carte {
    public TrainDeTourisme() {
        super("Train de tourisme", "Train", 4, 1, "Bleue");
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);
        joueur.setScoreTotal(joueur.getScoreTotal() + 1);
    }
}
