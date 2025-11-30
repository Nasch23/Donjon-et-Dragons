package donjons.cartes;

import donjons.EntiteCombat;

import java.util.Optional;

public class Carte
{
    private int m_longueur;
    private int m_largeur;
    private Case[][] m_grille;

    public Carte(int longueur, int largeur)
    {
        this.m_longueur = longueur;
        this.m_largeur = largeur;
        this.m_grille = new Case[m_largeur][m_longueur];

        for (int y = 0 ; y < m_largeur ; y++)
        {
            for (int x = 0 ; x < m_longueur ; x++)
            {
                m_grille[y][x] = new Case();
            }
        }
    }

    public Case getCase(int x, int y)
    {
        if (x < 0 || x >= m_longueur || y < 0 || y >= m_largeur)
        {
            throw new IndexOutOfBoundsException("Coordonnées hors limites.");
        }
        return m_grille[y][x];
    }

    public boolean setObstacle(int x, int y)
    {
        if (x < 0 || x >= m_longueur || y < 0 || y >= m_largeur)
        {
            return false;
        } else {
            getCase(x, y).placerObstacle();
            return true;
        }
    }

    public int getLongueur()
    {
        return m_longueur;
    }

    public int getLargeur()
    {
        return m_largeur;
    }

    public void genererObstacles()
    {
        // TODO: Implémenter la génération automatique d'obstacles
    }

    // Retourne une entité (personnage ou monstre) présente sur la case (x,y) si elle existe
    public Optional<EntiteCombat> getEntite(int x, int y) {
        Case c = getCase(x, y);

        if (c.getPersonnage().isPresent()) {
            return Optional.of(c.getPersonnage().get());
        } else if (c.getMonstre().isPresent()) {
            return Optional.of(c.getMonstre().get());
        } else {
            return Optional.empty();
        }
    }
    public Optional<EntiteCombat> getEntite(String nom) {
        // Parcourir toutes les cases de la carte
        for (int x = 0; x < m_longueur; x++) {
            for (int y = 0; y < m_largeur; y++) {
                Case c = getCase(x, y);

                if (c.getPersonnage().isPresent() && c.getPersonnage().get().getNom().equalsIgnoreCase(nom)) {
                    return Optional.of(c.getPersonnage().get());
                }

                if (c.getMonstre().isPresent() && c.getMonstre().get().getNom().equalsIgnoreCase(nom)) {
                    return Optional.of(c.getMonstre().get());
                }
            }
        }
        return Optional.empty();
    }
    public void genererEtPlacerEquipementsAleatoirement(GestionPlacement gestionPlacement) {
        // Ne rien faire
    }
    // Vérifie si la case est accessible (dans les limites, pas d'obstacle, pas d'entité)
    public boolean estAccessible(int x, int y) {
        if (x < 0 || x >= m_longueur || y < 0 || y >= m_largeur) {
            return false;
        }
        Case c = m_grille[y][x];
        return !c.estObstacle() && c.getEntiteCombat() == null;
    }
    public int compterObstacles() {
        int count = 0;
        for (int x = 0; x < m_longueur; x++) {
            for (int y = 0; y < m_largeur; y++) {
                if (m_grille[y][x].estObstacle()) {
                    count++;
                }
            }
        }
        return count;
    }

    public int compterPersonnages() {
        int count = 0;
        for (int x = 0; x < m_longueur; x++) {
            for (int y = 0; y < m_largeur; y++) {
                if (m_grille[y][x].contientPersonnage()) {
                    count++;
                }
            }
        }
        return count;
    }

    public int compterMonstres() {
        int count = 0;
        for (int x = 0; x < m_longueur; x++) {
            for (int y = 0; y < m_largeur; y++) {
                if (m_grille[y][x].contientMonstre()) {
                    count++;
                }
            }
        }
        return count;
    }

    public int compterEquipements() {
        int count = 0;
        for (int x = 0; x < m_longueur; x++) {
            for (int y = 0; y < m_largeur; y++) {
                if (m_grille[y][x].contientEquipement()) {
                    count++;
                }
            }
        }
        return count;
    }
    @Override
    public String toString() {
        return "Carte [" + m_longueur + "x" + m_largeur + "] - Obstacles: " + compterObstacles() +
                ", Personnages: " + compterPersonnages() +
                ", Monstres: " + compterMonstres() +
                ", Equipements: " + compterEquipements();
    }
}
