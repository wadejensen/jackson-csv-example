```
$ git clone https://github.com/wadejensen/jackson-csv-example.git
$ cd jackson-csv-example
$ mvn package
```
```
// person.csv
lastname,firstname,age  
jensen,wade,22  
gene,elizabeth,24  
james,joshua,43  
everest, carmen, 55
```

```
$ java -jar target/jackson-csv-example.jar /path/to/person.csv"

Person{firstname='wade', lastname='jensen', age='22'}
Person{firstname='elizabeth', lastname='gene', age='24'}
Person{firstname='joshua', lastname='james', age='43'}
Person{firstname=' carmen', lastname='everest', age='55'}
```

```
// Person.java (model class matching CSV schema)
package com.wadejensen;  
  
public class Person {  
    private String firstname;  
  
 private String lastname;  
  
 private int age;  
  
  // getters and setters required for Jackson serde library  
  ...
  
    @Override  
  public String toString() {  
        return "Person{" +  
                "firstname='" + firstname + '\'' +  
                ", lastname='" + lastname + '\'' +  
                ", age='" + age + '\'' +  
                '}';  
  }  
}
```