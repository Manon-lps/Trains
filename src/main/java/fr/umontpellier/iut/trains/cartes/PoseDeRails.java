package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

import java.util.ArrayList;
import java.util.List;

public class PoseDeRails extends Carte {
    public PoseDeRails() {
        super("Pose de rails", "Rail", 3, 0, "Verte");
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);

        joueur.prendreFerraille();
        joueur.ajoutPointsRails(1);
    }
}
