package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

import java.util.ArrayList;
import java.util.List;

public class AtelierDeMaintenance extends Carte {
    public AtelierDeMaintenance() {
        super("Atelier de maintenance", "Action", 5, 0, "Rouge");
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);
        if(joueur.contient("Train")){
            String choix = joueur.choisir("Choisissez une carte Train de votre main", joueur.carteMain("Train"),null,false);
            joueur.log(choix);
            joueur.ajouterCarteRecue(choix);
        }
    }
}
