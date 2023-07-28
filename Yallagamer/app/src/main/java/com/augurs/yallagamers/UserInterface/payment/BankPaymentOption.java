package com.augurs.yallagamers.UserInterface.payment;

public class BankPaymentOption {
     String Name,Name1; String CardNo ;
    int Symbol;
    public BankPaymentOption ( String name, String cardNo,String name1,int symbol ) {
        Name = name;
        Name1 = name1;
        CardNo = cardNo;
        Symbol=symbol;
    }

    public String getName ( ) {
        return Name;
    }

    public int getSymbol ( ) {
        return Symbol;
    }

    public String getCardNo ( ) {
        return CardNo;
    }
}
