package personnages.sorts;
import personnages.Personnage;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Guerison implements Sort {
    private final Random rand = new Random();

    @Override
    public String getNom() {
        return "Guérison";
    }

    public Personnage demanderCiblePersonnage(List<Personnage> personnages, Personnage lanceur)
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println("🔍 Qui souhaitez-vous soigner ?");
        for (int i = 0; i < personnages.size(); i++) {
            Personnage p = personnages.get(i);
            System.out.println("  " + (i + 1) + ". " + p.getNom() + " (PV : " + p.getPvActuels() + "/" + p.getClasse().getPv() + ")");
        }

        System.out.print("👉 Entrez le numéro du personnage à soigner (ou vide pour vous-même) : ");
        String input = scanner.nextLine().trim();

        try {
            if (input.isEmpty()) {
                return lanceur;
            }
            int index = Integer.parseInt(input) - 1;
            if (index >= 0 && index < personnages.size()) {
                return personnages.get(index);
            }
        } catch (NumberFormatException e) {
            // Ignoré
        }
        System.out.println("⚠ Entrée invalide, vous vous soignez vous-même par défaut.");
        return lanceur;
    }

}
