package com.MiniProject.eventregistration.entity.Enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum FormFieldType {
    text,
    textarea,
    number,
    email,
    phone,
    dropdown,
    checkbox,
    radio,
    date;
}