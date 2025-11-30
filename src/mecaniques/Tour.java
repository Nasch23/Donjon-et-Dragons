package mecaniques;

import donjons.EntiteCombat;
import java.util.ArrayList;
import java.util.Collections;

public class Tour {
    private ArrayList<EntiteCombat> m_listeEntiteCombat;
    private int m_indiceActuel;

    // Nouveau constructeur qui prend la liste en paramètre
    public Tour(ArrayList<EntiteCombat> listeEntiteCombat)
    {
        this.m_listeEntiteCombat = listeEntiteCombat;
        this.m_indiceActuel = 0;
    }
    public Tour()
    {
        this.m_listeEntiteCombat = new ArrayList<>();
        this.m_indiceActuel = 0;
    }

    public void trierParInitiative()
    {
        Collections.sort(m_listeEntiteCombat, (e1, e2) -> Integer.compare(e2.getInitiative(), e1.getInitiative()));
    }

    public void ajouterEntite(EntiteCombat entiteCombat)
    {
        m_listeEntiteCombat.add(entiteCombat);
    }

    public ArrayList<EntiteCombat> getListeEntiteCombat()
    {
        return m_listeEntiteCombat;
    }

    @Override
    public String toString() {
        return "Tour{" +
                "nombreEntites=" + m_listeEntiteCombat.size() +
                ", indiceActuel=" + m_indiceActuel +
                '}';
    }

}
