package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

import java.util.ArrayList;
import java.util.List;

public class HorairesTemporaires extends Carte {
    public HorairesTemporaires() {
        super("Horaires temporaires", "Action", 5, 0, "Rouge");
    }

    public void acheter(Joueur joueur) {
        super.acheter(joueur);
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);

        ListeDeCartes defPioche = new ListeDeCartes();
        defPioche.addAll(joueur.getPioche());
        defPioche.addAll(joueur.getDefausse());

        ListeDeCartes cartesTrain = new ListeDeCartes();
        ListeDeCartes cartesNonTrain = new ListeDeCartes();

        for (Carte carte : defPioche) {
            if (carte.getType().equals("Train")) {
                cartesTrain.add(carte);
                if (cartesTrain.size() >= 2) {
                    break;
                }
            } else {
                cartesNonTrain.add(carte);
            }
        }

        if (cartesTrain.size() < 2) {
            joueur.ajouterMain(cartesTrain);
            joueur.defausserCartePioche(cartesNonTrain);
            return;
        }

        List<Carte> cartesAPrendre = new ArrayList<>(cartesTrain.subList(0, 2));
        joueur.ajouterMain(cartesAPrendre);
        joueur.retirerPioche(cartesAPrendre);
        joueur.defausserCartePioche(cartesNonTrain);
    }
}
