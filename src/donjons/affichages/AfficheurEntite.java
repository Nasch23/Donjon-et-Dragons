package donjons.affichages;

import mecaniques.Dice;
import monstres.Attaque;
import monstres.CaracteristiqueMonstre;
import personnages.Personnage;
import personnages.classes.*;
import personnages.equipements.armes.Arme;
import personnages.equipements.armures.Armure;
import personnages.races.*;
import monstres.Monstre;
import personnages.equipements.Equipement;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AfficheurEntite {

    private List<Personnage> m_personnages;
    private List<Monstre> m_monstres;
    private List<Equipement> m_equipements;

    // Codes couleurs ANSI pour console
    private static final String RESET = "\u001B[0m";
    private static final String VERT = "\u001B[32m";
    private static final String ROUGE = "\u001B[31m";
    private static final String JAUNE = "\u001B[33m";
    private static final String CYAN = "\u001B[36m";
    private static final String BLANC_BOLD = "\033[1;37m";

    public AfficheurEntite() {
        this.m_personnages = new ArrayList<>();
        this.m_monstres = new ArrayList<>();
        this.m_equipements = new ArrayList<>();
    }
    private boolean validerNomLettreSeulement(String texte) {
        // Cette regex accepte uniquement des lettres (majuscules/minuscules) et espaces
        // Pour accepter les accents, on peut utiliser \\p{L} (classe Unicode lettres)
        return texte.matches("[\\p{L} ]+");
    }

    public void creerPersonnages(Scanner scanner) {
        System.out.println(CYAN + "\n--- Création des personnages ---" + RESET);
        System.out.println("Entrez -1 comme nom pour terminer.");

        while (true) {
            String nom;
            while (true) {
                System.out.print("Nom du personnage : ");
                nom = scanner.nextLine().trim();
                if (nom.equals("-1")) break;
                if (!validerNomLettreSeulement(nom)) {
                    System.out.println("Erreur : le nom ne doit contenir que des lettres et espaces. Réessayez.");
                } else {
                    break;
                }
            }
            if (nom.equals("-1")) break;


            Race race = choisirRace(scanner);
            Classe classe = choisirClasse(scanner);

            Personnage p = new Personnage(nom, classe, race);
            m_personnages.add(p);
            System.out.println(VERT + "→ Personnage \"" + nom + "\" créé." + RESET + "\n");
        }
    }

    private Race choisirRace(Scanner scanner) {
        System.out.println("Choisissez une race :");
        System.out.println("1. Elfe");
        System.out.println("2. Humain");
        System.out.println("3. Nain");
        System.out.println("4. Halfein");
        System.out.print("Votre choix : ");

        int choix = lireEntier(scanner, 1, 4);

        switch (choix) {
            case 1: return new Elfe();
            case 2: return new Humain();
            case 3: return new Nain();
            case 4: return new Halfein();
            default: return new Humain(); // Par défaut
        }
    }

    private Classe choisirClasse(Scanner scanner) {
        System.out.println("Choisissez une classe :");
        System.out.println("1. Clerc");
        System.out.println("2. Guerrier");
        System.out.println("3. Magicien");
        System.out.println("4. Roublard");
        System.out.print("Votre choix : ");

        int choix = lireEntier(scanner, 1, 4);

        switch (choix) {
            case 1: return new Clerc();
            case 2: return new Guerrier();
            case 3: return new Magicien();
            case 4: return new Roublard();
            default: return new Guerrier();
        }
    }

    private int lireEntier(Scanner scanner, int min, int max) {
        int val = -1;
        while (val < min || val > max) {
            try {
                val = Integer.parseInt(scanner.nextLine().trim());
                if (val < min || val > max) {
                    System.out.print("Veuillez entrer un nombre entre " + min + " et " + max + " : ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Entrée invalide, veuillez entrer un nombre : ");
            }
        }
        return val;
    }
    public void demanderEquipementInitial(List<Personnage> personnages) {
        Scanner scanner = new Scanner(System.in);

        for (Personnage perso : personnages) {
            System.out.println("\n=== Equipement pour " + perso.getNom() + " ===");

            // --- Choix arme ---
            List<Arme> armesDispo = perso.getInventaire().getArmes();
            if (!armesDispo.isEmpty()) {
                System.out.println("Voulez-vous équiper une arme ? (oui/non)");
                String reponseArme = scanner.nextLine().trim().toLowerCase();

                if (reponseArme.equals("oui")) {
                    System.out.println("Choisissez une arme à équiper :");
                    for (int i = 0; i < armesDispo.size(); i++) {
                        System.out.println(i + " - " + armesDispo.get(i).getNom());
                    }
                    int choixArme = lireEntier(scanner, 0, armesDispo.size() - 1);
                    perso.getInventaire().equiperArme(armesDispo.get(choixArme));
                    System.out.println(perso.getNom() + " équipe l'arme " + armesDispo.get(choixArme).getNom());
                } else {
                    System.out.println(perso.getNom() + " ne change pas d'arme.");
                }
            } else {
                System.out.println(perso.getNom() + " n'a pas d'arme disponible.");
            }

            // --- Choix armure ---
            List<Armure> armuresDispo = perso.getInventaire().getArmures();
            if (!armuresDispo.isEmpty()) {
                System.out.println("Voulez-vous équiper une armure ? (oui/non)");
                String reponseArmure = scanner.nextLine().trim().toLowerCase();

                if (reponseArmure.equals("oui")) {
                    System.out.println("Choisissez une armure à équiper :");
                    for (int i = 0; i < armuresDispo.size(); i++) {
                        System.out.println(i + " - " + armuresDispo.get(i).getNom());
                    }
                    int choixArmure = lireEntier(scanner, 0, armuresDispo.size() - 1);
                    perso.getInventaire().equiperArmure(armuresDispo.get(choixArmure));
                    System.out.println(perso.getNom() + " équipe l'armure " + armuresDispo.get(choixArmure).getNom());
                } else {
                    System.out.println(perso.getNom() + " ne change pas d'armure.");
                }
            } else {
                System.out.println(perso.getNom() + " n'a pas d'armure disponible.");
            }
        }
    }

    public void creerMonstres(Scanner scanner) {
        System.out.println(CYAN + "\n--- Création des monstres ---" + RESET);

        System.out.println("Choisissez une option :");
        System.out.println("1 - Ajouter uniquement les monstres prédéfinis");
        System.out.println("2 - Ajouter uniquement des monstres manuels");
        System.out.println("3 - Ajouter un mélange des deux");
        System.out.print("Votre choix : ");

        int choix = lireEntier(scanner, 1, 3);

        if (choix == 1) {
            genererMonstresAutomatique();
            System.out.println(ROUGE + "Monstres prédéfinis ajoutés." + RESET + "\n");
        } else if (choix == 2) {
            creerMonstresManuellement(scanner);
        } else {
            choisirMonstresParmiPredifinis(scanner);
            creerMonstresManuellement(scanner);
        }
    }
    public List getPersonnages()
    {
        return m_personnages;
    }
    public List getMonstres()
    {
        return m_monstres;
    }
    public List getEquipements()
    {
        return m_equipements;
    }
    private void genererMonstresAutomatique() {
        m_monstres.clear();
        m_monstres.add(Monstre.gobelin("1"));
        m_monstres.add(Monstre.orc("1"));
        m_monstres.add(Monstre.squelette("1"));
    }

    private void choisirMonstresParmiPredifinis(Scanner scanner) {
        m_monstres.clear();

        System.out.println("\nChoisissez les monstres prédéfinis à ajouter :");
        System.out.println("1 - Gobelin");
        System.out.println("2 - Orc");
        System.out.println("3 - Troll");
        System.out.println("0 - Terminer la sélection");

        while (true) {
            System.out.print("Numéro du monstre à ajouter (0 pour terminer) : ");
            int choix = lireEntier(scanner, 0, 3);
            if (choix == 0) break;

            Monstre monstre = null;
            switch (choix) {
                case 1: monstre = Monstre.gobelin("1"); break;
                case 2: monstre = Monstre.orc("1"); break;
                case 3: monstre = Monstre.squelette("1"); break;
            }

            if (monstre != null) {
                m_monstres.add(monstre);
                System.out.println(ROUGE + monstre.getNom() + " ajouté." + RESET + "\n");
            }
        }
    }
    public static final String[] ESPECES_DISPONIBLES = {
            "Gobelin", "Orc", "Dragon", "Troll", "Demon", "Kaiju", "Biju",
            "Fléau", "Squelette", "Zombie", "Vampire", "Loup-garou", "Ogre",
            "Harpie", "Golem", "Arachnide", "Sorcier", "Elementaire", "Fantome"
    };

    private void creerMonstresManuellement(Scanner scanner) {
        System.out.println(CYAN + "\n--- Création manuelle des monstres ---" + RESET);
        System.out.println("Entrez -1 comme numéro d'espèce pour terminer.");

        // Pour compter combien de monstres de chaque type ont déjà été créés
        java.util.Map<String, Integer> compteType = new java.util.HashMap<>();

        while (true) {
            // Affichage menu choix espèce
            System.out.println("\nChoisissez l'espèce du monstre :");
            for (int i = 0; i < ESPECES_DISPONIBLES.length; i++) {
                System.out.println((i + 1) + " - " + ESPECES_DISPONIBLES[i]);
            }
            System.out.print("Votre choix (-1 pour terminer) : ");

            int choixEspece;
            try {
                choixEspece = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un numéro valide.");
                continue;
            }

            if (choixEspece == -1) {
                System.out.println("Fin de la création des monstres.");
                break;
            }
            if (choixEspece < 1 || choixEspece > ESPECES_DISPONIBLES.length) {
                System.out.println("Choix invalide, veuillez réessayer.");
                continue;
            }

            String espece = ESPECES_DISPONIBLES[choixEspece - 1];
            String especeLower = espece.toLowerCase();

            // Calcul du numéro automatiquement
            int numero = compteType.getOrDefault(especeLower, 0) + 1;
            compteType.put(especeLower, numero);
            String numeroStr = (numero > 1) ? String.valueOf(numero) : "";

            System.out.println("Numéro attribué automatiquement : " + (numeroStr.isEmpty() ? "(aucun)" : numeroStr));

            // Lecture des autres caractéristiques
            try {
                System.out.print("Portée de l'attaque (1 = corps à corps, >1 = distance) : ");
                int portee = Integer.parseInt(scanner.nextLine().trim());

                System.out.print("Nombre de dés de dégâts (ex: 2 pour 2d6) : ");
                int nbDes = Integer.parseInt(scanner.nextLine().trim());

                System.out.print("Nombre de faces des dés (ex: 6 pour 2d6) : ");
                int facesDes = Integer.parseInt(scanner.nextLine().trim());

                System.out.print("Points de vie : ");
                int pv = Integer.parseInt(scanner.nextLine().trim());

                System.out.print("Force : ");
                int force = Integer.parseInt(scanner.nextLine().trim());

                System.out.print("Vitesse (en cases) : ");
                int vitesse = Integer.parseInt(scanner.nextLine().trim());

                System.out.print("Dextérité : ");
                int dexterite = Integer.parseInt(scanner.nextLine().trim());

                System.out.print("Initiative : ");
                int initiative = Integer.parseInt(scanner.nextLine().trim());

                System.out.print("Classe d'armure : ");
                int classeArmure = Integer.parseInt(scanner.nextLine().trim());

                Dice degats = new Dice(nbDes, facesDes);
                Attaque attaque = new Attaque(portee, degats);
                CaracteristiqueMonstre carac = new CaracteristiqueMonstre(pv, force, vitesse, dexterite, initiative, classeArmure);
                Monstre monstre = new Monstre(espece, numeroStr, attaque, carac);

                m_monstres.add(monstre);
                System.out.println(ROUGE + "✅ Monstre " + espece + (numeroStr.isEmpty() ? "" : " #" + numeroStr) + " créé." + RESET + "\n");

            } catch (NumberFormatException e) {
                System.out.println("Erreur : veuillez entrer un nombre valide. Recommencez la création de ce monstre.");
            }
        }
    }

    // Affiche toutes les entités avec couleurs
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(BLANC_BOLD).append("=== Personnages ===").append(RESET).append("\n");
        if (m_personnages.isEmpty()) {
            sb.append(VERT).append("Aucun personnage créé.").append(RESET).append("\n");
        } else {
            for (Personnage p : m_personnages) {
                sb.append(toStringPersonnage(p)).append("\n");
            }
        }

        sb.append("\n").append(BLANC_BOLD).append("=== Monstres ===").append(RESET).append("\n");
        if (m_monstres.isEmpty()) {
            sb.append(ROUGE).append("Aucun monstre créé.").append(RESET).append("\n");
        } else {
            for (Monstre m : m_monstres) {
                sb.append(toStringMonstre(m)).append("\n");
            }
        }
        return sb.toString();
    }

    private String toStringPersonnage(Personnage p) {
        StringBuilder sb = new StringBuilder();
        sb.append(VERT)
                .append("[Nom] ").append(p.getNom())
                .append(" | [Race] ").append(p.getRace().getNom())
                .append(" | [Classe] ").append(p.getClasse().getNom())
                .append(" | [PV] ").append(p.getPv())
                .append(" | [FOR] ").append(p.getForce())
                .append(" | [DEX] ").append(p.getDexterite())
                .append(" | [VIT] ").append(p.getVitesse())
                .append(" | [INI] ").append(p.getInitiative())
                .append(" | [CA] ").append(p.getClasseArmure())
                .append(RESET);
        return sb.toString();
    }

    private String toStringMonstre(Monstre m) {
        StringBuilder sb = new StringBuilder();
        sb.append(ROUGE)
                .append("[Nom] ").append(m.getNom())
                .append(" #").append(m.getNumero())
                .append(" | [PV] ").append(m.getPv())
                .append(" | [FOR] ").append(m.getForce())
                .append(" | [DEX] ").append(m.getDexterite())
                .append(" | [VIT] ").append(m.getVitesse())
                .append(" | [INI] ").append(m.getInitiative())
                .append(" | [CA] ").append(m.getClasseArmure())
                .append(" | [Attaque] portee=").append(m.getAttaque().getPortee())
                .append(", degats=").append(m.getAttaque().getDeDegats().toString())
                .append(RESET);
        return sb.toString();
    }
}
