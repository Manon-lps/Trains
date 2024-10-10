package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class TrainMatinal extends Carte {
    public TrainMatinal() {
        super("Train matinal", "Train", 5, 2, "Bleue");
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);
    }
}
