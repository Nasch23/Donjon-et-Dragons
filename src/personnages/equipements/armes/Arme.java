package personnages.equipements.armes;
import java.util.Optional;
import mecaniques.Dice;
import personnages.Inventaire;
import personnages.Personnage;
import personnages.equipements.EffetEquipement;
import personnages.equipements.Equipement;

public class Arme implements Equipement
{
    private Dice m_dice;
    private int m_portee;
    private String m_nom;
    private Optional<EffetEquipement> m_effetEquipement;
    private int m_x;
    private int m_y;

    public Arme (String nom, Dice dice, int portee, Optional<EffetEquipement> effetEquipement)
    {
        this.m_nom = nom;
        this.m_portee = portee;
        this.m_dice = dice;
        this.m_effetEquipement = effetEquipement;
    }
    public int getDegat()
    {
        return m_dice.lancer();
    }
    public int getPortee()
    {
        return m_portee;
    }
    public void augmenterBonusAttaque(int valeur) {
        m_effetEquipement.ifPresent(effet -> effet.augmenterBonusAttaque(valeur));
    }

    public void augmenterBonusDegats(int valeur) {
        m_effetEquipement.ifPresent(effet -> effet.augmenterBonusDegats(valeur));
    }
    @Override
    public String getNom()
    {
        return m_nom;
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
        inventaire.ajouterArme(this); // si c'est une Arme
    }
    @Override
    public void equipeSurCePersonnage(Personnage p) {
        p.getInventaire().equiperArme(this);
    }
    @Override
    public boolean estArme() {
        return true;
    }
    @Override
    public boolean estArmure() { return false; }

    @Override
    public String toString() {
        return "Arme{" +
                "nom='" + m_nom + '\'' +
                ", degats=" + m_dice +
                ", portee=" + m_portee +
                ", effetEquipement=" + (m_effetEquipement.isPresent() ? m_effetEquipement.get() : "aucun") +
                ", position=(" + m_x + ", " + m_y + ")" +
                '}';
    }


}
