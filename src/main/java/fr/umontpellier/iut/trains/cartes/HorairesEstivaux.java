package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Bouton;
import fr.umontpellier.iut.trains.Joueur;

import java.util.ArrayList;
import java.util.List;

public class HorairesEstivaux extends Carte{
    public HorairesEstivaux() {
        super("Horaires estivaux", "Action", 3, 0, "Rouge");
    }

    public void acheter(Joueur joueur) {
        super.acheter(joueur);
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);

        List<String> options = new ArrayList<>();
        options.add("oui"); options.add("non");

        List<Bouton> boutons = new ArrayList<>();
        boutons.add(new Bouton("oui")); boutons.add(new Bouton("non"));
        String choix = joueur.choisir("Voulez-vous écarter cette carte et recevoir 3 argent?", options, boutons, false);

        joueur.ecarteCarteHoraireEstivaux(choix);
    }
}
