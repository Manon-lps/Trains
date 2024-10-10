package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Bouton;
import fr.umontpellier.iut.trains.Joueur;

import java.util.ArrayList;
import java.util.List;

public class Remorquage extends Carte{
    public Remorquage() {
        super("Remorquage", "Action", 3, 0, "Rouge");
    }

    public void acheter(Joueur joueur) {
        super.acheter(joueur);
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);
        List<String> cartes = new ArrayList<>();
        cartes.addAll(joueur.carteDefausse("Train"));
        List<Bouton> boutons = new ArrayList<>();
        if(cartes.isEmpty()){
            joueur.log("Vous n'avez pas de carte Train dans votre défausse.");
        }else {
            for (String carte : cartes) {
                boutons.add(new Bouton(carte));
            }
            String choix = joueur.choisir("Choisissez une carte Train de votre défausse ", joueur.carteDefausse("Train"), boutons, true);
            joueur.log("Vous avez choisi "+ choix);
            joueur.defausseToMain(choix);
        }


    }
}
