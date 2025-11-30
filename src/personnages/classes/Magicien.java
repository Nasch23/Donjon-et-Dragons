package personnages.classes;

import personnages.Inventaire;
import personnages.equipements.armes.ArmeCourante;
import personnages.equipements.armes.ArmeDistance;

public class Magicien extends Classe
{
    public Magicien()
    {
        super("Magicien", 12); //appel du constructeur de la classe "Classe"
    }
    @Override
    public Inventaire attribuerEquipements()
    {
        Inventaire inventaire = new Inventaire();
        inventaire.ajouterArme(ArmeCourante.getBaton());
        inventaire.ajouterArme(ArmeDistance.getArbaleteLegere());
        return inventaire;
    }
    @Override
    public String toString()
    {
        return getNom();
    }
}
