package personnages.races;

public abstract class Race
{
    // Méthodes abstraites qui doivent être implémentées par chaque race
    public abstract int getBonusForce();
    public abstract int getBonusDexterite();
    public abstract int getBonusVitesse();
    public abstract int getBonusInitiative();
    public abstract String getNom();
    public abstract String toString();
}