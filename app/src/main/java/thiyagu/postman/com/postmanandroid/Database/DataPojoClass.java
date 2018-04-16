package thiyagu.postman.com.postmanandroid.Database;

/**
 * Created by thiyagu on 3/29/2018.
 */

public class DataPojoClass {

    private String Key;
    private String Value;
    private String Flag;
    private int Id;
   public DataPojoClass(int id,String key,String Value)
   {


this.Key = key;
this.Value = Value;
this.Flag = Flag;
this.Id = id;

   }
    public DataPojoClass(String key,String Value)
    {


        this.Key = key;
        this.Value = Value;
        this.Flag = Flag;

    }
    public void setId(int id) {
        Id = id;
    }

    public int getId() {
        return Id;
    }

    public String getKey() {
        return Key;
    }

    public String getValue() {
        return Value;
    }


    public void setKey(String key) {
        this.Key = key;
    }

    public void setValue(String value) {
        this.Value = value;
    }

    public void setFlag(String flag) {
        Flag = flag;
    }

    public String getFlag() {
        return Flag;
    }
}
