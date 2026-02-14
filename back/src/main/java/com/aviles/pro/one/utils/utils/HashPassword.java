/*
 * Copyright 2026 Alex Aviles
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND.
 */

package com.aviles.pro.one.utils.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public final class HashPassword {

    private static final PasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String hashPassword(String password) {
        return encoder.encode(password);
    }

    // metodo para verificar la contraseña
    public static boolean verifyPassword(String password, String hash) {
        return encoder.matches(password, hash);
    }

    // metodo para generar un hash
    public static String generateHash(String password) {
        return hashPassword(password);
    }

}


