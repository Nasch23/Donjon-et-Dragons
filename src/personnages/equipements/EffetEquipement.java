package personnages.equipements;

public class EffetEquipement
{
    private final int m_bonusForce;
    private final int m_bonusVitesse;

    public EffetEquipement(int force, int vitesse)
    {
        this.m_bonusForce = force;
        this.m_bonusVitesse = vitesse;
    }
    public void augmenterBonusAttaque(int valeur) {
        // rien par défaut, à surcharger dans les sous-classes si besoin
    }

    public void augmenterBonusDegats(int valeur) {
        // rien par défaut, à surcharger dans les sous-classes si besoin
    }
    @Override
    public String toString() {
        return "EffetEquipement{" +
                "bonusForce=" + m_bonusForce +
                ", bonusVitesse=" + m_bonusVitesse +
                '}';
    }
}
