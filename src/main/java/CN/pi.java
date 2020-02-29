package CN;

public class pi implements java.io.Serializable {
    //Info
    private int rank;
    private int tp;
    private int gp;
    private boolean verified;

    public pi() {
        this.rank = 0;
        this.gp = 0;
        this.tp = 0;
        this.verified = false;
    }

    //modify
    public void changeRank(int rank) {
        this.rank = rank;
    }
    public void verify() {
        this.verified = true;
    }
    public void addTP(int ltp) {
        this.tp = this.tp + ltp;
    }
    public void addGP(){
        this.gp++;
    }
    //get
    public int getTP() {
        return tp;
    }
    public int getGP() {
        return gp;
    }
    public int getRank() {
        return rank;
    }
    public boolean getVerified() {return verified;}
}
