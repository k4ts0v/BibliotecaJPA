

import modelo.menus.MenuPrincipal;

import java.sql.SQLException;

public class Main {
    static MenuPrincipal menu = new MenuPrincipal();

    public static void main(String[] args) throws SQLException {
        menu.run();
    }

}
