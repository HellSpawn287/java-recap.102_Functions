package functions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.*;

public class Functional_Interface_Main {
    public static void main(String[] args) {

//       >>>>>>>>>>>>>>>>>>>
//        Functional interfaces has exactly one abstract method
//        i.e. functional interface is lambda expression used with interface that contain only one method.

//        Note that instances of functional interfaces can be created with
//        lambda expressions, method references, or constructor references.
//        <<<<<<<<<<<<<<<<<<


        Employee justyna = new Employee("Justyna Łąk", 28);
        Employee robert = new Employee("Robert Prus", 32);
        Employee tim = new Employee("Tim Buchalka", 21);
        Employee snow = new Employee("Snow White", 23);
        Employee robin = new Employee("Robin Hood", 35);
        Employee batman = new Employee("Bruce Wayne", 40);

        List<Employee> employees = new ArrayList<>();
        employees.add(justyna);
        employees.add(robert);
        employees.add(tim);
        employees.add(snow);
        employees.add(robin);
        employees.add(batman);

        employees.forEach(employee -> {
            System.out.println(employee.getName() + ", " + employee.getAge());
        });

        System.out.println("\n==================");
        System.out.println("Employees over 30:");
        for (Employee employee : employees) {
            if (employee.getAge() > 30) {
                System.out.println(employee.getName() + ", " + employee.getAge());
            }
        }

        System.out.println("\n==================");
        System.out.println("#2 \nEmployees over 30:");
        employees.forEach(employee -> {
            if (employee.getAge() > 30) {
                System.out.println(employee.getName() + ", " + employee.getAge());
            }
        });

        System.out.println("\n==================");
        System.out.println("#3 \nEmployees 30 and younger:");
        employees.forEach(employee -> {
            if (employee.getAge() <= 30) {
                System.out.println(employee.getName() + ", " + employee.getAge());
            }
        });

//            Predicate
        System.out.println("\n=====>>Predicate<<=============");

//      Consumer and predicte interfaces are general interfaces. They don't care about the type of parameter
//      being passed to the lambda expression. They are using generic to infer the type.
        printEmpployeesByAge(employees, "Employees over 30:", employee -> employee.getAge() > 30);
        printEmpployeesByAge(employees, "Employees 30 and younger:", employee -> employee.getAge() <= 30);
        printEmpployeesByAge(employees, "Employees younger than 25:", new Predicate<Employee>() {
            @Override
            public boolean test(Employee employee) {
                return employee.getAge() < 25;
            }
        });


//      >>>>>>>>Integer predicate
        System.out.println("\n\nInteger predicate");

//      Each lambda exp is like nested block :
//        { int i:
//            return i > 15;
//        }
        IntPredicate greater_than_15_intPred = i -> i > 15;

        System.out.println(">>>Is greater than 15: " + greater_than_15_intPred.test(20));
        int a = 21;
        System.out.println(">>>Is greater than 15: " + greater_than_15_intPred.test(a + 5));
        int b = 1;
        System.out.println(">>>Is greater than 15: " + greater_than_15_intPred.test(b + 5));
        int c = 15;
        System.out.println(">>>Is greater than 15: " + greater_than_15_intPred.test(c));

//        >>>>Supplier<<<<
        System.out.println("\n\n====>>>Integer supplier<<<====");

        Random random = new Random();
//        for (int i = 0; i < 10; i++) {
//            System.out.println(random.nextInt(1000));
//        }
        Supplier<Integer> randomSupplier = () -> random.nextInt(1000);
        for (int i = 0; i < 10; i++) {
            System.out.println(randomSupplier.get());
        }

        System.out.println("==========================");

        employees.forEach(employee -> {
            String lastName = employee.getName().substring(employee.getName().indexOf(' ') + 1);
            System.out.println("Last Name is: " + lastName);
        });


        Function<Employee, String> getLastName = (Employee employee) ->
                employee.getName().substring(employee.getName().indexOf(' ') + 1);

        Function<Employee, String> getFirstName = (Employee employee) ->
                employee.getName().substring(0, employee.getName().indexOf(' '));

        String lastName = getLastName.apply(employees.get(3));
        System.out.println("\n" + lastName);


        System.out.println("\n========");
        Random random1 = new Random();
        for (Employee employee : employees) {
            if (random1.nextBoolean()) {
                System.out.println(
                        getName(getFirstName, employee)
                );
            } else {
                System.out.println(getName(getLastName, employee));
            }
        }

        System.out.println("\n====>>>Chained Functions<<<<====");
        Function<Employee, String> upperCase = employee -> employee.getName().toUpperCase();
        Function<String, String> concat_FirstName = name -> name.substring(0, name.indexOf(' '));
        Function chainedFunction = upperCase.andThen(concat_FirstName);
        System.out.println(chainedFunction.apply(employees.get(0)));

        BiFunction<String, Employee, String> concatAge = (String name, Employee employee) -> {
           return name.concat(" " + employee.getAge());
        };

        String upperName = upperCase.apply(employees.get(0));
        System.out.println(concatAge.apply(upperName, employees.get(0)));

//        IntUnaryOperator
        System.out.println("\n>>>>>>IntUnaryOperator<<<<<<");

        IntUnaryOperator incBy5 = i -> i + 5;
        System.out.println(incBy5.applyAsInt(10));
        System.out.println("\n>>>>>Chained Consumers<<<<<");
        Consumer<String> c1 = s -> s.toUpperCase();
        Consumer<String> c2 = s -> System.out.println(s);
        c1.andThen(c2).accept("Hello, World! - from chained consumers");
    }

    private static String getName(Function<Employee, String> getName, Employee employee) {
        return getName.apply(employee);
    }

    private static void printEmpployeesByAge(List<Employee> employees,
                                             String ageText,
                                             Predicate<Employee> ageCondition) {
        System.out.println("\n=====From enhanced for loop with predicate=============");
        System.out.println(ageText);
        for (Employee employee : employees) {
            if (ageCondition.test(employee)) {
                System.out.println(employee.getName() + ", " + employee.getAge());
            }
        }
    }
}
