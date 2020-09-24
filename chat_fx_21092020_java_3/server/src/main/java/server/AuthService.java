package server;

import java.sql.*;

public class AuthService {
    private static Connection connection; // java sql (Интерфейс)
    private static Statement stmt; // Statement - запросы java sql (Интерфейс)

    static void connection() throws ClassNotFoundException, SQLException { //  вызываем ошибки
        Class.forName ("org.sqlite.JDBC"); // обратится к классу > к драйверу JDBC
        connection = DriverManager.getConnection ("jdbc:sqlite:main.db"); // сделай подключение к базе jdbc > main.db
        stmt = connection.createStatement (); // запрос какие либо к базе данных
    }

    public boolean registration(String login, String pass, String nick) throws SQLException, ClassNotFoundException {
        connection();
        int hash = pass.hashCode();
        String sql = String.format("INSERT INTO main (login, password, nickname) VALUES ('%s', '%d', '%s')", login, hash, nick);

        try {
            boolean rs = stmt.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    static int getIdByLogin(String _login) {
        String idLogin = String.format ("SELECT id FROM main where login = '%s'", _login);
        try {
            ResultSet rs = stmt.executeQuery (idLogin);

            if (rs.next ()) {
                return rs.getInt (1);
            }

        } catch (SQLException e) {
            e.printStackTrace ();
        }
        return 0;
    }

    public static void disconnect() {
        try { // попробуй
            stmt.close (); // закрыть запрос
        } catch (SQLException e) {
            e.printStackTrace ();
        }
        try {
            connection.close (); // закрой соеднинение
        } catch (SQLException e) {
            e.printStackTrace ();
        }

    }

    public String getNicknameByLoginAndPassword(String login, String pass) {
        String sql = String.format("SELECT nickname FROM main where login = '%s' and password = '%s'", login, pass);

        try {
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                String str = rs.getString(1);
                return rs.getString(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
