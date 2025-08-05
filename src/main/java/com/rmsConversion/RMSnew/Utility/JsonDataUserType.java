package com.rmsConversion.RMSnew.Utility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

public class JsonDataUserType implements UserType<Map<String, Object>> {

    private final Gson gson = new GsonBuilder().serializeNulls().create();

    @Override
    public int getSqlType() {
        return Types.LONGVARCHAR;
    }

    @Override
    public Class<Map<String, Object>> returnedClass() {
        return (Class<Map<String, Object>>) (Class<?>) Map.class;
    }

    @Override
    public boolean equals(Map<String, Object> x, Map<String, Object> y) throws HibernateException {
        if (x == y) {
            return true;
        }
        if (x == null || y == null) {
            return false;
        }
        return x.equals(y);
    }

    @Override
    public int hashCode(Map<String, Object> x) throws HibernateException {
        return x != null ? x.hashCode() : 0;
    }

    @Override
    public Map<String, Object> nullSafeGet(ResultSet rs, int position, SharedSessionContractImplementor session, Object owner) throws HibernateException, SQLException {
        String value = rs.getString(position);
        if (value == null) {
            return null;
        }
        try {
            return gson.fromJson(value, Map.class);
        } catch (Exception e) {
            throw new HibernateException("Error deserializing JSON", e);
        }
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Map<String, Object> value, int index, SharedSessionContractImplementor session) throws HibernateException, SQLException {
        if (value == null) {
            st.setNull(index, getSqlType());
        } else {
            try {
                String json = gson.toJson(value);
                st.setString(index, json);
            } catch (Exception e) {
                throw new HibernateException("Error serializing JSON", e);
            }
        }
    }

    @Override
    public Map<String, Object> deepCopy(Map<String, Object> value) throws HibernateException {
        if (value == null) {
            return null;
        }
        return new HashMap<>(value);
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(Map<String, Object> value) throws HibernateException {
        return (Serializable) deepCopy(value);
    }

    @Override
    public Map<String, Object> assemble(Serializable cached, Object owner) throws HibernateException {
        return deepCopy((Map<String, Object>) cached);
    }

    @Override
    public Map<String, Object> replace(Map<String, Object> detached, Map<String, Object> managed, Object owner) throws HibernateException {
        return deepCopy(detached);
    }
} 