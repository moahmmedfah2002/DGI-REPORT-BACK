package com.example.back.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import javax.naming.directory.DirContext;
import javax.naming.directory.Attributes;
import javax.naming.NamingException;
import javax.naming.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class ServiceLdap {
    @Autowired
    private  LdapTemplate ldapTemplate;
    @Autowired
    private  LdapContextSource contextSource;



    public boolean authenticate(String username, String password) {
        DirContext ctx = null;
        try {
            String userDn = "CN=" + username + ",CN=Users,DC=cdg,DC=ma";
            ctx = contextSource.getContext(userDn, password);
            Attributes attrs = ctx.getAttributes("");
            System.out.println("User authenticated: " + username);
            System.out.println("User attributes: " + attrs);

            return true;
        } catch (AuthenticationException e) {
            System.err.println("Authentication failed for: " + username);
            return false;
        } catch (NamingException e) {
            System.err.println("LDAP error: " + e.getMessage());
            return false;
        } finally {
            if (ctx != null) {
                try {
                    ctx.close();
                } catch (NamingException e) {
                    System.err.println("Error closing LDAP context");

                }
            }
        }
    }
}