package donjons;

import donjons.cartes.Case;
import mecaniques.Dice;
import monstres.Attaque;

public interface EntiteCombat
{
    String getNom();
    boolean estVivant();
    int getInitiative();
    int lancerInitiative(Dice dice);
    String getSymbolePourAffichage();
    void setPosition(int x, int y);
    int getX();
    int getY();
    void placerDansCase(Case c);
    int getVitesse();
    int getClasseArmure();
    int getPortee();
    boolean peutAttaquer(EntiteCombat cible);
    boolean estMonstre();
    boolean estPersonnage();
    int getBonusAttaque();
    int getDeDegats();
    int getPv();
    int getPvMax();
    int getForce();
    int getDexterite();
    void subirDegats(int degats);
    String getDescriptionPourOrdreTour();
    Dice getDesDegats();

}