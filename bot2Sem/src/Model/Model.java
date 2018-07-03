package Model;

import java.util.ArrayList;

import Controller.ControllerSearch;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;


public class Model {
    String red = "\033[31m";
    String yellow = "\033[32m";
    String lyellow = "\033[33m";
    String blue = "\033[34m";
    String purple = "\033[35m";
    String clear = "\033[0;0m";

    //mongoDB
    private ObjectContainer Topico = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "E:\\bot2Sem\\BD\\Topico.top");
    private ObjectContainer User = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "E:\\bot2Sem\\BD\\User.usu");

    // criar model como unica instancia
    private static Model uniqueInstance;

    private ArrayList<User> userID = new ArrayList<User>();
    private ArrayList<Topico> Tópicos = new ArrayList<>();
    private ArrayList<User> Users = new ArrayList<>();

    public Model() {
        this.Start();
    }

    public static Model getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new Model();
        }
        return uniqueInstance;
    }

    private void registerUser(long ID, String name) {
        System.out.println(yellow + "\nI'm in registerUser()" + clear + "\n");
        User usu = new User(ID, name);
        Topico user = new Topico(ID, 0, 0, "User", "Você", "https://web.telegram.org/#/im?p=@HelperfullBot");
        Tópicos.add(user);
        Topico.store(user);
        User.store(usu);
        Topico.commit();
        User.commit();
        Start();
    }

    private String validateUser(long id, String name) {
        System.out.println(yellow + "\nI'm in validateUser()" + clear + "\n");
        String resp = "Bem vindo ao helpper bot 2.0" + name + ", caso nao saiba como nosso sistema funcione, é so pedir uma maozinha com /help!";
        boolean verify = false;
        for (User user : userID) {
            if (user.getId_us() == id)
                verify = true;
        }

        if (verify) {
            System.out.println("\t" + blue + name + " is an user." + clear + "\n");
        } else {
            System.out.println("\t" + red + name + " is not an user, registrating..." + clear + "\n");
            registerUser(id, name);
        }
        return resp;
    }

    private void getUsersId() {
        userID = new ArrayList<>();
        System.out.println(yellow + "\nI'm in getUsersId()" + clear);
        Query Query = User.query();
        Query.constrain(User.class);
        ObjectSet<User> allUser = Query.execute();

        userID.addAll(allUser);
    }

    private void callTop() {
        Tópicos = new ArrayList<>();
        System.out.println(yellow + "I'm in callTop() " + clear);
        Query Query = Topico.query();
        Query.constrain(Topico.class);
        ObjectSet<Topico> allTopicos = Query.execute();
        Tópicos.addAll(allTopicos);
    }

    private void organize() {
        System.out.println(purple + "I'm in organize()" + clear);
        for (User usu : userID) {
            for (Topico topico : Tópicos)
                if (topico.getIDus() == usu.getId_us())
                    usu.addTopico(topico);
            Users.add(usu);
        }
    }

    private void Start() {
        System.out.println(purple + "Start" + clear);
        getUsersId();
        callTop();
        organize();
        Topico.commit();

    }

    private String export(long ID, String msg, int index) {
        String resp;
        switch (callUser(ID).getPasso()) {
            case 1:
                resp = "De qual tópico voce quer exportar?.";
                callUser(ID).setPasso(2);
                break;
            case 2:
                if (callUser(ID).getTopico(msg).equals(null)) {
                    ControllerSearch.Flags.get(index).setWhere("");
                    callUser(ID).setPasso(1);
                    return "Tópico não achado";
                } else {
                    resp = doExport(callUser(ID).getTopico(msg), "");
                    ControllerSearch.Flags.get(index).setWhere("");
                    callUser(ID).setPasso(1);
                    break;
                }
            default:
                ControllerSearch.Flags.get(index).setWhere("");
                callUser(ID).setPasso(1);
                resp = "Ocorreu algum erro";
        }
        return resp;
    }

    private String doExport(Topico top, String resp) {
        for (Topico topico : top.getTopicos()) {
            doExport(topico, resp);
            resp += getTopico(top.getidPai()).getNome() + " " + top.getNome() + " " + top.getLink() + " " + top.getDesc() + "||";
        }
        return resp;
    }

    private String Doimport(long ID, String msg,int index) {
        String resp;
        switch (callUser(ID).getPasso()) {
            case 1:
                resp = "Coloque o texto gerado pelo import.";
                callUser(ID).setPasso(2);
                break;
            case 2:
                resp = "Texto errado!";
                // User Evoluir www.crescendorapico.com.br é nescessario para a vida crescer||
                String[] MSGS = msg.split("||");
                if (MSGS.length == 0)
                    return "Sem nenhum import!";
                for (String a : MSGS) {
                    Importadd(ID, a);
                }
                ControllerSearch.Flags.get(index).setWhere("");
                callUser(ID).setPasso(1);
                break;
            default:
                resp = "Algo deu de errado";
                ControllerSearch.Flags.get(index).setWhere("");
                callUser(ID).setPasso(1);
        }

        return resp;
    }

    private String StringMaker(int x, String[] msg) {
        String resp = "";
        for (; x < msg.length; x++) {
            resp += msg[x];
            if (x == msg.length - 1) ;
            else
                resp += " ";
        }
        return resp;
    }

    private String Importadd(long ID, String msg) {
        int op = 1;
        System.out.println(purple + "Importadd" + op + clear);
        if (op == 1) {

        }
        String resp = "Tópico adicionada com exito!", desc;
        String[] listmsg = msg.split(" ");
        int idtop = -1;
        int idpaitop = -1;
        idtop = getIndexTop(ID);
        idpaitop = getIndex(listmsg[1]);
        desc = StringMaker(4, listmsg);
        Topico top = new Topico(ID, idpaitop, idtop, listmsg[2], desc, listmsg[3]);
        if (idtop == -1)
            resp = "Erro ao tentar encontrar a matéria, escreva certo o nome dela!";
        if (desc.equals(null))
            resp = "Erro descrição nao encontrada!";
        addtop(top);
        Topico.store(top);
        Topico.commit();
        Start();
        return resp;
    }

    private String addTópico(long ID, String msg, int index) {
        String resp;
        System.out.println(purple + "addtopico" + callUser(ID).getPasso() + clear);
        switch (callUser(ID).getPasso()) {
            case 1:
                resp = "Qual nome do tópico pai? (Caso não tenha use User)";
                callUser(ID).setPasso(2);
                break;
            case 2:
                Topico tops = callUser(ID).getTopico(msg);
                if (tops == null) {
                    resp = "Tópico pai não encontrado";
                    ControllerSearch.Flags.get(index).setWhere("");
                    callUser(ID).setPasso(1);
                } else {
                    System.out.println(callUser(ID).getTamanhoList());
                    System.out.println(callUser(ID).getTopico(msg).getNome());
                    Topico top = new Topico(ID, callUser(ID).getTopico(msg).getID(), callUser(ID).getTamanhoList(), "", "", "");
                    Tópicos.add(top);
                    callUser(ID).addTopico(top);
                    callUser(ID).getTopico(msg).addTopico(top);
                    resp = "Qual é o nome do novo tópico?";
                    callUser(ID).setPasso(3);
                }
                break;
            case 3:
                System.out.println(callUser(ID).getTamanhoList());
                callUser(ID).getTopico(callUser(ID).getTamanhoList() - 1).setNome(msg);
                resp = "Digite a descrição do tópico";
                callUser(ID).setPasso(4);
                break;
            case 4:
                System.out.println(Tópicos.size());
                System.out.println(callUser(ID).getTamanhoList());
                callUser(ID).getTopico(callUser(ID).getTamanhoList() - 1).setDesc(msg);
                resp = "Digite o link de estudo do tópico";
                callUser(ID).setPasso(5);
                break;
            case 5:
                callUser(ID).getTopico(callUser(ID).getTamanhoList() - 1).setLink(msg);
                resp = "Tópico adicionado com exito!";
                ControllerSearch.Flags.get(index).setWhere("");
                callUser(ID).setPasso(1);
                Topico.store(callUser(ID).getTopico(callUser(ID).getTamanhoList() - 1));
                Topico.commit();
                break;
            default:
                resp = "Algo deu de errado";
                ControllerSearch.Flags.get(index).setWhere("");
                callUser(ID).setPasso(1);
        }
        return resp;
    }

    private String Study(long ID, String msg,int index) {
        System.out.println(purple + "\nI'm in Study()" + clear + "\n");
        String resp = "Voce nao possui este tópico.";
        switch (callUser(ID).getPasso()) {
            case 1:
                resp = "Digite o nome do tópico";
                callUser(ID).setPasso(2);
                break;
            case 2:
                for (Topico topico : Tópicos) {
                    if (callUser(ID).isTopico(topico.getID())) {
                        if (topico.getNome().equals(msg)) {
                            resp = "";
                            resp += "Descrição: " + topico.getDesc() + " \nLink: " + topico.getLink();
                        }
                    }
                }
                ControllerSearch.Flags.get(index).setWhere("");
                callUser(ID).setPasso(1);
                break;
            default:
                resp = "Algo deu de errado";
                ControllerSearch.Flags.get(index).setWhere("");
                callUser(ID).setPasso(1);
        }

        return resp;
    }

    private String Normalresponse(long ID, String msg) {
        String resp;
        System.out.println(purple + "\nI'm in NormalResponse()" + clear + "\n");
        if(callUser(ID).getTopico(msg).equals(null))
             resp = "Nao é o nome de nenhuma matéria";
        else{
            resp = "Os tópicos são: ";
            for (int x = 0; x < callUser(ID).getTopico(msg).getTopicos().size(); x++) {
                resp += callUser(ID).getTopico(msg).getTopicos().get(x).getNome();
                System.out.println(x);
                resp += " ";
            }
        }
        return resp;
    }

    private int getIndex(String nometop) {
        int resp = -1;
        for (Topico top : Tópicos)
            if (top.getNome().equals(nometop))
                resp = top.getID();
        return resp;
    }

    private void addtop(Topico top) {
        for (Topico tops : Tópicos) {
            if (tops.getID() == top.getidPai())
                tops.addTopico(top);
        }
    }

    private User callUser(long ID) {
        for (User user : Users) {
            if (user.getId_us() == ID)
                return user;
        }
        return null;
    }

    private int getIndexTop(long id) {
        for (User usu : Users) {
            if (usu.getId_us() == id)
                return usu.getTamanhoList();
        }
        return 1;
    }

    private Topico getTopico(int id) {
        for (Topico top : Tópicos)
            if (top.getID() == id)
                return top;
        return null;
    }

    private void deleteRecursivo(Topico top, long id) {
        for (Topico topico : top.getTopicos()) {
            deleteRecursivo(topico, id);
        }
        callUser(id).removeTopico(top);
        Tópicos.remove(top);
        Topico.delete(top);
    }

    private void concertarindex(Topico top,long ID){
        for(Topico tops :top.getTopicos()){
            concertarindex(tops,ID);
        }
        top.setID(top.getID()-1);
    }

    private String delete(long ID, String msg,int index) {
        String resp;
        switch (callUser(ID).getPasso()) {
            case 1:
                resp = "Digite a operação que voce deseja executar (1 - deletar apenas o tópico, 2 - deletar o tópico e tudo dentro dele)";
                callUser(ID).setPasso(2);
                break;
            case 2:
                resp = "Digite o nome do tópico que você deseja deletar";
                callUser(ID).setPasso(3);
                callUser(ID).setFlag(Integer.parseInt(msg));
                break;
            case 3:
                if (msg.equals("User")) {
                    resp = "Nao pode se deletar o User";
                    ControllerSearch.Flags.get(index).setWhere("");
                    callUser(ID).setPasso(1);
                    break;
                } else {
                    resp = "Matéria não encontrada!";
                    for (Topico top : Tópicos)
                        if (top.getID() == callUser(ID).getTopico(msg).getID()) {
                            if (callUser(ID).getFlag() == 1) {
                                getTopico(top.getidPai()).getTopicos().addAll(top.getTopicos());
                                getTopico(top.getidPai()).getTopicos().remove(top);
                                concertarindex(top,ID);
                                callUser(ID).removeTopico(top);
                                Tópicos.remove(top);
                                Topico.delete(top);
                                Topico.commit();
                                ControllerSearch.Flags.get(index).setWhere("");
                                callUser(ID).setPasso(1);
                                return "Tópico removido com sucesso.";
                            } else if (callUser(ID).getFlag() == 2) {
                                deleteRecursivo(top, ID);
                                Topico.commit();
                                getTopico(top.getidPai()).getTopicos().remove(top);
                                callUser(ID).removeTopico(top);
                                Start();
                                ControllerSearch.Flags.get(index).setWhere("");
                                callUser(ID).setPasso(1);
                                return "Tópico removido com sucesso.";
                            } else {
                                resp = "Operação invalida";
                            }
                        } else {
                            resp = "Não achamos o tópico";
                        }
                    ControllerSearch.Flags.get(index).setWhere("");
                    callUser(ID).setPasso(1);
                    break;
                }
            default:
                resp = "Algo deu de errado";
                ControllerSearch.Flags.get(index).setWhere("");
                callUser(ID).setPasso(1);
        }
        return resp;
    }


    private String alterTopico(long ID, String msg,int index) {
        String resp;

        switch (callUser(ID).getPasso()) {
            case 1:
                resp = "Qual é o nome do tópico que você deseja alterar?";
                callUser(ID).setPasso(2);
                break;
            case 2:
                if (getIndex(msg) == -1) {
                    resp = "Tópico não encontrado";
                    ControllerSearch.Flags.get(index).setWhere("");
                    callUser(ID).setPasso(1);
                } else {
                    callUser(ID).setFlag(callUser(ID).getTopico(msg).getID());
                    Topico.delete(callUser(ID).getFlag());
                    resp = "Qual o nome do tópico pai?";
                    callUser(ID).setPasso(3);
                }
                break;
            case 3:
                if (getIndex(msg) == -1) {
                    ControllerSearch.Flags.get(index).setWhere("");
                    callUser(ID).setPasso(1);
                    resp = "Tópico pai nao encontrado";
                } else {
                    getTopico(callUser(ID).getTopico(callUser(ID).getFlag()).getidPai()).deleteTopico(callUser(ID).getTopico(callUser(ID).getFlag()));
                    getTopico(getIndex(msg)).addTopico(callUser(ID).getTopico(callUser(ID).getFlag()));
                    callUser(ID).getTopico(callUser(ID).getFlag()).setIdPai(getIndex(msg));
                    resp = "Qual é o novo nome do tópico?";
                    callUser(ID).setPasso(4);
                }
                break;
            case 4:
                getTopico(callUser(ID).getFlag()).setNome(msg);
                resp = "Qual é a nova descrição do tópico?";
                callUser(ID).setPasso(5);
                break;
            case 5:
                getTopico(callUser(ID).getFlag()).setDesc(msg);
                resp = "Qual é o novo link de estudo do tópico?";
                callUser(ID).setPasso(6);
                break;
            case 6:
                getTopico(callUser(ID).getFlag()).setLink(msg);
                resp = "Tópico alterado com êxito!";
                ControllerSearch.Flags.get(index).setWhere("");
                callUser(ID).setPasso(1);
                Topico.store(getTopico(callUser(ID).getFlag()));
                Topico.commit();
                break;
            default:
                resp = "Algo deu de errado";
                ControllerSearch.Flags.get(index).setWhere("");
                callUser(ID).setPasso(1);
        }
        return resp;
    }

    private String help(String USN) {
        String resp = "Está com dúvida, " + USN + "? Bem, vamos lá! Aqui no StudyBot você pode:\n" +
                "- Ver os subtópicos de um tópico apenas mandando o nome do tópico.\n" +
                "- Se aprofundar em um tópico apenas maandando /study.\n" +
                "- Para adicionar um tópico deve ser escrito /addtopico.\n" +
                "- Para alterar um tópico escreva /alterartopico.\n" +
                "- Para deletar um tópico escreva /delete.\n" +
                "\nAlém dessas finalidades voce pode:\n" +
                "- Exportar seus conhecimentos para importar em outra sala, usando" +
                " /export para exportar e /import texto para importar.";
        return resp;
    }

    public String makeResponse(int op, String USN, String msg, long id, int index) {
        System.out.println(lyellow + "\nI'm in makeResponse()" + clear + "\n");
        String resp = "";
        if (callUser(id) == null && op != 0)
            resp = "Você previsa se cadastrar com o /start para utilizar nosso bot!";
        else {
            switch (op) {
                case 0://start
                    resp = validateUser(id, USN);
                    break;
                case 1://export
                    resp = export(id, msg, index);
                    break;
                case 2://import
                    resp = Doimport(id, msg,index);
                    break;
                case 3://addtop
                    resp = addTópico(id, msg, index);
                    break;
                case 4://study
                    resp = Study(id, msg, index);
                    break;
                case 5://altertopico
                    resp = alterTopico(id, msg, index);
                    break;
                case 6://delete
                    resp = delete(id, msg, index);
                    break;
                case 7://help
                    resp = help(USN);
                    break;
                case 8://normal
                    resp = Normalresponse(id, msg);
                    break;
                default:
                    resp = "Desculpe, aconteceu algo, tente denovo!";
            }
        }
        return resp;
    }
}