package personnages;
import mecaniques.Dice;
import personnages.classes.*;
import personnages.races.*;

public class Caracteristique
{
    private int m_pv;
    private int m_pvActuels;
    private int m_pvMax;
    private int m_force;
    private int m_dexterite;
    private int m_vitesse;
    private int m_initiative;

    public Caracteristique(Classe classe, Race race)
    {
        Dice dice = new Dice(4,4);
        Dice diceInit = new Dice(1,2);

        // pv correspondant à la classe choisie
        this.m_pv = classe.getPv();
        this.m_pvMax = m_pv;
        this.m_pvActuels = m_pvMax;
        // le reste des caractéristiques correspondant à la race choisi
        this.m_force = 3 + dice.lancer() + race.getBonusForce();
        this.m_dexterite = 3 + dice.lancer() + race.getBonusDexterite();
        this.m_vitesse = (3 + dice.lancer() + race.getBonusVitesse()) /3;
        this.m_initiative = (3 + dice.lancer() + race.getBonusInitiative()) + diceInit.lancer();
    }

    // Accesseurs pour obtenir les caractéristiques
    public int getPv() 
    {
        return m_pv;
    }
    public int setPv(int pv)
    {
        return m_pv = pv;
    }
    public int getPvMax() {return m_pvMax; }
    public int getPvActuels() { return m_pvActuels; }
    public void setPvActuels(int pv) { this.m_pvActuels = pv; }
    public int getForce()
    {
        return m_force;
    }

    public int getDexterite() 
    {
        return m_dexterite;
    }

    public int getVitesse() 
    {
        return m_vitesse;
    }

    public int getInitiative()
    {
        return m_initiative;
    }
    @Override
    public String toString() {
        return "Caractéristiques {" +
                "\n  PV : " + m_pvActuels + "/" + m_pvMax +
                "\n  Force : " + m_force +
                "\n  Dextérité : " + m_dexterite +
                "\n  Vitesse : " + m_vitesse +
                "\n  Initiative : " + m_initiative +
                "\n}";
    }

}
