package fr.umontpellier.iut.trains.cartes;

import fr.umontpellier.iut.trains.Joueur;

public class CarteRail extends Carte{
    /**
     * Constructeur simple
     * <p>
     * Important : La classe Carte est actuellement très incomplète. Vous devrez
     * ajouter des attributs et des méthodes et donc probablement définir au moins
     * un autre constructeur plus complet. Les sous-classes de Cartes qui vous sont
     * fournies font appel à ce constructeur simple mais au fur et à mesure que vous
     * les compléterez, elles devront utiliser les autres constructeurs de Carte. Si
     * vous n'utilisez plus ce constructeur, vous pouvez le supprimer.
     *
     * @param nom
     * @param type
     * @param cout
     * @param valeur
     * @param couleur
     */
    public CarteRail(String nom, String type, int cout, int valeur, String couleur) {
        super(nom, type, cout, valeur, couleur);
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);
        if (joueur.getCartesEnJeu().stream().anyMatch(carte -> carte.getNom().equals("Ferronnerie"))) {
            joueur.setArgent(joueur.getArgent()+2);
        }
    }
}
