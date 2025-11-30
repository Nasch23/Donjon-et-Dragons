import donjons.EntiteCombat;
import donjons.affichages.AffichageMiseEnPlace;
import donjons.affichages.AfficheurEntite;
import donjons.affichages.AfficheurTour;
import donjons.cartes.CarteDefaut;
import donjons.cartes.Case;
import donjons.cartes.GestionPlacement;
import mecaniques.Jeu;
import mecaniques.Tour;
import monstres.Monstre;
import personnages.Personnage;
import personnages.equipements.Equipement;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bienvenue dans Donjon et Dragons");

        // Initialisation fixe pour toute la session de jeu
        CarteDefaut carteDefaut = new CarteDefaut();
        GestionPlacement placement = new GestionPlacement(carteDefaut);
        AfficheurEntite afficheurEntite = new AfficheurEntite();
        AffichageMiseEnPlace miseEnPlace = new AffichageMiseEnPlace(carteDefaut, carteDefaut);
        AfficheurEntite preparation = new AfficheurEntite();
        AfficheurTour afficheurTour = null;  // déclaration avant la boucle
        Case c = new Case();

        // Création des personnages une seule fois
        preparation.creerPersonnages(scanner);
        List<Personnage> personnages = preparation.getPersonnages();

        if (personnages.isEmpty()) {
            System.out.println("Aucun personnage créé, fin du programme.");
            scanner.close();
            return;
        }

        int donjonsReussis = 0;  // compteur de donjons terminés avec succès

        while (donjonsReussis < 3) {
            System.out.println("\n--- Passage au prochain donjon ! ---");

            // Soigner les personnages survivants
            for (Personnage p : personnages) {
                p.soigner(); // suppose que la méthode existe dans Personnage
            }

            // Génération de nouveaux monstres et équipements
            preparation.getMonstres().clear();
            preparation.getEquipements().clear();
            preparation.creerMonstres(scanner);
            List<Monstre> monstres = preparation.getMonstres();

            if (monstres.isEmpty()) {
                System.out.println("Aucun monstre créé, fin du programme.");
                break;
            }
            c.attribuerNumerosMonstres(preparation.getMonstres());
            List<Equipement> equipements = preparation.getEquipements();

            afficheurEntite.demanderEquipementInitial(personnages);

            // Réinitialiser carte et placement
            carteDefaut.reinitialiserCarte(); // crée cette méthode si besoin
            placement = new GestionPlacement(carteDefaut);

            miseEnPlace.afficherIntroduction(personnages, monstres, equipements);
            carteDefaut.afficherCarte();

            // Préparer un nouveau tour
            Tour tour = new Tour();
            for (EntiteCombat e : personnages) tour.ajouterEntite(e);
            for (EntiteCombat e : monstres) tour.ajouterEntite(e);
            tour.trierParInitiative();

            int numeroDonjon = donjonsReussis + 1;
            Jeu jeu = new Jeu(personnages, monstres, carteDefaut, placement, tour, null, numeroDonjon);
            afficheurTour = new AfficheurTour(carteDefaut, placement, tour, jeu, afficheurEntite);
            // Mettre à jour l'afficheurTour dans le jeu
            jeu.setAfficheurTour(afficheurTour);

            boolean victoire = jeu.lancerBoucleDeJeu();

            if (victoire) {
                donjonsReussis++;
                System.out.println("Donjon réussi ! (" + donjonsReussis + "/3)");
            } else {
                System.out.println("Les joueurs ont perdu, fin du jeu.");
                break;
            }
        }
        afficheurTour.afficherFin();
    }
}