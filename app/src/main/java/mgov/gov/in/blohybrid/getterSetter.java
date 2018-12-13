package mgov.gov.in.blohybrid;

/**
 * Created by kgaurav on 07-Nov-17.
 */

public class getterSetter
{
    private String  HOF_GENDER;
    private String HOF_SLNO;
    private String HOF_RLN_GENDER;
    private String HOF_RLN_SLNO;
    private String S_SLNO;
    private String DM_SLNO;
    private String DU_SLNO;
    private String VERSION_CODE;
    private String STRINGBUFFER;
    private String STRINGBUFFERCOM;

   /* public getterSetter(String HOF_GENDER, String HOF_SLNO, String HOF_RLN_GENDER,String HOF_RLN_SLNO,String S_SLNO,
                        String DM_SLNO,String DU_SLNO) {
        this.HOF_GENDER = HOF_GENDER;
        this.HOF_SLNO = HOF_SLNO;
        this.HOF_RLN_GENDER = HOF_RLN_GENDER;
        this.HOF_RLN_SLNO = HOF_RLN_SLNO;
        this.S_SLNO = S_SLNO;
        this.DM_SLNO = DM_SLNO;
        this.DU_SLNO = DU_SLNO;
    }

    public getterSetter() {

    }*/


    public String getHOF_GENDER() {
        return HOF_GENDER;
    }

    public void setHOF_GENDER(String HOF_GENDER) {
        this.HOF_GENDER = HOF_GENDER;
    }

    public String getSTRINGBUFFER(){
        return STRINGBUFFER;
    }

    public void setSTRINGBUFFER(String STRINGBUFFER){
        this.STRINGBUFFER = STRINGBUFFER;
    }

    public String getSTRINGBUFFERCOM(){
        return STRINGBUFFERCOM;
    }

    public void setSTRINGBUFFERCOM(String STRINGBUFFERCOM){
        this.STRINGBUFFERCOM = STRINGBUFFERCOM;
    }


    public String getHOF_SLNO() {
        return HOF_SLNO;
    }

    public void setHOF_SLNO(String HOF_SLNO) {
        this.HOF_SLNO = HOF_SLNO;
    }

    public String getHOF_RLN_GENDER() {
        return HOF_RLN_GENDER;
    }

    public void setHOF_RLN_GENDER(String HOF_RLN_GENDER) {
        this.HOF_RLN_GENDER = HOF_RLN_GENDER;
    }

    public String getHOF_RLN_SLNO() {
        return HOF_RLN_SLNO;
    }

    public void setHOF_RLN_SLNO(String HOF_RLN_SLNO) {
        this.HOF_RLN_SLNO = HOF_RLN_SLNO;
    }

    public String getS_SLNO() {
        return S_SLNO;
    }

    public void setS_SLNO(String s_SLNO) {
        S_SLNO = s_SLNO;
    }

    public String getDM_SLNO() {
        return DM_SLNO;
    }

    public void setDM_SLNO(String DM_SLNO) {
        this.DM_SLNO = DM_SLNO;
    }

    public String getDU_SLNO() {
        return DU_SLNO;
    }

    public void setDU_SLNO(String DU_SLNO) {
        this.DU_SLNO = DU_SLNO;
    }

    public String getVERSION_CODE() {
        return VERSION_CODE;
    }

    public void setVERSION_CODE(String VERSION_CODE) {
        this.VERSION_CODE = VERSION_CODE;
    }

    public static final getterSetter gs=new getterSetter();


}
