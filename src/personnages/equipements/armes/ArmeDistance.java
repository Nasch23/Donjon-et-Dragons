package personnages.equipements.armes;
import mecaniques.Dice;
import personnages.equipements.EffetEquipement;

import java.util.Optional;

public class ArmeDistance extends Arme
{
    public ArmeDistance(String nom, Dice dice, int portee, Optional<EffetEquipement> effetEquipement)
    {
        super(nom, dice, portee, effetEquipement);
    }
    public static Arme getArbaleteLegere()
    {
        return new Arme("arbalète légère", new Dice(1,8), 16, Optional.empty());
    }
    public static Arme getFronde()
    {
        return new Arme("fronde", new Dice(1,4),6, Optional.empty());
    }
    public static Arme getArcCourt()
    {
        return new Arme("arc court", new Dice(1,6),16, Optional.empty());
    }
    @Override
    public String toString() {
        return "ArmeDistance{" + super.toString() + "}";
    }
}

