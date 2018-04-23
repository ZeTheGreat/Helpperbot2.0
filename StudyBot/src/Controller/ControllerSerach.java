package Controller;

import java.sql.SQLException;

public interface ControllerSerach {
    String getResponse(String cod,long ID) throws SQLException;
}
