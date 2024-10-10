package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class PersonnelDeGare extends Carte{
    public PersonnelDeGare() {
        super("Personnel de gare", "Action", 2, 0, "Rouge");
    }

    public void acheter(Joueur joueur) {
        super.acheter(joueur);
    }

    public void jouer(Joueur joueur) {
        super.jouer(joueur);

        boolean choix = false;
        joueur.log("Choisissez entre 'piocher','argent' ou 'ferraille'");

        //si le choix est "piocher"
        while (!choix) {
            String input = joueur.getJeu().lireLigne();
            if (input.equals("piocher")) {
                joueur.piocherEtAjouterMain(1);
                choix = true;
            }
            //si le choix est "argent"
            else if (input.equals("argent")) {
                    joueur.setArgent(joueur.getArgent() + 1);
                    choix = true;
                }

                //si le choix est "ferraille" est qu'on a une carte ferraille en main
            else if (input.equals("ferraille")) {
                boolean fer = joueur.FerrailleDeMainAReserve();
                if (fer) {
                    choix = true;
                } else {
                    joueur.log("Vous n'avez pas de carte ferraille");
                }
            }
            else {
                joueur.log("option non valide");
            }
        }
    }
}
