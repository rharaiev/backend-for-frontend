package com.course.bff.frontapp.item;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthorItem {
    UUID id;
    String firstName;
    String lastName;
    String address;
    String language;
}
