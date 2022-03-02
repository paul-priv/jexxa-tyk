package io.jexxa.tutorials.bookstorej16.domain.aggregate;

import io.jexxa.addend.applicationcore.Aggregate;
import io.jexxa.addend.applicationcore.AggregateID;
import io.jexxa.tutorials.bookstorej16.domain.businessexception.BookNotInStockException;
import io.jexxa.tutorials.bookstorej16.domain.domainevent.BookSoldOut;
import io.jexxa.tutorials.bookstorej16.domain.valueobject.ISBN13;

import java.util.Optional;

import static io.jexxa.tutorials.bookstorej16.domain.domainevent.BookSoldOut.bookSoldOut;

@Aggregate
public final class Book
{
    private final ISBN13 isbn13;
    private int amountInStock = 0;

    private Book(ISBN13 isbn13)
    {
        this.isbn13 = isbn13;
    }

    @AggregateID
    public ISBN13 getISBN13()
    {
        return isbn13;
    }

    public boolean inStock()
    {
        return amountInStock > 0;
    }

    public int amountInStock()
    {
        return amountInStock;
    }

    public void addToStock( int amount )
    {
        amountInStock += amount;
    }

    public Optional<BookSoldOut> sell() throws BookNotInStockException
    {
        if ( ! inStock() )
        {
            throw new BookNotInStockException();
        }

        amountInStock -= 1;

        if ( ! inStock() )
        {
            return Optional.of(bookSoldOut(isbn13));
        }

        return Optional.empty();
    }

    //AggregateFactory
    public static Book newBook(ISBN13 isbn13)
    {
        return new Book(isbn13);
    }

}
