package personnages.equipements;


public class EffetBonusMagique extends EffetEquipement {
    private int m_bonusAttaque;
    private int m_bonusDegats;

    public EffetBonusMagique(int bonusAttaque, int bonusDegats) {
        super(0,0);
        this.m_bonusAttaque = bonusAttaque;
        this.m_bonusDegats = bonusDegats;
    }
    @Override
    public void augmenterBonusAttaque(int valeur) {
        m_bonusAttaque += valeur;
    }

    @Override
    public void augmenterBonusDegats(int valeur) {
        m_bonusDegats += valeur;
    }
    @Override
    public String toString() {
        return "EffetBonusMagique{" +
                "bonusAttaque=" + m_bonusAttaque +
                ", bonusDegats=" + m_bonusDegats +
                '}';
    }

}
