package personnages.equipements.armures;

import personnages.Personnage;
import personnages.classes.Guerrier;
import personnages.races.Elfe;
import personnages.equipements.armures.Armure;
import personnages.equipements.armures.ArmureLourde;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ArmureTest {

    @Test
    void testArmureEquipement() {
        Personnage perso = new Personnage("Chevalier", new Guerrier(), new Elfe());
        Armure armure = ArmureLourde.getCotteDeMaille();

        armure.ajouterAInventaire(perso.getInventaire());
        armure.equipeSurCePersonnage(perso);

        Armure portee = perso.getInventaire().getArmurePortee().orElseThrow();

        assertEquals("cotte de mailles", portee.getNom());
        assertEquals(11, portee.getClasseArmure());
    }
}