package com.augurs.yallagamers.data_models;

public interface OrderFilterInterface {
    void  PeriodId(String PeriodValue);
    void  OrderStatusAdd( String orderStatusList );
    void  OrderStatusRemove( String orderStatusList );
}
