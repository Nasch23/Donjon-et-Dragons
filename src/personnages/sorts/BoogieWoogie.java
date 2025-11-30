package personnages.sorts;

import donjons.EntiteCombat;
import donjons.cartes.Carte;
import donjons.cartes.GestionPlacement;
import monstres.Monstre;
import personnages.Personnage;

import java.util.List;
import java.util.Scanner;

public class BoogieWoogie implements Sort {

    @Override
    public String getNom() {
        return "Boogie Woogie";
    }

    public EntiteCombat demanderEntite(List<Personnage> personnages, List<Monstre> monstres, String message)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println(message);

        // Affichage clair de toutes les entités disponibles avec un index
        int index = 1;
        System.out.println("Personnages :");
        for (Personnage p : personnages) {
            System.out.println(" " + index + " - " + p.getNom());
            index++;
        }
        System.out.println("Monstres :");
        for (Monstre m : monstres) {
            System.out.println(" " + index + " - " + m.getNom());
            index++;
        }

        int choix = -1;
        int total = personnages.size() + monstres.size();

        while (choix < 1 || choix > total) {
            System.out.print("Entrez le numéro correspondant : ");
            if (scanner.hasNextInt()) {
                choix = scanner.nextInt();
                if (choix < 1 || choix > total) {
                    System.out.println("Choix invalide, réessayez.");
                }
            } else {
                scanner.next(); // consomme l'entrée invalide
                System.out.println("Veuillez entrer un nombre.");
            }
        }

        if (choix <= personnages.size()) {
            return personnages.get(choix - 1);
        } else {
            return monstres.get(choix - 1 - personnages.size());
        }
    }

    // méthode pour lancer le sort, échange les positions des deux entités
    public void lancerSort(Personnage lanceur, EntiteCombat e1, EntiteCombat e2, Carte carte)
    {
        System.out.println(lanceur.getNom() + " lance Boogie Woogie pour échanger la position de " + e1.getNom() + " et " + e2.getNom() + ".");

        // On stocke les positions des deux entités
        int x1 = e1.getX();
        int y1 = e1.getY();
        int x2 = e2.getX();
        int y2 = e2.getY();

        // Échanger les positions (supposons que tu as une méthode pour ça dans GestionPlacement)
        GestionPlacement gestionPlacement = new GestionPlacement(carte);
        boolean ok = gestionPlacement.echangerPositions(x1, y1, x2, y2);
        if (ok) {
            System.out.println("Positions échangées avec succès !");
        } else {
            System.out.println("Échange impossible.");
        }

    }
}

