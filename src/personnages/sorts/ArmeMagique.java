package personnages.sorts;

import monstres.Monstre;
import personnages.Personnage;
import personnages.equipements.armes.Arme;

import java.util.List;
import java.util.Scanner;

public class ArmeMagique implements Sort {

    @Override
    public String getNom() {
        return "Arme magique";
    }
    public Personnage demanderCiblePersonnage(List<Personnage> personnages)
    {
        System.out.println("Choisissez un personnage cible :");
        for (int i = 0; i < personnages.size(); i++) {
            System.out.println(i + " : " + personnages.get(i).getNom());
        }
        System.out.print("Votre choix : ");
        Scanner scanner = new Scanner(System.in);
        try {
            int choix = Integer.parseInt(scanner.nextLine());
            if (choix >= 0 && choix < personnages.size()) {
                return personnages.get(choix);
            }
        } catch (NumberFormatException e) {
            // Ignorer
        }
        return null; // choix invalide
    }

    // Méthode pour choisir une arme dans l'inventaire d'un personnage
    public Arme demanderArme(Personnage personnage)
    {
        List<Arme> armes = personnage.getInventaire().getArmes();
        if (armes.isEmpty()) {
            System.out.println(personnage.getNom() + " ne possède aucune arme.");
            return null;
        }
        System.out.println("Choisissez une arme à améliorer :");
        for (int i = 0; i < armes.size(); i++) {
            System.out.println(i + " : " + armes.get(i).getNom());
        }
        System.out.print("Votre choix : ");
        Scanner scanner = new Scanner(System.in);
        try {
            int choix = Integer.parseInt(scanner.nextLine());
            if (choix >= 0 && choix < armes.size()) {
                return armes.get(choix);
            }
        } catch (NumberFormatException e) {
            // Ignorer
        }
        return null; // choix invalide
    }
    // Méthode à appeler pour lancer le sort, applique le bonus
    public void lancerSort(Personnage lanceur, Personnage cible, Arme arme) {
        // Augmente les bonus cumulés de l'arme
        arme.augmenterBonusAttaque(1);
        arme.augmenterBonusDegats(1);
    }
}
