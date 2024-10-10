package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class Depot extends Carte {
    public Depot() {
        super("Dépôt", "Action", 3, 1, "Rouge");
    }

    public void acheter(Joueur joueur) {
        super.acheter(joueur);
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);
        joueur.piocherEtAjouterMain(2);
        String choix1 = joueur.choisir("Choisir une carte à défausser :", joueur.carteMain(), null, false);
        joueur.log("Vous défaussez : "+ choix1);
        joueur.defausserCarte(choix1);
        String choix2 = joueur.choisir("Choisir une deuxième carte à défausser :", joueur.carteMain(), null, false);
        joueur.log("Vous défaussez : "+ choix2);
        joueur.defausserCarte(choix2);
    }
}
