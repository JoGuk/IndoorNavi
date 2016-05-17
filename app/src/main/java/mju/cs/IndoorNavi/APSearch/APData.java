package mju.cs.IndoorNavi.APSearch;

/**
 * Created by Joguk on 2016-05-10.
 */
public class APData {
    private int ap_no;
    private String ap_name;
    private int x_position;
    private int y_position;
    private double avg_sig;

    public int getAp_no() {
        return ap_no;
    }

    public void setAp_no(int ap_no) {
        this.ap_no = ap_no;
    }

    public String getAp_name() {
        return ap_name;
    }

    public void setAp_name(String ap_name) {
        this.ap_name = ap_name;
    }

    public int getX_position() {
        return x_position;
    }

    public void setX_position(int x_position) {
        this.x_position = x_position;
    }

    public int getY_position() {
        return y_position;
    }

    public void setY_position(int y_position) {
        this.y_position = y_position;
    }

    public double getAvg_sig() {
        return avg_sig;
    }

    public void setAvg_sig(double avg_sig) {
        this.avg_sig = avg_sig;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [ap_no = "+ap_no+", ap_name = "+ap_name+", x_position = "+x_position+", y_position = "+y_position+", avg_sig = "+avg_sig+"]";
    }
}
