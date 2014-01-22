package com.example.dao;


import com.example.model.User;
import com.example.util.GeneralDaoTest;
import org.junit.Before;
import org.junit.Test;
import org.unitils.UnitilsJUnit4;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.orm.jpa.JpaUnitils;
import org.unitils.orm.jpa.annotation.JpaEntityManagerFactory;
import org.unitils.reflectionassert.ReflectionAssert;

import java.util.Arrays;
import java.util.List;

@JpaEntityManagerFactory(persistenceUnit = "userDb", configFile = "META-INF/persistence-test.xml")
@DataSet
public class UserDAOTest extends GeneralDaoTest {

    @TestedObject
    private UserDAO userDao;

    @Before
    @Override
    public void init() {
        userDao = new UserDAO();
        JpaUnitils.injectEntityManagerInto(userDao);
    }

    @Test
    public void testFindByName() {
        User result = userDao.findByName("doe", "john");
        ReflectionAssert.assertPropertyLenientEquals("username", "jdoe", result);
    }

    @Test
    public void testFindByMinimalAge() {
        List<User> result = userDao.findByMinimalAge(18);
        ReflectionAssert.assertPropertyLenientEquals("firstName", Arrays.asList("jack"), result);
    }
}