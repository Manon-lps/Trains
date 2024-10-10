package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class PassageEnGare extends Carte{
    public PassageEnGare() {
        super("Passage en gare", "Action", 3, 1, "Rouge");
    }


    public void jouer(Joueur joueur) {
        super.jouer(joueur);
        joueur.piocherEtAjouterMain(1);
    }
}
