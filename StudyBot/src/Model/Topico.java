package Model;

public class Topico {
    int ID_mat;
    String nome,desc,link;
    protected Topico(int ID,String nome, String desc, String link){
        this.ID_mat=ID;
        this.nome=nome;
        this.desc=desc;
        this.link=link;
    }

    protected int getID() {
        return ID_mat;
    }

    protected String getNome(){
        return this.nome;
    }
    protected String getDesc(){
        return this.desc;
    }
    protected String getLink(){
        return this.link;
    }
    protected void setID(int newID){
        this.ID_mat=newID;
    }
    protected void setNome(String newnome){
        this.nome=newnome;
    }
    protected void setDesc(String newdesc){
        this.nome=newdesc;
    }
    protected void setLink(String newlink){
        this.nome=newlink;
    }
}
