package personnages.equipements.armes;
import mecaniques.Dice;
import personnages.equipements.EffetEquipement;

import java.util.Optional;

public class ArmeGuerre extends Arme
{
    public ArmeGuerre(String nom, Dice dice, int portee, Optional<EffetEquipement> effetEquipement)
    {
        super(nom, dice, portee, effetEquipement);
    }
    public static Arme getEpeeLongue()
    {
        return new Arme("Épée longue", new Dice(1, 8), 1, Optional.of(new EffetEquipement(4,-2)));
    }

    public static Arme getRapiere()
    {
        return new Arme("rapière", new Dice(1,8),1, Optional.empty());
    }
    public static Arme getEpeeDeuxMains()
    {
        return new Arme("Épée à deux main", new Dice(2,6),1, Optional.of(new EffetEquipement(4,-2)));
    }
    @Override
    public String toString() {
        return "ArmeGuerre{" + super.toString() + "}";
    }
}
