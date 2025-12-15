import java.util.*;

public class Main {

    public static final int REPEATS = 50;   // число повторений

    public static void main(String[] args) {

        int[] sizes = {100000, 250000, 500000, 1000000, 2000000};
        Random rnd = new Random();

        for (int n : sizes) {
            System.out.println("\n=== Размер: " + n + " ===");

            long totalAL = 0;
            long totalLL = 0;
            long totalDQ = 0;

            for (int r = 0; r < REPEATS; r++) {

                // База с дубликатами (чем меньше maxValue, тем больше повторов)
                int[] base = generateArrayWithDuplicates(n, rnd, Math.max(10, n / 5));

                // ArrayList
                ArrayList<Integer> al = toArrayList(base);
                totalAL += makeUniqueArrayList(al);

                // LinkedList
                LinkedList<Integer> ll = toLinkedList(base);
                totalLL += makeUniqueLinkedList(ll);

                // ArrayDeque
                ArrayDeque<Integer> dq = toArrayDeque(base);
                totalDQ += makeUniqueArrayDeque(dq);
            }

            System.out.println("ArrayList среднее:   " + (totalAL / REPEATS) + " ms");
            System.out.println("LinkedList среднее:  " + (totalLL / REPEATS) + " ms");
            System.out.println("ArrayDeque среднее:  " + (totalDQ / REPEATS) + " ms");
        }
    }

    // ===== Генерация массива с дубликатами =====
    // maxValue задаёт диапазон значений [0..maxValue), чем он меньше, тем больше повторов.
    static int[] generateArrayWithDuplicates(int n, Random rnd, int maxValue) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = rnd.nextInt(maxValue);
        return a;
    }

    // ===== Преобразования =====
    static ArrayList<Integer> toArrayList(int[] a) {
        ArrayList<Integer> list = new ArrayList<>(a.length);
        for (int v : a) list.add(v);
        return list;
    }

    static LinkedList<Integer> toLinkedList(int[] a) {
        LinkedList<Integer> list = new LinkedList<>();
        for (int v : a) list.add(v);
        return list;
    }

    static ArrayDeque<Integer> toArrayDeque(int[] a) {
        ArrayDeque<Integer> dq = new ArrayDeque<>(a.length);
        for (int v : a) dq.add(v);
        return dq;
    }

    // ===== Удаление дубликатов (итог — только уникальные, порядок сохранён) =====

    static long makeUniqueArrayList(ArrayList<Integer> list) {
        long start = System.currentTimeMillis();

        HashSet<Integer> seen = new HashSet<>();
        ArrayList<Integer> unique = new ArrayList<>(list.size());

        for (int v : list) {
            if (seen.add(v)) {          // true, если v встретилось впервые
                unique.add(v);
            }
        }

        list.clear();
        list.addAll(unique);

        return System.currentTimeMillis() - start;
    }

    static long makeUniqueLinkedList(LinkedList<Integer> list) {
        long start = System.currentTimeMillis();

        HashSet<Integer> seen = new HashSet<>();
        LinkedList<Integer> unique = new LinkedList<>();

        for (int v : list) {
            if (seen.add(v)) {
                unique.add(v);
            }
        }

        list.clear();
        list.addAll(unique);

        return System.currentTimeMillis() - start;
    }

    static long makeUniqueArrayDeque(ArrayDeque<Integer> dq) {
        long start = System.currentTimeMillis();

        HashSet<Integer> seen = new HashSet<>();
        ArrayDeque<Integer> unique = new ArrayDeque<>(dq.size());

        for (int v : dq) {
            if (seen.add(v)) {
                unique.add(v);
            }
        }

        dq.clear();
        dq.addAll(unique);

        return System.currentTimeMillis() - start;
    }
}
