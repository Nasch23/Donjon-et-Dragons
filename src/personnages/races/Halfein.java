package personnages.races;

public class Halfein extends Race
{
    @Override
    public int getBonusForce()
    {
        return 0;
    }
    @Override
    public int getBonusDexterite()
    {
        return 4;
    }
    @Override
    public int getBonusVitesse(){
        return 2;
    }
    @Override
    public int getBonusInitiative(){
        return 0;
    }
    @Override
    public String getNom()
    {
        return this.toString();
    }
    @Override
    public String toString()
    {
        return "Halfein";
    }
}
