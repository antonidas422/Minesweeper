package com.example.minesweaper;

import androidx.room.TypeConverter;

public class ArrayConverter {

    @TypeConverter
    public String fromArray(char[][] arr){
        StringBuilder result = new StringBuilder();
        for (char[] chars : arr) {
            for (char aChar : chars) {
                result.append(aChar);
            }
            result.append(',');
        }
        return result.toString();
    }

    @TypeConverter
    public char[][] toArray(String str){
        int rowLength = str.indexOf(',');
        int count=0;
        System.out.print(str.length()/(rowLength+1));
        System.out.print(rowLength);
        char[][] result = new char[str.length()/(rowLength+1)][rowLength];
        for (int i=0; i<str.length()/(rowLength+1); ++i) {
            for (int j=0; j<rowLength; ++j) {
                result[i][j] = str.charAt(count);
                ++count;
            }
            ++count;
        }
        return result;
    }
}
