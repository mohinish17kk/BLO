package mgov.gov.in.blohybrid;

import java.util.Comparator;

public class DashboardClass {
    String date,pused_timestamp;
    int workdone,pending_work,pushed_records;

    public DashboardClass(String date, String pused_timestamp, int workdone, int pending_work, int pushed_records) {
        this.date = date;
        this.pused_timestamp = pused_timestamp;
        this.workdone = workdone;
        this.pending_work = pending_work;
        this.pushed_records=pushed_records;
    }

    public DashboardClass() {
        this.date = "-";
        this.pused_timestamp = "-";
        this.workdone = 0;
        this.pending_work = 0;
        this.pushed_records=0;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPused_timestamp() {
        return pused_timestamp;
    }

    public void setPused_timestamp(String pused_timestamp) {
        this.pused_timestamp = pused_timestamp;
    }

    public int getWorkdone() {
        return workdone;
    }

    public void setWorkdone(int workdone) {
        this.workdone = workdone;
    }

    public int getPending_work() {
        return pending_work;
    }

    public void setPending_work(int pending_work) {
        this.pending_work = pending_work;
    }

    public int getPushed_records() {
        return pushed_records;
    }

    public void setPushed_records(int pushed_records) {
        this.pushed_records = pushed_records;
    }

    @Override
    public String toString() {
        return "DashboardClass{" +
                "date='" + date + '\'' +
                ", pused_timestamp='" + pused_timestamp + '\'' +
                ", workdone=" + workdone +
                ", pending_work=" + pending_work +
                ", pushed_records=" + pushed_records +
                '}';
    }

    public static Comparator<DashboardClass> DateComparator
            = new Comparator<DashboardClass>() {

        public int compare(DashboardClass db1, DashboardClass db2) {

            String date1 = db1.getDate().toUpperCase();
            String date2 = db2.getDate().toUpperCase();

            String date1_array[]=date1.split("/");
            String date2_array[]=date2.split("/");

            String final_date1=date1_array[2]+date1_array[1]+date1_array[0];
            String final_date2=date2_array[2]+date2_array[1]+date2_array[0];

            //ascending order
            //return fruitName1.compareTo(fruitName2);

            //descending order
            return final_date2.compareTo(final_date1);
        }

    };
}
