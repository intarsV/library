package com.accenture.library.service.librarySrv;

import com.accenture.library.domain.Book;

import java.util.Map;

public interface LibrarySrv {

    Map<Book, Integer> reservedBookCount();
}
