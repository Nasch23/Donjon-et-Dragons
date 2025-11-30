package personnages.races;

public class Nain extends Race
{
    @Override
    public int getBonusForce()
    {
        return 6;
    }
    @Override
    public int getBonusDexterite()
    {
        return 0;
    }
    @Override
    public int getBonusVitesse(){
        return 0;
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
        return "Nain";
    }
}
