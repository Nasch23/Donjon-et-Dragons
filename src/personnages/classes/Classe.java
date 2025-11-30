package personnages.classes;
import personnages.Inventaire;
import personnages.equipements.armes.Arme;
import personnages.equipements.armures.Armure;
import java.util.ArrayList;

public class Classe
{
    private String m_nom;
    private int m_pv;
    private ArrayList<Arme> m_armes;
    private ArrayList<Armure> m_armures;

    public Classe(String nom, int pv) //Constructeur de la Classe avec le nom de la classe et ses pv
    {
        this.m_nom = nom;
        this.m_pv = pv;
        this.m_armes = new ArrayList<>();
        this.m_armures = new ArrayList<>();
    }
    //accesseurs Pour avoir la valeur des pv et du nom
    public String getNom(){
        return m_nom;
    }
    public int getPv(){
        return m_pv;
    }
    public Inventaire attribuerEquipements()
    {
        Inventaire inventaire = new Inventaire();
        return inventaire;
    }
    @Override
    public String toString() {
        return "Classe{" +
                "nom='" + m_nom + '\'' +
                ", pv=" + m_pv +
                ", nombre d'armes=" + m_armes.size() +
                ", nombre d'armures=" + m_armures.size() +
                '}';
    }

}
