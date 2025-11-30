package donjons.cartes;

import donjons.affichages.AffichageCarte;
import personnages.equipements.Equipement;
import personnages.equipements.armes.Arme;
import personnages.equipements.armes.ArmeCourante;
import personnages.equipements.armes.ArmeDistance;
import personnages.equipements.armes.ArmeGuerre;
import personnages.equipements.armures.Armure;
import personnages.equipements.armures.ArmureLegere;
import personnages.equipements.armures.ArmureLourde;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CarteDefaut extends Carte implements GenerateurObstacle {

    private Random m_random;

    public CarteDefaut() {
        super(genererLongueur(), genererLargeur());
        this.m_random = new Random();
    }

    private static int genererLongueur() {
        Random rand = new Random();
        return 15 + rand.nextInt(11);
    }

    private static int genererLargeur() {
        Random rand = new Random();
        int largeur;
        do {
            largeur = 15 + rand.nextInt(11);
        } while (largeur == genererLongueur()); // évite carré
        return largeur;
    }

    public void genererObstacles() {
        for (int y = 0; y < getLargeur(); y++) {
            for (int x = 0; x < getLongueur(); x++) {
                if (m_random.nextDouble() < 0.05) {
                    setObstacle(x, y);
                }
            }
        }
    }

    public List<Equipement> genererEquipementsAleatoires(int maxEquipements) {
        Random random = new Random();
        int nbEquipements = random.nextInt(maxEquipements) + 1;

        List<Equipement> equipements = new ArrayList<>();

        for (int i = 0; i < nbEquipements; i++) {
            if (random.nextBoolean()) {
                equipements.add(genererArmeAleatoire());
            } else {
                equipements.add(genererArmureAleatoire());
            }
        }

        return equipements;
    }

    public Equipement genererArmeAleatoire() {
        List<Arme> armes = List.of(
                ArmeCourante.getBaton(),
                ArmeCourante.getMasse(),
                ArmeDistance.getArbaleteLegere(),
                ArmeDistance.getFronde(),
                ArmeDistance.getArcCourt(),
                ArmeGuerre.getEpeeLongue(),
                ArmeGuerre.getRapiere()
        );
        return armes.get(new Random().nextInt(armes.size()));
    }

    public Equipement genererArmureAleatoire() {
        List<Armure> armures = List.of(
                ArmureLegere.getArmureEcaille(),
                ArmureLegere.getArmureDemiPlate(),
                ArmureLourde.getCotteDeMaille(),
                ArmureLourde.getArmureDemiPlate()
        );
        return armures.get(new Random().nextInt(armures.size()));
    }

    public void placerEquipementsAleatoirement(List<Equipement> equipements, GestionPlacement gestion) {
        Random rand = new Random();
        for (Equipement e : equipements) {
            int x, y;
            do {
                x = rand.nextInt(getLongueur());
                y = rand.nextInt(getLargeur());
            } while (!getCase(x, y).estLibre());

            gestion.placerEquipement(x, y, e);
        }
    }

    @Override
    public void genererEtPlacerEquipementsAleatoirement(GestionPlacement gestion) {
        int maxEquipements = (getLongueur() * getLargeur()) / 30;
        List<Equipement> equipements = genererEquipementsAleatoires(maxEquipements);
        placerEquipementsAleatoirement(equipements, gestion);
    }
    public void reinitialiserCarte() {
        // Parcours de toutes les cases pour les vider
        for (int y = 0; y < this.getLargeur(); y++) {
            for (int x = 0; x < this.getLongueur(); x++) {
                Case c = this.getCase(x, y);
                c.viderCase();  // méthode déjà définie dans ta classe Case
            }
        }
    }
    public void afficherCarte() {
        AffichageCarte affichage = new AffichageCarte(this);
        affichage.afficherCarte();
    }
    @Override
    public String toString() {
        return "CarteDefaut [" + getLongueur() + "x" + getLargeur() + "] - Obstacles: " + compterObstacles() +
                ", Equipements placés: " + compterEquipements();
    }
}
