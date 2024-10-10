package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class Decharge extends Carte {
    public Decharge() {
        super("Décharge", "Action", 2, 0, "Rouge");
    }

    public void acheter(Joueur joueur) {
        super.acheter(joueur);
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);

        boolean fer = true;
        while(fer){
            fer = joueur.FerrailleDeMainAReserve();
        }
    }
}
