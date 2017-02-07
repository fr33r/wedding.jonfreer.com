package com.jonfreer.wedding.infrastructure.repositories;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ GuestRepository_BlackBoxTest.class, GuestRepository_WhiteBoxTest.class })
public class GuestRepositoryTestSuite {

}
