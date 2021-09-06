package com.internet.engineering.IECA5.repository;

import java.sql.SQLException;

public interface IMapper<T, I> {
    T find(I id) throws SQLException;
    void insert(T t) throws SQLException;
    void delete(I id) throws SQLException;
}
