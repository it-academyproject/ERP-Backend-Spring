package cat.itacademy.proyectoerp.util;

import cat.itacademy.proyectoerp.domain.PaymentMethod;

public class StringToPaymentMethod {

  public static PaymentMethod stringToPaymentMethod(String method) {
	  PaymentMethod paymentMethod;
	  method = method.toUpperCase();

    if (!method.equals(null)) {
      switch (method) {
        case "CREDIT_CARD":
        	paymentMethod = PaymentMethod.CREDIT_CARD;
          break;
        case "PAYPAL":
        	paymentMethod = PaymentMethod.PAYPAL;
          break;
        case "CASH":
        	paymentMethod = PaymentMethod.CASH;
          break;
        default:
          return null;
      }
      return paymentMethod;
    }
    return null;
  }
}
