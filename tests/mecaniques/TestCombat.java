package mecaniques;

import monstres.Attaque;
import monstres.CaracteristiqueMonstre;
import monstres.Monstre;
import personnages.Personnage;
import donjons.cartes.CarteDefaut;
import personnages.classes.Classe;
import personnages.classes.Guerrier;
import personnages.equipements.armures.Armure;
import personnages.equipements.armures.ArmureLourde;
import personnages.races.Humain;
import personnages.races.Race;

public class TestCombat {
    public static void main(String[] args) {
        // Création d'une carte 10x10 (hypothèse CarteDefaut)
        CarteDefaut carte = new CarteDefaut();

        // Création du monstre avec 100 PV, 100 dégâts
        // Exemple hypothétique, adapte selon la signature exacte
        CaracteristiqueMonstre caracMonstre = new CaracteristiqueMonstre(
                100, // pv
                100, // pv max
                10,  // force
                0,   // dexterite
                10,  // classeArmure
                5    // initiative
        );
        Dice diceDegat = new Dice(100, 100); // 1 dé à 100 faces = toujours entre 1 et 100
        Attaque attaque = new Attaque(100, diceDegat);  // portée 1 + dé dégâts

        Monstre monstre = new Monstre("Troll", "1", attaque, caracMonstre);
        monstre.setPosition(2, 2);

        // Création du personnage avec armure élevée
        Classe classe = new Guerrier();
        Race race = new Humain(); //Race
        Personnage perso = new Personnage("Héros", classe, race);
        Armure cotteDeMaille = ArmureLourde.getCotteDeMaille();
        perso.equiperArmure(cotteDeMaille);
        perso.setPosition(1,0);

        // Création du combat
        Combat combat = new Combat(monstre, perso, carte);

        // Lancement de l'attaque
        int degats = combat.attaquer();

        System.out.println("Dégâts infligés : " + degats);
        System.out.println("PV restant du personnage : " + perso.getPv());

        if (degats == 0) {
            System.out.println("L'attaque a échoué, armure trop élevée !");
        } else {
            System.out.println("L'attaque a réussi !");
        }
    }
}
