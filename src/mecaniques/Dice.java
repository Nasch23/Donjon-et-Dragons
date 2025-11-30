package mecaniques;
import java.util.Random;


public class Dice
{
    private int m_nb_dice;
    private int m_nb_face;

    public Dice(int nbdice, int nbface)
    {
        this.m_nb_dice = nbdice;
        this.m_nb_face = nbface;
    }

    public int lancer()
    {
        Random random = new Random();
        int total = 0;
        for (int i = 0; i < m_nb_dice; i++)
        {
            int tirage = random.nextInt(m_nb_face) + 1; // entre 1 et m_nb_face inclus
            total += tirage;
        }
        return total;
    }
    @Override
    public String toString() {
        return m_nb_dice + "d" + m_nb_face;
    }
}
