package com.augurs.yallagamers.UserInterface.address;

public class DilieveryEstimatesModel {
    String Name ;
    int Symbol;
    public DilieveryEstimatesModel ( String name,int symbol ) {
        Name = name;
        Symbol=symbol;
    }

    public String getName ( ) {
        return Name;
    }

    public int getSymbol ( ) {
        return Symbol;
    }
}
