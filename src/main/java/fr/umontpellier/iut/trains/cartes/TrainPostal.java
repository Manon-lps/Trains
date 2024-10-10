package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Bouton;
import fr.umontpellier.iut.trains.Joueur;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TrainPostal extends Carte {
    public TrainPostal() {
        super("Train postal", "Train", 4, 1, "Bleue");
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);
        List<Bouton> boutons = new ArrayList<>();
        boutons.add(new Bouton("Fin", ""));
        while(!joueur.getMain().isEmpty()) {
            String choix = joueur.choisir("Choisissez les cartes que vous voulez défausser, vous gagnerez un or pour chaque carte défaussée.", joueur.carteMain(), boutons, true);
            if (!choix.equals("")) {
                joueur.defausserCarte(choix);
                joueur.setArgent(joueur.getArgent()+1);
            }else{
                break;
            }
        }
    }
}
