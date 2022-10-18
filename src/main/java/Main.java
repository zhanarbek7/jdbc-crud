import util.ConnectionUtil;

import java.sql.*;

public class Main {
    public static void main(String[] args) {


        // STATEMENT
        // PREPARED_STATEMENT
        // CALLABLE_STATEMENT

        // RESULT_SET

        // execute()
        // executeUpdate()
        // executeQuery()
        getAllData(7);

    }

    public static void createTableWithStatement(){
        String query = """
                create table info(
                id serial primary key,
                name varchar);
                """;
        try (Connection connection = ConnectionUtil.getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(query);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createTableWithPreparedStatement(){
        String query = """
                create table info(
                id serial primary key,
                name varchar);
                """;
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void insertWithStatement(String name){
        String query = """
        insert into info(name)
        values('%s')
        returning *
        """.formatted(name);
        try (Connection connection = ConnectionUtil.getConnection();
             Statement statement = connection.createStatement()) {

            System.out.println(statement.execute(query));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void insertWithPreparedStatement(String name){
        String query = """
                insert into info(name)
                values (?);
                """;
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            System.out.println(preparedStatement.executeUpdate());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateWithPreparedStatement(String name){
        String query = """
                update info
                set name = ? where name = 'Java7'
                """;
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            System.out.println(preparedStatement.executeUpdate());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteWithPreparedStatement(String name){
        String query = """
                delete from info
                where name = ?
                """;
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            System.out.println(preparedStatement.executeUpdate());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void getAllData(Integer num){
        String query = """
                select * from info
                where id > ?;
                """;

        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(query)) {
            prepareStatement.setInt(1, num);
            ResultSet resultSet = prepareStatement.executeQuery();
            while(resultSet.next()){
                System.out.println(resultSet.getInt("id"));
                System.out.println(resultSet.getString("name"));
                System.out.println("---------------");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
