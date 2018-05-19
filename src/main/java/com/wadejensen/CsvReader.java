package com.wadejensen;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import static com.wadejensen.ThrowingFunction.unchecked;


public class CsvReader {

    /**
     *
     * @param filepath fully qualified path to CSV file
     * @param type     Java class with schema matching CSV file
     * @param schema   CSV file Jackson schema definition. Ensure the schema is built
     *                 CsvSchema.withHeader to skip the header row.
     * @param <T>      Type to cast each row of the CSV file into
     * @return         Lazy stream of typed objects corresponding to each row
     * @throws IOException
     */
    public static <T> Stream<T> readWithSchema(String filepath, Class<T> type, CsvSchema schema, boolean hasHeader)
            throws IOException {
        // ensure headers are ignored
        CsvSchema normalisedSchema = normaliseSchema(schema);

        ObjectReader reader = new CsvMapper().readerFor(type)
                                             .with(normalisedSchema);

        Path path = FileSystems.getDefault().getPath(filepath);
        // Ignore the first row of the CSV file if it has a header
        Stream<String> rows = hasHeader ? Files.lines(path).skip(1) : Files.lines(path);
        return rows.map(unchecked(reader::readValue));
    }

    private static CsvSchema normaliseSchema(CsvSchema schema) {
        if (schema.usesHeader()) {
            return schema.withoutHeader();
        }
        return schema;
    }
}
