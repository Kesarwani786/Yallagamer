package com.augurs.yallagamers.UserInterface.payment;

public class BankOtherOption {
    String Name; int CardNo ;

    public BankOtherOption ( String name, int cardNo ) {
        Name = name;
        CardNo = cardNo;
    }

    public String getName ( ) {
        return Name;
    }

    public int getCardNo ( ) {
        return CardNo;
    }
}
