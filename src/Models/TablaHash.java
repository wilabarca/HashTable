package Models;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TablaHash {
    private int size;
    private LinkedList<Entry>[] table;
    private FuncionHashSuma hashFunction1;
    private FuncionHashDivision hashFunction2;

    public TablaHash(int size) {
        this.size = size;
        this.table = new LinkedList[this.size];
        for (int i = 0; i < this.size; i++) {
            table[i] = new LinkedList<>();
        }
        this.hashFunction1 = new FuncionHashSuma(size);
        this.hashFunction2 = new FuncionHashDivision(size);
    }

    public void put(Object key, Object value, int hashFunction) {
        long startTime = System.nanoTime();

        int hash = (hashFunction == 1) ? hashFunction1.hashSumaAscii(key.toString()) : hashFunction2.hashDivisionAscii(key.toString());
        int index = findIndex(hash);

        LinkedList<Entry> bucket = table[index];
        for (Entry entry : bucket) {
            if (entry.getKey().equals(key)) {
                entry.setValue(value);
                long endTime = System.nanoTime();
                System.out.println("Tiempo de inserción: " + (endTime - startTime) + " nanosegundos.");
                return;
            }
        }

        bucket.add(new Entry(key, value));
        long endTime = System.nanoTime();
        System.out.println("Tiempo de inserción: " + (endTime - startTime) + " nanosegundos.");
    }

    public Object get(Object key, int hashFunction) {
        long startTime = System.nanoTime();

        int hash = (hashFunction == 1) ? hashFunction1.hashSumaAscii(key.toString()) : hashFunction2.hashDivisionAscii(key.toString());
        int index = findIndex(hash);

        LinkedList<Entry> bucket = table[index];
        for (Entry entry : bucket) {
            if (entry.getKey().equals(key)) {
                long endTime = System.nanoTime();
                System.out.println("Tiempo de recuperación: " + (endTime - startTime) + " nanosegundos.");
                return entry.getValue();
            }
        }
        long endTime = System.nanoTime();
        System.out.println("Tiempo de recuperación: " + (endTime - startTime) + " nanosegundos.");
        return null;
    }

    public List<Integer> searchAndGetIndices(Object key) {
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            LinkedList<Entry> bucket = table[i];
            for (Entry entry : bucket) {
                if (entry.getKey().equals(key)) {
                    indices.add(i);
                    break;
                }
            }
        }
        return indices;
    }

    public List<String> getDataAtIndex(int index) {
        List<String> data = new ArrayList<>();
        if (index >= 0 && index < size) {
            LinkedList<Entry> bucket = table[index];
            for (Entry entry : bucket) {
                data.add(entry.getValue().toString());
            }
        } else {
            System.err.println("Índice " + index + " fuera de rango.");
        }
        return data;
    }

    private int findIndex(int hash) {
        return Math.abs(hash % size);
    }

    public void printTable() {
        for (int i = 0; i < size; i++) {
            LinkedList<Entry> bucket = table[i];
            if (!bucket.isEmpty()) {
                System.out.print("Índice " + i + ": ");
                for (Entry entry : bucket) {
                    System.out.print("[" + entry.getKey() + " = " + entry.getValue() + "] ");
                }
                System.out.println();
            }
        }
    }
}
