package monstres;
import mecaniques.Dice;

public class Attaque
{
    private int m_portee;
    private Dice m_degat;

    public Attaque(int portee, Dice degat)
    {
        this.m_portee = portee;
        this.m_degat = degat;
    }
    public int lancerDegat()
    {
        return m_degat.lancer();
    }
    public int getPortee()
    {
        return m_portee;
    }
    public Dice getDeDegats()
    {
        return m_degat;
    }
    @Override
    public String toString() {
        return "Attaque{" +
                "portee=" + m_portee +
                ", degats=" + m_degat.lancer() + // affiche un lancer de dé pour donner une idée
                '}';
    }
}
