package personnages.equipements;

import java.util.Optional;
import personnages.Inventaire;
import personnages.Personnage;

public interface Equipement 
{
    String getNom();
    void setPosition(int x, int y);
    int getX();
    int getY();
    void ajouterAInventaire(Inventaire inventaire);
    void equipeSurCePersonnage(Personnage p);
    boolean estArme();
    boolean estArmure();
}
