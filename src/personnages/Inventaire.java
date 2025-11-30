package personnages;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import personnages.equipements.Equipement;
import personnages.equipements.armes.Arme;
import personnages.equipements.armures.Armure;

public class Inventaire
{
    private ArrayList<Arme> m_armes;
    private ArrayList<Armure> m_armures;
    private Optional<Arme> m_arme_porter;
    private Optional<Armure> m_armure_porter;

    public Inventaire ()
    {
        this.m_arme_porter = Optional.empty();
        this.m_armure_porter = Optional.empty();
        this.m_armes = new ArrayList<>();
        this.m_armures = new ArrayList<>();
    }

    public void ajouterArme(Arme arme)
    {
        this.m_armes.add(arme);
    }
    public void ajouterArmure(Armure armure)
    {
        this.m_armures.add(armure);
    }
    public Optional<Arme> getArmePortee() {
        return m_arme_porter;
    }
    public Optional<Armure> getArmurePortee() {
        return m_armure_porter;
    }
    public void equiperArme(Arme arme) 
    {
    m_arme_porter.ifPresent(m_armes::add);
    m_arme_porter = Optional.of(arme);
    m_armes.remove(arme);
    }
    public void equiperArmure(Armure armure) 
    {
    m_armure_porter.ifPresent(m_armures::add);
    m_armure_porter = Optional.of(armure);
    m_armures.remove(armure);
    }
    public String getInventaire()
    {
        ArrayList<Equipement> tousEquipements = new ArrayList<>();
        tousEquipements.addAll(m_armes);
        tousEquipements.addAll(m_armures);

        return tousEquipements.toString();
    }
    public ArrayList<Equipement> getContenu()
    {
        ArrayList<Equipement> tousEquipements = new ArrayList<>();
        tousEquipements.addAll(m_armes);
        tousEquipements.addAll(m_armures);
        return tousEquipements;
    }
    public void ajouter(Equipement equipement)
    {
        equipement.ajouterAInventaire(this);
    }
    public List<Arme> getArmes() {
        return new ArrayList<>(m_armes);
    }

    public List<Armure> getArmures() {
        return new ArrayList<>(m_armures);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Inventaire ===\n");

        sb.append("Arme équipée : ");
        sb.append(m_arme_porter.map(Arme::getNom).orElse("Aucune")).append("\n");

        sb.append("Armure équipée : ");
        sb.append(m_armure_porter.map(Armure::getNom).orElse("Aucune")).append("\n");

        sb.append("Armes dans l'inventaire :\n");
        if (m_armes.isEmpty()) {
            sb.append("- Aucune\n");
        } else {
            for (Arme arme : m_armes) {
                sb.append("- ").append(arme.getNom()).append("\n");
            }
        }

        sb.append("Armures dans l'inventaire :\n");
        if (m_armures.isEmpty()) {
            sb.append("- Aucune\n");
        } else {
            for (Armure armure : m_armures) {
                sb.append("- ").append(armure.getNom()).append("\n");
            }
        }

        return sb.toString();
    }

}
