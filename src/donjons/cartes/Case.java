package donjons.cartes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import donjons.EntiteCombat;
import monstres.Monstre;
import personnages.Personnage;
import personnages.equipements.Equipement;
import java.util.Random;

public class Case 
{

    private boolean m_estObstacle;
    private Optional<Personnage> m_personnage;
    private Optional<Monstre> m_monstre;
    private Optional<Equipement> m_equipement;

    private Optional<EntiteCombat> m_entite = Optional.empty();
    private String m_symbole;

    public Case() 
    {
        this.m_estObstacle = false;
        this.m_personnage = Optional.empty();
        this.m_monstre = Optional.empty();
        this.m_equipement = Optional.empty();
        this.m_symbole = " . ";
    }
    public void placerObstacle() 
    {
        m_estObstacle = true;
        m_symbole = " X ";
    }
    public void placerPersonnage(Personnage personnage)
    {
        m_personnage = Optional.of(personnage);
        // 3 premières lettres centrées (pas d'espace en trop)
        String nom = personnage.getNom();
        m_symbole = nom.length() >= 3 ? nom.substring(0, 3) : String.format("%-3s", nom);
    }
    public char getSymboleFixe(Monstre monstre) {
        String type = monstre.getType(); // ou monstre.getNom(), selon ce que tu as
        switch(type.toLowerCase()) {
            case "gobelin": return ')';
            case "orc": return '@';
            case "dragon": return '|';
            case "troll": return '>';
            case "demon": return 'o';
            case "kaiju": return '*';
            case "biju": return '#';
            case "fléau": return '~';
            case "squelette": return '^';
            case "zombie": return '!';
            case "vampire": return '(';
            case "loup-garou": return 'D';
            case "ogre": return '$';
            case "harpie": return '&';
            case "golem": return '=';
            case "arachnide": return ']';
            case "sorcier": return ';';
            case "elementaire": return '@';
            case "fantome": return 'p';
            default: return '?';
        }
    }
    public static void attribuerNumerosMonstres(List<Monstre> monstres) {
        // 1. Compter combien il y a de chaque type
        java.util.Map<String, Integer> compteTotal = new java.util.HashMap<>();
        for (Monstre m : monstres) {
            String type = m.getType().toLowerCase();
            compteTotal.put(type, compteTotal.getOrDefault(type, 0) + 1);
        }

        // 2. Compter les occurrences pour assigner un numéro
        java.util.Map<String, Integer> compteurs = new java.util.HashMap<>();

        for (Monstre m : monstres) {
            String type = m.getType().toLowerCase();

            int total = compteTotal.get(type);
            if (total > 1) {
                // Si plusieurs monstres du même type, on met un numéro
                int numero = compteurs.getOrDefault(type, 0) + 1;
                compteurs.put(type, numero);
                m.setNumeroType(String.valueOf(numero)); // ici on met le numéro sous forme de String
            } else {
                // Sinon on met une chaîne vide
                m.setNumeroType("");
            }
        }
    }




    public void placerMonstre(Monstre monstre)
    {
        m_monstre = Optional.of(monstre);

        // On récupère le symbole de base du monstre (ex: ")")
        char symboleBase = getSymboleFixe(monstre);

        // Récupère le numéro unique de ce monstre parmi son type
        String numeroType = monstre.getNumeroType();

        // Construit le symbole affiché, ex: ":)1"
        String symbole = ":" + symboleBase + numeroType;

        // Stocke ce symbole dans le monstre (optionnel si tu veux)
        monstre.setSymbole(symbole);

        // Affiche ce symbole dans la case, en format 3 caractères
        m_symbole = String.format("%-3s", symbole);
    }

    public void placerEquipement(Equipement equipement) 
    {
        m_equipement = Optional.of(equipement);
        m_symbole = " | ";
    }
    public void viderCase()
    {
        m_estObstacle = false;
        m_personnage = Optional.empty();
        m_monstre = Optional.empty();
        // On ne vide PAS l'équipement pour ne pas le perdre
        // m_equipement = Optional.empty();

        // Mise à jour du symbole selon ce qu'il reste
        if (m_equipement.isPresent()) {
            m_symbole = " | ";
        } else {
            m_symbole = " . ";
        }
    }

    public boolean estLibre()
    {
        return !m_estObstacle && !m_monstre.isPresent() && !m_personnage.isPresent();
    }
    public Optional<Equipement> getEquipement() 
    {
        return m_equipement;
    }

    public Equipement retirerEquipement()
    {
        if (m_equipement.isPresent()) {
            Equipement equipementRetire = m_equipement.get();
            m_equipement = Optional.empty();
            return equipementRetire;
        } else {
            return null;
        }
    }
    public boolean estObstacle()
    {
        return m_estObstacle;
    }
    public boolean contientEquipement() {
        return m_equipement.isPresent();
    }
    public boolean contientPersonnage() { return m_personnage.isPresent();}
    public boolean contientMonstre() { return m_monstre.isPresent();}
    public EntiteCombat getEntiteCombat()
    {
        if (m_personnage.isPresent())
        {
            return m_personnage.get();
        } else if (m_monstre.isPresent())
        {
            return m_monstre.get();
        } else {
            return null;
        }
    }
    public Optional<Personnage> getPersonnage()
    {
        return m_personnage;
    }
    public Optional<Monstre> getMonstre()
    {
        return m_monstre;
    }

    @Override
    public String toString() 
    {
        return m_symbole;
    }
}
