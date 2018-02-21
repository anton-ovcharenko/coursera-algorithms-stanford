package oaa.stanfordAlgorithms.course_4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class TSP_NearestNeighbour {
    static final long START_TS = System.currentTimeMillis();

    public static void main(String[] args) {
        InputData inputData = inputDataFromFile("nn.txt");

        log("Started...");

        City currentCity = inputData.cities[0];
        List<City> remainingCities = new LinkedList<>(Arrays.asList(inputData.cities));
        remainingCities.remove(currentCity);

        //List<Integer> pathList = new ArrayList<>(inputData.amount);
        //pathList.add(currentCity.id);

        double totalDistance = 0;
        while (remainingCities.size() > 0) {
            City nearestCity = null;
            double minDistance = Double.MAX_VALUE;
            for (City city : remainingCities) {
                if (nearestCity == null || distance(currentCity, city) < minDistance) {
                    minDistance = distance(currentCity, city);
                    nearestCity = city;
                }
            }
            totalDistance += minDistance;
            //pathList.add(nearestCity.id);
            remainingCities.remove(nearestCity);
            currentCity = nearestCity;
        }

        totalDistance += distance(currentCity, inputData.cities[0]);
        //System.out.println(Arrays.toString(pathList.toArray()));
        System.out.println(totalDistance);

        log("Finished");
    }

    private static double distance(City c1, City c2) {
        return Math.sqrt(Math.pow(c1.x - c2.x, 2) + Math.pow(c1.y - c2.y, 2));
    }

    private static void log(String msg) {
        System.out.println(String.format("%s [%d ms.]", msg, System.currentTimeMillis() - START_TS));
    }

    static InputData inputDataFromFile(String fileName) {
        try (Scanner scanner = new Scanner(new File("./src/main/resources/" + fileName))) {
            int amount = Integer.parseInt(scanner.nextLine());
            City[] cities = new City[amount];
            cities[0] = null;
            for (int i = 0; i < amount; i++) {
                String s = scanner.nextLine();
                String[] lineArray = s.split(" ");
                int id = Integer.parseInt(lineArray[0]);
                double x = Double.parseDouble(lineArray[1]);
                double y = Double.parseDouble(lineArray[2]);
                cities[i] = new City(id, x, y);
            }
            return new InputData(amount, cities);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    static class City {
        int id;
        double x;
        double y;

        City(int id, double x, double y) {
            this.id = id;
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "City{" +
                    "id='" + id + '\'' +
                    ", x=" + x +
                    ", y=" + y +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            City city = (City) o;

            if (id != city.id) return false;
            if (Double.compare(city.x, x) != 0) return false;
            return Double.compare(city.y, y) == 0;
        }

        @Override
        public int hashCode() {
            int result;
            long temp;
            result = id;
            temp = Double.doubleToLongBits(x);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            temp = Double.doubleToLongBits(y);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            return result;
        }
    }

    static class InputData {
        int amount;
        City[] cities;

        public InputData(int amount, City[] cities) {
            this.amount = amount;
            this.cities = cities;
        }
    }
}
