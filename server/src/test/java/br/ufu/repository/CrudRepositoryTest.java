package br.ufu.repository;

import org.junit.Assert;
import org.junit.Test;

import static java.math.BigInteger.ONE;

public class CrudRepositoryTest {

    @Test
    public void shouldCreateItem() throws DatabaseException {
        CrudRepository crudRepository = new CrudRepository();
        String item = "ITEM";
        crudRepository.create(ONE, item);
        Assert.assertEquals(item, crudRepository.read(ONE));
    }

    @Test
    public void shouldUpdateItem() throws DatabaseException {
        CrudRepository crudRepository = new CrudRepository();

        String item = "ITEM";
        crudRepository.create(ONE, item);
        Assert.assertEquals(item, crudRepository.read(ONE));

        String itemUpdated = "ITEM-U";
        crudRepository.update(ONE, itemUpdated);
        Assert.assertEquals(itemUpdated, crudRepository.read(ONE));

    }

    @Test(expected = DatabaseException.class)
    public void shouldDeleteItem() throws DatabaseException {
        CrudRepository crudRepository = new CrudRepository();

        String item = "ITEM";
        crudRepository.create(ONE, item);
        Assert.assertEquals(item, crudRepository.read(ONE));

        crudRepository.delete(ONE);
        crudRepository.read(ONE);

    }

}
