package cat.itacademy.proyectoerp.util;

import cat.itacademy.proyectoerp.domain.OrderStatus;

public class StringToOrderStatus {

  public static OrderStatus stringToOrderStatus(String status) {
    OrderStatus orderStatus;
    status = status.toUpperCase();

    if (!status.equals(null)) {
      switch (status) {
        case "UNASSIGNED":
          orderStatus = OrderStatus.UNASSIGNED;
          break;
        case "ASSIGNED":
          orderStatus = OrderStatus.ASSIGNED;
          break;
        case "PENDING_DELIVERY":
          orderStatus = OrderStatus.PENDING_DELIVERY;
          break;
        case "IN_DELIVERY":
          orderStatus = OrderStatus.IN_DELIVERY;
          break;
        case "COMPLETED":
          orderStatus = OrderStatus.COMPLETED;
          break;
        case "CANCELLED":
          orderStatus = OrderStatus.CANCELLED;
          break;
        default:
          return null;
      }
      return orderStatus;
    }
    return null;
  }
}
