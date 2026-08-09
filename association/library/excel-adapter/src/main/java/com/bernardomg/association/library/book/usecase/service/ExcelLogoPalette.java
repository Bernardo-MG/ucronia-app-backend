
package com.bernardomg.association.library.book.usecase.service;

import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFColor;

final class ExcelLogoPalette {

    static final XSSFColor BAND_BACKGROUND   = color(0xD8, 0xEC, 0xE4);

    static final XSSFColor FICTION_TAB       = color(0x79, 0xBD, 0xAA);

    static final XSSFColor GAMES_TAB         = color(0xEB, 0xAE, 0x45);

    static final XSSFColor HEADER_BACKGROUND = color(0xEB, 0xAE, 0x45);

    static final XSSFColor HISTORY_TAB       = color(0x23, 0x92, 0x97);

    static final XSSFColor LENDINGS_TAB      = color(0xE3, 0x49, 0x25);

    static final XSSFColor TITLE_BACKGROUND  = color(0x3F, 0x43, 0x50);

    private static XSSFColor color(final int red, final int green, final int blue) {
        return new XSSFColor(new byte[] { (byte) red, (byte) green, (byte) blue }, new DefaultIndexedColorMap());
    }

    private ExcelLogoPalette() {
        super();
    }

}
