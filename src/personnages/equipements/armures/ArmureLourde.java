package personnages.equipements.armures;

import personnages.equipements.EffetEquipement;

import java.util.Optional;

public class ArmureLourde extends Armure
{
    public ArmureLourde(String nom, int classeArm, Optional<EffetEquipement> effetEquipement)
    {
        super(nom, classeArm, effetEquipement);
    }
    public static Armure getCotteDeMaille()
    {
        return new Armure("cotte de mailles",11, Optional.of(new EffetEquipement(0,-4)));
    }
    public static Armure getArmureDemiPlate()
    {
        return new Armure("harnois",12, Optional.of(new EffetEquipement(0,-4)));
    }
    @Override
    public String toString() {
        return "ArmureLourde{" + super.toString() + "}";
    }
}
