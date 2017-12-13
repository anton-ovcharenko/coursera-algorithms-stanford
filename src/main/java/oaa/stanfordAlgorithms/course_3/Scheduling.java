package oaa.stanfordAlgorithms.course_3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Scheduling {
    // decreasing order of the difference (weight - length)
    static Comparator<Job> differenceJobComparator =
            Comparator.<Job>comparingLong(j -> j.weight - j.length).thenComparing(j -> j.weight).reversed();

    // decreasing order of the ratio (weight/length)
    static Comparator<Job> ratioJobComparator =
            Comparator.<Job>comparingDouble(j -> ((double) j.weight / j.length)).reversed();

    public static void main(String[] args) {
        Job[] array = jobFromFile("jobs.txt");
        Objects.nonNull(array);

        List<Job> sortedJobList = Arrays
                .stream(array)
                .sorted(differenceJobComparator)
                //.sorted(ratioJobComparator)
                .collect(Collectors.toList());

        long result = 0;
        long currentLength = 0;
        for (Job job : sortedJobList) {
            currentLength += job.length;
            result += job.weight * currentLength;
        }

        System.out.println(result);
    }

    static Job[] jobFromFile(String fileName) {
        try (Scanner scanner = new Scanner(new File("./src/main/resources/" + fileName))) {
            int size = scanner.nextInt();
            Job[] array = new Job[size];
            int i = 0;
            while (scanner.hasNextLine()) {
                String row = scanner.nextLine();
                if (!row.isEmpty()) {
                    String[] num = row.split(" ");
                    array[i++] = new Job(Long.parseLong(num[0]), Long.parseLong(num[1]));
                }
            }
            return array;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    static class Job {
        long weight;
        long length;

        Job(long weight, long length) {
            this.weight = weight;
            this.length = length;
        }
    }
}
