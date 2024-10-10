package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;
import fr.umontpellier.iut.trains.plateau.Tuile;

import java.util.ArrayList;
import java.util.List;

public class Gare extends Carte {
    public Gare() {
        super("Gare", "Gare", 3, 0, "Violette");
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);
        if (joueur.getJeu().getNbJetonsGare() != 0) {
            List<String> tuileVille = new ArrayList<>();
            for (int i = 0; i < joueur.getJeu().getTuiles().size(); i++) {
                Tuile tuiles = joueur.getJeu().getTuile(i);
                if (tuiles.getType().equals("ville") && tuiles.getNbGares() < tuiles.getNbGaresMax()) {
                    tuileVille.add("TUILE:" + i);
                }
            }

            String choix = joueur.choisir("Choisissez la tuile sur laquelle vous voulez poser votre gare", tuileVille, null, true);
            int numeroTuile = Integer.parseInt(choix.substring(6));
            Tuile tuileChoisie = joueur.getJeu().getTuile(numeroTuile);
            tuileChoisie.poserGare(joueur);
        }

        if(!joueur.getCartesEnJeu().stream().anyMatch(carte -> carte.getNom().equals("Dépotoir"))) {
            joueur.prendreFerraille();
        }
    }
}
