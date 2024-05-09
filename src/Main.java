import java.util.Random;

public class Main {
    public static void main(String[] args) {
        SplayTree<Integer> splayTree = new SplayTree<>();

        // Создание массива из 10000 случайных целых чисел
        Integer[] randomNumbers = generateRandomNumbers(10000);

        // Переменные для подсчета операций
        long totalInsertOperations = 0;
        long totalSearchOperations = 0;
        long totalDeleteOperations = 0;

        // Переменные для подсчета времени
        long totalInsertTime = 0;
        long totalSearchTime = 0;
        long totalDeleteTime = 0;

        // Поэлементное добавление чисел в структуру, замеряя время работы и количество операций для каждого добавления
        System.out.println("\n  Поэлементное добавление чисел в структуру, замеряя время работы и количество операций для каждого добавления:");
        for (int i = 0; i < randomNumbers.length; i++) {
            long startTime = System.nanoTime();
            splayTree.insert(randomNumbers[i]);
            long endTime = System.nanoTime();
            long elapsedTime = endTime - startTime;
            totalInsertTime += elapsedTime;
            totalInsertOperations += splayTree.getInsertOperations(); // Получаем количество операций для текущего элемента
            System.out.printf("Добавление элемента %d: Время: %d нс, Операции: %d%n", randomNumbers[i], elapsedTime, splayTree.getInsertOperations());
        }

        // Поиск 100 случайных элементов в структуре, замеряя время работы и количество операций для каждого поиска
        System.out.println("\n\n   Поиск 100 случайных элементов в структуре, замеряя время работы и количество операций для каждого поиска:");
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            Integer target = randomNumbers[random.nextInt(randomNumbers.length)];
            long startTime = System.nanoTime();
            splayTree.find(target);
            long endTime = System.nanoTime();
            long elapsedTime = endTime - startTime;
            totalSearchTime += elapsedTime;
            totalSearchOperations += splayTree.getSearchOperations(); // Получаем количество операций для текущего элемента
            System.out.printf("Поиск элемента %d: Время: %d нс, Операции: %d%n", target, elapsedTime, splayTree.getSearchOperations());
        }

        // Удаление 1000 случайных элементов из структуры, замеряя время работы и количество операций для каждого удаления
        System.out.println("\n\n    Удаление 1000 случайных элементов из структуры, замеряя время работы и количество операций для каждого удаления:");
        for (int i = 0; i < 1000; i++) {
            Integer target = randomNumbers[random.nextInt(randomNumbers.length)];
            long startTime = System.nanoTime();
            splayTree.delete(target);
            long endTime = System.nanoTime();
            long elapsedTime = endTime - startTime;
            totalDeleteTime += elapsedTime;
            totalDeleteOperations += splayTree.getDeleteOperations(); // Получаем количество операций для текущего элемента
            System.out.printf("Удаление элемента %d: Время: %d нс, Операции: %d%n", target, elapsedTime, splayTree.getDeleteOperations());
        }

        // Вычисление среднего количества операций
        long averageInsertOperations = totalInsertOperations / randomNumbers.length;
        long averageSearchOperations = totalSearchOperations / 100;
        long averageDeleteOperations = totalDeleteOperations / 1000;

        // Вычисление среднего времени
        long averageInsertTime = totalInsertTime / randomNumbers.length;
        long averageSearchTime = totalSearchTime / 100;
        long averageDeleteTime = totalDeleteTime / 1000;


        // Вывод результатов
        System.out.println("\n\n    Результаты:");
        System.out.println("Среднее количество операций вставки: " + averageInsertOperations);
        System.out.println("Среднее количество операций поиска: " + averageSearchOperations);
        System.out.println("Среднее количество операций удаления: " + averageDeleteOperations);
        System.out.println("Среднее время вставки: " + averageInsertTime + " нс");
        System.out.println("Среднее время поиска: " + averageSearchTime + " нс");
        System.out.println("Среднее время удаления: " + averageDeleteTime + " нс");
    }

    // Метод для генерации массива случайных целых чисел
    private static Integer[] generateRandomNumbers(int size) {
        Random random = new Random();
        Integer[] numbers = new Integer[size];
        for (int i = 0; i < size; i++) {
            numbers[i] = random.nextInt();
        }
        return numbers;
    }
}
