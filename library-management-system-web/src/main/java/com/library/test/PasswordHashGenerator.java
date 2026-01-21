package com.library.test;

import com.library.util.PasswordUtil;

public class PasswordHashGenerator {

    public static void main(String[] args) {
        String hashed = PasswordUtil.hashPassword("admin123");
        System.out.println(hashed);
        String hashed1 = PasswordUtil.hashPassword("librarian123");
        System.out.println(hashed1);
        String hashed2 = PasswordUtil.hashPassword("member123");
        System.out.println(hashed2);
    }
}
