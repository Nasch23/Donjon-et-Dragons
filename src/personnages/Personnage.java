package personnages;

import donjons.EntiteCombat;
import donjons.cartes.Case;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mecaniques.Dice;
import personnages.classes.*;
import personnages.equipements.armes.Arme;
import personnages.equipements.armures.Armure;
import personnages.races.*;
import personnages.sorts.Sort;

public class Personnage implements EntiteCombat 
{
    private String m_nom;
    private Classe m_classe;
    private Race m_race;
    private Caracteristique m_caracteristique;
    private Inventaire m_inventaire;
    private int m_x;
    private int m_y;
    private List<Sort> m_sorts = new ArrayList<>();

    public Personnage(String nom, Classe classe, Race race) 
    {
        this.m_nom = nom;
        this.m_classe = classe;
        this.m_race = race;
        this.m_caracteristique = new Caracteristique(classe, race);
        this.m_inventaire = classe.attribuerEquipements();
    }

    @Override
    public String getNom() 
    {
        return m_nom;
    }

    @Override
    public int getInitiative() 
    {
        return m_caracteristique.getInitiative();
    }

    @Override
    public boolean estVivant() 
    {
        return m_caracteristique.getPv() > 0;
    }

    @Override
    public int lancerInitiative(Dice dice) 
    {
        return dice.lancer() + m_caracteristique.getInitiative();
    }

    @Override
    public int getVitesse() 
    {
        return m_caracteristique.getVitesse();
    }

    @Override
    public void setPosition(int x, int y) 
    {
        this.m_x = x;
        this.m_y = y;
    }
    @Override
    public int getX() 
    {
        return m_x;
    }

    @Override
    public int getY()
    {
        return m_y;
    }

    @Override
    public void placerDansCase(Case c) 
    {
        c.placerPersonnage(this);
    }

    @Override
    public int getPortee() 
    {
        return m_inventaire.getArmePortee().map(Arme::getPortee).orElse(1);
    }
    @Override
    public boolean peutAttaquer(EntiteCombat cible) { return cible.estMonstre(); }
    @Override
    public int getBonusAttaque() 
    {
        return (getPortee() == 1) ? m_caracteristique.getForce() : m_caracteristique.getDexterite();
    }
    @Override
    public int getClasseArmure() 
    {
        if (m_inventaire.getArmurePortee().isPresent()) 
        {
            Armure armure = m_inventaire.getArmurePortee().get();
            return armure.getClasseArmure();
        } else 
        {
            return 0;
        }
    }
    @Override
    public int getDeDegats()
    {
        if (m_inventaire.getArmePortee().isPresent()) 
        {
            Arme arme = m_inventaire.getArmePortee().get();
            return arme.getDegat();
        } else {
            return 0;
        }
    }
    @Override
    public int getPv() 
    {
        return m_caracteristique.getPv();
    }

    @Override
    public void subirDegats(int degats) 
    {
        m_caracteristique.setPv(Math.max(0, m_caracteristique.getPv() - degats));
    }

    @Override
    public int getPvMax()
    {
        return m_caracteristique.getPvMax();
    }
    @Override
    public int getForce()
    {
        return m_caracteristique.getForce();
    }
    @Override
    public int getDexterite()
    {
        return m_caracteristique.getDexterite();
    }
    @Override
    public boolean estMonstre()
    {
        return false;
    }
    @Override
    public boolean estPersonnage()
    {
      return true;
    }
    @Override
    public String getDescriptionPourOrdreTour() {
        return String.format("(%s %s, %d/%d)", getRace(), getClasse(), getPv(), getPvMax());
    }
    @Override
    public String getSymbolePourAffichage() {
        return getNom().substring(0, Math.min(3, getNom().length()));
    }
    @Override
    public Dice getDesDegats() {
        Dice dice = new Dice (1,20);
        return dice;
    }
    public boolean estDeClasse(String nom) {
        return this.m_classe.getNom().equalsIgnoreCase(nom);
    }
    public int getPvActuels() {
        return m_caracteristique.getPvActuels();
    }
    public boolean peutLancerSort(String nomSort)
    {
        String classeNom = this.getClasse().getNom();

        if (classeNom.equalsIgnoreCase("Clerc")) {
            // Clerc ne maîtrise que "guerison"
            return nomSort.equalsIgnoreCase("guerison");
        } else if (classeNom.equalsIgnoreCase("Magicien")) {
            // Magicien maîtrise tous les sorts
            return true;
        }
        // Autres classes ne maîtrisent pas les sorts
        return false;
    }
    public void setPvActuels(int pvActuels)
    {
        // On s'assure de ne pas dépasser le max ni descendre en dessous de 0
        if (pvActuels > getPvMax()) {
            m_caracteristique.setPvActuels(getPvMax());
        } else if (pvActuels < 0) {
            m_caracteristique.setPvActuels(0);
        } else {
            m_caracteristique.setPvActuels(pvActuels);
        }
    }
    public void soigner()
    {
        this.m_caracteristique.setPv(m_caracteristique.getPvMax());
    }
    public Classe getClasse() 
    {
        return m_classe;
    }

    public Race getRace() 
    {
        return m_race;
    }

    public Optional<Arme> getArmePortee()
    {
        return m_inventaire.getArmePortee();
    }

    public Optional<Armure> getArmurePortee() 
    {
        return m_inventaire.getArmurePortee();
    }

    public Inventaire getInventaire() 
    {
        return m_inventaire;
    }

    public void equiperArmure(Armure armure) 
    {
        m_inventaire.equiperArmure(armure);
    }
    @Override
    public String toString() {
        return String.format("%s (%s %s)", m_nom, m_race.getNom(), m_classe.getNom().toLowerCase());
    }
}
