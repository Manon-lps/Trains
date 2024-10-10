package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

import java.util.ArrayList;
import java.util.List;

public class CentreDeControle extends Carte{
    public CentreDeControle() {
        super("Centre de contrôle", "Action", 3, 0, "Rouge");
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);
        joueur.piocherEtAjouterMain(1);
        if(joueur.getPioche().isEmpty()) {
            joueur.defausseToPioche();
            Choix(joueur);
        }else {
            Choix(joueur);
        }

    }

    private void Choix(Joueur joueur) {
        String choix = joueur.choisir("Choisir la carte que vous pensez être au dessus de votre deck", joueur.getJeu().getListeNomsCartes(), null, true);
        Carte carteDeck = joueur.piocher();
        joueur.log("La carte au dessus du deck est : " + carteDeck);

        if (choix.equals(carteDeck.getNom())) {
            List<Carte> carteAajouter = new ArrayList<>();
            carteAajouter.add(carteDeck);
            joueur.ajouterMain(carteAajouter);
            joueur.log("Vous piochez " + choix);
        } else {
            joueur.log("Vous avez choisi " + choix + " ce n'était pas la bonne carte.");
            joueur.getPioche().add(0,carteDeck);
        }
    }
}
