package view;

import controller.CompetitionManager;
import model.Athlete;
import model.AthleteDatabase;
import model.Competition;
import model.InputUtils;

import java.util.*;

public class MenuDrawer {
    public static void mainMenu() {
        Scanner sc = new Scanner(System.in);
        boolean closeProgram = true;
        String menu = """
                Welcome to AthleteSimulator!
                 · 1. Start race
                 · 2. Register new athlete
                 · 3. Delete athlete
                 · 4. Check registered athletes
                 · 5. Check ranking based on elo
                 · 6. Exit
                """;
        while (closeProgram) {
            System.out.println(menu);
            int option = InputUtils.typeIntBounded(1, 6);
            switch (option) {
                case 1:
                    System.out.println();
                    startRaceMenu();
                    System.out.println();
                    break;
                case 2:
                    System.out.println();
                    registerAthleteMenu();
                    System.out.println();
                    break;
                case 3:
                    System.out.println();
                    deleteAthleteMenu();
                    System.out.println();
                    break;
                case 4:
                    System.out.println();
                    if (AthleteDatabase.getInstance().getDatabase().isEmpty()) {
                        System.out.println("Athlete list is empty. Create some ones");
                    }
                    AthleteDatabase.getInstance().getDatabase().values().forEach(System.out::println);
                    System.out.println();
                    break;
                case 5:
                    System.out.println();
                    if (AthleteDatabase.getInstance().getDatabase().isEmpty()) {
                        System.out.println("Athlete list is empty. Create some ones");
                    }
                    List<Athlete> sortedList = new ArrayList<>(AthleteDatabase.getInstance().getDatabase().values());
                    sortedList.sort(Comparator.comparing(Athlete::getElo).reversed());
                    int j=1;
                    for (Athlete a: sortedList) {
                        System.out.println(j + ".- " + a);
                        j++;
                    }
                    System.out.println();
                    break;
                case 6:
                    System.out.println();
                    System.out.println("Bye!");
                    System.out.println();
                    closeProgram = false;
                    break;
            }
        }
    }

    private static void startRaceMenu() {
        AthleteDatabase.getInstance().getDatabase().values().forEach(System.out::println);
        if (AthleteDatabase.getInstance().getDatabase().isEmpty()) {
            System.out.println("There isn't any athlete. Create some ones");
            return;
        }
        System.out.println();
        Athlete a1 = null, a2 = null;
        boolean end1 = false;
        AthleteDatabase currentDb = AthleteDatabase.getInstance();

        do {
            System.out.print("Select the number of the first athlete you want to be racing. ");
            int number1 = InputUtils.typeInt();
            if (currentDb.getDatabase().containsKey(number1)) {
                a1 = currentDb.getAthlete(number1);
                end1 = true;
            }
        } while (!end1);
        System.out.println("First athlete chosen");

        boolean end2 = false;
        do {
            System.out.print("Select the number of the second athlete you want to be racing: ");
            int number2 = InputUtils.typeInt();
            if (currentDb.getDatabase().containsKey(number2)) {
                a2 = currentDb.getAthlete(number2);
                end2 = true;
            }
        } while (!end2);
        System.out.println("Second athlete chosen");
        CompetitionManager race = new CompetitionManager(new Competition(a1, a2));
        race.start();
    }

    private static void registerAthleteMenu() {
        Scanner sc = new Scanner(System.in);
        AthleteDatabase db = AthleteDatabase.getInstance();
        String name = "";
        do {
            name = InputUtils.input("Enter athlete's name (full numbers name isn't valid): ");
        } while (name.chars().allMatch(Character::isDigit));

        String numberString;
        int number = 0;
        boolean isValidFormat;
        boolean isUnique;
        boolean failed = false;

        do {
            if (failed) {
                System.out.print("Used dorsals: ");
                db.getDatabase().values().stream().map(Athlete::getNumber).forEach(d -> System.out.print(d + " "));
                System.out.println();
            }
            numberString = InputUtils.input("Enter athlete's dorsal (it mustn't be the same as others dorsal): ");
            isValidFormat = false;
            isUnique = false;
            try {
                number = Integer.parseInt(numberString);
                isValidFormat = true;
                isUnique = db.getAthlete(number) == null; //
            } catch (NumberFormatException ignored) {failed = false;}

            if (isValidFormat && !isUnique) {
                System.out.println("That dorsal exists");
                failed = true;
            }

        } while (!isValidFormat || !isUnique);

        String probString = "";
        double probabilityToStep = 0.0;
        do {
            probString = InputUtils.input("Enter the probability the athlete has to take a step: ");
        } while (probString.chars().anyMatch(Character::isAlphabetic) || Double.parseDouble(probString) > 100.0
                || Double.parseDouble(probString) < 0.0);
        probabilityToStep = Double.parseDouble(probString);
        Athlete newAthlete = new Athlete(name, number, probabilityToStep);
        db.addAthlete(newAthlete);
        System.out.println();
        System.out.println("Athlete created successfully!");
        System.out.println();
    }

    private static void deleteAthleteMenu() {
        AthleteDatabase db = AthleteDatabase.getInstance();
        if (db.getDatabase().isEmpty()) {
            System.out.println("There isn't any athlete to delete");
            return;
        }
        db.getDatabase().values().forEach(System.out::println);
        System.out.println();
        int dorsalAthleteToRemove = 0;
        boolean end = false;
        do {
            String dorsal = InputUtils.input("Enter the athlete's dorsal you want to remove: ");
            try {
                dorsalAthleteToRemove = Integer.parseInt(dorsal);
            } catch (NumberFormatException ignored) {}
            if (db.getAthlete(dorsalAthleteToRemove) == null) {
                System.out.println("That dorsal does not exist");
            } else {
                db.deleteAthlete(db.getAthlete(dorsalAthleteToRemove));
                end = true;
                System.out.println();
                System.out.println("Athlete deleted");
                System.out.println();
            }
        } while (!end);
    }
}
