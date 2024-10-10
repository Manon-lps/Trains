package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class SalleDeControle extends Carte {
    public SalleDeControle() {
        super("Salle de contrôle", "Action", 7, 0, "Rouge");
    }


    public void jouer(Joueur joueur){
        super.jouer(joueur);
        joueur.piocherEtAjouterMain(3);
    }
}
