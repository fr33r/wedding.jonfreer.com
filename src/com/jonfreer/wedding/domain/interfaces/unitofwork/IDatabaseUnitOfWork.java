package com.jonfreer.wedding.domain.interfaces.unitofwork;

import java.sql.PreparedStatement;

public interface IDatabaseUnitOfWork extends IUnitOfWork {

    PreparedStatement createPreparedStatement(String sql);

}
