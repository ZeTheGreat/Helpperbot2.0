package Controller;

public class Flag {
    long id;
    String where;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getWhere() {
        return where;
    }
    public void setWhere(String where) {
        this.where = where;
    }
    protected Flag(long id, String where){
        this.id=id;
        this.where=where;
    }

}
