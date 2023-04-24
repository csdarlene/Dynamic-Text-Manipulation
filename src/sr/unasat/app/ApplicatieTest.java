package sr.unasat.app;

import org.junit.Assert;

import java.io.*;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

class ApplicatieTest {

    @org.junit.jupiter.api.Test
    void vervangerTest() {
        assertEquals("Vijf", Applicatie.Vervanger("Vijf", new HashMap<>()));
        assertEquals("Paramaribo", Applicatie.Vervanger("Paramaribo", new HashMap<>()));
        assertEquals("Kat", Applicatie.Vervanger("Kat", new HashMap<>()));
    }

}