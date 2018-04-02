package com.mabang.android.entity;

public enum Enable {
    ENABLE(1, "可用"), UNABLE(0, "不可用");

    private Integer value;
    private String text;

    private Enable(Integer value, String text) {
        this.value = value;
        this.text = text;
        return;
    }

    public Integer getValue() {
        return this.value;
    }

    public String getText() {
        return this.text;
    }

    public static String getTextByValue(Integer value) {
        try {
            for (Enable val : Enable.values()) {
                if (val.value.equals(value))
                    return val.text;
            }
        } catch (Exception e) {
        }
        return "";
    }
}
