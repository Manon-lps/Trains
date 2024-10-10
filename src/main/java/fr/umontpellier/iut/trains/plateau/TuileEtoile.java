package fr.umontpellier.iut.trains.plateau;

/**
 * Classe représentant une tuile étoile (lieu éloigné)
 */
public class TuileEtoile extends Tuile {
    /**
     * Valeur du lieu éloigné (valeur indiquée sur le plateau)
     */
    private int valeur;

    public TuileEtoile(int valeur) {
        super();
        this.valeur = valeur;
    }
    @Override
    public String getType(){
        return "etoile";
    }

    @Override
    public int getValeur() { return valeur; }
}
