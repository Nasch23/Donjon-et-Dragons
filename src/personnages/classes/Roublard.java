package personnages.classes;

import personnages.Inventaire;
import personnages.equipements.armes.ArmeDistance;
import personnages.equipements.armes.ArmeGuerre;

public class Roublard extends Classe
{
    public Roublard()
    {
        super("Roublard", 16); //appel du constructeur de la classe "Classe"
    }
    @Override
    public Inventaire attribuerEquipements()
    {
        Inventaire inventaire = new Inventaire();
        inventaire.ajouterArme(ArmeGuerre.getRapiere());
        inventaire.ajouterArme(ArmeDistance.getArcCourt());
        return inventaire;
    }
    @Override
    public String toString()
    {
        return getNom();
    }
}
