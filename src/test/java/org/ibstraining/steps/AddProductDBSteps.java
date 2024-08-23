package org.ibstraining.steps;

import io.cucumber.java.ru.И;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;


public class AddProductDBSteps {

    private ResultSet resultSet;
    private Statement statement;
    private String queryAllFromFood;

//    private int countOfRows;
    private int id;
    private String productName;
    private String type;
    private int isExotic;

    private ArrayList<Object> productToBeAddList;
    private ArrayList<Object> productAddedList;


    @Step
    @Test
    @И("создание подключения к БД")
    public void создание_подключения_к_бд() throws SQLException {

        Connection connection = DriverManager.getConnection(
                "jdbc:h2:tcp://localhost:9092/mem:testdb",
                "user",
                "pass");

        statement = connection.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
    }

    @Step
    @И("получение содержащихся в БД данных")
    public void получение_содержащихся_в_бд_данных() throws SQLException {

        queryAllFromFood = "SELECT FOOD_ID, FOOD_NAME, FOOD_TYPE, FOOD_EXOTIC FROM food";
        resultSet = statement.executeQuery(queryAllFromFood);



    }

    @Step
    @И("создание товара {string}, {string}, экзотический - {string} для добавления в БД")
    public void создание_товара_экзотический_для_добавления_в_бд(String string, String string2, String string3) throws SQLException {
        //        Создание переменной для определения количества строк в таблице
        resultSet.last();
        id = resultSet.getInt(1)+1;
        productName = string;
        type = string2;
        if (string3 == "нет") {
            isExotic = 0;
        } else isExotic =1;

//        Создание коллекции с информацией о добавляемом товаре
//        с целью последующей проверки корректности его добавления в БД
        productToBeAddList = new ArrayList<>();
        productToBeAddList.add(id);
        productToBeAddList.add(productName);
        productToBeAddList.add(type);
        productToBeAddList.add(isExotic);
        System.out.println("Добавляем товар: " + productToBeAddList);
    }

    @Step
    @И("добавление товара в базу данных")
    public void добавление_товара_в_базу_данных() throws SQLException {
        String insert = "INSERT INTO food VALUES (" + id + ",'" + productName + "','" + type + "'," + isExotic + ")";
        statement.executeUpdate(insert);
    }

    @Step
    @И("получение содержащихся в БД данных после добавления товара")
    public void получение_содержащихся_в_бд_данных_после_добавления_товара() throws SQLException {
        resultSet = statement.executeQuery(queryAllFromFood);
        resultSet.last();
        int idDB = resultSet.getInt("FOOD_ID");
        String fruitNameDB = resultSet.getString("FOOD_NAME");
        String typeDB = resultSet.getString("FOOD_TYPE");
        int isExoticDB = resultSet.getInt("FOOD_EXOTIC");

        //        Создание коллекции с информацией о добавленном товаре, полученной из БД
        productAddedList = new ArrayList<>();
        productAddedList.add(idDB);
        productAddedList.add(fruitNameDB);
        productAddedList.add(typeDB);
        productAddedList.add(isExoticDB);
        System.out.println("В БД добавлен товар: " + productAddedList);
    }

    @Step
    @И("проверка корректности добавления товара")
    public void проверка_корректности_добавления_товара() {
        Assertions.assertEquals(productToBeAddList,productAddedList,
                "Товар не был добавлен в БД или добавлен некорректно");
    }











}
