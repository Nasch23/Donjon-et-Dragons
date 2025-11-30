package personnages.classes;

import personnages.Inventaire;
import personnages.equipements.armes.ArmeCourante;
import personnages.equipements.armes.ArmeDistance;
import personnages.equipements.armures.ArmureLegere;

public class Clerc extends Classe
{
    public Clerc()
    {
        super("Clerc", 16); //appel du constructeur de la classe "Classe"
    }
    @Override
    public Inventaire attribuerEquipements()
    {
        Inventaire inventaire = new Inventaire();
        inventaire.ajouterArme(ArmeCourante.getMasse());
        inventaire.ajouterArme(ArmeDistance.getArbaleteLegere());
        inventaire.ajouterArmure(ArmureLegere.getArmureEcaille());
        return inventaire;
    }
    @Override
    public String toString()
    {
        return getNom();
    }
}
