package analysis;

public class Pair<T, ST> {
    private T first;
    private ST second;

    public Pair(){

    }

    public Pair(T first, ST second) {
        this.first = first;
        this.second = second;
    }

    public void setFirst(T arg) {
        first = arg;
    }

    public void setSecond(ST arg) {
        second = arg;
    }

    public T getFirst() {
        return first;
    }

    public ST getSecond() {
        return second;
    }
}
