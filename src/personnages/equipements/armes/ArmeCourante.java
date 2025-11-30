package personnages.equipements.armes;
import mecaniques.Dice;
import personnages.Caracteristique;
import personnages.equipements.EffetEquipement;

import java.util.Optional;

public class ArmeCourante extends Arme
{

    public ArmeCourante(String nom, Dice dice, int portee, Optional<EffetEquipement> effetEquipement)
    {
        super(nom, dice, portee, effetEquipement);
    }
    public static Arme getBaton()
    {
        return new Arme("Bâton", new Dice(1,6),1, Optional.empty());
    }
    public static Arme getMasse()
    {
        return new Arme("masse d'armes", new Dice(1,6), 1, Optional.of(new EffetEquipement(4,-2)));
    }
    @Override
    public String toString() {
        return "ArmeCourante{" + super.toString() + "}";
    }

}
