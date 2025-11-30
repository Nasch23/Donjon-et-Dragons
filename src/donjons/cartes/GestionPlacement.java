package donjons.cartes;

import donjons.EntiteCombat;
import personnages.equipements.Equipement;

public class GestionPlacement 
{
    private Carte m_carte;

    public GestionPlacement(Carte carte)
    {
        this.m_carte = carte;
    }
    public void placerEntite(int x, int y, EntiteCombat entite) {
    Case cible = m_carte.getCase(x, y);
    if (!cible.estLibre()) 
    {
        throw new IllegalArgumentException("Impossible de placer l'entité : case occupée.");
    }

    entite.placerDansCase(cible);
    }

    public void placerEquipement(int x, int y, Equipement equipement)
    {
        Case cible = m_carte.getCase(x, y);
        if (cible.estLibre()) 
        {
            cible.placerEquipement(equipement);
        } else {
            throw new IllegalArgumentException("Impossible de placer l'équipement : case occupée.");
        }
    }
    public double calculDeplacement(int x, int y, int x2, int y2)
    {
        int dx = x2 - x;
        int dy = y2 - y;
        double distance = Math.sqrt((dx * dx) + (dy * dy));
        return distance;
    }
    public boolean deplacerEntite(int x, int y, int x2, int y2, EntiteCombat entite) {

        double distance = calculDeplacement(x, y, x2, y2);
        if (distance > entite.getVitesse()) {
            return false;  // déplacement trop long
        }
        Case caseDestination = m_carte.getCase(x2, y2);
        if (!caseDestination.estLibre()) {
            return false;  // case occupée
        }
        m_carte.getCase(x, y).viderCase();

        entite.placerDansCase(caseDestination);

        entite.setPosition(x2, y2);

        return true;
    }
    public boolean echangerPositions(int x1, int y1, int x2, int y2) {
        Case case1 = m_carte.getCase(x1, y1);
        Case case2 = m_carte.getCase(x2, y2);

        EntiteCombat entite1 = case1.getEntiteCombat();
        EntiteCombat entite2 = case2.getEntiteCombat();

        // Vérifier que les deux cases contiennent bien une entité
        if (entite1 == null || entite2 == null) {
            return false;
        }

        // Vider les deux cases avant d'échanger
        case1.viderCase();
        case2.viderCase();

        // Placer entite1 dans case2 et mettre à jour sa position
        entite1.placerDansCase(case2);
        entite1.setPosition(x2, y2);

        // Placer entite2 dans case1 et mettre à jour sa position
        entite2.placerDansCase(case1);
        entite2.setPosition(x1, y1);

        return true;
    }

    @Override
    public String toString() {
        return "GestionPlacement: carte de taille " + m_carte.getLongueur() + "x" + m_carte.getLargeur();
    }

}