package com.muk.cmpletablefuture;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Employee {

    private int id;

    private int age;

    private String name;

    public Employee(int id, int age) {
        this.id = id;
        this.age = age;
    }

    public Employee(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}

public class CompletableFutureThenCombineAsync {

    private  static Map<Integer, String> nameMap = new HashMap<>();

    static  {
        nameMap.put(1, "Mukul");
        nameMap.put(2, "Alok");
        nameMap.put(3, "Satpal");
        nameMap.put(4, "Sheldon");
        nameMap.put(5, "Raj");
        nameMap.put(6, "Richard");
        nameMap.put(7, "Gillfoyle");
        nameMap.put(8, "Chris");
        nameMap.put(9, "Bruno");
        nameMap.put(10, "Nikhil");
    }

    private static List<Integer> getUserIds() {
        return Arrays.asList(new Integer [] {1,2,3});
    }

    private static List<Integer> getUserIds8() {
        return Arrays.stream(new Integer [] {1,2,3,4,5,6,7,8,9,10}).collect(Collectors.toList());
    }

    private static List<String> getUserNames(List<Integer> ids) {
        Function<Integer, String> getNameFunction = (id) -> nameMap.getOrDefault(id, "Mukul");
        return ids.parallelStream().map(id -> getUserName(id)).collect(Collectors.toList());
    }

    private static String getUserName(Integer id) {
        return nameMap.getOrDefault(id, "Mukul");
    }

    public static void main(String ... args) {

        CompletableFuture<List<Integer>> userIds = CompletableFuture.supplyAsync(() -> getUserIds());
        CompletableFuture<List<String>> names = CompletableFuture.supplyAsync(() -> List.of("Mukul", "Alok", "Nikhil"));
        Map<Integer, String> idToNameMap = new HashMap<>();

        // Using a consumer when both of the completable future tasks are complete.These tasks are independent of each other.
        userIds.thenAcceptBoth(names, (id, name) -> {
            System.out.println("IDS are " + id);
            System.out.println("Names are " + name);
        });

        userIds.thenAcceptBothAsync(names, (id, name) -> {
            System.out.println("IDS are " + id);
            System.out.println("Names are " + name);
        });


        // This is a BIFunction that accepts integer and string and returns an Employee
        BiFunction<Integer, String, Employee> createEmployeeFunction = ((empId, empName) -> {
            return new Employee(empId, empName);
        });

        //Now by using userIds and names Completable Future can we create Employee objects and store ttthem in a list?
        // If I write this it gives me a compilation error. as two completable futures are of different types, one retturns an integer and
        // other returns list of string.

        //userIds.thenCombineAsync(names, createEmployeeFunction);

        // Now if we have another functional interface
        // This is a BIFunction that accepts integer and string and returns an Employee
        BiFunction<List<Integer>, List<Integer>, List<Employee>> createEmployeeFunction2 = ((empIds, ages) -> {
            Map<Integer, Integer> idToAgeMap = IntStream.range(0, Math.min(empIds.size(), ages.size())).boxed()
                    .collect(Collectors.toMap(empIds::get, ages::get));
            List<Employee> employees = new ArrayList<>();
            for (Map.Entry<Integer, Integer> entry: idToAgeMap.entrySet()) {
                employees.add(new Employee(entry.getKey(), entry.getValue()));
            }
            return employees;
        });

        // Same using JAVA 8 foreach for Map interface.
        BiFunction<List<Integer>, List<Integer>, List<Employee>> createEmployeeFunction3 = ((empIds, ages) -> {
            System.out.println(Thread.currentThread().getName());
            Map<Integer, Integer> idToAgeMap = IntStream.range(0, Math.min(empIds.size(), ages.size())).boxed()
                    .collect(Collectors.toMap(empIds::get, ages::get));
            List<Employee> employees = new ArrayList<>();
            idToAgeMap.forEach((id,ag) -> employees.add(new Employee(id, ag)));
            return employees;
        });

        CompletableFuture<List<Integer>> ages = CompletableFuture.supplyAsync(() -> List.of(33, 334, 35));

        CompletableFuture<List<Employee>> employees = userIds.thenCombineAsync(ages, createEmployeeFunction3);
        System.out.println(employees.join());


        try {
            //If we don't put a sleep here main thread exists every time and nothing gets printed.
            // As Compleetable future uses fork join pool.
            Thread.sleep(500);
        }catch (InterruptedException ex ) {
            System.out.println(ex.getLocalizedMessage());
        }

    }
}
