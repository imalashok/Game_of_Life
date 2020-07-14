import java.util.Scanner;

class StringProcessor extends Thread {

    final Scanner scanner = new Scanner(System.in); // use it to read string from the standard input

    @Override
    public void run() {
        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();

            if (s.equals(s.toUpperCase())) {
                System.out.println("FINISHED");
                return;
            } else {
                System.out.println(s.toUpperCase());
            }
        }
    }
}