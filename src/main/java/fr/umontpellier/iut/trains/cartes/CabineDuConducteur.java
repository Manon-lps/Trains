package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class CabineDuConducteur extends Carte {
    public CabineDuConducteur() {
        super("Cabine du conducteur", "Action", 2, 0, "Rouge");
    }

    public void acheter(Joueur joueur) {
        super.acheter(joueur);
    }

    public void jouer(Joueur joueur) {
        super.jouer(joueur);

        int nbCartes = 0;
        String input = " ";
        while(!input.isEmpty() && !joueur.getMain().isEmpty()) {
            input = joueur.getJeu().lireLigne();

            if (joueur.carteMain().contains(input)) {
                joueur.defausserCarte(input);
                nbCartes++;
            }
        }

        joueur.piocherEtAjouterMain(nbCartes);
    }
}
