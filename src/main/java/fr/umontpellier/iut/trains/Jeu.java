package fr.umontpellier.iut.trains;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import fr.umontpellier.iut.trains.cartes.Carte;
import fr.umontpellier.iut.trains.cartes.FabriqueListeDeCartes;
import fr.umontpellier.iut.trains.cartes.Ferraille;
import fr.umontpellier.iut.trains.cartes.ListeDeCartes;
import fr.umontpellier.iut.trains.plateau.Plateau;
import fr.umontpellier.iut.trains.plateau.Tuile;

public class Jeu implements Runnable {
    /**
     * Liste des joueurs
     */
    private ArrayList<Joueur> joueurs;
    /**
     * Joueur dont c'est le tour
     */
    private Joueur joueurCourant;
    /**
     * Dictionnaire des piles de réserve.
     * <p>
     * Associe à un nom de carte la liste des cartes correspondantes disponibles
     * dans la réserve
     */
    private Map<String, ListeDeCartes> reserve;
    /**
     * Liste des cartes écartées par les joueurs au cours de la partie
     */
    private ListeDeCartes cartesEcartees;
    /**
     * Nom de la ville du plateau (pour afficher le plateau dans l'interface
     * graphique)
     */
    private String nomVille;
    /**
     * Tuiles du plateau de jeu (indexées dans l'ordre de lecture)
     */
    private List<Tuile> tuiles;
    /**
     * Nombre de jetons Gare restant (non placés sur les tuiles)
     */
    private int nbJetonsGare;
    /**
     * Scanner pour lire les entrées clavier
     * <p>
     * ATTENTION: cet attribut ne doit pas être utilisé directement. Il faut
     * toujours utiliser la méthode {@code lireLigne} (ou les méthodes qui
     * l'appellent telles que {@code Joueur.choisir}) pour lire les instructions des
     * joueurs afin que l'interface graphique et les tests puissent fonctionner
     * correctement.
     */
    private Scanner scanner;
    /**
     * Messages d'information du jeu (affichés dans l'interface graphique)
     */
    private final List<String> log;
    /**
     * Instruction affichée au joueur courant
     */
    private String instruction;
    /**
     * Liste des boutons à afficher dans l'interface
     */
    private List<Bouton> boutons;


    /**
     * Constructeur de la classe Jeu
     * 
     * @param nomsJoueurs       noms des joueurs de la partie
     * @param cartesPreparation noms des cartes à utiliser pour créer les piles de
     *                          réserve (autres que les piles de cartes communes)
     * @param plateau           choix du plateau ({@code Plateau.OSAKA} ou
     *                          {@code Plateau.TOKYO})
     */
    public Jeu(String[] nomsJoueurs, String[] cartesPreparation, Plateau plateau) {
        // initialisation des entrées/sorties
        scanner = new Scanner(System.in);
        log = new ArrayList<>();

        // préparation du plateau
        this.nomVille = plateau.getNomVille();
        this.tuiles = plateau.makeTuiles();

        this.nbJetonsGare = 30;
        this.cartesEcartees = new ListeDeCartes();

        // construction des piles de réserve
        this.reserve = new HashMap<>();


        // ajouter les cartes communes et les cartes de préparation
        creerCartesCommunes();
        for (String nomCarte : cartesPreparation) {
            reserve.put(nomCarte, FabriqueListeDeCartes.creerListeDeCartes(nomCarte, 10));
        }

        // création des joueurs
        this.joueurs = new ArrayList<>();
        ArrayList<CouleurJoueur> couleurs = new ArrayList<>(List.of(CouleurJoueur.values()));
        Collections.shuffle(couleurs);
        for (String nomJoueur : nomsJoueurs) {
            this.joueurs.add(new Joueur(this, nomJoueur, couleurs.remove(0)));
        }
        this.joueurCourant = joueurs.get(0);
    }

    public Joueur getJoueurCourant() {
        return joueurCourant;
    }

    public List<Tuile> getTuiles() {
        return tuiles;
    }

    public Tuile getTuile(int index) {
        return tuiles.get(index);
    }

    public void setNbJetonsGare(int nbJetonsGare) {
        this.nbJetonsGare = nbJetonsGare;
    }


    public Map<String, ListeDeCartes> getReserve() {
        return reserve;
    }

    /**
     * Renvoie un ensemble de tous les noms des cartes en jeu.
     * 
     * Cette liste contient les noms des cartes qui étaient disponibles dans les
     * piles la réserve et "Train omnibus" que les joueurs ont en main en début de
     * partie mais ne correspond pas à une pile de la réserve.
     */
    public Set<String> getListeNomsCartes() {
        Set<String> noms = new HashSet<>(reserve.keySet());
        noms.add("Train omnibus");
        return noms;
    }

    /**
     * Construit les piles de réserve pour les cartes communes
     */
    private void creerCartesCommunes() {
        reserve.put("Train express", FabriqueListeDeCartes.creerListeDeCartes("Train express", 20));
        reserve.put("Train direct", FabriqueListeDeCartes.creerListeDeCartes("Train direct", 10));
        reserve.put("Pose de rails", FabriqueListeDeCartes.creerListeDeCartes("Pose de rails", 20));
        reserve.put("Gare", FabriqueListeDeCartes.creerListeDeCartes("Gare", 20));
        reserve.put("Appartement", FabriqueListeDeCartes.creerListeDeCartes("Appartement", 10));
        reserve.put("Immeuble", FabriqueListeDeCartes.creerListeDeCartes("Immeuble", 10));
        reserve.put("Gratte-ciel", FabriqueListeDeCartes.creerListeDeCartes("Gratte-ciel", 10));
        reserve.put("Ferraille", FabriqueListeDeCartes.creerListeDeCartes("Ferraille", 70));
    }


    /**
     * Renvoie une carte prise dans la réserve
     * 
     * @param nomCarte nom de la carte à prendre
     * @return la carte retirée de la réserve ou `null` si aucune disponible (ou si
     *         le nom de carte n'existe pas dans la réserve)
     */
    public Carte prendreDansLaReserve(String nomCarte) {
        if (!reserve.containsKey(nomCarte)) {
            return null;
        }

        ListeDeCartes pile = reserve.get(nomCarte);
        if (pile.isEmpty()) {
            return null;
        }


        return pile.remove(0);

    }

    public void ajouterReserve(String nomCarte, Carte carte){
        ListeDeCartes pile = reserve.get(nomCarte);
        pile.add(carte);
        reserve.put(nomCarte, pile);
        log(pile.toString());
    }

    //Juste une fonction pour débuter le jeu, vu qu'on à pas d'argent au debut mais qu'il faut quand même
    //placer un rail j'ai decider de ne pas utiliser les fonction utiliser dans la boucle de base vu qu'elle
    //regarde l'argent justement, en revanche on peut toujours pas placer dans la mer, ¯\_(ツ)_/¯
    public boolean choixTuileDebut(Joueur joueur, int index) {
        if (tuiles.get(index).getType().equals("mer")) {
            joueur.log("Vous ne pouvez pas mettre un rail en mer");
            return false;
        }
        tuiles.get(index).ajouterRail(joueur);
        return true;
    }

    public boolean ajouterJoueurSurTuile(Joueur joueur, int index) {
        Tuile tuile = tuiles.get(index);
        String tuileType = tuile.getType();
        int surcout = 0;


        //si il y a deja un rail, on peut pas en mettre un autre, logique
        if (tuile.hasRail(joueur)) {
            joueur.log("Vous avez déjà mis un rail sur cette tuile");
            return false;
        }


        //On ne peut que placer un rail à côté d'un autre rail qu'on à placer nous
        //Il faut donc faire une boucle à travers chaque tuile voisine et regarder si il y a un rail qui est à nous
        boolean contientJoueur = false;
        ArrayList<Tuile> tuilesVoisines = tuile.getTuilesVoisines();
        for (Tuile tuilesVoisine : tuilesVoisines) {
            if (tuilesVoisine.hasRail(joueur)) {
                contientJoueur = true;
            }
        }
        //si non, bah t bête (╯°□°)╯︵ ┻━┻
        if (!contientJoueur) {
            joueur.log("Vous devez placer un rail à côté d'un autre");
            return false;
        }


        //conditions des types de tuiles

        //logiquement on ne peut pas poser un rail en mer
        if (tuileType.equals("mer")) {
            joueur.log("Vous ne pouvez pas poser de rails en mer");
            return false;
        }

        //Chaque tuile a un cout en argent pour placer un rails a part la carte plaine
        //Je regarde donc les types et j'ajuste le prix basé sur le résultat
        else if (tuileType.equals("FLEUVE")) { surcout = 1; }
        else if (tuileType.equals("MONTAGNE")) { surcout = 2; }
        else if (tuileType.equals("ville")) { surcout = 1 + tuile.getNbGares(); }
        else if (tuileType.equals("etoile")) { surcout = tuile.getValeur(); }

        //Pas sur mais dans les règles le prix augmente + X fois le nombre de rail d'autre joueurs
        //Et vu qu'on à déjà tester si on est pas sur une case ou on a déjà placer un rail,
        //on peut tout simplement regarder la taille
        surcout += tuile.getJoueursTuile().size();

        if (joueur.getArgent() < surcout) {
            joueur.log("Vous n'avez pas assez d'argent");
            return false;
        }
        joueur.gagnerArgent(-surcout);

        //Encore une fois pas sur mais il me semble qu'il faut donner une feraille si jamais il y a d'autre
        //joueur sur la tuile
        if (tuile.getJoueursTuile().size() > 0) {
            joueur.prendreFerraille();
        }

        tuiles.get(index).ajouterRail(joueur);
        return true;
    }



    public Carte getCarteReserve(String nomCarte){
        if (!reserve.containsKey(nomCarte)) {
            return null;
        }

        ListeDeCartes pile = reserve.get(nomCarte);
        if (pile.isEmpty()) {
            return null;
        }

        return pile.getCarte(nomCarte);
    }

    public void ajouterEcarte(Carte carte) {
        cartesEcartees.add(carte);
    }

    /**
     * Modifie l'attribut {@code joueurCourant} pour passer au joueur suivant dans
     * l'ordre du tableau {@code joueurs} (le tableau est considéré circulairement)
     */
    public void passeAuJoueurSuivant() {
        int i = joueurs.indexOf(joueurCourant);
        i = (i + 1) % joueurs.size();
        joueurCourant = joueurs.get(i);
    }

    /**
     * Démarre la partie et exécute les tours des joueurs jusqu'à ce que la partie
     * soit terminée
     */
    public void run() {
        // initialisation (chaque joueur choisit une position de départ)
        // À FAIRE: compléter la partie initialisation
        List<String> choixPossibles = new ArrayList<>();
        for (int i = 0; i <= 75; i++) {
            // ajoute les indexes des tuiles préfixés de "TUILE:"
            choixPossibles.add("TUILE:" + i);
        }
        choixPossibles.add("TUILE:");


        for (Joueur j : joueurs) {
            String choix = "";
            while(!choix.startsWith("TUILE:")) {
                choix = j.choisir(String.format("Choisissez une tuile sur laquelle poser votre premier rail", j.getNom()), choixPossibles, null, true);

                String tuile = choix.split(":")[1];
                int index = Integer.parseInt(tuile);
                j.getJeu().choixTuileDebut(j, index);
                j.perdNbJetonsRails(1);
                passeAuJoueurSuivant();
            }
        }


        // tours des joueurs jusqu'à une condition de fin
        while (!estFini()) {
            joueurCourant.jouerTour();
            passeAuJoueurSuivant();
        }

        // fin de la partie
        log("<div class=\"tour\">Fin de la partie</div>");
        for (Joueur j : joueurs) {
            log(String.format("%s : %d points", j.toLog(), j.getScoreTotal()));
        }
        prompt("Fin de la partie.", null, true);
    }

    /**
     * @return {@code true} si la partie est finie, {@code false} sinon
     */
    public boolean estFini() {
        for(int i = 0; i< joueurs.size();i++){
            if(joueurs.get(i).getNbJetonsRails()==0){
                return true;
            }
        }
        if(nbJetonsGare==0){
            return true;
        }

        int pilesVides = 0;
        for (Map.Entry<String, ListeDeCartes> entry : reserve.entrySet()) {
            String nomDeCarte = entry.getKey();
            ListeDeCartes listeDeCartes = entry.getValue();
            if (!nomDeCarte.equals("Ferraille")) {
                if (listeDeCartes.isEmpty()) {
                    pilesVides++;
                }
            }
        }
        if (pilesVides >= 4) {
            return true;
        }
        return false;
    }

    /**
     * Ajoute un message au log du jeu
     */
    public void log(String message) {
        log.add(message);
    }

    /**
     * Envoie l'état de la partie pour affichage aux joueurs avant de faire un choix
     *
     * @param instruction l'instruction qui est donnée au joueur
     * @param boutons     boutons pour les choix qui ne sont pas disponibles
     *                    autrement (ou {@code null})
     * @param peutPasser  indique si le joueur peut passer sans faire de choix
     */
    public void prompt(String instruction, List<Bouton> boutons, boolean peutPasser) {
        if (boutons == null) {
            boutons = new ArrayList<>();
        }

        this.instruction = instruction;
        this.boutons = boutons;

        System.out.println();
        System.out.println(this);
        if (boutons.isEmpty()) {
            System.out.printf(">>> %s: %s <<<\n", joueurCourant.getNom(), instruction);
        } else {
            StringJoiner joiner = new StringJoiner(" / ");
            for (Bouton bouton : boutons) {
                joiner.add(bouton.toString());
            }
            System.out.printf(">>> %s: %s [%s] <<<\n", joueurCourant.getNom(), instruction, joiner);
        }
    }

    /**
     * Lit une ligne de l'entrée standard
     * 
     * ATTENTION: Pour que l'interface graphique et les tests fonctionnent
     * correctement il faut toujours utiliser cette méthode pour lire l'entrée
     * clavier et non pas appeler directement les méthodes de {@code scanner} (c'est
     * en particulier ce que fait la méthode {@code Player.choisir})
     *
     * @return le prochain input de l'utilisateur
     */
    public String lireLigne() {
        return scanner.nextLine();
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner("\n");

        StringJoiner reserveJoiner = new StringJoiner(", ");
        for (String nomCarte : reserve.keySet()) {
            reserveJoiner.add(String.format("%s (%d)", nomCarte, reserve.get(nomCarte).size()));
        }
        joiner.add("=== Réserve ===\n" + reserveJoiner + "\n");
        joiner.add(joueurCourant.toString() + "\n");
        return joiner.toString();
    }

    /**
     * @return une représentation du jeu sous la forme d'un dictionnaire de
     *         valeurs sérialisables (qui sera converti en JSON pour l'envoyer à
     *         l'interface
     *         graphique)
     */
    public Map<String, Object> dataMap() {
        // liste des données des piles de réserve
        List<Map<String, Object>> listeReserve = new ArrayList<>();
        List<String> nomsCartesEnReserve = reserve.keySet().stream().sorted().collect(Collectors.toList());
        // piles de cartes communes en premier
        for (String nomCarte : FabriqueListeDeCartes.getNomsCartesCommunes()) {
            if (nomsCartesEnReserve.contains(nomCarte)) {
                listeReserve.add(Map.of("carte", nomCarte, "nombre", reserve.get(nomCarte).size()));
                nomsCartesEnReserve.remove(nomCarte);
            }
        }
        // autres piles de réserve après, par ordre alphabétique
        for (String nomCarte : nomsCartesEnReserve) {
            listeReserve.add(Map.of("carte", nomCarte, "nombre", reserve.get(nomCarte).size()));
        }

        return Map.ofEntries(
                Map.entry("joueurs", joueurs.stream().map(Joueur::dataMap).toList()),
                Map.entry("joueurCourant", joueurs.indexOf(joueurCourant)),
                Map.entry("instruction", instruction),
                Map.entry("boutons", boutons),
                Map.entry("ville", nomVille),
                Map.entry("tuiles", tuiles.stream().map(Tuile::dataMap).toList()),
                Map.entry("log", log),
                Map.entry("reserve", listeReserve));
    }

    public int getNbJetonsGare() {
        return nbJetonsGare;
    }
}
