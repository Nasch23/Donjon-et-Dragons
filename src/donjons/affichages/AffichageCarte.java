package donjons.affichages;

import donjons.cartes.Carte;
import donjons.cartes.Case;

public class AffichageCarte {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";       // personnage
    private static final String ANSI_RED = "\u001B[31m";         // monstre
    private static final String ANSI_YELLOW = "\u001B[33m";      // équipement
    private static final String ANSI_GRAY = "\u001B[37m";        // case vide
    private static final String ANSI_BG_GRAY = "\u001B[100m";    // obstacle

    private Carte m_carte;

    public AffichageCarte(Carte carte) {
        this.m_carte = carte;
    }

    public void afficherCarte() {
        System.out.println(this.toString());
    }

    private String couleurPourCase(Case c) {
        if (c.getPersonnage().isPresent()) return ANSI_GREEN;
        if (c.getMonstre().isPresent()) return ANSI_RED;
        if (c.getEquipement().isPresent()) return ANSI_YELLOW;
        if (c.estObstacle()) return ANSI_BG_GRAY + ANSI_GRAY;  // texte gris sur fond gris foncé
        return ANSI_GRAY;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        int longueur = m_carte.getLongueur();
        int largeur = m_carte.getLargeur();

        // En-tête alphabétique des colonnes
        sb.append("    ");
        for (int x = 0; x < longueur; x++) {
            sb.append("  ").append((char) ('A' + x)).append("  ");
        }
        sb.append("\n");

        // Bordure supérieure
        sb.append("   *");
        for (int x = 0; x < longueur; x++) {
            sb.append("-----");
        }
        sb.append("*\n");

        // Corps de la carte
        for (int y = 0; y < largeur; y++) {
            sb.append(String.format("%2d |", y + 1));
            for (int x = 0; x < longueur; x++) {
                Case c = m_carte.getCase(x, y);
                sb.append(" ").append(couleurPourCase(c)).append(c.toString()).append(ANSI_RESET).append(" ");
            }
            sb.append("|\n");
        }

        // Bordure inférieure
        sb.append("   *");
        for (int x = 0; x < longueur; x++) {
            sb.append("-----");
        }
        sb.append("*\n");

        // Légende
        sb.append("\nLégende :\n");
        sb.append(ANSI_GREEN).append("NOM").append(ANSI_RESET).append(" Personnage (3 premières lettres du nom)\n");
        sb.append(ANSI_RED).append(":X ").append(ANSI_RESET).append(" Monstre (symbole variable, ex : :@, :>, :D...)\n");
        sb.append(ANSI_YELLOW).append(" | ").append(ANSI_RESET).append(" Equipement\n");
        sb.append(ANSI_BG_GRAY).append(" X ").append(ANSI_RESET).append(" Obstacle\n");
        sb.append(" .  Case vide\n");

        return sb.toString();
    }
}
