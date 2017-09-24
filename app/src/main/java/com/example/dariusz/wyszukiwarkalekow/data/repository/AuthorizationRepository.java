package com.example.dariusz.wyszukiwarkalekow.data.repository;

import com.example.dariusz.wyszukiwarkalekow.data.model.OAuthEntity;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;



public class AuthorizationRepository {

    private static final String NAME_AUTH_TOKEN = "auth_token";
    private static final String NAME_REFRESH_TOKEN = "refresh_token";

    private Dao<OAuthEntity,String> mOAuthDAO;

    public AuthorizationRepository(Dao<OAuthEntity, String> mOAuthDAO) {
        this.mOAuthDAO = mOAuthDAO;
    }

    public String getAuthToken(){
        try {
            OAuthEntity tokenEntity = mOAuthDAO.queryForId(NAME_AUTH_TOKEN);
            if(tokenEntity == null){
                return null;
            }else{
                return tokenEntity.getValue();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    public String getRefreshToken(){
        try {
            OAuthEntity tokenEntity = mOAuthDAO.queryForId(NAME_REFRESH_TOKEN);
            if(tokenEntity == null){
                return null;
            }else{
                return tokenEntity.getValue();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    public void saveAuthToken(String token){
        try {
            OAuthEntity tokenEntity = new OAuthEntity(NAME_AUTH_TOKEN,token);
            mOAuthDAO.createOrUpdate(tokenEntity);
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    public void saveRefreshToken(String token){
        try {
            OAuthEntity tokenEntity = new OAuthEntity(NAME_REFRESH_TOKEN,token);
            mOAuthDAO.createOrUpdate(tokenEntity);
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    public void deleteAuthToken() {
        try {
            OAuthEntity tokenEntity = mOAuthDAO.queryForId(NAME_AUTH_TOKEN);
            mOAuthDAO.delete(tokenEntity);
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
}
