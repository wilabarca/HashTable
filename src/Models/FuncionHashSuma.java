package Models;

public class FuncionHashSuma {
    private int size;

    public FuncionHashSuma(int size) {
        this.size = size;
    }

    public int hashSumaAscii(String input) {
        int hash = 0;
        for (int i = 0; i < input.length(); i++) {
            hash = 31 * hash + input.charAt(i);
        }
        return Math.abs(hash % size);
    }
}
