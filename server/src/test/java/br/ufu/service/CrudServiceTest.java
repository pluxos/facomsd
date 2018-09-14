package br.ufu.service;

import br.ufu.exception.InvalidCommandException;
import br.ufu.repository.CrudRepository;
import br.ufu.repository.DatabaseException;
import org.junit.Test;
import org.mockito.Mockito;

import static java.math.BigInteger.ONE;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CrudServiceTest {


    private static final String CREATE = "CREATE %s %s";
    private static final String READ = "READ %s";
    private static final String DELETE = "DELETE %s";
    private static final String UPDATE = "UPDATE %s %s";

    @Test
    public void shouldCreateItem() throws InvalidCommandException, DatabaseException {

        CrudRepository crudRepository = new CrudRepository();
        CrudRepository crudRepositorySpy = Mockito.spy(crudRepository);

        CrudService crudService = new CrudService(crudRepositorySpy);

        String item = "ITEM";

        String result = crudService.execute(String.format(CREATE, ONE, item));

        assertEquals(item, result);

        verify(crudRepositorySpy, times(1)).create(ONE, item);
        verify(crudRepositorySpy, never()).read(any());
        verify(crudRepositorySpy, never()).update(any(), any());
        verify(crudRepositorySpy, never()).delete(any());

    }

    @Test
    public void shouldReadItem() throws InvalidCommandException, DatabaseException {

        CrudRepository crudRepository = new CrudRepository();
        CrudRepository crudRepositorySpy = Mockito.spy(crudRepository);

        CrudService crudService = new CrudService(crudRepositorySpy);

        String item = "ITEM";

        crudService.execute(String.format(CREATE, ONE, item));
        String result = crudService.execute(String.format(READ, ONE));

        assertEquals(item, result);

        verify(crudRepositorySpy, times(1)).create(ONE, item);
        verify(crudRepositorySpy, times(1)).read(ONE);
        verify(crudRepositorySpy, never()).update(any(), any());
        verify(crudRepositorySpy, never()).delete(any());

    }

    @Test
    public void shouldDeleteItem() throws InvalidCommandException, DatabaseException {

        CrudRepository crudRepository = new CrudRepository();
        CrudRepository crudRepositorySpy = Mockito.spy(crudRepository);

        CrudService crudService = new CrudService(crudRepositorySpy);

        String item = "ITEM";

        crudService.execute(String.format(CREATE, ONE, item));
        crudService.execute(String.format(DELETE, ONE));

        verify(crudRepositorySpy, times(1)).create(any(), any());
        verify(crudRepositorySpy, never()).read(any());
        verify(crudRepositorySpy, never()).update(any(), any());
        verify(crudRepositorySpy, times(1)).delete(ONE);


    }

    @Test
    public void shouldUpdateItem() throws InvalidCommandException, DatabaseException {

        CrudRepository crudRepository = new CrudRepository();
        CrudRepository crudRepositorySpy = Mockito.spy(crudRepository);

        CrudService crudService = new CrudService(crudRepositorySpy);

        String item = "ITEM";
        String itemUpdated = "ITEM-UPDATED";

        crudService.execute(String.format(CREATE, ONE, item));
        crudService.execute(String.format(UPDATE, ONE, itemUpdated));

        String result = crudService.execute(String.format(READ, ONE));

        assertEquals(itemUpdated, result);
        verify(crudRepositorySpy, times(1)).create(any(), any());
        verify(crudRepositorySpy, times(1)).read(any());
        verify(crudRepositorySpy, times(1)).update(ONE, itemUpdated);
        verify(crudRepositorySpy, never()).delete(any());


    }

    @Test(expected = InvalidCommandException.class)
    public void shouldThrowExceptionOnInvalidOption() throws InvalidCommandException, DatabaseException {

        CrudRepository crudRepository = new CrudRepository();
        CrudRepository crudRepositorySpy = Mockito.spy(crudRepository);

        CrudService crudService = new CrudService(crudRepositorySpy);
        crudService.execute("INVALID COMMAND");

        verify(crudRepositorySpy, never()).create(any(), any());
        verify(crudRepositorySpy, never()).read(any());
        verify(crudRepositorySpy, never()).update(any(), any());
        verify(crudRepositorySpy, never()).delete(any());

    }


}
