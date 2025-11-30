package personnages.races;

public class Humain extends Race
{
    @Override
    public int getBonusForce()
    {
        return 2;
    }
    @Override
    public int getBonusDexterite()
    {
        return 2;
    }
    @Override
    public int getBonusVitesse(){
        return 2;
    }
    @Override
    public int getBonusInitiative(){
        return 2;
    }
    @Override
    public String getNom()
    {
        return this.toString();
    }
    @Override
    public String toString()
    {
        return "Humain";
    }
}
