public class ParkingSystem {

    static String[][] parking = new String[6][9];
    static Scanner sc = new Scanner(System.in);

    static int[] perdorimiVendit = new int[6];
    static int shkeljeTotale = 0;

    public static void main(String[] args) {

        inicializoParkimin();
        ngarkoNgaFile();

        int zgjedhja;
        do {
            System.out.println("\n--- SISTEM INTELIGJENT PARKIMI ---");
            System.out.println("1. Regjistro automjet");
            System.out.println("2. Liro vend parkimi");
            System.out.println("3. Shfaq parkimin");
            System.out.println("4. Statistika");
            System.out.println("0. Dil");

            zgjedhja = sc.nextInt();

            switch (zgjedhja) {
                case 1 -> regjistroAutomjet();
                case 2 -> liroVend();
                case 3 -> shfaqParkimin();
                case 4 -> shfaqStatistika();
            }

        } while (zgjedhja != 0);

        ruajNeFile();
    }

    // Inicializimi i parkimit
    static void inicializoParkimin() {
        for (int i = 0; i < parking.length; i++) {
            parking[i][0] = String.valueOf(i + 1);
            parking[i][1] = (i < 2) ? "VIP" : "NORMAL";
            parking[i][2] = "FREE";
            parking[i][3] = "-";
            parking[i][4] = "0";
            parking[i][5] = "0";
            parking[i][6] = "0";
            parking[i][7] = "0";
            parking[i][8] = "0";
        }
    }

    // Regjistrimi i automjetit
    static void regjistroAutomjet() {
        try {
            System.out.print("Vend parkimi: ");
            int v = sc.nextInt() - 1;

            if (parking[v][2].equals("OCCUPIED"))
                throw new Exception("Vendi eshte i zene!");

            System.out.print("Targa: ");
            parking[v][3] = sc.next();

            parking[v][4] = String.valueOf(System.currentTimeMillis());
            parking[v][2] = "OCCUPIED";
            perdorimiVendit[v]++;

            System.out.println("Automjeti u regjistrua.");

        } catch (Exception e) {
            System.out.println("Gabim input ose vend i pavlefshem!");
        }
    }

    // Lirimi i vendit dhe llogaritjet
    static void liroVend() {
        try {
            System.out.print("Vend parkimi: ");
            int v = sc.nextInt() - 1;

            if (parking[v][2].equals("FREE"))
                throw new Exception("Vendi eshte i lire!");

            long hyrja = Long.parseLong(parking[v][4]);
            long dalja = System.currentTimeMillis();

            long ore = (dalja - hyrja) / (1000 * 60 * 60);

            double cmimi = parking[v][1].equals("VIP") ? ore * 3 : ore * 2;

            parking[v][5] = String.valueOf(dalja);
            parking[v][6] = String.valueOf(ore);
            parking[v][7] = String.valueOf(cmimi);

            if (ore > 3) {
                parking[v][8] = "10";
                shkeljeTotale++;
            } else {
                parking[v][8] = "0";
            }

            parking[v][2] = "FREE";
            parking[v][3] = "-";
            parking[v][4] = "0";

            System.out.println("Koha: " + ore + " ore");
            System.out.println("Cmimi: " + cmimi + " €");
            System.out.println("Gjoba: " + parking[v][8] + " €");

        } catch (Exception e) {
            System.out.println("Gabim gjate lirimit!");
        }
    }

    // Shfaq parkimin
    static void shfaqParkimin() {
        System.out.println("\nNr | Lloji | Status | Targa");
        for (String[] r : parking) {
            System.out.println(r[0] + " | " + r[1] + " | " + r[2] + " | " + r[3]);
        }
    }

    // Statistika sipas kerkeses
    static void shfaqStatistika() {
        int max = 0, vend = 0;

        for (int i = 0; i < perdorimiVendit.length; i++) {
            if (perdorimiVendit[i] > max) {
                max = perdorimiVendit[i];
                vend = i + 1;
            }
        }

        System.out.println("Vend me i perdorur: " + vend);
        System.out.println("Shkelje totale: " + shkeljeTotale);
    }

    // Ruajtja ne file
    static void ruajNeFile() {
        try (PrintWriter pw = new PrintWriter("data.txt")) {
            for (String[] r : parking)
                pw.println(String.join(",", r));
        } catch (IOException e) {
            System.out.println("Problem me file!");
        }
    }

    // Ngarkimi nga file
    static void ngarkoNgaFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {
            String r;
            int i = 0;
            while ((r = br.readLine()) != null && i < parking.length) {
                parking[i] = r.split(",");
                i++;
            }
        } catch (IOException e) {
            System.out.println("File nuk ekziston.");
        }
    }
}
