package donjons.affichages;

import donjons.EntiteCombat;
import donjons.cartes.Carte;
import donjons.cartes.GenerateurObstacle;
import donjons.cartes.GestionPlacement;
import monstres.Monstre;
import personnages.Personnage;
import personnages.equipements.Equipement;
import personnages.equipements.armes.*;
import personnages.equipements.armures.*;
import utiles.Utile;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.function.Supplier;

public class AffichageMiseEnPlace {

    private final Carte m_carte;
    private final GestionPlacement m_gestionPlacement;
    private final GenerateurObstacle m_generateur;

    // Couleurs ANSI console
    private static final String RESET = "\u001B[0m";
    private static final String ROUGE = "\u001B[31m";
    private static final String VERT = "\u001B[32m";
    private static final String JAUNE = "\u001B[33m";
    private static final String CYAN = "\u001B[36m";
    private static final String BLEU = "\u001B[34m";
    private static final String MAGENTA = "\u001B[35m";
    private static final String GRIS = "\u001B[37m";

    public AffichageMiseEnPlace(Carte carte, GenerateurObstacle generateur) {
        this.m_carte = carte;
        this.m_gestionPlacement = new GestionPlacement(m_carte);
        this.m_generateur = generateur;
    }

    public void afficherIntroduction(List<Personnage> personnages, List<Monstre> monstres, List<Equipement> equipements) {
        Scanner scanner = new Scanner(System.in);

        System.out.println(BLEU + "┌───────────────────────────────────────────────┐" + RESET);
        System.out.println(BLEU + "│            " + CYAN + "Déroulement d'un donjon" + BLEU + "            │" + RESET);
        System.out.println(BLEU + "└───────────────────────────────────────────────┘" + RESET + "\n");

        System.out.println(JAUNE + "~ Mise en Place ~" + RESET);
        System.out.println("Vous allez préparer le donjon de taille " + CYAN + m_carte.getLongueur() + " x " + m_carte.getLargeur() + RESET + " cases.");
        System.out.println("Voulez-vous :");
        System.out.println(VERT + "1 - Placer manuellement obstacles, monstres, joueurs et équipements" + RESET);
        System.out.println(VERT + "2 - Laisser le système les placer automatiquement" + RESET);
        System.out.print("Votre choix (1 ou 2) : ");

        int choix = 2;
        try {
            choix = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException ignored) {}

        if (choix == 1) {
            System.out.println(GRIS + "Placement manuel sélectionné." + RESET);
            placementManuel(scanner, personnages, monstres, equipements);
        } else {
            System.out.println(GRIS + "Placement automatique sélectionné." + RESET);
            placementAutomatique(personnages, monstres);
        }

        System.out.println("\n" + BLEU + "Voici la carte du donjon après placement :" + RESET + "\n");
        new AffichageCarte(m_carte).afficherCarte();

        System.out.println("\n" + JAUNE + "Préparez-vous à l’aventure !" + RESET);
    }

    private void placementAutomatique(List<Personnage> personnages, List<Monstre> monstres) {
        System.out.println(GRIS + "Placement automatique activé." + RESET);

        if (m_generateur != null) {
            m_generateur.genererObstacles();
        } else {
            System.out.println(ROUGE + "Cette carte ne supporte pas la génération automatique d’obstacles." + RESET);
        }

        Random random = new Random();

        for (Personnage p : personnages) {
            boolean placeTrouvee = false;
            while (!placeTrouvee) {
                int x = random.nextInt(m_carte.getLongueur());
                int y = random.nextInt(m_carte.getLargeur());

                if (m_carte.getCase(x, y).estLibre()) {
                    m_gestionPlacement.placerEntite(x, y, p);
                    p.setPosition(x, y);
                    placeTrouvee = true;
                }
            }
        }

        for (Monstre m : monstres) {
            boolean placeTrouvee = false;
            while (!placeTrouvee) {
                int x = random.nextInt(m_carte.getLongueur());
                int y = random.nextInt(m_carte.getLargeur());

                if (m_carte.getCase(x, y).estLibre()) {
                    m_gestionPlacement.placerEntite(x, y, m);
                    m.setPosition(x, y);
                    placeTrouvee = true;
                }
            }
        }

        // Génération et placement d’équipements via CarteDefaut
        m_carte.genererEtPlacerEquipementsAleatoirement(m_gestionPlacement);
    }

    private void placementManuel(Scanner scanner, List<Personnage> personnages, List<Monstre> monstres, List<Equipement> equipements) {
        System.out.println(GRIS + "Placement manuel activé." + RESET);

        // Placement des obstacles
        System.out.println("Placement des obstacles :");
        while (true) {
            System.out.print("Entrez coordonnées (lettre + chiffre) pour placer un obstacle (ex: B7), ou -1 pour finir : ");
            String input = scanner.nextLine().trim().toUpperCase();
            if (input.equals("-1")) break;

            try {
                int[] coords = Utile.convertirCoordonnees(input, m_carte.getLongueur());
                int x = coords[0];
                int y = coords[1];
                if (!m_carte.getCase(x, y).estLibre()) {
                    System.out.println(ROUGE + "Case déjà occupée." + RESET);
                    continue;
                }
                m_carte.setObstacle(x, y);
                System.out.println(VERT + "Obstacle placé en " + input + RESET);
            } catch (IllegalArgumentException e) {
                System.out.println(ROUGE + "Coordonnées invalides, réessayez." + RESET);
            }
        }
        // Placement des personnages
        System.out.println("\n" + VERT + "Placement des personnages :" + RESET);
        for (Personnage p : personnages) {
            System.out.println("Personnage : " + CYAN + p.getNom() + RESET);
            placerEntiteManuellement(scanner, p);
        }

        // Placement des monstres
        System.out.println("\n" + MAGENTA + "Placement des monstres :" + RESET);
        for (Monstre m : monstres) {
            System.out.println("Monstre : " + CYAN + m.getNom() + m.getNumero() + RESET);
            placerEntiteManuellement(scanner, m);
        }

        // Création et placement des équipements
        System.out.print("\n" + JAUNE + "Combien d'équipements voulez-vous créer et placer ? " + RESET);
        int nbEquipements = 0;
        try {
            nbEquipements = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println(ROUGE + "Entrée invalide, aucun équipement ne sera créé." + RESET);
        }

        for (int i = 0; i < nbEquipements; i++) {
            Equipement e = creerEquipementManuellement(scanner);
            System.out.println("Placement de l’équipement : " + CYAN + e.getNom() + RESET);
            while (true) {
                int[] coords = demanderCoordonnees(scanner, "l'entité");
                int x = coords[0];
                int y = coords[1];
                if (!m_carte.getCase(x, y).estLibre()) {
                    System.out.println(ROUGE + "Case occupée ou invalide, réessayez." + RESET);
                    continue;
                }
                try {
                    m_gestionPlacement.placerEquipement(x, y, e);
                    equipements.add(e);
                    break;
                } catch (Exception ex) {
                    System.out.println(ROUGE + "Erreur lors du placement, réessayez." + RESET);
                }
            }
        }
    }

    private static final Map<String, Supplier<Equipement>> equipementsDisponibles = Map.ofEntries(
            Map.entry("1", ArmeCourante::getBaton),
            Map.entry("2", ArmeCourante::getMasse),
            Map.entry("3", ArmeDistance::getArbaleteLegere),
            Map.entry("4", ArmeDistance::getFronde),
            Map.entry("5", ArmeDistance::getArcCourt),
            Map.entry("6", ArmeGuerre::getEpeeLongue),
            Map.entry("7", ArmeGuerre::getRapiere),
            Map.entry("8", ArmeGuerre::getEpeeDeuxMains),
            Map.entry("9", ArmureLegere::getArmureEcaille),
            Map.entry("10", ArmureLegere::getArmureDemiPlate),
            Map.entry("11", personnages.equipements.armures.ArmureLourde::getCotteDeMaille),
            Map.entry("12", personnages.equipements.armures.ArmureLourde::getArmureDemiPlate)
    );

    private Equipement creerEquipementManuellement(Scanner scanner) {
        System.out.println(JAUNE + "Choisissez l'équipement à créer :" + RESET);
        System.out.println("1 - Bâton");
        System.out.println("2 - Masse d'armes");
        System.out.println("3 - Arbalète légère");
        System.out.println("4 - Fronde");
        System.out.println("5 - Arc court");
        System.out.println("6 - Épée longue");
        System.out.println("7 - Rapière");
        System.out.println("8 - Épée à deux mains");
        System.out.println("9 - Armure d'écailles");
        System.out.println("10 - Demi-plate");
        System.out.println("11 - Cotte de mailles");
        System.out.println("12 - Harnois");

        while (true) {
            System.out.print("Votre choix (1-12) : ");
            String choix = scanner.nextLine().trim();
            Supplier<Equipement> constructeur = equipementsDisponibles.get(choix);
            if (constructeur != null) {
                return constructeur.get();
            } else {
                System.out.println(ROUGE + "Choix invalide, veuillez réessayer." + RESET);
            }
        }
    }

    private int[] demanderCoordonnees(Scanner scanner, String type) {
        while (true) {
            System.out.print("Entrez coordonnées (lettre + chiffre) pour placer " + type + " : ");
            String input = scanner.nextLine().trim().toUpperCase();
            try {
                int[] coords = Utile.convertirCoordonnees(input, m_carte.getLongueur());
                return coords;
            } catch (IllegalArgumentException e) {
                System.out.println(ROUGE + "Coordonnées invalides, réessayez." + RESET);
            }
        }
    }

    private void placerEntiteManuellement(Scanner scanner, EntiteCombat entite) {
        while (true) {
            int[] coords = demanderCoordonnees(scanner, entite.getNom());
            int x = coords[0];
            int y = coords[1];
            if (!m_carte.getCase(x, y).estLibre()) {
                System.out.println(ROUGE + "Case déjà occupée, réessayez." + RESET);
                continue;
            }
            m_gestionPlacement.placerEntite(x, y, entite);
            entite.setPosition(x, y);
            System.out.println(VERT + entite.getNom() + " placé en " + (char) ('A' + x) + (y + 1) + RESET);
            break;
        }
    }

    @Override
    public String toString() {
        int nbObstacles = m_carte.compterObstacles();
        int nbPersonnages = m_carte.compterPersonnages();
        int nbMonstres = m_carte.compterMonstres();
        int nbEquipements = m_carte.compterEquipements();

        StringBuilder sb = new StringBuilder();
        sb.append(BLEU).append("=== Résumé de la mise en place du donjon ===").append(RESET).append("\n");
        sb.append("Taille carte : ").append(m_carte.getLongueur()).append(" x ").append(m_carte.getLargeur()).append("\n");
        sb.append(ROUGE).append("Obstacles : ").append(nbObstacles).append(RESET).append("\n");
        sb.append(VERT).append("Personnages : ").append(nbPersonnages).append(RESET).append("\n");
        sb.append(MAGENTA).append("Monstres : ").append(nbMonstres).append(RESET).append("\n");
        sb.append(JAUNE).append("Équipements : ").append(nbEquipements).append(RESET).append("\n");
        return sb.toString();
    }
}
