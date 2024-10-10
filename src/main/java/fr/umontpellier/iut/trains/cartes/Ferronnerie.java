package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class Ferronnerie extends Carte{
    public Ferronnerie() {
        super("Ferronnerie", "Action", 4, 1, "Rouge");
    }

    public void acheter(Joueur joueur) {
        super.acheter(joueur);
    }
}
