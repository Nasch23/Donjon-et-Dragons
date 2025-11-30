package donjons.affichages;

import donjons.EntiteCombat;
import donjons.cartes.Carte;
import donjons.cartes.Case;
import donjons.cartes.GestionPlacement;
import mecaniques.Combat;
import mecaniques.Dice;
import mecaniques.Jeu;
import mecaniques.Tour;
import monstres.Monstre;
import personnages.Personnage;
import personnages.equipements.Equipement;
import personnages.equipements.armes.Arme;
import personnages.equipements.armures.Armure;
import personnages.sorts.ArmeMagique;
import personnages.sorts.BoogieWoogie;
import personnages.sorts.Guerison;
import utiles.Utile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class AfficheurTour {

    private Carte m_carte;
    private GestionPlacement m_placement;
    private Tour m_tour;
    private Jeu m_jeu;
    private AfficheurEntite m_afficheurEntite;
    // Codes ANSI pour les couleurs (tu peux les mettre en constantes)
    private static final String RESET = "\u001B[0m";
    private static final String CYAN = "\u001B[36m";
    private static final String MAGENTA = "\u001B[35m";
    private static final String BOLD = "\u001B[1m";
    private static final String VERT = "\u001B[32m";
    private static final String JAUNE = "\u001B[33m";
    private static final String ROUGE = "\u001B[31m";
    private static final String BLEU = "\u001B[34m";


    public AfficheurTour(Carte carte, GestionPlacement placement, Tour tour, Jeu jeu,AfficheurEntite afficheurEntite) {
        this.m_carte = carte;
        this.m_placement = placement;
        this.m_tour = tour;
        this.m_jeu = jeu;
        this.m_afficheurEntite = afficheurEntite;
    }

    /**
     * Affiche le tour complet avec toutes les informations demandées.
     * @param tourActuel numéro du tour actuel
     * @param entiteActive entité active du tour
     * @param ordreTour liste de toutes les entités (personnages et monstres) dans l'ordre de jeu
     */
    public boolean afficherTour(int tourActuel, EntiteCombat entiteActive, List<EntiteCombat> ordreTour, int numeroDonjon) {
        afficherEnTeteDonjon(entiteActive, numeroDonjon);
        afficherOrdreTour(tourActuel, entiteActive, ordreTour);
        afficherCarte();
        afficherDetailsEntite(entiteActive);
        Scanner scanner = new Scanner(System.in);
        return gererActions(entiteActive, m_carte, scanner);

    }

    private void afficherEnTeteDonjon(EntiteCombat entiteActive, int numeroDonjon) {
        System.out.println("\n********************************************************************************");
        System.out.printf("Donjon %d:%n", numeroDonjon);

        String ligne = entiteActive.toString(); // Utilise toString() personnalisé
        int largeur = 80;
        int espacesGauche = (largeur - ligne.length()) / 2;
        System.out.printf("%" + espacesGauche + "s%s%n", "", ligne);

        System.out.println("********************************************************************************\n");
    }

    private void afficherOrdreTour(int tourActuel, EntiteCombat entiteActive, List<EntiteCombat> ordreTour) {
        System.out.printf("Tour %d:%n", tourActuel);

        for (EntiteCombat entite : ordreTour) {
            String prefixe = entite.equals(entiteActive) ? "-> " : "   ";
            String Nom = entite.getNom();
            String symbole = entite.getSymbolePourAffichage();
            String description = entite.getDescriptionPourOrdreTour();

            System.out.printf("%s%s   %s %s%n", prefixe, symbole, Nom, description);
        }

        System.out.println();
    }
    private void afficherCarte() {
        System.out.println("Carte actuelle :");
        new AffichageCarte(m_carte).afficherCarte();
        System.out.println();
    }
    private void afficherDetailsEntite(EntiteCombat entite) {
        System.out.println(entite.getNom());
        System.out.printf("  Vie : %d/%d%n", entite.getPv(), entite.getPvMax());

        if (entite.estMonstre()) {
            Monstre m = (Monstre) entite;
            System.out.printf("  Portée d'attaque : %d%n", m.getPortee());
            System.out.printf("  Dégats d'attaque : %s%n", m.getDesDegats());
            System.out.printf("  Force : %d%n", m.getForce());
            System.out.printf("  Dextérité : %d%n", m.getDexterite());
            System.out.printf("  Classe d'armure : %d%n", m.getClasseArmure());
        }

        if (entite.estPersonnage()) {
            // On cast en toute sécurité car on a vérifié
            Personnage p = (Personnage) entite;

            // Armure
            String armureNom = "aucune";
            int armureClasse = 0;
            if (p.getArmurePortee().isPresent()) {
                Armure armure = p.getArmurePortee().get();
                armureNom = armure.getNom();
                armureClasse= armure.getClasseArmure();
            }
            System.out.printf("  Armure: %s (classe d'armure : %d)%n", armureNom, armureClasse);

            // Arme
            if (p.getArmePortee().isPresent()) {
                Arme arme = p.getArmePortee().get();
                System.out.printf("  Arme: %s (dégâts: %d, portée: %d)%n",
                        arme.getNom(), arme.getDegat(), arme.getPortee());
            } else {
                System.out.println("  Arme: aucune");
            }

            // Inventaire
            System.out.print("  Inventaire: ");
            List<Equipement> inv = p.getInventaire().getContenu();
            if (inv.isEmpty()) {
                System.out.println("vide");
            } else {
                for (int i = 0; i < inv.size(); i++) {
                    System.out.printf("[%d] %s ", i + 1, inv.get(i).getNom());
                }
                System.out.println();
            }

            // Stats
            System.out.printf("  Force: %d%n", p.getForce());
            System.out.printf("  Dextérité: %d%n", p.getDexterite());
            System.out.printf("  Vitesse: %d%n", p.getVitesse());
        }
        System.out.println();
    }
    private void afficherActionsRestantes(EntiteCombat entite, int actionsRestantes, Carte carte) {


        System.out.println("\n" + VERT + entite.getNom() + RESET + " : il vous reste " + JAUNE + actionsRestantes + RESET + " action" +
                (actionsRestantes > 1 ? "s" : "") + ", que souhaitez-vous faire ?");

        if (actionsRestantes == 1) {
            System.out.println(ROUGE + "⚠ Attention : dernière action possible !" + RESET);
        }

        System.out.println("\n" + BLEU + "📜 Commandes disponibles :" + RESET);
        System.out.println("  • ✍ " + CYAN + "mj <texte>" + RESET + " — Le maître du jeu commente l'action précédente.");
        System.out.println("  • ✍ " + CYAN + "com <texte>" + RESET + " — Vous commentez l'action précédente.");
        System.out.println("  • ⚔ " + CYAN + "att <Case>" + RESET + " — Attaquer une entité sur une case (ex : A2, B6).");
        System.out.println("  • 🧭 " + CYAN + "dep <Case>" + RESET + " — Se déplacer vers une case (ex : A2, B6).");

        if (entite.estPersonnage()) {
            System.out.println("  • 🧰 " + CYAN + "equ <numero>" + RESET + " — S'équiper d’un objet de l’inventaire.");
        }

        if (entite.estPersonnage()) {
            Case caseActuelle = m_carte.getCase(entite.getX(), entite.getY());
            if (caseActuelle.contientEquipement()) {
                Equipement e = caseActuelle.getEquipement().get();
                System.out.println("\n🎁 " + VERT + "Un équipement se trouve ici : " + e.getNom() + RESET);
                System.out.println("  • 📦 " + CYAN + "ram" + RESET + " — Ramasser l'équipement au sol.");
            }
        }

        if (entite.estPersonnage()) {
            Personnage perso = (Personnage) entite;

            if (perso.estDeClasse("Clerc")) {
                System.out.println("\n✨ " + MAGENTA + "Sort disponible (Clerc) :" + RESET);
                System.out.println("  • 🩺 " + CYAN + "guer <nom>" + RESET + " — Soigne un personnage de " + JAUNE + "1d10 PV" + RESET +
                        ", sans dépasser ses PV de départ.");
            } else if (perso.estDeClasse("Magicien")) {
                System.out.println("\n✨ " + MAGENTA + "Sorts disponibles (Magicien) :" + RESET);

                System.out.println("  • 🩺 " + CYAN + "guer" + RESET + " — Soigne un personnage de " + JAUNE + "1d10 PV" + RESET +
                        ", sans dépasser ses PV de départ.");
                System.out.println("  • 🌀 " + CYAN + "boogie" + RESET +
                        " — Échange la position de deux entités du donjon.");
                System.out.println("  • 🪄 " + CYAN + "arme" + RESET +
                        " — Améliore une arme : +" + JAUNE + "1" + RESET + " aux jets d’attaque et de dégâts.");
            }
        }
    }
    private void afficherChoixMaitreJeu() {


        System.out.println("\n🎲 " + JAUNE + "Actions disponibles pour le Maître du Jeu :" + RESET);

        System.out.println("  " + CYAN + "• 📍 Déplacer une entité" + RESET);
        System.out.println("    → Commande : " + BLEU + "mv <Position> <Destination>" + RESET);
        System.out.println("    → Exemple  : " + BLEU + "mv A3 B4" + RESET);

        System.out.println("\n  " + CYAN + "• 💥 Infliger des dégâts personnalisés" + RESET);
        System.out.println("    → Commande : " + BLEU + "dg <NomCible> <nbDés> <faces>" + RESET);
        System.out.println("    → Exemple  : " + BLEU + "dg Gobelin 2 6" + RESET + " (" + MAGENTA + "2d6 dégâts" + RESET + ")");

        System.out.println("\n  " + CYAN + "• 🧱 Ajouter un obstacle sur la carte" + RESET);
        System.out.println("    → Commande : " + BLEU + "X <Case>" + RESET);
        System.out.println("    → Exemple  : " + BLEU + "X B6" + RESET);
    }

    public boolean gererActions(EntiteCombat entite, Carte carte, Scanner scanner) {
        int actionsRestantes = 3;
        Optional<Personnage> optPerso = entite.estPersonnage() ? Optional.of((Personnage) entite) : Optional.empty();

        while (actionsRestantes > 0) {
            afficherActionsRestantes(entite, actionsRestantes, m_carte);

            System.out.print("> ");
            String ligne = scanner.nextLine().trim();

            if (ligne.isEmpty()) {
                System.out.println("Commande vide, réessayez.");
                continue;
            }

            String[] parts = ligne.split(" ", 2);
            String commande = parts[0].toLowerCase();
            String argument = parts.length > 1 ? parts[1].trim() : "";

            boolean actionEffectue = false;

            switch (commande) {
                case "mj":
                    System.out.println("[Maître du jeu] " + argument);
                    break;

                case "com":
                    System.out.println("[Commentaire] " + argument);
                    break;

                case "att":
                    boolean attaque = gererAttaque(entite, carte, argument, scanner);
                    if (attaque) {
                        actionsRestantes--;
                        actionEffectue = true;
                    }
                    afficherCarte();
                    afficherDetailsEntite(entite);
                    break;
                case "dep":
                    boolean deplace = gererDeplacement(entite, carte, argument);
                    if (deplace) {
                        actionsRestantes--;
                        actionEffectue = true;
                    }
                    afficherCarte();
                    afficherDetailsEntite(entite);
                    break;

                case "equ":
                    if (optPerso.isPresent()) {
                        Personnage perso = optPerso.get();
                        ArrayList<Equipement> contenu = perso.getInventaire().getContenu();
                        if (contenu.isEmpty()) {
                            System.out.println("Inventaire vide. Aucun équipement à porter.");
                            break;
                        }

                        System.out.println("Quel équipement voulez-vous porter ?");
                        for (int i = 0; i < contenu.size(); i++) {
                            Equipement eq = contenu.get(i);
                            System.out.println(i + " : " + eq.getNom() + " (" + eq.getClass().getSimpleName() + ")");
                        }

                        System.out.print("Choisissez un numéro : ");
                        try {
                            int choix = Integer.parseInt(scanner.nextLine());
                            if (choix >= 0 && choix < contenu.size()) {
                                Equipement aEquiper = contenu.get(choix);
                                aEquiper.equipeSurCePersonnage(perso);
                                System.out.println(aEquiper.getNom() + " a été équipé !");
                                actionsRestantes--;
                                actionEffectue = true;
                            } else {
                                System.out.println("Choix invalide.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Entrée invalide. Veuillez entrer un nombre.");
                        }
                    } else {
                        System.out.println("Cette commande n'est pas disponible pour cette entité.");
                    }
                    afficherDetailsEntite(entite);
                    break;

                case "ram":
                    if (optPerso.isPresent()) {
                        Personnage perso = optPerso.get();
                        Case caseActuelle = carte.getCase(perso.getX(), perso.getY());

                        if (caseActuelle.contientEquipement()) {
                            Equipement e = caseActuelle.retirerEquipement();
                            perso.getInventaire().ajouter(e);
                            System.out.println(perso.getNom() + " ramasse : " + e.getNom());
                            actionsRestantes--;
                            actionEffectue = true;
                        } else {
                            System.out.println("Aucun équipement à ramasser sur cette case.");
                        }
                    } else {
                        System.out.println("Seuls les personnages peuvent ramasser des objets.");
                    }
                    afficherDetailsEntite(entite);
                    break;

                case "guer":
                    if (optPerso.isEmpty()) {
                        System.out.println("❌ Seuls les personnages peuvent lancer des sorts.");
                        break;
                    }
                {
                    Personnage perso = optPerso.get();
                    if (!perso.peutLancerSort("guerison")) {
                        System.out.println("❌ Vous ne maîtrisez pas le sort de guérison.");
                        break;
                    }

                    Guerison sortGuerison = new Guerison();
                    Personnage cible = sortGuerison.demanderCiblePersonnage(m_jeu.getListPersonnage(), perso);

                    Dice dice = new Dice(1,10);
                    int soin = dice.lancer();
                    int pvAvant = cible.getPvActuels();
                    int pvMax = cible.getClasse().getPv();

                    int pvApres = Math.min(pvAvant + soin, pvMax);
                    cible.setPvActuels(pvApres);

                    System.out.println("🩺 " + perso.getNom() + " lance le sort de guérison sur " + cible.getNom() + " !");
                    System.out.println("💚 " + cible.getNom() + " récupère " + (pvApres - pvAvant) + " PV (total : " + pvApres + "/" + pvMax + ")");
                    actionEffectue = true;
                }
                break;

                case "boogie":
                    if (optPerso.isPresent() && optPerso.get().estDeClasse("Magicien")) {
                        Personnage perso = optPerso.get();
                        BoogieWoogie boogie = new BoogieWoogie();
                        EntiteCombat e1 = boogie.demanderEntite(m_jeu.getListPersonnage(), m_jeu.getListMonstre(), "Choisissez la première entité à échanger");
                        EntiteCombat e2 = boogie.demanderEntite(m_jeu.getListPersonnage(), m_jeu.getListMonstre(), "Choisissez la seconde entité à échanger");
                        boogie.lancerSort(perso, e1, e2, carte);
                        actionEffectue = true;
                    } else {
                        System.out.println("Seuls les magiciens maîtrisent ce sort.");
                    }
                    afficherCarte();
                    break;

                case "arme":
                    if (optPerso.isEmpty()) {
                        System.out.println("❌ Seuls les personnages peuvent lancer des sorts.");
                        break;
                    }

                    Personnage lanceur = optPerso.get();

                    if (!lanceur.estDeClasse("Magicien") && !lanceur.estDeClasse("Clerc")) {
                        System.out.println("❌ Vous ne maîtrisez pas ce sort.");
                        break;
                    }

                    ArmeMagique armeMagique = new ArmeMagique();

                    // Demander la cible personnage
                    Personnage ciblePersonnage = armeMagique.demanderCiblePersonnage(m_jeu.getListPersonnage());
                    if (ciblePersonnage == null) {
                        System.out.println("Aucun personnage sélectionné.");
                        break;
                    }

                    // Construire la liste d'armes disponibles, y compris l'arme portée
                    List<Arme> armesDisponibles = new ArrayList<>();

                    Optional<Arme> armeEquipee = ciblePersonnage.getArmePortee();
                    if (armeEquipee.isPresent()) {
                        armesDisponibles.add(armeEquipee.get());
                        System.out.println("0 - " + armeEquipee.get().getNom() + " (équipée)");
                    }

                    List<Arme> armesInventaire = ciblePersonnage.getInventaire().getArmes();
                    for (int i = 0; i < armesInventaire.size(); i++) {
                        int index = armeEquipee.isPresent() ? i + 1 : i;
                        armesDisponibles.add(armesInventaire.get(i));
                        System.out.println(index + " - " + armesInventaire.get(i).getNom());
                    }

                    System.out.print("Choisissez une arme à améliorer : ");
                    int choix = scanner.nextInt();

                    if (choix < 0 || choix >= armesDisponibles.size()) {
                        System.out.println("❌ Choix invalide.");
                        break;
                    }

                    Arme armeChoisie = armesDisponibles.get(choix);

                    armeMagique.lancerSort(lanceur, ciblePersonnage, armeChoisie);

                    System.out.println("✨ " + lanceur.getNom() + " améliore l'arme " + armeChoisie.getNom() + " de " + ciblePersonnage.getNom() + " !");
                    actionEffectue = true;
                    break;

                default:
                    System.out.println("Commande inconnue, essayez encore.");
                    break;
            }

            if (actionEffectue) {
                if (conditionDeFin()) {
                    return true;
                }
                actionsRestantes--;
            }
        }
        System.out.println("Fin du tour pour " + entite.getNom());
        return false;
    }
    private boolean gererAttaque(EntiteCombat attaquant, Carte carte, String argument, Scanner scanner) {
        try {
            int[] coords = Utile.convertirCoordonnees(argument, carte.getLongueur());
            int x = coords[0];
            int y = coords[1];

            Optional<EntiteCombat> optCible = carte.getEntite(x, y);
            if (optCible.isEmpty()) {
                System.out.println("Aucune cible à cette position.");
                return false;
            }

            EntiteCombat cible = optCible.get();
            Combat combat = new Combat(attaquant, cible, carte);

            if (!combat.verifPortee()) {
                System.out.println("Cible hors de portée.");
                return false;
            }

            System.out.println("Lancer un dé de 20 (appuyer sur une touche)");
            scanner.nextLine();

            // Lance le d20 sans bonus (on veut juste le jet brut pour afficher)
            Dice dice = new Dice(1,20);
            int jetDe = dice.lancer();  // Crée un getter getDice() dans Combat si besoin
            int bonus = attaquant.getBonusAttaque();
            int jetAttaque = jetDe + bonus;

            System.out.println("Vous avez fait " + jetDe);
            System.out.println("Votre attaque est de " + jetDe + "+" + bonus + "=" + jetAttaque);

            int classeArmureCible = cible.getClasseArmure();
            if (jetAttaque >= classeArmureCible) {
                System.out.println("Votre attaque perce l'armure du " + cible.getNom() + " (" + classeArmureCible + ").");

                System.out.println("Lancer un dé de " + attaquant.getDeDegats() + " pour infliger des dégâts (appuyer sur une touche)");
                scanner.nextLine();

                int degats = (int)(Math.random() * attaquant.getDeDegats()) + 1;

                cible.subirDegats(degats);
                System.out.println("Le " + cible.getNom() + " subit " + degats + " dégâts !");
                System.out.println("Il lui reste " + cible.getPv() + " PV.");

            } else {
                System.out.println("Votre attaque ne perce pas l'armure du " + cible.getNom() + " (" + classeArmureCible + ").");
                System.out.println(attaquant.getNom() + " attaque vers " + argument.toUpperCase() + " mais rate.");
            }

            return true;
        } catch (Exception e) {
            System.out.println("Coordonnées invalides : utilisez le format A1, B3...");
            return false;
        }
    }


    private boolean gererDeplacement(EntiteCombat entite, Carte carte, String argument) {
        int[] destination = Utile.convertirCoordonnees(argument, carte.getLongueur());
        if (destination == null) {
            System.out.println("Coordonnées invalides. Format attendu : A1, B3...");
            return false;
        }

        int x2 = destination[0];
        int y2 = destination[1];
        int x = entite.getX();
        int y = entite.getY();

        if (!carte.estAccessible(x2, y2)) {
            System.out.println("La case " + argument.toUpperCase() + " est inaccessible.");
            return false;
        }

        boolean deplacementOk = m_placement.deplacerEntite(x, y, x2, y2, entite);
        if (deplacementOk) {
            System.out.println(entite.getNom() + " se déplace vers " + argument.toUpperCase() + ".");
            return true;
        } else {
            System.out.println("Déplacement impossible (vitesse insuffisante ou case occupée).");
            return false;
        }
    }
    public boolean finAction( Scanner scanner, EntiteCombat entite, Carte carte) {

        afficherChoixMaitreJeu();
        System.out.println("Tapez '-1' pour quitter.");
        boolean actionEffectue = false;

        while (true) {
            System.out.print("> ");
            String ligne = scanner.nextLine().trim();

            if (ligne.equals("-1")) {
                System.out.println("Fin de la phase de contrôle du maître du jeu.");
                break;  // Sort de la boucle si on tape -1
            }

            if (ligne.isEmpty()) {
                System.out.println("Commande vide, réessayez.");
                continue;
            }

            String[] parts = ligne.split(" ", 2);
            String commande = parts[0].toLowerCase();
            String argument = parts.length > 1 ? parts[1].trim() : "";

            switch (commande) {
                case "mv":
                    String[] mvArgs = argument.split(" ");
                    if (mvArgs.length != 2) {
                        System.out.println("Utilisation : mv <PositionActuelle> <NouvellePosition> (ex : mv A1 B2)");
                        break;
                    }

                    int[] posActuelle = Utile.convertirCoordonnees(mvArgs[0], carte.getLongueur());
                    int[] posNouvelle = Utile.convertirCoordonnees(mvArgs[1], carte.getLongueur());

                    if (posActuelle == null || posNouvelle == null) {
                        System.out.println("Coordonnées invalides. Utilisez le format A1, B2...");
                        break;
                    }

                    int xa = posActuelle[0];
                    int ya = posActuelle[1];
                    int xb = posNouvelle[0];
                    int yb = posNouvelle[1];

                    Optional<EntiteCombat> optEntite = carte.getEntite(xa, ya);
                    if (optEntite.isEmpty()) {
                        System.out.println("Aucune entité à déplacer en " + mvArgs[0].toUpperCase());
                        break;
                    }

                    EntiteCombat cibleAMv = optEntite.get();

                    if (!carte.estAccessible(xb, yb)) {
                        System.out.println("La destination " + mvArgs[1].toUpperCase() + " est inaccessible.");
                        break;
                    }

                    boolean deplace = m_placement.deplacerEntite(xa, ya, xb, yb, cibleAMv);
                    if (deplace) {
                        System.out.println("📦 Le maître du jeu déplace " + cibleAMv.getNom() + " de " + mvArgs[0].toUpperCase() +
                                " vers " + mvArgs[1].toUpperCase() + ".");
                        afficherCarte();
                    } else {
                        System.out.println("Déplacement impossible : la case est occupée ou bloquée.");
                    }
                    actionEffectue = true;
                    break;

                case "dg":
                    // Exemple : dg <cible> <nombreDeDes> <faces>
                    String[] dgArgs = argument.split(" ");
                    if (dgArgs.length != 3) {
                        System.out.println("Utilisation : dg <nomCible> <nbDes> <faces>");
                        break;
                    }
                    String nomCible = dgArgs[0];
                    int nbDes, faces;
                    try {
                        nbDes = Integer.parseInt(dgArgs[1]);
                        faces = Integer.parseInt(dgArgs[2]);
                    } catch (NumberFormatException e) {
                        System.out.println("Nombre de dés et nombre de faces doivent être des nombres entiers.");
                        break;
                    }

                    Optional<EntiteCombat> cibleOpt = carte.getEntite(nomCible);
                    if (cibleOpt.isEmpty()) {
                        System.out.println("Cible inconnue : " + nomCible);
                        break;
                    }

                    EntiteCombat cible = cibleOpt.get();
                    Dice dice = new Dice(nbDes, faces);
                    int degats = dice.lancer();
                    cible.subirDegats(degats);

                    System.out.println("Le maître du jeu inflige " + degats + " dégâts à " + cible.getNom() + ".");
                    actionEffectue = true;
                    break;

                case "x":
                    // Exemple : x <coordonnée> (ex : x B3)
                    if (argument.isEmpty()) {
                        System.out.println("Utilisation : x <coordonnée> (ex : x B3)");
                        break;
                    }

                    int[] pos = Utile.convertirCoordonnees(argument, carte.getLongueur());
                    if (pos == null) {
                        System.out.println("Coordonnées invalides.");
                        break;
                    }
                    int x = pos[0];
                    int y = pos[1];

                    boolean ajoutObstacle = carte.setObstacle(x, y);
                    if (ajoutObstacle) {
                        System.out.println("Obstacle ajouté en " + argument.toUpperCase() + ".");
                        afficherCarte();
                    } else {
                        System.out.println("Impossible d'ajouter un obstacle ici.");
                    }
                    actionEffectue = true;
                    break;

                default:
                    System.out.println("Commande inconnue, essayez encore.");
                    break;
            }
            if (actionEffectue)
            {
                if(conditionDeFin())
                {
                    return true;
                }
            }
        }
        return false;
    }
    public void afficherFin() {

        System.out.println(CYAN + "========================================" + RESET);
        System.out.println(BOLD + CYAN + "         🏰 FIN DE L'AVENTURE 🏰         " + RESET);
        System.out.println(CYAN + "========================================\n" + RESET);

        if(m_jeu.tousLesMonstresMorts()) {
            if (m_jeu.getListPersonnage().size() == 1) {
                System.out.println(VERT + BOLD + "🎉 Félicitations, vaillant héros solitaire ! 🎉\n" + RESET);
                System.out.println("Tu as traversé seul les " + JAUNE + "trois donjons les plus dangereux" + RESET + ",");
                System.out.println("terrassé des " + ROUGE + "monstres" + RESET + " et surmonté tous les obstacles.\n");
                System.out.println(MAGENTA + "🌟 Ton nom résonnera à jamais dans les couloirs du royaume... 🌟\n" + RESET);
            } else {
                System.out.println(VERT + BOLD + "🎉 Félicitations à votre équipe de héros ! 🎉\n" + RESET);
                System.out.println("Ensemble, vous avez vaincu les " + JAUNE + "trois donjons" + RESET + ",");
                System.out.println("bravé les " + ROUGE + "pièges" + RESET + " et terrassé les créatures les plus redoutables.\n");
                System.out.println(MAGENTA + "🌟 Vos noms sont gravés dans la légende du royaume ! 🌟\n" + RESET);
            }
        }

        System.out.println(CYAN + "========================================" + RESET);
        System.out.println(BOLD + BLEU + "         MERCI D’AVOIR JOUÉ !" + RESET);
        System.out.println(CYAN + "========================================" + RESET);
    }
    public boolean conditionDeFin()
    {
        if (m_jeu.personnageMort()) {
            System.out.println("\n===== Partie terminée ! =====");
            System.out.println("Un personnage est mort. Vous avez perdu !");
            return true;
        } else if (m_jeu.tousLesMonstresMorts()) {
            System.out.println("\n===== Donjon terminée ! =====");
            System.out.println("Tous les monstres sont vaincus. Vous avez gagné !");
            return true;
        }
        return false;
    }
    @Override
    public String toString() {
        return "AfficheurTour: affichage du tour en cours";
    }

}