package io.jexxa.tutorials.bookstore.domainservice;


import io.jexxa.core.JexxaMain;
import io.jexxa.jexxatest.JexxaTest;
import io.jexxa.tutorials.bookstore.BookStore;
import io.jexxa.tutorials.bookstore.applicationservice.BookStoreService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

class ReferenceLibraryTest
{
    private static final String DRIVEN_ADAPTER = BookStore.class.getPackageName() + ".infrastructure.drivenadapter";
    private static final String DOMAIN_SERVICE = BookStore.class.getPackageName() + ".domainservice";
    private static final String APPLICATION_SERVICE = BookStore.class.getPackageName() + ".applicationservice";

    private static JexxaMain jexxaMain;

    @BeforeAll
    static void initBeforeAll()
    {
        jexxaMain = new JexxaMain(ReferenceLibraryTest.class.getSimpleName());
        jexxaMain.addToInfrastructure(DRIVEN_ADAPTER)
                .addToApplicationCore(APPLICATION_SERVICE)
                .addToApplicationCore(DOMAIN_SERVICE);
    }


    @Test
    void validateAddLatestBooks()
    {
        //Arrange : Get the inbound port that we would like to test
        JexxaTest jexxaTest = new JexxaTest(jexxaMain);
        var objectUnderTest = jexxaTest.getInstanceOfPort(ReferenceLibrary.class);
        var bookStore = jexxaTest.getInstanceOfPort(BookStoreService.class);

        //Act
        objectUnderTest.addLatestBooks();

        //Assert: After adding books via our service, our bookstore must know these books
        assertFalse( bookStore.getBooks().isEmpty() );
    }
}