package mecaniques;

import donjons.EntiteCombat;
import donjons.cartes.Carte;
import donjons.cartes.GestionPlacement;

public class Combat {

    private EntiteCombat m_attaquant;
    private EntiteCombat m_cible;
    private Carte m_carte;
    private Dice m_dice;
    private GestionPlacement m_distance = new GestionPlacement(m_carte);

    public Combat(EntiteCombat attaquant, EntiteCombat cible, Carte carte)
    {
        this.m_attaquant = attaquant;
        this.m_cible = cible;
        this.m_carte = carte;
        this.m_dice = new Dice(1, 20);
    }

    public boolean verifPortee() 
    {
        double distance = m_distance.calculDeplacement(m_attaquant.getX(), m_attaquant.getY(), m_cible.getX(), m_cible.getY());
        return m_attaquant.getPortee() >= distance;
    }
    public int attaquer() 
    {
        if (!m_attaquant.peutAttaquer(m_cible)) { throw new IllegalArgumentException("L'attaque ne peux pas être effectué"); }
        if (!verifPortee()) { return 0; }

        int jetAttaque = m_dice.lancer() + m_attaquant.getBonusAttaque();
        if (jetAttaque >= m_cible.getClasseArmure()) 
        {
            int degats = m_attaquant.getDeDegats();
            m_cible.subirDegats(degats);
            return degats;
        }
        return 0;
    }
    @Override
    public String toString() {
        return "Combat entre " + m_attaquant.getNom() +
                " (attaquant) et " + m_cible.getNom() +
                " (cible), portée vérifiée : " + verifPortee();
    }
}