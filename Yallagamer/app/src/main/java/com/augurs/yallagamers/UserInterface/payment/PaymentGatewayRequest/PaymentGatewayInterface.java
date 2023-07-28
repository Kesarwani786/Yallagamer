package com.augurs.yallagamers.UserInterface.payment.PaymentGatewayRequest;



import com.augurs.yallagamers.api_module.PaymentGatewayBodyRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PaymentGatewayInterface {
    @POST ("identity/auth/access-token")
    Call<PaymentGatewayAccessTokenResponse> GetAccessToken( @Header ("Authorization") String Authorization,@Header ("Content-Type") String Content);
    @POST ("/transactions/outlets/{OutletId}/orders?")
    Call<CreateOrderResponseDto> CreateOrderApi ( @Header ("Authorization") String Authorization, @Header ("Content-Type") String Content, @Path ("OutletId") String OutletId, @Body PaymentGatewayBodyRequest paymentGatewayBodyRequest );
//
//    @POST ("api/createOrder")
//    Call<CreateOrderResponseDto> createOrderService( @Body CreatePaymentOrderDto paymentOrderDto);
}
