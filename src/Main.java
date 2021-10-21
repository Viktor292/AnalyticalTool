import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main (String args[]) throws IOException {
        Scanner sc = new Scanner(System.in);
        String fileName = "C:\\Users\\murat\\OneDrive\\Desktop\\Analytical tool\\src\\Input";
        Path path = Paths.get(fileName);
        Scanner scanner = new Scanner(path);
        scanner.nextLine();
        List<String[]> waitingTimelines = new ArrayList<String[]>();
        while(scanner.hasNext()) {
            String buffer = scanner.nextLine();
            if(buffer.charAt(0) == 'C') {
                waitingTimelines.add(buffer.split(" "));
            } else if(buffer.charAt(0) == 'D') {
                analyze(waitingTimelines, buffer.split(" "));
            } else {
                System.out.println("Invalid input");
            }
        }

    }

    private static boolean relevantType(String a, String b) {
        if(a.equals("*")) return true;
        if(b.length() < a.length()) return false;
        if(b.substring(0, a.length()).equals(a)) return true;
        return false;
    }

    private static boolean relevantDate(String a, String b) {
        try {
            if (a.indexOf('-') == -1) {
                Date queryLine = new SimpleDateFormat("dd.MM.yyyy").parse(a);
                Date test = new SimpleDateFormat("dd.MM.yyyy").parse(b);
                if(queryLine.equals(test)) return true;
                return false;
            }
            Date begin = new SimpleDateFormat("dd.MM.yyyy").parse(a.split("-")[0]);
            Date end = new SimpleDateFormat("dd.MM.yyyy").parse(a.split("-")[1]);
            Date test = new SimpleDateFormat("dd.MM.yyyy").parse(b);
            return test.after(begin) && test.before(end);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static void analyze(List<String[]> waitingTimelines, String[] query) {
        int result = 0;
        int counter = 0;
        for(int i = 0; i < waitingTimelines.size(); i++) {
            if(relevantType(query[1], waitingTimelines.get(i)[1]) && relevantType(query[2], waitingTimelines.get(i)[2])
                    && query[3].equals(waitingTimelines.get(i)[3]) && relevantDate(query[4], waitingTimelines.get(i)[4]))
            {
                result += Integer.parseInt(waitingTimelines.get(i)[5]);
                counter++;
            }
        }
        if(counter == 0) System.out.println("-");
        else System.out.println(result/counter);
    }
}
