package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Bouton;
import fr.umontpellier.iut.trains.Joueur;

import java.util.Arrays;
import java.util.List;

public class FeuDeSignalisation extends Carte {
    public FeuDeSignalisation() {
        super("Feu de signalisation", "Action", 2, 0, "Rouge");
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);
        joueur.piocherEtAjouterMain(1);
        joueur.log("La carte au dessus de votre deck est :");
        joueur.devoilerCarte(joueur.getPioche(), 1);
        List<Bouton> boutons = Arrays.asList(new Bouton("oui"), new Bouton("non"));
        String choix = joueur.choisir("Voulez vous la défausser ?", joueur.cartePioche(),boutons,false);
        if(choix.equals("oui")){
            joueur.defausserCartePioche(0);
        }
    }
}
