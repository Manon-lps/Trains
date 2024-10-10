package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;
import fr.umontpellier.iut.trains.plateau.Tuile;

import java.util.ArrayList;
import java.util.List;

public class UsineDeWagons extends Carte {
    public UsineDeWagons() {
        super("Usine de wagons", "Action", 5, 0, "Rouge");
    }

    public void acheter(Joueur joueur) {
        super.acheter(joueur);
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);
        String choix = joueur.choisir("Choisissez une carte Train à écarter", joueur.carteMain("Train"),null,true);
        joueur.log(choix);
        Carte c =  joueur.getCarteMain(choix);
        joueur.getJeu().ajouterEcarte(c);
        joueur.retirerMain(choix);
        List<ListeDeCartes> carte = new ArrayList<>(joueur.getJeu().getReserve().values());
        List<Carte> reserve = new ArrayList<>();
        for (ListeDeCartes liste : carte) {
            if (!liste.isEmpty()) {
                reserve.add(liste.get(0));
            }
        }
        List<String> options = new ArrayList<>();
        for(int i = 0; i<joueur.getJeu().getReserve().values().size();i++) {
            if (reserve.get(i).getType().equals("Train") && (reserve.get(i).getValeur()<=c.getValeur()+3)) {
                options.add("ACHAT:" +reserve.get(i).getNom());
            }
        }
        String choix2 = joueur.choisir("Choisissez une carte Train dans la reserve",options,null,false);
        if (choix2.startsWith("ACHAT:")) {
            String nomCarteAchat = choix2.substring(6);
            Carte carteAchat = joueur.getJeu().getCarteReserve(nomCarteAchat);
            if (carteAchat != null) {
                joueur.ajouterMain(joueur.getJeu().prendreDansLaReserve(nomCarteAchat));
            }
        }
    }
}
