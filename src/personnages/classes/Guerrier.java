package personnages.classes;

import personnages.Inventaire;
import personnages.equipements.armes.ArmeDistance;
import personnages.equipements.armes.ArmeGuerre;
import personnages.equipements.armures.ArmureLourde;

public class Guerrier extends Classe
{
    public Guerrier()
    {
        super("Guerrier", 20); //appel du constructeur de la classe "Classe"
    }
    @Override
    public Inventaire attribuerEquipements()
    {
        Inventaire inventaire = new Inventaire();
        inventaire.ajouterArme(ArmeGuerre.getEpeeLongue());
        inventaire.ajouterArme(ArmeDistance.getArbaleteLegere());
        inventaire.ajouterArmure(ArmureLourde.getCotteDeMaille());
        return inventaire;
    }
    @Override
    public String toString()
    {
        return getNom();
    }
}
