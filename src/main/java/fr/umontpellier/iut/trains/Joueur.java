package fr.umontpellier.iut.trains;

import java.util.*;

import fr.umontpellier.iut.trains.cartes.*;

public class Joueur {
    /**
     * Le jeu auquel le joueur est rattaché
     */
    private Jeu jeu;
    /**
     * Nom du joueur (pour les affichages console et UI)
     */
    private String nom;
    /**
     * Quantité d'argent que le joueur a (remis à zéro entre les tours)
     */
    private int argent;
    /**
     * Nombre de points rails dont le joueur dispose. Ces points sont obtenus en
     * jouant les cartes RAIL (vertes) et remis à zéro entre les tous
     */
    private int pointsRails;
    /**
     * Nombre de jetons rails disponibles (non placés sur le plateau)
     */
    private int nbJetonsRails;
    /**
     * Score du joueur
     */
    private int scoreTotal;
    /**
     * Liste des cartes en main
     */
    private ListeDeCartes main;
    /**
     * Liste des cartes dans la pioche (le début de la liste correspond au haut de
     * la pile)
     */
    private ListeDeCartes pioche;
    /**
     * Liste de cartes dans la défausse
     */
    private ListeDeCartes defausse;
    /**
     * Liste des cartes en jeu (cartes jouées par le joueur pendant le tour)
     */
    private ListeDeCartes cartesEnJeu;
    /**
     * Liste des cartes reçues pendant le tour
     */
    private ListeDeCartes cartesRecues;
    /**
     * Couleur du joueur (utilisé par l'interface graphique)
     */
    private CouleurJoueur couleur;

    public Joueur(Jeu jeu, String nom, CouleurJoueur couleur) {
        this.jeu = jeu;
        this.nom = nom;
        this.couleur = couleur;
        argent = 0;
        pointsRails = 0;
        nbJetonsRails = 20;
        main = new ListeDeCartes();
        defausse = new ListeDeCartes();
        pioche = new ListeDeCartes();
        cartesEnJeu = new ListeDeCartes();
        cartesRecues = new ListeDeCartes();

        // créer 7 Train omnibus (non disponibles dans la réserve)
        pioche.addAll(FabriqueListeDeCartes.creerListeDeCartes("Train omnibus", 7));
        // prendre 2 Pose de rails de la réserve
        for (int i = 0; i < 2; i++) {
            pioche.add(jeu.prendreDansLaReserve("Pose de rails"));
        }
        // prendre 1 Gare de la réserve
        pioche.add(jeu.prendreDansLaReserve("Gare"));

        // mélanger la pioche
        pioche.melanger();
        // Piocher 5 cartes en main
        // Remarque : on peut aussi appeler piocherEnMain(5) si la méthode est écrite
        for (int i = 0; i < 5; i++) {
            main.add(pioche.remove(0));
        }

    }

    public ListeDeCartes getCartesEnJeu() {
        return cartesEnJeu;
    }

    public ListeDeCartes getDefausse() {
        return defausse;
    }

    public int getNbJetonsRails() {
        return nbJetonsRails;
    }
    public void perdNbJetonsRails(int nb) {nbJetonsRails -= 1; }

    public int getArgent() {
        return argent;
    }

    public void setArgent(int argent) {
        this.argent = argent;
    }

    public void gagnerArgent(int n){
        this.argent += n;
    }

    public void gagnerPV(Carte carte){
        this.scoreTotal+= carte.getPV();
    }
    public void ajouterMain(List<Carte> cartes){
        this.main.addAll(cartes);
    }
    public void ajouterMain(Carte carte){main.add(carte);}

    public void piocherEtAjouterMain(int n) {
        ajouterMain(piocher(n));
    }
    public void defausserCarte(String nomCarte){
        defausse.add(main.getCarte(nomCarte));
        main.retirer(nomCarte);
    }
    public void defausserCartePioche(List<Carte> cartes){
        defausse.addAll(cartes);
        pioche.removeAll(cartes);
    }

    public void defausserCartePioche(int i){
        defausse.add(pioche.get(i));
        pioche.remove(i);
    }

    public String getNom() {
        return nom;
    }

    public CouleurJoueur getCouleur() {
        return couleur;
    }

    public List<Carte> devoilerCarte(ListeDeCartes cartes, int nbCartes){
        List<Carte> listCartes = new ArrayList<>();
        if(!cartes.isEmpty()) {
            for (int i = 0; i < nbCartes; i++) {
                log(cartes.get(i).getNom());
                listCartes.add(cartes.get(i));
            }
        }
        return listCartes;
    }

    /**
     * Renvoie le score total du joueur
     * <p>
     * Le score total est la somme des points obtenus par les effets suivants :
     * <ul>
     * <li>points de rails (villes et lieux éloignés sur lesquels le joueur a posé
     * un rail)
     * <li>points des cartes possédées par le joueur (cartes VICTOIRE jaunes)
     * <li>score courant du joueur (points marqués en jouant des cartes pendant la
     * partie p.ex. Train de tourisme)
     * </ul>
     * 
     * @return le score total du joueur
     */
    public int getScoreTotal() {
        // À FAIRE
        return scoreTotal;
    }

    public void setScoreTotal(int scoreTotal) {
        this.scoreTotal = scoreTotal;
    }

    public void ajoutPointsRails(int pointsRails) { this.pointsRails += pointsRails; }

    /**
     * Retire et renvoie la première carte de la pioche.
     * <p>
     * Si la pioche est vide, la méthode commence par mélanger toute la défausse
     * dans la pioche.
     *
     * @return la carte piochée ou {@code null} si aucune carte disponible
     */
    public Carte piocher() {
        Carte carte;
        if(!(pioche.isEmpty())) {
            return pioche.remove(0);
        }
        if (pioche.isEmpty() && (!defausse.isEmpty())) {
            defausseToPioche();
            carte = pioche.remove(0);
        } else {
            carte = null;
        }
        return carte;
    }

    public void defausseToPioche(){
        defausse.melanger();
        pioche.addAll(defausse);
        defausse.clear();
    }

    public void defausseToMain(String nomCarte){
       Carte carte = defausse.retirer(nomCarte);
        main.add(carte);
    }

    public void ajouterMainReserve(String nomCarte){
        Carte c = main.getCarte(nomCarte);
        if(c!=null) {
            jeu.ajouterReserve(nomCarte,c);
            main.remove(c);
        }
    }

    public void retirerMain(String nomCarte){
        main.retirer(nomCarte);
    }
    public void retirerPioche(List<Carte> cartes){
        pioche.removeAll(cartes);
    }

    public Carte getCarteMain(String nomCarte){
        return main.getCarte(nomCarte);
    }

    public void ajouterCarteRecue(String nomCarte){
        Carte carte = jeu.prendreDansLaReserve(nomCarte);
        log("Reçoit " + carte); // affichage dans le log
        cartesRecues.add(carte);
    }

    /**
     * Retire et renvoie les {@code n} premières cartes de la pioche.
     * <p>
     * Si à un moment il faut encore piocher des cartes et que la pioche est vide,
     * la défausse est mélangée et toutes ses cartes sont déplacées dans la pioche.
     * S'il n'y a plus de cartes à piocher la méthode s'interromp et les cartes qui
     * ont pu être piochées sont renvoyées.
     * 
     * @param n nombre de cartes à piocher
     * @return une liste des cartes piochées (la liste peut contenir moins de n
     *         éléments si pas assez de cartes disponibles dans la pioche et la
     *         défausse)
     */
    public List<Carte> piocher(int n) {
        //Je sais pas si ça peut fonctionner comme ça, peut-être faire une méthode booléenne peut piocher ?
        List<Carte> cartes = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Carte cartePiochee = this.piocher();
            if(cartePiochee == null) {
                return cartes;
            }
            cartes.add(cartePiochee);
            log("Vous piochez " + cartes.get(i));
        }
        return cartes;
    }

    public void piocher(String nomCarte){
        List<Carte> cartes = new ArrayList<>();
        cartes.add(pioche.getCarte(nomCarte));
        ajouterMain(cartes);
        pioche.retirer(nomCarte);
    }
    public void jouerCarte(String nomCarte){
        main.getCarte(nomCarte).jouer(this);
    }

    public void prendreFerraille(){
        cartesRecues.add(jeu.prendreDansLaReserve("Ferraille"));
        log("Reçoit Ferraille");
    }

    public Jeu getJeu() {
        return jeu;
    }

    public Collection<String> getMain() {
        return Collections.singleton(main.toString());
    }

    public ListeDeCartes getPioche() {
        return pioche;
    }

    /**
     * Joue un tour complet du joueur
     * <p>
     * Le tour du joueur se déroule en plusieurs étapes :
     * <ol>
     * <li>Initialisation
     * <p>
     * Dans ce jeu il n'y a rien de particulier à faire en début de tour à part un
     * éventuel affichage dans le log.
     * 
     * <li>Boucle principale
     * <p>
     * C'est le cœur de la fonction. Tant que le tour du joueur n'est pas terminé,
     * il faut préparer la liste de tous les choix valides que le joueur peut faire
     * (jouer des cartes, poser des rails, acheter des cartes, etc.), puis demander
     * au joueur de choisir une action (en appelant la méthode {@code choisir}).
     * <p>
     * Ensuite, en fonction du choix du joueur il faut exécuter l'action demandée et
     * recommencer la boucle si le tour n'est pas terminé.
     * <p>
     * Le tour se termine lorsque le joueur décide de passer (il choisit {@code ""})
     * ou lorsqu'il exécute une action qui termine automatiquement le tour (par
     * exemple s'il choisit de recycler toutes ses cartes Ferraille en début de
     * tour)
     * 
     * <li>Finalisation
     * <p>
     * Actions à exécuter à la fin du tour : réinitialiser les attributs
     * du joueur qui sont spécifiques au tour (argent, rails, etc.), défausser
     * toutes les
     * cartes, piocher 5 nouvelles cartes en main, etc.
     * </ol>
     */
    public void jouerTour() {
        // Initialisation
        jeu.log("<div class=\"tour\">Tour de " + toLog() + "</div>");

        boolean finTour = false;
        // Boucle principale
        while (!finTour) {
            List<String> choixPossibles = new ArrayList<>();
            for (Carte c : main) {
                if(c!=null) {
                    // ajoute les noms de toutes les cartes en main
                    choixPossibles.add(c.getNom());
                }
            }
            for (String nomCarte : jeu.getReserve().keySet()) {
                // ajoute les noms des cartes dans la réserve préfixés de "ACHAT:"
                choixPossibles.add("ACHAT:" + nomCarte);
            }
            for (int i = 0; i <= 75; i++) {
                // ajoute les indexes des tuiles préfixés de "TUILE:"
                choixPossibles.add("TUILE:" + i);
            }

            // Choix de l'action à réaliser
            String choix = choisir(String.format("Tour de %s", this.nom), choixPossibles, null, true);

            if (choix.startsWith("ACHAT:")) {
                String nomCarte = choix.split(":")[1];
                // prendre une carte dans la réserve
                Carte carte = jeu.getCarteReserve(nomCarte);
                if(!contient(cartesEnJeu, "Train matinal")) {
                    if (carte != null && argent >= carte.getCout() && (!carte.getType().equals("Ferraille"))) {
                        jeu.prendreDansLaReserve(nomCarte);
                        log("Reçoit " + carte); // affichage dans le log
                        cartesRecues.add(carte);
                        gagnerPV(carte);
                        carte.acheter(this);
                    }
                }else {
                    List<String> options = new ArrayList<>();
                    options.add("oui"); options.add("non");

                    List<Bouton> boutons = new ArrayList<>();
                    boutons.add(new Bouton("oui")); boutons.add(new Bouton("non"));
                    String choix1 =choisir("Voulez-vous mettre la caret que vous venez d'acheter dans votre pioche ?",options,boutons,false);
                    if(choix1.equals("oui") && carte != null && argent >= carte.getCout() && (!carte.getType().equals("Ferraille"))){
                        jeu.prendreDansLaReserve(nomCarte);
                        log("Reçoit " + carte); // affichage dans le log
                        pioche.add(0,carte);
                        gagnerPV(carte);
                        carte.acheter(this);
                    }
                    if(choix1.equals("non") && carte != null && argent >= carte.getCout() && (!carte.getType().equals("Ferraille"))){
                        jeu.prendreDansLaReserve(nomCarte);
                        log("Reçoit " + carte); // affichage dans le log
                        cartesRecues.add(carte);
                        gagnerPV(carte);
                        carte.acheter(this);
                    }
                }

            } else if (choix.startsWith("TUILE:")) {
                String tuile = choix.split(":")[1];
                int index = Integer.parseInt(tuile);
                if (nbJetonsRails > 0) {
                    if (jeu.ajouterJoueurSurTuile(this, index)) {
                        log("Vous avez poser un rail sur la tuile: " + index);
                        nbJetonsRails--;
                    }
                }
            } else if (choix.equals("")) {
                // terminer le tour
                if(main.contains(main.getCarte("Ferraille"))) {
                    while (main.contains(main.getCarte("Ferraille"))) {
                        jeu.ajouterReserve("Ferraille", new Ferraille());
                        main.retirer("Ferraille");
                    }
                }
                finTour = true;
            } else if (!choix.equals("Ferraille")){
                // jouer une carte de la main
                Carte carte = main.retirer(choix);
                log("Joue " + carte); // affichage dans le log
                cartesEnJeu.add(carte); // mettre la carte en jeu
                carte.jouer(this); // exécuter l'action de la carte
            } else {
                log("Vous ne pouvez pas jouer cette carte.");
            }
        }
        // Finalisation
        // défausser toutes les cartes
        defausse.addAll(main);
        main.clear();
        defausse.addAll(cartesRecues);
        cartesRecues.clear();
        defausse.addAll(cartesEnJeu);
        cartesEnJeu.clear();
        argent=0;

        piocherEtAjouterMain(5); // piocher 5 cartes en main
    }
    public List<String> carteJeu(String type){
        List<String> cartes = new ArrayList<>();
        for(Carte c : cartesEnJeu) {
            if (c.getType().equals(type)) {
                cartes.add(c.getNom());
            }
        }
        return cartes;
    }

    public List<String> carteJeu(){
        List<String> cartes = new ArrayList<>();
        for(Carte c : cartesEnJeu) {
            cartes.add(c.getNom());
        }
        return cartes;
    }

    public List<String> cartePioche(){
        List<String> cartes = new ArrayList<>();
        for(Carte c : pioche) {
            cartes.add(c.getNom());
        }
        return cartes;
    }

    public List<String> carteDefausse(String type){
        List<String> cartes = new ArrayList<>();
        for(Carte c : defausse){
            if(c.getType().equals(type)){
                cartes.add(c.getNom());
            }
        }
        return cartes;
    }
    public List<String> carteMain(String type){
        List<String> cartes = new ArrayList<>();
        for(Carte c : main) {
            if (c.getType().equals(type)) {
                cartes.add(c.getNom());
            }
        }
        return cartes;
    }

    public List<String> carteMain(){
        List<String> cartes = new ArrayList<>();
        for(Carte c : main) {
            cartes.add(c.getNom());
        }
        return cartes;
    }


    public boolean contient(String type) {
        for (Carte c : main) {
            return c.getType().equals(type);
        }
        return false;
    }

    public boolean contient(ListeDeCartes liste, String nom) {
        for (Carte c : liste) {
            return c.getNom().equals(nom);
        }
        return false;
    }

    public boolean FerrailleDeMainAReserve() {
        if (main.contains(main.getCarte("Ferraille"))) {
            jeu.ajouterReserve("Ferraille", main.retirer("Ferraille"));
            return true;
        }
        return false;
    }

    public void ecarteCarteHoraireEstivaux(String choix) {
        if (choix.equals("oui")) {
            Carte carte = cartesEnJeu.retirer("Horaires estivaux");
            jeu.ajouterEcarte(carte);
            gagnerArgent(3);
            log("Vous avez ecartée la carte et gagné 3 argent");
        }
        else {
            log("Vous n'avez rien fait");
        }
    }



    /**
     * Attend une entrée de la part du joueur (au clavier ou sur la websocket) et
     * renvoie le choix du joueur.
     * <p>
     * Cette méthode lit les entrées du jeu ({@code Jeu.lireligne()}) jusqu'à ce
     * qu'un choix valide (un élément de {@code choix} ou la valeur d'un élément de
     * {@code boutons} ou éventuellement la chaîne vide si l'utilisateur est
     * autorisé à passer) soit reçu.
     * Lorsqu'un choix valide est obtenu, il est renvoyé par la fonction.
     * <p>
     * Exemple d'utilisation pour demander à un joueur de répondre à une question
     * par "oui" ou "non" :
     * <p>
     * 
     * <pre>{@code
     * List<String> choix = Arrays.asList("oui", "non");
     * String input = choisir("Voulez-vous faire ceci ?", choix, null, false);
     * }</pre>
     * <p>
     * Si par contre on voulait proposer les réponses à l'aide de boutons, on
     * pourrait utiliser :
     * 
     * <pre>{@code
     * List<String> boutons = Arrays.asList(new Bouton("Oui !", "oui"), new Bouton("Non !", "non"));
     * String input = choisir("Voulez-vous faire ceci ?", null, boutons, false);
     * }</pre>
     * 
     * (ici le premier bouton a le label "Oui !" et envoie la String "oui" s'il est
     * cliqué, le second a le label "Non !" et envoie la String "non" lorsqu'il est
     * cliqué)
     *
     * <p>
     * <b>Remarque :</b> Normalement, si le paramètre {@code peutPasser} est
     * {@code false} le choix
     * {@code ""} n'est pas valide. Cependant s'il n'y a aucun choix proposé (les
     * listes {@code choix} et {@code boutons} sont vides ou {@code null}), le choix
     * {@code ""} est accepté pour éviter un blocage.
     * 
     * @param instruction message à afficher à l'écran pour indiquer au joueur la
     *                    nature du choix qui est attendu
     * @param choix       une collection de chaînes de caractères correspondant aux
     *                    choix valides attendus du joueur (ou {@code null})
     * @param boutons     une liste d'objets de type {@code Bouton} définis par deux
     *                    chaînes de caractères (label, valeur) correspondant aux
     *                    choix valides attendus du joueur qui doivent être
     *                    représentés par des boutons sur l'interface graphique (le
     *                    label est affiché sur le bouton, la valeur est ce qui est
     *                    envoyé au jeu quand le bouton est cliqué) ou {@code null}
     * @param peutPasser  booléen indiquant si le joueur a le droit de passer sans
     *                    faire de choix. S'il est autorisé à passer, c'est la
     *                    chaîne de caractères vide ({@code ""}) qui signifie qu'il
     *                    désire passer.
     * @return le choix de l'utilisateur (un élement de {@code choix}, ou la valeur
     *         d'un élément de {@code boutons} ou la chaîne vide)
     */
    public String choisir(
            String instruction,
            Collection<String> choix,
            List<Bouton> boutons,
            boolean peutPasser) {
        if (choix == null)
            choix = new ArrayList<>();
        if (boutons == null)
            boutons = new ArrayList<>();

        HashSet<String> choixDistincts = new HashSet<>(choix);
        choixDistincts.addAll(boutons.stream().map(Bouton::valeur).toList());
        if (peutPasser || choixDistincts.isEmpty()) {
            // si le joueur a le droit de passer ou s'il n'existe aucun choix valide, on
            // ajoute "" à la liste des choix possibles
            choixDistincts.add("");
        }

        String entree;
        // Lit l'entrée de l'utilisateur jusqu'à obtenir un choix valide
        while (true) {
            jeu.prompt(instruction, boutons, peutPasser);
            entree = jeu.lireLigne();
            // si une réponse valide est obtenue, elle est renvoyée
            if (choixDistincts.contains(entree)) {
                return entree;
            }
        }
    }

    /**
     * Ajoute un message dans le log du jeu
     * 
     * @param message message à ajouter dans le log
     */
    public void log(String message) {
        jeu.log(message);
    }

    @Override
    public String toString() {
        // Vous pouvez modifier cette fonction comme bon vous semble pour afficher
        // d'autres informations si nécessaire
        StringJoiner joiner = new StringJoiner("\n");
        joiner.add(String.format("=== %s (%d pts) ===", nom, getScoreTotal()));
        joiner.add(String.format("  Argent: %d  Rails: %d", argent, pointsRails));
        joiner.add("  Cartes en jeu: " + cartesEnJeu);
        joiner.add("  Cartes reçues: " + cartesRecues);
        joiner.add("  Cartes en main: " + main);
        return joiner.toString();
    }

    /**
     * @return une représentation du joueur pour l'affichage dans le log du jeu
     */
    public String toLog() {
        return String.format("<span class=\"joueur %s\">%s</span>", couleur.toString(), nom);
    }

    /**
     * @return une représentation du joueur sous la forme d'un dictionnaire de
     *         valeurs sérialisables (qui sera converti en JSON pour l'envoyer à
     *         l'interface graphique)
     */
    Map<String, Object> dataMap() {
        return Map.ofEntries(
                Map.entry("nom", nom),
                Map.entry("couleur", couleur),
                Map.entry("scoreTotal", getScoreTotal()),
                Map.entry("argent", argent),
                Map.entry("rails", pointsRails),
                Map.entry("nbJetonsRails", nbJetonsRails),
                Map.entry("main", main.dataMap()),
                Map.entry("defausse", defausse.dataMap()),
                Map.entry("cartesEnJeu", cartesEnJeu.dataMap()),
                Map.entry("cartesRecues", cartesRecues.dataMap()),
                Map.entry("pioche", pioche.dataMap()),
                Map.entry("actif", jeu.getJoueurCourant() == this));
    }
}
