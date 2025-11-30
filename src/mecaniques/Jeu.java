package mecaniques;

import donjons.EntiteCombat;
import personnages.Personnage;
import monstres.Monstre;
import donjons.affichages.AfficheurTour;
import donjons.cartes.Carte;
import donjons.cartes.GestionPlacement;
import personnages.equipements.armes.Arme;
import personnages.equipements.armures.Armure;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Jeu {
    private List<Personnage> m_personnages;
    private List<Monstre> m_monstres;

    private final Carte carte;
    private final GestionPlacement placement;
    private final Tour m_tour;
    private AfficheurTour m_afficheurTour;
    private int m_tourActuel = 1;
    private int m_numeroDonjon;
    private Scanner scanner = new Scanner(System.in);

    public Jeu(List<Personnage> personnages, List<Monstre> monstres, Carte carte, GestionPlacement placement, Tour tour, AfficheurTour afficheurTour, int numeroDonjon) {
        this.m_personnages = personnages;
        this.m_monstres = monstres;
        this.carte = carte;
        this.placement = placement;
        this.m_tour = tour;
        this.m_afficheurTour = afficheurTour;
        this.m_numeroDonjon = numeroDonjon;
    }

    public void setAfficheurTour(AfficheurTour afficheurTour) {
        this.m_afficheurTour = afficheurTour;
    }

    public boolean lancerBoucleDeJeu() {

        while (true) {
            List<EntiteCombat> ordreTour = m_tour.getListeEntiteCombat();
            ordreTour.clear();
            ordreTour.addAll(m_personnages.stream().filter(Personnage::estVivant).collect(Collectors.toList()));
            ordreTour.addAll(m_monstres.stream().filter(Monstre::estVivant).collect(Collectors.toList()));
            m_tour.trierParInitiative();

            for (EntiteCombat entite : ordreTour) {
                if (!entite.estVivant()) continue;

                boolean partieFinie = m_afficheurTour.afficherTour(m_tourActuel, entite, ordreTour, m_numeroDonjon);

                if (partieFinie) {
                    return !personnageMort(); // true = victoire, false = défaite
                }
                boolean partieFinie2 = m_afficheurTour.finAction(scanner, entite, carte);
                if (partieFinie2) {
                    return !personnageMort(); // true = victoire, false = défaite
                }
            }

            m_tourActuel++;
        }
    }

    public boolean personnageMort() {
        return m_personnages.stream().anyMatch(p -> !p.estVivant());
    }

    public boolean tousLesMonstresMorts() {
        return m_monstres.stream().noneMatch(Monstre::estVivant);
    }

    public List getListPersonnage()
    {
        return m_personnages;
    }
    public List getListMonstre()
    {
        return m_monstres;
    }
    @Override
    public String toString() {
        return "Jeu{" +
                "personnages=" + m_personnages.size() +
                ", monstres=" + m_monstres.size() +
                ", numeroDonjon=" + m_numeroDonjon +
                ", tourActuel=" + m_tourActuel +
                '}';
    }

}