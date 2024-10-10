package fr.umontpellier.iut.trains.plateau;

/**
 * Classe représentant une tuile plaine, fleuve ou montagne.
 */
public class TuileTerrain extends Tuile {
    /**
     * Type de terrain de la tuile ({@code PLAINE}, {@code FLEUVE} ou {@code MONTAGNE})
     */
    private TypeTerrain type;

    public TuileTerrain(TypeTerrain type) {
        super();
        this.type = type;
    }
    @Override
    public String getType(){
        return type.toString();
    }
}
