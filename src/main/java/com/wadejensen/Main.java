package com.wadejensen;

import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.csv.CsvSchema.ColumnType;
import java.util.stream.Stream;

import com.wadejensen.Person;

import static com.wadejensen.CsvReader.readWithSchema;

public class Main {
    public static void main (String args[]) throws java.io.IOException {

        String filepath = args[0];

        CsvSchema schema = CsvSchema.builder()
                                    .addColumn("lastname", ColumnType.STRING)
                                    .addColumn("firstname", ColumnType.STRING)
                                    .addColumn("age", ColumnType.NUMBER)
                                    .build()
                                    .withHeader();

        Stream<Person> people = readWithSchema(filepath, Person.class, schema, true);

        people.forEach(System.out::println);
    }
}
