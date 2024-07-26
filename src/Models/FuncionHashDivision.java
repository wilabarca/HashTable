package Models;

public class FuncionHashDivision {
    private int size;

    public FuncionHashDivision(int size) {
        this.size = size;
    }

    public int hashDivisionAscii(String key) {
        int hashValue = 0;
        for (char c : key.toCharArray()) {
            hashValue += c;
        }
        return Math.abs(hashValue % size);
    }
}
