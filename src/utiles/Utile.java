package utiles;

public class Utile
{
    public static int[] convertirCoordonnees(String coord, int longueurMax) {
        if (coord == null || coord.length() < 2) {
            System.out.println("❌ Format invalide : " + coord);
            return null;
        }

        char lettre = Character.toUpperCase(coord.charAt(0));
        int x = lettre - 'A';
        if (x < 0 || x >= longueurMax) {
            System.out.println("❌ Lettre hors limites (A à " + (char)('A' + longueurMax - 1) + ")");
            return null;
        }

        try {
            int y = Integer.parseInt(coord.substring(1)) - 1;
            if (y < 0) {
                System.out.println("❌ Numéro de ligne invalide : " + coord);
                return null;
            }
            return new int[] { x, y };
        } catch (NumberFormatException e) {
            System.out.println("❌ Numéro de ligne invalide : " + coord);
            return null;
        }
    }


    /**
     * Convertit des coordonnées {x, y} en chaîne comme "C5 ou B2".
     */
}
