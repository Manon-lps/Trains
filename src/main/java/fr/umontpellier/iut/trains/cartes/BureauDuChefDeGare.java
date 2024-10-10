package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class BureauDuChefDeGare extends Carte {
    public BureauDuChefDeGare() {
        super("Bureau du chef de gare", "Action", 4, 0, "Rouge");
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);
        String choix = joueur.choisir("Choisissez une carte Action de votre main ", joueur.carteMain("Action"), null, true);
        joueur.log(choix);
        joueur.jouerCarte(choix);
    }
}
