package personnages.equipements.armures;

import java.util.Optional;
import personnages.Inventaire;
import personnages.Personnage;
import personnages.equipements.EffetEquipement;
import personnages.equipements.Equipement;

public class Armure implements Equipement
{
    private String m_nom;
    private int m_classeArmure;
    private Optional<EffetEquipement> m_effetEquipement;
    private int m_x;
    private int m_y;

    public Armure(String nom, int classeArm, Optional<EffetEquipement> effetEquipement)
    {
        this.m_nom = nom;
        this.m_classeArmure = classeArm;
        this.m_effetEquipement = effetEquipement;
    }
    @Override
    public String getNom()
    {
        return m_nom;
    }
    public int getClasseArmure()
    {
        return m_classeArmure;
    }
    @Override
    public void setPosition(int x, int y)
    {
        this.m_x = x;
        this.m_y = y;
    }
    @Override
    public int getX() { return m_x; }
    @Override
    public int getY() { return m_y; }
    @Override
    public void ajouterAInventaire(Inventaire inventaire) 
    {
        inventaire.ajouterArmure(this);
    }
    @Override
    public void equipeSurCePersonnage(Personnage p) {
        p.getInventaire().equiperArmure(this);
    }
    @Override
    public boolean estArme() {
        return false;
    }
    @Override
    public boolean estArmure() { return true; }
    @Override
    public String toString() {
        return "Armure{" +
                "nom='" + m_nom + '\'' +
                ", classeArmure=" + m_classeArmure +
                ", effetEquipement=" + (m_effetEquipement.isPresent() ? m_effetEquipement.get() : "aucun") +
                ", position=(" + m_x + ", " + m_y + ")" +
                '}';
    }


}
