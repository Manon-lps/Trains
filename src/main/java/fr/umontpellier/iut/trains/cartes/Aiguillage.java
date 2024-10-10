package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class Aiguillage extends Carte{
    public Aiguillage() {
        super("Aiguillage", "Action", 5, 0, "Rouge");
    }


    public void jouer(Joueur joueur){
        super.jouer(joueur);
        joueur.piocherEtAjouterMain(2);
    }

}
