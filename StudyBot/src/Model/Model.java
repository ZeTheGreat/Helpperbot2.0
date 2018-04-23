package Model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Model {

    String red = "\033[31m";
    String yellow = "\033[32m";
    String lyellow = "\033[33m";
    String blue = "\033[34m";
    String purple = "\033[35m";
    String clear = "\033[0;0m";

    // criar model como unica instancia
    private static Model uniqueInstance;

    public Model() {
        new Connector();
        this.Start();
    }

    private ArrayList<Long> userID = new ArrayList<>();
    private ArrayList<Materia> Matérias = new ArrayList<>();
    private ArrayList<Topico> Tópicos = new ArrayList<>();
    private ArrayList<User> Users = new ArrayList<>();

    public static Model getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new Model();
        }
        return uniqueInstance;
    }

    private void registerUser(long id, String name) throws SQLException {
        System.out.println(yellow + "\nI'm in registerUser" + clear);
        String query = "INSERT INTO user (id_us, nome_us)" + " values (?, ?)";
        PreparedStatement ps = Connector.conn.prepareStatement(query);
        ps.setLong(1, id);
        ps.setString(2, name);
        ps.execute();

    }

    private void validateUser(long id, String name) throws SQLException {
        System.out.println(yellow + "\nI'm in validateUser()" + clear + "\n");

        if (userID.contains(id)) {
            System.out.println("\t" + blue + name + " is an user." + clear + "\n");
        } else {
            System.out.println("\t" + red + name + " is not an user, registrating..." + clear + "\n");
            registerUser(id, name);
        }

    }
    private void getUsersId() {
        userID= new ArrayList<>();
        System.out.println(yellow + "\nI'm in getUsersId()" + clear);
        java.sql.Statement stmt;
        ResultSet rs;
        String query = "select id_us from user";

        try {
            stmt = Connector.conn.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                userID.add(rs.getLong("id_us"));
            }

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

    }

    private void callMat() {
        Matérias=new ArrayList<>();
        System.out.println(yellow + "I'm in callMat() " + clear);
        String nome_mat, desc_mat;
        int id_mat;
        long id_us;
        Materia materia;
        java.sql.Statement stmt;
        ResultSet rs;
        String query = "select id_us, id_mat, nome_mat, desc_mat from materia";

        try {
            stmt = Connector.conn.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                id_us = rs.getInt("id_us");
                id_mat = rs.getInt("id_mat");
                nome_mat = rs.getString("nome_mat");
                desc_mat = rs.getString("desc_mat");
                System.out.println(nome_mat);
                materia= new Materia(id_us ,id_mat, nome_mat,desc_mat);
                Matérias.add(materia);

            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
    }
    private void callTop() {
        Tópicos= new ArrayList<>();
        System.out.println(yellow + "I'm in callTop() " + clear);
        int id_mat;
        String nome_top, desc_top,link_top;
        Topico topico;
        java.sql.Statement stmt;
        ResultSet rs;
        String query = "select id_mat, nome_top, desc_top, link_top from topico";

        try {
            stmt = Connector.conn.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                id_mat = rs.getInt("id_mat");
                nome_top = rs.getString("nome_top");
                desc_top = rs.getString("desc_top");
                link_top = rs.getString("link_top");
                topico= new Topico(id_mat,nome_top,desc_top,link_top);
                Tópicos.add(topico);

            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
    }
    private void organize(){
        User usu;
        for(long x :userID){
            usu=new User(x);
            for(Materia materia : Matérias){
                for(Topico topico : Tópicos)
                    if(topico.getID()==materia.ID)
                        materia.add(topico);
                if(materia.ID_us== x)
                    usu.addMateria(materia);
            }
            Users.add(usu);
        }
    }
    private void Start (){
        System.out.println(purple+"Start"+clear);
        getUsersId();
        callMat();
        callTop();
        organize();

    }
    private String export(long ID){
        String resp="Em construção!";

        return resp;
    }
    private String Doimport(long ID,String msg){
        String resp="Em construção!!";

        return resp;
    }
    private String addMatéria(long ID,String msg) throws SQLException {
        String resp="Matéria adicionada com exito!",desc;
        String[] listmsg= msg.split(" ");
        desc=StringMaker(2,listmsg);
        String query = "INSERT INTO Materia (id_us, nome_mat, desc_mat)" + " values (?, ?, ?)";
        PreparedStatement ps = Connector.conn.prepareStatement(query);
        ps.setLong(1, ID);
        ps.setString(2, listmsg[1]);
        ps.setString(3, desc);
        ps.execute();
        Start();
        return resp;
    }
    private String addTópico(long ID,String msg) throws SQLException {
        System.out.println(purple+"addtopico"+clear);
        String resp="Tópico adicionada com exito!",desc;
        String[] listmsg= msg.split(" ");
        int idmat=-1;
        idmat=getIndex(listmsg[1]);
        desc=StringMaker(4,listmsg);
        String query = "INSERT INTO Topico (id_mat, nome_top, desc_top, link_top)" + " values (?, ?, ?, ?)";
        PreparedStatement ps = Connector.conn.prepareStatement(query);
        ps.setInt(1, idmat);
        ps.setString(2, listmsg[2]);
        ps.setString(3, desc);
        ps.setString(4,listmsg[3]);
        if (idmat == -1)
            resp="Erro ao tentar encontrar a matéria, escreva certo o nome dela!";
        if(desc.equals(null))
            resp="Erro descrição nao encontrada!";
        ps.execute();
        Start();
        return resp;
    }
    private String descMatéria(long ID,String msg){
        String resp="As Matérias são: ";
            for (Materia materia:Matérias){
                if(materia.ID_us==ID) {
                    resp += materia.nome;
                    resp += " ";
                }
            }
        return resp;
    }
    private String Study(long ID,String msg){
        String resp="Voce nao possui este tópico.",nometop;
        User user=callUser(ID);
        String[] listmsg= msg.split(" ");
        nometop=StringMaker(1,listmsg);
            for(Topico topico:Tópicos){
                if(user.isMatéria(topico.ID_mat)) {
                    if (topico.nome.equals(nometop)) {
                        resp="";
                        resp += "descrição: " + topico.desc + " link: " + topico.link;
                    }
                }
            }
        return resp;
    }
    private String Normalresponse(long ID,String msg){
        Start();
        String resp="Nao é o nome de nenhuma matéria";
        for (Materia materia:Matérias){
            if(materia.ID_us==ID) {
                if(materia.nome.equals(msg)){
                    resp="Os tópicos são: ";
                    for (int x=0;x<materia.Tópicos.size()/4;x++){
                        resp+=materia.Tópicos.get(x).nome;
                        System.out.println(x);
                        resp+=" ";
                    }
                    break;
                }
            }
        }
        return resp;
    }
    private String StringMaker(int x,String[] msg){
        String resp="";
            for(;x<msg.length;x++) {
                resp += msg[x];
                if(x==msg.length-1);
                else
                    resp += " ";
            }
        return resp;
    }
    private int getIndex(String nomemat){
        int resp=0;
        for(Materia materia:Matérias)
            if(materia.nome.equals(nomemat))
                resp=materia.ID;
        return resp;
    }
    private User callUser(long ID){
        for(User user:Users) {
            if (user.id_us == ID)
                return user;
        }
        return null;
    }
    public String makeResponse(int op,String msg,long id) throws SQLException {
        String resp="";
        switch (op){
            case 0://start
                validateUser(id,msg);
                break;
            case 1://export
                resp=export(id);
                break;
            case 2://import
                resp=Doimport(id,msg);
                break;
            case 3://addmat
                resp=addMatéria(id,msg);
                break;
            case 4://addtop
                resp=addTópico(id,msg);
                break;
            case 5://listmat
                resp=descMatéria(id,msg);
                break;
            case 6://study
                resp=Study(id,msg);
                break;
            case 7://normal
                resp=Normalresponse(id,msg);
                break;
            default:
                resp="Desculpe, aconteceu algo, tente denovo!";
        }
        return resp;
    }


}