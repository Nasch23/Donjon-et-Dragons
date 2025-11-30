package personnages.equipements.armures;

import personnages.equipements.EffetEquipement;

import java.util.Optional;

public class ArmureLegere extends Armure
{
    public ArmureLegere(String nom, int classeArm, Optional<EffetEquipement> effetEquipement)
    {
        super(nom, classeArm, effetEquipement);
    }
    public static Armure getArmureEcaille()
    {
        return new Armure("armure d'écailles",9, Optional.empty());
    }
    public static Armure getArmureDemiPlate()
    {
        return new Armure("demi-plate",12, Optional.empty());
    }
    @Override
    public String toString() {
        return "ArmureLegere{" + super.toString() + "}";
    }
}
