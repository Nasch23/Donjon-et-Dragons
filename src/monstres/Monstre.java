package monstres;
import donjons.EntiteCombat;
import donjons.cartes.Case;
import mecaniques.Dice;

public class Monstre implements EntiteCombat
{
    private String m_espece;
    private String m_numero;
    private CaracteristiqueMonstre m_caracteristiqueMonstre;
    private Attaque m_attaque;
    private int m_x;
    private int m_y;
    private String m_symbole;
    public Monstre(String espece, String numero, Attaque attaque, CaracteristiqueMonstre caracteristiqueMonstre)
    {
        this.m_espece = espece;
        this.m_numero = numero;
        this.m_caracteristiqueMonstre = caracteristiqueMonstre;
        this.m_attaque = attaque;

        if (attaque.getPortee() == 0) {
            m_caracteristiqueMonstre.setForce(0);
        } else {
            m_caracteristiqueMonstre.setDexterite(0);
        }
    }
    @Override
    public String getNom() { return m_espece; }
    @Override
    public int getInitiative()
    {
        return m_caracteristiqueMonstre.getInitiative();
    }
    @Override
    public boolean estVivant()
    {
        return m_caracteristiqueMonstre.getPv() > 0;
    }
    @Override
    public int lancerInitiative(Dice dice)
    {
        int resultatDice = dice.lancer();
        int initiativeTotal = resultatDice + m_caracteristiqueMonstre.getInitiative();

        return initiativeTotal;
    }
    @Override
    public void setPosition(int x, int y)
    {
        this.m_x = x;
        this.m_y = y;
    }
    @Override
    public int getX() { return m_x; }
    @Override
    public int getY() { return m_y; }
    @Override
    public void placerDansCase(Case c) 
    {
        c.placerMonstre(this);
    }
    @Override
    public int getVitesse()
    {
        return m_caracteristiqueMonstre.getVitesse();
    }
    @Override
    public int getPortee()
    {
        return m_attaque.getPortee();
    }
    @Override
    public boolean peutAttaquer(EntiteCombat cible) { return cible.estPersonnage(); }
    @Override
    public boolean estMonstre()
    {
        return true;
    }
    @Override
    public boolean estPersonnage()
    {
        return false;
    }
    @Override
    public int getBonusAttaque()
    {
        if (m_attaque.getPortee() == 1)
        {
            return m_caracteristiqueMonstre.getForce();
        } else { return m_caracteristiqueMonstre.getDexterite(); }
    }
    @Override
    public int getClasseArmure()
    {
        return m_caracteristiqueMonstre.getClasseArmure();
    }
    @Override
    public int getDeDegats() { return m_attaque.lancerDegat(); }
    @Override
    public int getPv()
    {
        return m_caracteristiqueMonstre.getPv();
    }
    @Override
    public int getPvMax()
    {
        return m_caracteristiqueMonstre.getPvMax();
    }
    @Override
    public int getForce()
    {
        return m_caracteristiqueMonstre.getForce();
    }
    @Override
    public int getDexterite()
    {
        return m_caracteristiqueMonstre.getDexterite();
    }
    @Override
    public void subirDegats(int degats) 
    {
    int pvRestants = m_caracteristiqueMonstre.getPv() - degats;

    m_caracteristiqueMonstre.setPv(Math.max(0, pvRestants));
    }
    @Override
    public Dice getDesDegats() {
        return m_attaque.getDeDegats();
    }
    @Override
    public String getDescriptionPourOrdreTour() {
        return String.format("(%s, %d/%d)", getType(), getPv(), getPvMax());
    }
    @Override
    public String getSymbolePourAffichage() {
        return m_symbole;
    }
    public void setNumeroType(String numero) {
        this.m_numero = numero;
    }
    public String getNumeroType() {
        return m_numero;
    }
    public String getSymboleUnique() {
        return getSymbole() + m_numero; // par exemple ":)1"
    }
    public void setSymbole(String symbole)
    {
        this.m_symbole = symbole;
    }
    public String getSymbole()
    {
        return m_symbole;
    }
    public Attaque getAttaque()
    {
        return m_attaque;
    }
    public String getType()
    {
        return m_espece;
    }
    public String getNumero() { return m_numero; }
    public static Monstre gobelin(String numero) {
        Dice degats = new Dice(1, 6); // 1d6
        Attaque attaque = new Attaque(1, degats);
        CaracteristiqueMonstre carac = new CaracteristiqueMonstre(12, 3, 10, 4, 8, 11);
        return new Monstre("Gobelin", numero, attaque, carac);
    }

    public static Monstre orc(String numero) {
        Dice degats = new Dice(2, 6); // 2d6
        Attaque attaque = new Attaque(1, degats);
        CaracteristiqueMonstre carac = new CaracteristiqueMonstre(20, 6, 8, 2, 10, 13);
        return new Monstre("Orc", numero, attaque, carac);
    }

    public static Monstre squelette(String numero) {
        Dice degats = new Dice(1, 8); // 1d8
        Attaque attaque = new Attaque(3, degats);
        CaracteristiqueMonstre carac = new CaracteristiqueMonstre(10, 1, 9, 6, 6, 11);
        return new Monstre("Squelette", numero, attaque, carac);
    }
    @Override
    public String toString() {
        return String.format("%s (%s)", getNom(), getType());
    }
}
