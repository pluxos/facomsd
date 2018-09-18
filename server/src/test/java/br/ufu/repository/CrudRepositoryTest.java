package br.ufu.repository;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;

import static java.math.BigInteger.*;

public class CrudRepositoryTest {

    @Test
    public void shouldCreateItem() {
        CrudRepository crudRepository = new CrudRepository();
        String item = "ITEM";
        crudRepository.create(item);
        Assert.assertEquals(item, crudRepository.read(ONE));
    }

    @Test
    public void shouldUpdateItem() {
        CrudRepository crudRepository = new CrudRepository();

        String item = "ITEM";
        crudRepository.create(item);
        Assert.assertEquals(item, crudRepository.read(ONE));

        String itemUpdated = "ITEM-U";
        crudRepository.update(ONE, itemUpdated);
        Assert.assertEquals(itemUpdated, crudRepository.read(ONE));

    }

    @Test
    public void shouldDeleteItem() {
        CrudRepository crudRepository = new CrudRepository();

        String item = "ITEM";
        crudRepository.create(item);
        Assert.assertEquals(item, crudRepository.read(ONE));

        crudRepository.delete(ONE);
        Assert.assertNull(crudRepository.read(ONE));

    }

}
