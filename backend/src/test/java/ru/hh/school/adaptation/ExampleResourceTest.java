package ru.hh.school.adaptation;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ExampleResourceTest extends TestBase{

    @Test
    public void DistributorDaoTest() {
        DistributorDao bean = applicationContext.getBean(DistributorDao.class);

        Distributor distributor = bean.addDistributor("test");
        Distributor distributorById = bean.getDistributorById(distributor.getDid());

        assertEquals("test", distributorById.getName());
    }
}
