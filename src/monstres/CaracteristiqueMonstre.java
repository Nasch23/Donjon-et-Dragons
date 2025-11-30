package monstres;

import mecaniques.Dice;

public class CaracteristiqueMonstre
{
    private int m_pv;
    private int m_pvMax;
    private int m_force;
    private int m_vitesse;
    private int m_dexterite;
    private int m_initiative;
    private int m_classeArmure;

    public CaracteristiqueMonstre(int pv, int force, int vitesse, int m_dexterite, int initiative, int classeArmure)
    {
        Dice diceInit = new Dice(1,20);
        this.m_pv = pv;
        this.m_force = force;
        this.m_vitesse = vitesse /3;
        this.m_dexterite = m_dexterite;
        this.m_initiative = initiative + diceInit.lancer();
        this.m_classeArmure = classeArmure;
        this.m_pvMax = m_pv;
    }
    public int getPv()
    {
        return m_pv;
    }
    public int setPv(int pv)
    {
        return m_pv = pv;
    }
    public int getForce()
    {
        return m_force;
    }
    public int setForce(int force)
    {
        return  m_force = force;
    }
    public int getVitesse()
    {
        return m_vitesse;
    }
    public int getDexterite()
    {
        return m_dexterite;
    }
    public int setDexterite(int dexterite)
    {
        return m_dexterite = dexterite;
    }
    public int getInitiative()
    {
        return m_initiative;
    }
    public int getClasseArmure()
    {
        return m_classeArmure;
    }
    public int getPvMax()
    {
        return m_pvMax;
    }
    @Override
    public String toString() {
        return "CaracteristiqueMonstre{" +
                "PV=" + m_pv +
                "/" + m_pvMax +
                ", Force=" + m_force +
                ", Vitesse=" + m_vitesse +
                ", Dextérité=" + m_dexterite +
                ", Initiative=" + m_initiative +
                ", ClasseArmure=" + m_classeArmure +
                '}';
    }

}
