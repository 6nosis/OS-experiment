package schedule.resource;

/**
 * @author kaoso
 */
public class Pcb {
    private String name;
    private char status;
    private int[] apply = {0, 0, 0};
    private int[] needMax;
    private int[] occupy;

    public Pcb(String name, char s, int[] n, int[] o) {
        this.name = name;
        this.status = s;
        this.needMax = n;
        this.occupy = o;
    }

    char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    int[] getApply() {
        return apply;
    }

    void setApply(int[] apply) {
        this.apply = apply;
    }

    int[] getNeedMax() {
        return needMax;
    }

    void setNeedMax(int[] needMax) {
        this.needMax = needMax;
    }

    int[] getOccupy() {
        return occupy;
    }

    void setOccupy(int[] occupy) {
        this.occupy = occupy;
    }

    public void updateOccupy(int[] a) {
        for (int i = 0; i < a.length; i++) {
            this.occupy[i] += a[i];
        }
    }

    void release() {
        for (int i = 0; i < occupy.length; i++) {
            occupy[i] = 0;
        }
    }
}
