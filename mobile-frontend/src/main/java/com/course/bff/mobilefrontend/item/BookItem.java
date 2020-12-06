package com.course.bff.mobilefrontend.item;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookItem {
    UUID id;

    String title;

    int pages;

    UUID authorId;
}
