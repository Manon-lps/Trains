package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Bouton;
import fr.umontpellier.iut.trains.Joueur;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class CentreDeRenseignements extends Carte {
    public CentreDeRenseignements() {
        super("Centre de renseignements", "Action", 4, 1, "Rouge");
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);
        joueur.log("Les 4 premières carte de votre pioche sont :");
        List<String> cartes = new ArrayList<>();
        List<Carte> listCarte = new ArrayList<>();
        if(joueur.getPioche().size()>=4) {
            joueur.devoilerCarte(joueur.getPioche(), 4);
            for (int i = 0; i < 4; i++) {
                cartes.add(joueur.getPioche().get(i).getNom());
                listCarte.add(joueur.getPioche().get(i));
            }
        }else{
            joueur.devoilerCarte(joueur.getPioche(), joueur.getPioche().size());
            for (int i = 0; i < joueur.getPioche().size(); i++) {
                cartes.add(joueur.getPioche().get(i).getNom());
                listCarte.add(joueur.getPioche().get(i));
            }
        }
        joueur.getPioche().removeAll(listCarte);
        List<Bouton> boutons = new ArrayList<>();
        for (String carte : cartes) {
            boutons.add(new Bouton(carte));
        }
        String choix = joueur.choisir("Choisissez la carte que vous souhaitez garder ", cartes, boutons , true);
        joueur.log("Vous avez choisi : "+choix);
        for(int i = 0; i< listCarte.size(); i++){
            if(listCarte.get(i).getNom().equals(choix)){
                joueur.ajouterMain(listCarte.get(i));
                listCarte.remove(i);
                break;
            }
        }
        int indexOption1 = cartes.indexOf(choix);
        if (indexOption1 != -1) {
            boutons.remove(indexOption1);
        }
        cartes.remove(choix);

        while(!cartes.isEmpty()) {
            String option1 = joueur.choisir("Remettez les cartes dans l'ordre que vous voulez", cartes, boutons, true);
            for (Carte carte : listCarte) {
                if (carte.getNom().equals(option1)) {
                    joueur.getPioche().add(0,carte);
                    break;
                }
            }
            int indexOption2 = cartes.indexOf(option1);
            if (indexOption2 != -1) {
                boutons.remove(indexOption2);
            }
            cartes.remove(option1);
        }


    }
}
