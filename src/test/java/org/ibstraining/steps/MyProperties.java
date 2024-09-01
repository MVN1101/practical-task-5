package org.ibstraining.steps;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class MyProperties {

    private static Properties props = new Properties();

    public static Properties createProperties() {
        FileInputStream input;
        try {
            input = new FileInputStream("src/test/resources/application.properties");
        } catch (
                FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            props.load(input);
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }

        return props;
    }
}
